package hu.elte.whitespaces.tester.controller;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hu.elte.whitespaces.tester.config.Role;
import hu.elte.whitespaces.tester.model.User;
import hu.elte.whitespaces.tester.service.UserService;

/**
 * Controller class for users
 * 
 * @author WhiteSpaces
 *
 */
@CrossOrigin(origins = { "http://localhost:4200" }) // This is need for development
@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final String USER_ID = "/id";
    private static final String EMAIL = "/email";
    private static final String USER_LIST = "/all";
    private static final String LOGIN = "/login";
    private static final String LOGOUT = "/logout";

    private UserService service;

    /**
     * Constructor
     * 
     * @param service Service for User
     */
    @Autowired
    UserController(UserService service) {
        this.service = service;
    }

    /**
     * List all registered users
     * 
     * @return List of User
     */
    @GetMapping(USER_LIST)
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.ok(service.getAllUser());
    }

    /**
     * Get registered user by ID
     * 
     * @param id User ID to be looked up
     * @return User or NOT_FOUND if not found
     */
    @GetMapping(USER_ID)
    public ResponseEntity<User> getUserById(@RequestParam(value = "value", required = true) Integer id) {
        User response = service.getUserById(id);
        if (response != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(NOT_FOUND).build();
        }
    }

    /**
     * Get user by registered e-mail address
     * 
     * @param email E-mail to be looked up
     * @return User or NOT_FOUND if not found
     */
    @GetMapping(EMAIL)
    public ResponseEntity<User> getUserByEmail(@RequestParam(value = "value", required = true) String email) {
        User response = service.getUserByEmail(email);
        if (response != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(NOT_FOUND).build();
        }
    }

    /**
     * Create new User
     * 
     * @param user User to be created
     * @return Created User or NOT_FOUND if creation failed
     */
    @PostMapping("")
    public ResponseEntity<User> create(@RequestBody User user) {
        User saved = service.register(user);
        if (saved != null) {
            return ResponseEntity.ok(saved);
        } else {
            return ResponseEntity.status(NOT_FOUND).build();
        }
    }

    /**
     * Log existing user in
     * 
     * @param user User to be logged in
     * @return User or NOT_FOUND if not registered
     */
    @PostMapping(LOGIN)
    public ResponseEntity<User> login(@RequestBody User user) {
        User loggedInUser = service.login(user);
        if (loggedInUser != null) {
            return ResponseEntity.ok(loggedInUser);
        } else {
            return ResponseEntity.status(NOT_FOUND).build();
        }
    }

    /**
     * Log logged-in user out
     * 
     * @return Empty instance of User after logout
     */
    @Role({ User.Role.TEACHER, User.Role.STUDENT })
    @PostMapping(LOGOUT)
    public ResponseEntity<User> logout() {
        service.logout();
        return ResponseEntity.ok(new User());
    }

    /**
     * Delete registered user
     * 
     * @param id ID of the User to be deleted
     * @return OK response or NOT_FOUND if not found
     */
    @DeleteMapping(USER_ID)
    public ResponseEntity<User> delete(@RequestParam(value = "value", required = true) Integer id) {
        if (service.delete(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(NOT_FOUND).build();
        }
    }
}
