package hu.elte.whitespaces.tester.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.OK;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import hu.elte.whitespaces.tester.model.Assessment;
import hu.elte.whitespaces.tester.model.User;

/*Delete the imports from import.sql before the test*/
public class AssessmentControllerTest extends AbstractControllerTest {
    
    private final static String NAME1 = "ASSESSMENT NAME";
    private final static String NAME2 = "ASSESSMENT NAME2";
    private final static Double STAT1 = 99.9;
    private final static Double STAT2 = 89.9;
    
    private final static String FIRSTNAME = "FIRSTNAME1";
    private final static String LASTNAME = "LASTNAME1";
    private final static String SCHOOL = "SCHOOL_NAME";
    private final static String EMAIL1 = "EMAIL1";
    private final static String EMAIL2 = "EMAIL2";
    private final static String PASSWORD = "PASSWORD";
    private final static User.Role ROLE = User.Role.TEACHER;

    @Autowired
    private AssessmentController assessmentController;
    
    @Autowired
    private UserController userController;
    
    @Test
    public void getAllAssessmentsByUserIdTest() {
    	createAndLoginUserWithEmail(EMAIL1);
    	Assessment assessment1 = assessmentController.create(buildAssessmentWithName(NAME1))
    												 .getBody();
    	Assessment assessment2 = assessmentController.create(buildAssessmentWithName(NAME2))
				 									 .getBody();
    	
    	List<Assessment> assessments = assessmentController.getAllAssessmentsByUserId()
    													   .getBody();
    	
        assertThat(assessments).hasSize(2);
        assertThat(assessments.get(0)).isEqualToComparingFieldByField(assessment1);
        assertThat(assessments.get(1)).isEqualToComparingFieldByField(assessment2);   	
    }
    
    @Test
    public void getAssessmentByIdTest() {
    	createAndLoginUserWithEmail(EMAIL1);
    	Assessment assessment = assessmentController.create(buildAssessmentWithName(NAME1))
    			  								  	.getBody();
    	
    	assertThat(assessmentController.getAssessmentById(assessment.getId()).getBody()).isEqualToComparingFieldByField(assessment);
    }
    
    @Test
    public void getAllAssessmentsTest() {
    	createAndLoginUserWithEmail(EMAIL1);
    	Assessment assessment1 = assessmentController.create(buildAssessmentWithName(NAME1))
    												 .getBody();
    	logout();
    	
    	createAndLoginUserWithEmail(EMAIL2);
    	Assessment assessment2 = assessmentController.create(buildAssessmentWithName(NAME2))
				 									 .getBody();
    	
    	List<Assessment> assessments = assessmentController.getAllAssessments()
    													   .getBody();
    	
        assertThat(assessments).hasSize(2);
        assertThat(assessments.get(0)).isEqualToComparingFieldByField(assessment1);
        assertThat(assessments.get(1)).isEqualToComparingFieldByField(assessment2);  
    }
    
    @Test
    public void getAvaiableQuizzesForUser() {
        createAndLoginUserWithEmail(EMAIL1);
        Assessment assessment = assessmentController.create(buildAssessmentWithName(NAME1))
                                                     .getBody();
        
        List<Assessment> assessments = assessmentController.getAvaiableQuizzesForUser()
                                                           .getBody();
        
        assertThat(assessments).hasSize(1);
        assertThat(assessments.get(0)).isEqualToComparingFieldByField(assessment);
    }
    
    @Test
    public void createTest() {
        createAndLoginUserWithEmail(EMAIL1);
    	Assessment created = assessmentController.create(buildAssessmentWithName(NAME1))
    			 								 .getBody();
    	
    	assertThat(created.getName()).isEqualTo(NAME1);
    	assertThat(created.getStat()).isEqualTo(0.0); 	    	
    }
    
    @Test
    public void deleteTest() {
        createAndLoginUserWithEmail(EMAIL1);
    	Assessment assessment = assessmentController.create(buildAssessmentWithName(NAME1))
    			 									.getBody();
    	ResponseEntity<Assessment> deleteResponse = assessmentController.delete(assessment.getId());
    	assertThat(deleteResponse.getStatusCode()).isEqualTo(OK);    	    	
    }
    
    @Test
    public void updateTest() {
        createAndLoginUserWithEmail(EMAIL1);
    	Assessment assessment = assessmentController.create(buildAssessmentWithName(NAME1))
													.getBody();
    	
    	assessment.setName(NAME2);
    	assessment.setStat(STAT2);
    	Assessment updated = assessmentController.update(assessment.getId(), assessment).getBody();
    	
    	assertThat(updated.getName()).isEqualTo(NAME2);
    	assertThat(updated.getStat()).isEqualTo(STAT2);
    }
    
    private void logout() {
    	userController.logout();
    }
    
    private void createAndLoginUserWithEmail(String email) {
    	User user = new User();
    	
    	user.setFirstname(FIRSTNAME);
    	user.setLastname(LASTNAME);
    	user.setSchool(SCHOOL);
    	user.setEmail(email);
    	user.setPassword(PASSWORD);
    	user.setRole(ROLE);

        userController.create(user);

    	User loginUser = new User();

    	loginUser.setFirstname(FIRSTNAME);
    	loginUser.setLastname(LASTNAME);
    	loginUser.setSchool(SCHOOL);
    	loginUser.setEmail(email);
    	loginUser.setPassword(PASSWORD);
    	loginUser.setRole(ROLE);

    	userController.login(loginUser);
    }
    
    private Assessment buildAssessmentWithName(String name) {
    	Assessment assessment = new Assessment();
    	assessment.setName(name);
    	
    	return assessment;
    }
    
}
