package hu.elte.whitespaces.tester.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import hu.elte.whitespaces.tester.model.User;
import hu.elte.whitespaces.tester.repository.UserRepository;

@Service
@SessionScope
public class UserService {

    private UserRepository repository;

    private PasswordEncoder passwordEncoder;

    private User user;

    @Autowired
    UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public User login(User user) {
        Optional<User> result = repository.findByEmail(user.getEmail());

        System.out.println("PASSWORD: " + passwordEncoder.encode("1234"));

        if (result.isPresent() && passwordEncoder.matches(user.getPassword(), result.get().getPassword())) {
            this.user = result.get();
            return this.user;
        } else {
            return null;
        }
    }

    public void logout() {
        this.user = null;
    }

    public boolean isLoggedIn() {
        return user != null;
    }

    public User getUserById(Integer id) {
        Optional<User> result = repository.findById(id);

        if (result.isPresent()) {
            return result.get();
        } else {
            return null;
        }
    }

    public User getUserByEmail(String email) {
        Optional<User> result = repository.findByEmail(email);

        if (result.isPresent()) {
            return result.get();
        } else {
            return null;
        }
    }

    @Transactional
    public User register(User entity) {
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        return repository.save(entity);
    }

    @Transactional
    public boolean delete(int id) {
        Optional<User> entity = repository.findById(id);

        if (entity.isPresent()) {
            repository.delete(entity.get());
            return true;
        } else {
            return false;
        }
    }

    public List<User> getAllUser() {
        Iterable<User> users = repository.findAll();
        List<User> results = new ArrayList<>();

        users.forEach(user -> results.add(user));
        return results;
    }

    public User getCurrentUser() {
        return this.user;
    }

}
