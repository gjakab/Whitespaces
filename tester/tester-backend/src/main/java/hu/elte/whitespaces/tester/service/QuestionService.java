package hu.elte.whitespaces.tester.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.SessionScope;

import hu.elte.whitespaces.tester.model.Question;
import hu.elte.whitespaces.tester.repository.QuestionRepository;

@Service
@SessionScope
public class QuestionService {
	private final QuestionRepository questionRepository;
	private final AssessmentService assessmentService;
	
	@Autowired
	public QuestionService(QuestionRepository questionRepository, AssessmentService assessmentService) {
		this.questionRepository = questionRepository;
		this.assessmentService = assessmentService;
	}
	
	public Question getQuestionById(Integer id) {
		Optional<Question> result = questionRepository.findById(id);
		
		if (result.isPresent()) {
			return result.get();
		}
		
		return null;
	}
	
	public List<Question> getAllQuestionsByAssessmentId(Integer aId) {
		Iterable<Question> questions = questionRepository.findAllByAssessmentId(aId);
		List<Question> results = new ArrayList<>();
		
		questions.forEach(question -> results.add(question));
		return results;
	}
	
	@Transactional
	public Question create(Question question, Integer aId) {
		question.setAssessment(assessmentService.getAssessmentById(aId));
		return questionRepository.save(question);
	}
	
	@Transactional
	public Question update(int qId, Question question) {
		Optional<Question> result = questionRepository.findById(qId);
		
		if (result.isPresent()) {
			Question currentQuestion = result.get();
			currentQuestion.setQuestion(question.getQuestion());
			currentQuestion.setCategory(question.getCategory());
			currentQuestion.setAnswers(question.getAnswers());
			
			return questionRepository.save(currentQuestion);
		}
		
		return null;
	}
	
	@Transactional
	public boolean delete(int qId) {
		Optional<Question> result = questionRepository.findById(qId);
		
		if (result.isPresent()) {
			questionRepository.delete(result.get());
			return true;
		}
		
		return false;
	}
}
