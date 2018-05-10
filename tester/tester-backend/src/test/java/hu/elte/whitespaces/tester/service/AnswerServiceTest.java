package hu.elte.whitespaces.tester.service;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import hu.elte.whitespaces.tester.model.Answer;
import hu.elte.whitespaces.tester.model.Question;
import hu.elte.whitespaces.tester.repository.AnswerRepository;
import hu.elte.whitespaces.tester.repository.QuestionRepository;

public class AnswerServiceTest {
	private final static int ANSWER_ID = 100;
	private final static int QUESTION_ID = 100;
	
	private final QuestionRepository questionRepository = mock(QuestionRepository.class);
	private final AnswerRepository answerRepository = mock(AnswerRepository.class);
	private AssessmentService assessmentService = mock(AssessmentService.class);
	
	private QuestionService questionService;
	private AnswerService answerService;
	
	@Before
	public void setUp() {
		questionService = new QuestionService(questionRepository, assessmentService);
		answerService = new AnswerService(answerRepository, questionService);
	}
		
	@Test
	public void getAnswerByQuestionIdTest() {
		Answer answer = new Answer();
		answer.setId(ANSWER_ID);
		when(answerRepository.findById(ANSWER_ID)).thenReturn(Optional.of(answer));
		
		assertEquals(answerService.getAnswerByQuestionId(QUESTION_ID), answer);
		
		verify(answerRepository).findById(ANSWER_ID);
	}	
	
	@Test
	public void getAllAnswersByQuestionTest() {
		Question question = new Question();
		question.setId(QUESTION_ID);
		Answer answer = new Answer();
		answer.setId(ANSWER_ID);
		answer.setQuestion(question);
		when(answerRepository.findAllByQuestionId(QUESTION_ID)).thenReturn(asList(answer));
		
		assertTrue(answerService.getAllAnswersByQuestion(QUESTION_ID).size() == 1);
		
		verify(answerRepository).findAllByQuestionId(QUESTION_ID);
	}
	
	@Test
	public void createTest() {
		Question question = new Question();
		question.setId(QUESTION_ID);
		Answer answer = new Answer();
		answer.setId(ANSWER_ID);
		when(answerRepository.save(answer)).thenReturn(answer);
		
		assertEquals(answerService.create(answer, question.getId()), answer);
		
		verify(answerRepository).save(answer);
	}
	
	@Test
	public void updateTest() {
		Answer answer = new Answer();
		answer.setId(ANSWER_ID);
		when(answerRepository.findById(ANSWER_ID)).thenReturn(Optional.of(answer));
		when(answerRepository.save(answer)).thenReturn((answer));
		
		answer.setId(101);
		
		assertEquals(answerService.update(ANSWER_ID, answer), answer);
		
		verify(answerRepository).findById(ANSWER_ID);
		verify(answerRepository).save(answer);
	}
	
	@Test
	public void deleteTest() {
		Answer answer = new Answer();
		answer.setId(ANSWER_ID);
		when(answerRepository.findById(ANSWER_ID)).thenReturn(Optional.of(answer));
		
		assertTrue(answerService.delete(ANSWER_ID));
		
		verify(answerRepository).findById(ANSWER_ID);
		verify(answerRepository).delete(answer);		
	}
}
