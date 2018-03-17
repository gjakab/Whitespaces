package hu.elte.whitespaces.tester.controller;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.util.List;

import javax.validation.Valid;

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

import hu.elte.whitespaces.tester.model.User;
import hu.elte.whitespaces.tester.service.UserService;


@CrossOrigin(origins = {"http://localhost:4200"}) //This is need for development
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final static String USER_ID = "/id";
    private final static String EMAIL = "/email";
    private final static String USER_LIST = "/all";

    private final UserService service;

    @Autowired
    UserController(UserService service){
        this.service = service;
    }

    @GetMapping(USER_LIST)
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.ok(service.getAllUser());
    }

    @GetMapping(USER_ID)
    public ResponseEntity<User> getUserById (@RequestParam(value = "value", required = true) Integer id) {

        User response = service.getUserById(id);
        if(response != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(NOT_FOUND).build();
        }
    }

    @GetMapping(EMAIL)
    public ResponseEntity<User> getUserByEmail (@RequestParam(value = "value", required = true)  String email ) {

        User response = service.getUserByEmail(email);
        if(response != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(NOT_FOUND).build();
        }
    }

    @PostMapping("")
    public ResponseEntity<User> create(@Valid @RequestBody User user) {
        
        User saved = service.register(user);

        if(saved != null) {
            return ResponseEntity.ok(saved);
        } else {
            return ResponseEntity.status(NOT_FOUND).build();
        }
    }

    @DeleteMapping(USER_ID)
    public ResponseEntity<User> delete(@RequestParam(value = "value", required = true) Integer id) {

        if (service.delete(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(NOT_FOUND).build();
        }
    }
}