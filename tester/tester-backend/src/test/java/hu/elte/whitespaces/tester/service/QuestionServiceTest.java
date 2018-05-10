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

import hu.elte.whitespaces.tester.model.Assessment;
import hu.elte.whitespaces.tester.model.Question;
import hu.elte.whitespaces.tester.repository.QuestionRepository;

public class QuestionServiceTest {
	
	private final static int QUESTION_ID = 100;
	private final static int ASSESSMENT_ID = 100;
	
	private final QuestionRepository questionRepository = mock(QuestionRepository.class);
	private AssessmentService assessmentService = mock(AssessmentService.class);
	
	private QuestionService questionService;
	
	@Before
	public void setUp() {
		questionService = new QuestionService(questionRepository, assessmentService);
	}
		
	@Test
	public void getQuestionByIdTest() {
		Question question= new Question();
		question.setId(QUESTION_ID);
		when(questionRepository.findById(QUESTION_ID)).thenReturn(Optional.of(question));
		
		assertEquals(questionService.getQuestionById(QUESTION_ID), question);
		
		verify(questionRepository).findById(QUESTION_ID);
	}	
	
	@Test
	public void getAllQuestionsByAssessmentIdTest() {
		Question question = new Question();
		question.setId(QUESTION_ID);
		Assessment assessment = new Assessment();
		assessment.setId(ASSESSMENT_ID);
		when(questionRepository.findAllByAssessmentId(ASSESSMENT_ID)).thenReturn(asList(question));
		
		assertTrue(questionService.getAllQuestionsByAssessmentId(ASSESSMENT_ID).size() == 1);
		
		verify(questionRepository).findAllByAssessmentId(ASSESSMENT_ID);
	}
	
	@Test
	public void createTest() {
		Question question = new Question();
		question.setId(QUESTION_ID);
		Assessment assessment = new Assessment();
		assessment.setId(ASSESSMENT_ID);
		when(questionRepository.save(question)).thenReturn(question);
		
		assertEquals(questionService.create(question, assessment.getId()), question);
		
		verify(questionRepository).save(question);
	}
	
	@Test
	public void updateTest() {
		Question question = new Question();
		question.setId(QUESTION_ID);
		when(questionRepository.findById(QUESTION_ID)).thenReturn(Optional.of(question));
		when(questionRepository.save(question)).thenReturn((question));
		
		assertEquals(questionService.update(QUESTION_ID, question), question);
		
		verify(questionRepository).findById(QUESTION_ID);
		verify(questionRepository).save(question);
	}
	
	@Test
	public void deleteTest() {
		Question question = new Question();
		question.setId(QUESTION_ID);
		when(questionRepository.findById(QUESTION_ID)).thenReturn(Optional.of(question));
		
		assertTrue(questionService.delete(QUESTION_ID));
		
		verify(questionRepository).findById(QUESTION_ID);
		verify(questionRepository).delete(question);		
	}

}
