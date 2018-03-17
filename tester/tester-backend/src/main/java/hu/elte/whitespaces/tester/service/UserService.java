package hu.elte.whitespaces.tester.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.elte.whitespaces.tester.model.User;
import hu.elte.whitespaces.tester.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository repository;

    @Autowired
    UserService(UserRepository repository){
        this.repository = repository;
    }

    public User getUserById(Integer id) {
        Optional<User> result = repository.findById(id);
        
        if(result.isPresent()) {
            return result.get();
        } else {
            return null;
        }
    }

    public User getUserByEmail(String email) {
        Optional<User> result = repository.findByEmail(email);
        
        if(result.isPresent()) {
            return result.get();
        } else {
            return null;
        }
    }

    @Transactional
    public User register(User entity) {
        return repository.save(entity);
    }

    @Transactional
    public boolean delete(int id) {
        Optional<User> entity = repository.findById(id);
        
        if(entity.isPresent()) {
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

}
