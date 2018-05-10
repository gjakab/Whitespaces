package hu.elte.whitespaces.tester.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.SessionScope;

import hu.elte.whitespaces.tester.model.Assessment;
import hu.elte.whitespaces.tester.model.AssessmentResult;
import hu.elte.whitespaces.tester.model.User;
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
	public QuizResultService(QuizResultRepository quizResultRepository, UserRepository userRepository, AssessmentRepository assessmentRepository) {
		this.quizResultRepository = quizResultRepository;
		this.userRepository = userRepository;
		this.assessmentRepository = assessmentRepository;
	}
	
	public List<AssessmentResult> getAllQuizResultsByQuiz(Integer qId) {
		Iterable<AssessmentResult> quizResults = quizResultRepository.findAllByAssessmentId(qId);
		List<AssessmentResult> results = new ArrayList<>();
		
		quizResults.forEach(quizResult -> results.add(quizResult));
		return results;
	}
	
	public List<AssessmentResult> getAllQuizResultsByUserId(Integer userId) {
		Iterable<AssessmentResult> quizResults = quizResultRepository.findAllByUserId(userId);
		List<AssessmentResult> results = new ArrayList<>();
		
		quizResults.forEach(quizResult -> results.add(quizResult));
		return results;
	}
	
	@Transactional
	public AssessmentResult create(int quizId, User user, AssessmentResult quizResult) {
		User dbUser = userRepository.findByEmail(user.getEmail()).get();
		Assessment quiz = assessmentRepository.findById(quizId).get();
		
		quizResult.setUser(dbUser);
		quizResult.setAssessment(quiz);
		
		return quizResultRepository.save(quizResult);
	}
	
}
