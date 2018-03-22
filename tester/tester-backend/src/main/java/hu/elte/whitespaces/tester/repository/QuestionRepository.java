package hu.elte.whitespaces.tester.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import hu.elte.whitespaces.tester.model.Question;

@Repository
public interface QuestionRepository extends CrudRepository<Question, Integer> {
	Iterable<Question> findAllByAssessmentId(Integer id);
}
