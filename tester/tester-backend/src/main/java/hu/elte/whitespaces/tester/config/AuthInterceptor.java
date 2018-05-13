package hu.elte.whitespaces.tester.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import hu.elte.whitespaces.tester.model.User;
import hu.elte.whitespaces.tester.service.UserService;

@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {

    private static final int UNAUTHORIZED = 401;

    @Autowired
    private UserService userService;

    @Override
    public final boolean preHandle(final HttpServletRequest request, final HttpServletResponse response,
            final Object handler) {
        if (handler instanceof HandlerMethod) {
            List<User.Role> routeRoles = getRoles((HandlerMethod) handler);
            User user = userService.getCurrentUser();

            if (routeRoles.isEmpty() || routeRoles.contains(User.Role.GUEST)) {
                return true;
            }

            if (user != null && routeRoles.contains(user.getRole())) {
                return true;
            }
            response.setStatus(UNAUTHORIZED);
            return false;
        }
        return true;
    }

    private List<User.Role> getRoles(final HandlerMethod handler) {
        Role role = handler.getMethodAnnotation(Role.class);
        if (role == null) {
            return Collections.emptyList();
        }
        return Arrays.asList(role.value());
    }
}
