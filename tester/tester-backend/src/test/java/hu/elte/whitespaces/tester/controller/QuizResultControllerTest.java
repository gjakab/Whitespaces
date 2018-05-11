package hu.elte.whitespaces.tester.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.OK;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import hu.elte.whitespaces.tester.model.Assessment;
import hu.elte.whitespaces.tester.model.AssessmentResult;
import hu.elte.whitespaces.tester.model.User;
import hu.elte.whitespaces.tester.model.dto.QuizResultDTO;

/*Delete the imports from import.sql before the test*/
public class QuizResultControllerTest extends AbstractControllerTest {
	
    private final static String QUIZ_NAME = "QUIZ NAME";
	private final static int QUIZRESULT_ID = 100;
	private final static int USER_ID = 100;
	private final static int QUIZ_ID = 100;    
	private final static int SCORE = 100;
    private final static double STAT = 100.0;
    
    private final static String FIRSTNAME = "FIRSTNAME";
    private final static String LASTNAME = "LASTNAME";
    private final static String SCHOOL = "SCHOOL_NAME";
    private final static String EMAIL = "EMAIL";
    private final static String PASSWORD = "PASSWORD";
    private final static User.Role TEACHER_ROLE = User.Role.TEACHER;
    private final static User.Role STUDENT_ROLE = User.Role.STUDENT;

    @Autowired
    private AssessmentController assessmentController;
    
    @Autowired
    private QuizResultController quizResultController;
    
    @Autowired
    private UserController userController;
    
    @Test
    public void getAllQuizResultsByUserIdTest() {
    	createAndLoginUserWithRole(STUDENT_ROLE);
    	Assessment quiz = assessmentController.create(buildAssessment()).getBody();
    	AssessmentResult quizResult1 = quizResultController.create(buildQuizResult(), quiz.getId())
    												 	.getBody();
    	AssessmentResult quizResult2 = quizResultController.create(buildQuizResult(), quiz.getId())
				 										.getBody();
    	
    	List<QuizResultDTO> quizResults = quizResultController.getAllQuizResultsByUserId()
    													   	  .getBody();
    	
        assertThat(quizResults).hasSize(2);
        assertThat(quizResults.get(0)).isEqualToComparingFieldByField(buildQuizResultDTO(quizResult1));
        assertThat(quizResults.get(1)).isEqualToComparingFieldByField(buildQuizResultDTO(quizResult2));   	
    }
    
    @Test
    public void getAllQuizResultsByQuizId() {
    	createAndLoginUserWithRole(TEACHER_ROLE);
    	Assessment quiz = assessmentController.create(buildAssessment()).getBody();
    	AssessmentResult quizResult1 = quizResultController.create(buildQuizResult(), quiz.getId())
    												 	.getBody();
    	AssessmentResult quizResult2 = quizResultController.create(buildQuizResult(), quiz.getId())
				 										.getBody();
    	
    	List<QuizResultDTO> quizResults = quizResultController.getAllQuizResultsByQuizId(quiz.getId())
    													   	  .getBody();
    	
        assertThat(quizResults).hasSize(2);
        assertThat(quizResults.get(0)).isEqualToComparingFieldByField(buildQuizResultDTO(quizResult1));
        assertThat(quizResults.get(1)).isEqualToComparingFieldByField(buildQuizResultDTO(quizResult2));   		
    }
    
    @Test
    public void createTest() {
    	createAndLoginUserWithRole(STUDENT_ROLE);
    	Assessment quiz = assessmentController.create(buildAssessment()).getBody();
    	AssessmentResult created = quizResultController.create(buildQuizResult(), quiz.getId())
			 										   .getBody();
    	
    	assertThat(created.getScore()).isEqualTo(SCORE);
    	assertThat(created.getStats()).isEqualTo(STAT); 	    	
    }
    
    @Test
    public void deleteTest() {
    	createAndLoginUserWithRole(STUDENT_ROLE);
    	Assessment quiz = assessmentController.create(buildAssessment()).getBody();
    	AssessmentResult created = quizResultController.create(buildQuizResult(), quiz.getId())
			 										   .getBody();
    	
    	ResponseEntity<AssessmentResult> deleteResponse = quizResultController.delete(created.getId());
    	assertThat(deleteResponse.getStatusCode()).isEqualTo(OK);    	    	
    }
    
    private void createAndLoginUserWithRole(User.Role role) {
    	User user = new User();
    	
    	user.setFirstname(FIRSTNAME);
    	user.setLastname(LASTNAME);
    	user.setSchool(SCHOOL);
    	user.setEmail(EMAIL);
    	user.setPassword(PASSWORD);
    	user.setRole(role);

        userController.create(user);

    	User loginUser = new User();

    	loginUser.setFirstname(FIRSTNAME);
    	loginUser.setLastname(LASTNAME);
    	loginUser.setSchool(SCHOOL);
    	loginUser.setEmail(EMAIL);
    	loginUser.setPassword(PASSWORD);
    	loginUser.setRole(role);

    	userController.login(loginUser);
    }
    
    private AssessmentResult buildQuizResult() {
    	AssessmentResult quizResult = new AssessmentResult();
    	
    	quizResult.setScore(SCORE);
    	quizResult.setStats(STAT);
    	
    	return quizResult;
    }
    
    private QuizResultDTO buildQuizResultDTO(AssessmentResult quizResult) {
    	String fullName = quizResult.getUser().getFirstname() + " " + quizResult.getUser().getLastname();
    	return new QuizResultDTO(quizResult.getId(), quizResult.getScore(), quizResult.getStats(), fullName, quizResult.getAssessment().getId());
    }
    
    private Assessment buildAssessment() {
    	Assessment quiz = new Assessment();
    	quiz.setName(QUIZ_NAME);
    	
    	return quiz;
    }
    
}
