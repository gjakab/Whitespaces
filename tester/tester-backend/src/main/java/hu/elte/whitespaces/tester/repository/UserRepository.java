package hu.elte.whitespaces.tester.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import hu.elte.whitespaces.tester.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    @Override
    Optional<User> findById(Integer id);

    Optional<User> findByEmail(String email);
}
