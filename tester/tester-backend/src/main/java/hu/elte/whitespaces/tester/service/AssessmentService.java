package hu.elte.whitespaces.tester.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import hu.elte.whitespaces.tester.model.Assessment;
import hu.elte.whitespaces.tester.model.User;
import hu.elte.whitespaces.tester.repository.AssessmentRepository;
import hu.elte.whitespaces.tester.repository.UserRepository;

@Service
@SessionScope
public class AssessmentService {

    private AssessmentRepository assessmentRepository;
    private UserRepository userRepository;
    private QuizResultService quizResultService;

    @Autowired
    public AssessmentService(AssessmentRepository assessmentRepository, UserRepository userRepository,
            QuizResultService quizResultService) {
        this.assessmentRepository = assessmentRepository;
        this.userRepository = userRepository;
        this.quizResultService = quizResultService;
    }

    public Assessment getAssessmentById(Integer id) {
        Optional<Assessment> result = assessmentRepository.findById(id);

        if (result.isPresent()) {
            return result.get();
        }

        return null;
    }

    public List<Assessment> getAllAssessmentsByUserId(Integer userId) {
        Iterable<Assessment> assessments = assessmentRepository.findAllByUserId(userId);
        List<Assessment> results = new ArrayList<>();

        assessments.forEach(assessment -> {
            assessment.setQuestions(null);
            results.add(assessment);
        });
        return results;
    }

    public List<Assessment> getAllAssessments() {
        Iterable<Assessment> assessments = assessmentRepository.findAll();
        List<Assessment> results = new ArrayList<>();

        assessments.forEach(assessment -> results.add(assessment));
        return results;
    }

    public List<Assessment> getAvaiableQuizzesForUser(Integer userId) {
        List<Assessment> results = new ArrayList<>();

        for (Assessment quiz : getAllAssessments()) {
            if (!quizResultService.getQuizResultByUserIdAndQuizId(userId, quiz.getId())) {
                quiz.setQuestions(null);
                results.add(quiz);
            }
        }

        return results;
    }

    @Transactional
    public Assessment create(Assessment assessment, User user) {
        User dbUser = userRepository.findByEmail(user.getEmail()).get();
        assessment.setUser(dbUser);
        assessment.setCreationDate(new Timestamp(System.currentTimeMillis()));
        assessment.setStat(0.0);
        return assessmentRepository.save(assessment);
    }

    @Transactional
    public Assessment update(Integer id, Assessment assessment) {
        Optional<Assessment> result = assessmentRepository.findById(id);

        if (result.isPresent()) {
            Assessment currentAssessment = result.get();
            currentAssessment.setName(assessment.getName());
            currentAssessment.setStat(assessment.getStat());

            return assessmentRepository.save(currentAssessment);
        }

        return null;
    }

    @Transactional
    public boolean delete(Integer id) {
        Optional<Assessment> result = assessmentRepository.findById(id);

        if (result.isPresent()) {
            assessmentRepository.delete(result.get());
            return true;
        }

        return false;
    }

}
