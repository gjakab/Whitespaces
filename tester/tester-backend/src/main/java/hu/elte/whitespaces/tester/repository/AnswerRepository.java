package hu.elte.whitespaces.tester.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import hu.elte.whitespaces.tester.model.Answer;

@Repository
public interface AnswerRepository extends CrudRepository<Answer, Integer> {
    @Override
    Optional<Answer> findById(Integer anId);

    Iterable<Answer> findAllByQuestionId(Integer qId);
}
