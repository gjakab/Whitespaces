package hu.elte.whitespaces.tester.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import hu.elte.whitespaces.tester.model.AssessmentResult;

@Repository
public interface QuizResultRepository extends CrudRepository<AssessmentResult, Integer> {

    Iterable<AssessmentResult> findAllByAssessmentId(Integer qId);

    Iterable<AssessmentResult> findAllByUserId(Integer userId);

    Optional<AssessmentResult> findByUserIdAndAssessmentId(Integer userId, Integer qId);
}
