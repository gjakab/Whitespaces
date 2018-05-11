package hu.elte.whitespaces.tester;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class TesterApplication implements WebMvcConfigurer {

    @Autowired
    private HandlerInterceptor authInterceptor;

    @Override
    public final void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor);
    }

    public static void main(final String[] args) {
        SpringApplication.run(TesterApplication.class, args);
    }

    @Override
    public final void addCorsMappings(final CorsRegistry registry) {
        registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "DELETE");
    }
}
