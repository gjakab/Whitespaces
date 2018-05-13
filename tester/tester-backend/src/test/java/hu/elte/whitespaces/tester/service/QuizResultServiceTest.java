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
import hu.elte.whitespaces.tester.model.AssessmentResult;
import hu.elte.whitespaces.tester.model.User;
import hu.elte.whitespaces.tester.repository.AssessmentRepository;
import hu.elte.whitespaces.tester.repository.QuizResultRepository;
import hu.elte.whitespaces.tester.repository.UserRepository;

public class QuizResultServiceTest {
	
	private static final int QUIZRESULT_ID = 100;
	private static final int USER_ID = 100;
	private static final int QUIZ_ID = 100;
	private static final String EMAIL = "EMAIL";
	private static final String FIRSTNAME = "FIRSTNAME";
	private static final String LASTNAME = "LASTNAME";
	
	private final AssessmentRepository quizResository = mock(AssessmentRepository.class);
	private final UserRepository userRepository = mock(UserRepository.class);
	private final QuizResultRepository quizResultRepository = mock(QuizResultRepository.class);
	
	private QuizResultService quizResultService;
	
	@Before
	public void setUp() {
		quizResultService = new QuizResultService(quizResultRepository, userRepository, quizResository);
	}
	
	
	@Test
	public void getAllQuizResultsByQuizTest() {
		AssessmentResult quizResult = new AssessmentResult();
		quizResult.setId(QUIZRESULT_ID);
		
		User user = new User();
		user.setId(USER_ID);
		user.setFirstname(FIRSTNAME);
		user.setLastname(LASTNAME);
		quizResult.setUser(user);
		
		Assessment quiz = new Assessment();
		quiz.setId(QUIZ_ID);
		quizResult.setAssessment(quiz);
		
		when(quizResultRepository.findAllByAssessmentId(QUIZ_ID)).thenReturn(asList(quizResult));
		assertTrue(quizResultService.getAllQuizResultsByQuiz(QUIZ_ID).size() == 1);
		verify(quizResultRepository).findAllByAssessmentId(QUIZ_ID);
	}
	
	@Test
	public void getAllQuizResultsByUserId() {
		AssessmentResult quizResult = new AssessmentResult();
		quizResult.setId(QUIZRESULT_ID);
		
		User user = new User();
		user.setId(USER_ID);
		user.setFirstname(FIRSTNAME);
		user.setLastname(LASTNAME);
		quizResult.setUser(user);
		
		Assessment quiz = new Assessment();
		quiz.setId(QUIZ_ID);
		quizResult.setAssessment(quiz);
		
		when(quizResultRepository.findAllByUserId(USER_ID)).thenReturn(asList(quizResult));
		assertTrue(quizResultService.getAllQuizResultsByUserId(USER_ID).size() == 1);
		verify(quizResultRepository).findAllByUserId(USER_ID);
	}
	
    @Test
    public void getQuizResultByUserIdAndQuizId() {
        AssessmentResult quizResult = new AssessmentResult();
        
        quizResult.setId(QUIZRESULT_ID);
        when(quizResultRepository.findByUserIdAndAssessmentId(USER_ID, QUIZ_ID)).thenReturn(Optional.of(quizResult));
        
        assertEquals(quizResultService.getQuizResultByUserIdAndQuizId(USER_ID, QUIZ_ID), true);
        
        verify(quizResultRepository).findByUserIdAndAssessmentId(USER_ID, QUIZ_ID);
    }
	
	@Test
	public void createTest() {
		AssessmentResult quizResult = new AssessmentResult();
		quizResult.setId(QUIZRESULT_ID);
		
		User user = new User();
		user.setId(USER_ID);
		user.setEmail(EMAIL);
		
		Assessment quiz = new Assessment();
		quiz.setId(QUIZ_ID);

        when(userRepository.findByEmail(EMAIL)).thenReturn(Optional.of(user));
        when(quizResository.findById(QUIZ_ID)).thenReturn(Optional.of(quiz));
		when(quizResultRepository.save(quizResult)).thenReturn(quizResult);
		
		assertEquals(quizResultService.create(QUIZ_ID, user, quizResult), quizResult);
		
		verify(quizResultRepository).save(quizResult);
	}
	
	@Test
	public void deleteTest() {
		AssessmentResult quizResult = new AssessmentResult();
		quizResult.setId(QUIZRESULT_ID);
		
		when(quizResultRepository.findById(QUIZRESULT_ID)).thenReturn(Optional.of(quizResult));
		
		assertTrue(quizResultService.delete(QUIZRESULT_ID));
		
		verify(quizResultRepository).findById(QUIZRESULT_ID);
		verify(quizResultRepository).delete(quizResult);
	}
	
	
		


}
