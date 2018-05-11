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
import hu.elte.whitespaces.tester.model.User;
import hu.elte.whitespaces.tester.repository.AssessmentRepository;
import hu.elte.whitespaces.tester.repository.UserRepository;

public class AssessmentServiceTest {

    private final static int ASSESSMENT_ID = 100;
    private final static int USER_ID = 100;
    private final static int QUIZ_ID = 100;

    private final AssessmentRepository assessmentRepository = mock(AssessmentRepository.class);
    private final UserRepository userRepository = mock(UserRepository.class);

    private AssessmentService assessmentService;
    private QuizResultService quizResultService = mock(QuizResultService.class);

    @Before
    public void setUp() {
        assessmentService = new AssessmentService(assessmentRepository, userRepository, quizResultService);
    }

    @Test
    public void getAssessmentByIdTest() {
        Assessment assessment = new Assessment();
        assessment.setId(ASSESSMENT_ID);
        when(assessmentRepository.findById(ASSESSMENT_ID)).thenReturn(Optional.of(assessment));

        assertEquals(assessmentService.getAssessmentById(ASSESSMENT_ID), assessment);

        verify(assessmentRepository).findById(ASSESSMENT_ID);
    }

    @Test
    public void getAllAssessmentsByUserIdTest() {
        Assessment assessment = new Assessment();
        assessment.setId(ASSESSMENT_ID);
        User user = new User();
        user.setId(USER_ID);
        when(assessmentRepository.findAllByUserId(USER_ID)).thenReturn(asList(assessment));

        assertTrue(assessmentService.getAllAssessmentsByUserId(USER_ID).size() == 1);

        verify(assessmentRepository).findAllByUserId(USER_ID);
    }

    @Test
    public void getAllAssessmentsTest() {
        Assessment assessment = new Assessment();
        assessment.setId(ASSESSMENT_ID);
        when(assessmentRepository.findAll()).thenReturn(asList(assessment));

        assertEquals(assessmentService.getAllAssessments().size(), 1);

        verify(assessmentRepository).findAll();
    }

    @Test
    public void getAvaiableQuizzesForUser() {
        Assessment assessment = new Assessment();
        assessment.setId(ASSESSMENT_ID);

        when(assessmentRepository.findAll()).thenReturn(asList(assessment));
        when(quizResultService.getQuizResultByUserIdAndQuizId(USER_ID, QUIZ_ID)).thenReturn(false);

        assertEquals(assessmentService.getAvaiableQuizzesForUser(USER_ID).size(), 1);

        verify(quizResultService).getQuizResultByUserIdAndQuizId(USER_ID, QUIZ_ID);
    }

    @Test
    public void createTest() {
        Assessment assessment = new Assessment();
        assessment.setId(ASSESSMENT_ID);
        User user = new User();
        user.setId(USER_ID);
        user.setEmail("email");

        when(userRepository.findByEmail("email")).thenReturn(Optional.of(user));
        when(assessmentRepository.save(assessment)).thenReturn(assessment);

        assertEquals(assessmentService.create(assessment, user), assessment);

        verify(assessmentRepository).save(assessment);
    }

    @Test
    public void updateTest() {
        Assessment assessment = new Assessment();
        assessment.setId(ASSESSMENT_ID);
        when(assessmentRepository.findById(ASSESSMENT_ID)).thenReturn(Optional.of(assessment));
        when(assessmentRepository.save(assessment)).thenReturn((assessment));

        assertEquals(assessmentService.update(ASSESSMENT_ID, assessment), assessment);

        verify(assessmentRepository).findById(ASSESSMENT_ID);
        verify(assessmentRepository).save(assessment);
    }

    @Test
    public void deleteTest() {
        Assessment assessment = new Assessment();
        assessment.setId(ASSESSMENT_ID);
        when(assessmentRepository.findById(ASSESSMENT_ID)).thenReturn(Optional.of(assessment));

        assertTrue(assessmentService.delete(ASSESSMENT_ID));

        verify(assessmentRepository).findById(ASSESSMENT_ID);
        verify(assessmentRepository).delete(assessment);
    }

}
