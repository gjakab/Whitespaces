package hu.elte.whitespaces.tester.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import hu.elte.whitespaces.tester.model.Assessment;

@Repository
public interface AssessmentRepository extends CrudRepository<Assessment, Integer>{

	Optional<Assessment> findByName(String name);
	Iterable<Assessment> findAllByUserId(Integer userId);
	
}
