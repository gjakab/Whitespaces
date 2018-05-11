package hu.elte.whitespaces.tester.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.OK;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import hu.elte.whitespaces.tester.model.Assessment;
import hu.elte.whitespaces.tester.model.Question;
import hu.elte.whitespaces.tester.model.User;

/*Delete the imports from import.sql before the test*/
public class QuestionControllerTest extends AbstractControllerTest {
    
	//	Question Samples
	private final static String CAT1 = "Sample category1";
    private final static String QUESTION1 = "Sample question 1";
    private final static String QUESTION2 = "Sample question 2";
    
    //	User Samples
    private final static String FIRSTNAME = "FIRSTNAME1";
    private final static String LASTNAME = "LASTNAME1";
    private final static String SCHOOL = "SCHOOL_NAME";
    private final static String EMAIL1 = "EMAIL1";
    private final static String PASSWORD = "PASSWORD";
    private final static User.Role ROLE = User.Role.TEACHER;
    
    //	Assessment Samples
    private final static String NAME1 = "ASSESSMENT NAME";
    private final static Double STAT1 = 99.9;
    private final static Timestamp CREATION_DATE = new Timestamp(new Date().getTime());

    @Autowired
    private QuestionController questionController;
    
    @Autowired
    private AssessmentController assessmentController;
    
    @Autowired
    private UserController userController;
    
    @Test
    public void getQuestionByIdTest() {
    	createAndLoginUserWithEmail(EMAIL1);
    	Question question = buildQuestionWithNameAndAssessment(QUESTION1, buildAssessmentWithName(NAME1));
    	
    	assertThat(questionController.getQuestionById(question.getId()).getBody()).isEqualToComparingFieldByField(question);   	
    }
    
    @Test
    public void getAllQuestionsByAssessmentTest() {
    	createAndLoginUserWithEmail(EMAIL1);
    	
    	Assessment assessment = assessmentController.create(buildAssessmentWithName(NAME1)).getBody();
    	
    	Question question1 = buildQuestionWithNameAndAssessment(QUESTION1, assessment);
    	Question question2 = buildQuestionWithNameAndAssessment(QUESTION2, assessment);
    	
    	List<Question> questions = questionController.getAllQuestionsByAssessment(assessment.getId()).getBody();
    	
    	assertThat(questions).hasSize(2);
    	assertThat(questions.get(0)).isEqualToComparingFieldByField(question1);
    	assertThat(questions.get(1)).isEqualToComparingFieldByField(question2);
    }
    
    @Test
    public void createTest() {
    	createAndLoginUserWithEmail(EMAIL1);
    	
    	Assessment assessment = buildAssessmentWithName(NAME1);
    	Question created = buildQuestionWithNameAndAssessment(QUESTION1, assessment);
    	
    	assertThat(created.getQuestion()).isEqualTo(QUESTION1);
    	assertThat(created.getAssessment()).isEqualToComparingFieldByField(assessment);   	
    }
    
    @Test
    public void deleteTest() {
    	createAndLoginUserWithEmail(EMAIL1);
    	Question question= buildQuestionWithNameAndAssessment(QUESTION1, buildAssessmentWithName(NAME1));
    	
    	ResponseEntity<Question> deleteResponse = questionController.delete(question.getId());
    	
    	assertThat(deleteResponse.getStatusCode()).isEqualTo(OK);    	    	
    }
    
    @Test
    public void updateTest() {
    	createAndLoginUserWithEmail(EMAIL1);
    	Question question = buildQuestionWithNameAndAssessment(QUESTION1, buildAssessmentWithName(NAME1));
    	
    	question.setQuestion(QUESTION2);
    	
    	Question updated = questionController.update(question.getId(), question).getBody();
    	
    	assertThat(updated.getQuestion()).isEqualTo(QUESTION2);
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
    	assessment.setCreationDate(CREATION_DATE);
    	assessment.setStat(STAT1);
    	
    	return assessmentController.create(assessment).getBody();
    }
    
    private Question buildQuestionWithNameAndAssessment(String name, Assessment assessment) {
    	Question question = new Question();
    	question.setCategory(CAT1);
    	question.setQuestion(name);
    	question.setAssessment(assessment);
    	
    	return questionController.create(question, assessment.getId()).getBody();
    }
    
}

