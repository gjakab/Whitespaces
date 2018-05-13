package hu.elte.whitespaces.tester.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.SessionScope;

import hu.elte.whitespaces.tester.model.Assessment;
import hu.elte.whitespaces.tester.model.AssessmentResult;
import hu.elte.whitespaces.tester.model.User;
import hu.elte.whitespaces.tester.model.dto.QuizResultDTO;
import hu.elte.whitespaces.tester.model.dto.StudentQuizResultDTO;
import hu.elte.whitespaces.tester.repository.AssessmentRepository;
import hu.elte.whitespaces.tester.repository.QuizResultRepository;
import hu.elte.whitespaces.tester.repository.UserRepository;

@Service
@SessionScope
public class QuizResultService {

    private QuizResultRepository quizResultRepository;
    private UserRepository userRepository;
    private AssessmentRepository assessmentRepository;

    @Autowired
    public QuizResultService(QuizResultRepository quizResultRepository, UserRepository userRepository,
            AssessmentRepository assessmentRepository) {
        this.quizResultRepository = quizResultRepository;
        this.userRepository = userRepository;
        this.assessmentRepository = assessmentRepository;
    }

    public List<QuizResultDTO> getAllQuizResultsByQuiz(Integer qId) {
        Iterable<AssessmentResult> quizResults = quizResultRepository.findAllByAssessmentId(qId);
        List<QuizResultDTO> results = new ArrayList<>();

        for (AssessmentResult quizResult : quizResults) {
            results.add(new QuizResultDTO(quizResult.getId(), quizResult.getScore(), quizResult.getStats(),
                    quizResult.getUser().getFirstname() + " " + quizResult.getUser().getLastname(),
                    quizResult.getAssessment().getId()));
        }

        return results;
    }

    public List<StudentQuizResultDTO> getAllQuizResultsByUserId(Integer userId) {
        Iterable<AssessmentResult> quizResults = quizResultRepository.findAllByUserId(userId);
        List<StudentQuizResultDTO> results = new ArrayList<>();

        for (AssessmentResult quizResult : quizResults) {
            results.add(new StudentQuizResultDTO(quizResult.getScore(), quizResult.getStats(),
                    quizResult.getAssessment().getName()));
        }

        return results;
    }

    public boolean getQuizResultByUserIdAndQuizId(Integer userId, Integer quizId) {
        Optional<AssessmentResult> quizResult = quizResultRepository.findByUserIdAndAssessmentId(userId, quizId);

        if (quizResult.isPresent()) {
            return true;
        }

        return false;
    }

    @Transactional
    public AssessmentResult create(Integer quizId, User user, AssessmentResult quizResult) {
        User dbUser = userRepository.findByEmail(user.getEmail()).get();
        Assessment quiz = assessmentRepository.findById(quizId).get();

        quizResult.setUser(dbUser);
        quizResult.setAssessment(quiz);

        quiz.setStat(getNewStatForQuiz(quiz, quizResult));
        assessmentRepository.save(quiz);

        return quizResultRepository.save(quizResult);
    }

    private double getNewStatForQuiz(Assessment quiz, AssessmentResult quizResult) {
        double newStat = 0.0;
        int size = 0;

        Iterable<AssessmentResult> quizResults = quizResultRepository.findAllByAssessmentId(quiz.getId());
        for (AssessmentResult qr : quizResults) {
            newStat += qr.getScore();
            size++;
        }

        newStat += quizResult.getScore();
        size++;
        newStat /= size;

        return newStat;
    }

    @Transactional
    public boolean delete(Integer id) {
        Optional<AssessmentResult> result = quizResultRepository.findById(id);

        if (result.isPresent()) {
            quizResultRepository.delete(result.get());
            return true;
        }

        return false;
    }

}
