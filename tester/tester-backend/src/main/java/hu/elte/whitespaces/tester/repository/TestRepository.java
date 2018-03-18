package hu.elte.whitespaces.tester.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import hu.elte.whitespaces.tester.model.Test;

@Repository
public interface TestRepository extends CrudRepository<Test, Integer>{

	Optional<Test> findByName(String name);
	Iterable<Test> findAllByUserId(Integer userId);
	
}
