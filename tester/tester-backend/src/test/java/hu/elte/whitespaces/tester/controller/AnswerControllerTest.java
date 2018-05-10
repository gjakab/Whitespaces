package hu.elte.whitespaces.tester.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.OK;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import hu.elte.whitespaces.tester.model.Answer;
import hu.elte.whitespaces.tester.model.Assessment;
import hu.elte.whitespaces.tester.model.Question;
import hu.elte.whitespaces.tester.model.User;

public class AnswerControllerTest extends AbstractControllerTest {
//	Question Samples
	private final static String CAT1 = "Sample category1";
    private final static String QUESTION1 = "Sample question 1";
    
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
    
    //	Answer Samples
    private final static String ANSW1 = "ANSWER 1";
    private final static String ANSW2 = "ANSWER 2";

    @Autowired
    private QuestionController questionController;
    
    @Autowired
    private AssessmentController assessmentController;
    
    @Autowired
    private UserController userController;
    
    @Autowired
    private AnswerController answerController;
    
    @Test
    public void getAnswerByIdTest() {
    	createAndLoginUserWithEmail(EMAIL1);
    	Assessment assessment = buildAssessmentWithName(NAME1);
    	Question question = buildQuestionWithNameAndAssessment(QUESTION1, assessment);
    	Answer answer = buildAnswer(ANSW1, question);
    	
    	assertThat(answerController.getAnswerById(question.getId(), answer.getId()).getBody()).isEqualToComparingFieldByField(answer);   	
    }
    
    @Test
    public void getAllQuestionsByAssessmentTest() {
    	createAndLoginUserWithEmail(EMAIL1);
    	
    	Question question = buildQuestionWithNameAndAssessment(QUESTION1, buildAssessmentWithName(NAME1));
    	Answer answer1 = buildAnswer(ANSW1, question);
    	Answer answer2 = buildAnswer(ANSW2, question);
    	
    	List<Answer> answers = answerController.getAllAnswersByQuestionId(question.getId()).getBody();
    	
    	assertThat(answers).hasSize(2);
    	assertThat(answers.get(0)).isEqualToComparingFieldByField(answer1);
    	assertThat(answers.get(1)).isEqualToComparingFieldByField(answer2);
    }
    
    @Test
    public void createTest() {
    	createAndLoginUserWithEmail(EMAIL1);
    	
    	Assessment assessment = buildAssessmentWithName(NAME1);
    	Question question = buildQuestionWithNameAndAssessment(QUESTION1, assessment);
    	Answer created = buildAnswer(ANSW1, question);
    	
    	assertThat(created.getAnswer()).isEqualTo(ANSW1);
    	assertThat(created.getQuestion()).isEqualToComparingFieldByField(question);   	
    }
    
    @Test
    public void deleteTest() {
    	createAndLoginUserWithEmail(EMAIL1);
    	Question question = buildQuestionWithNameAndAssessment(QUESTION1, buildAssessmentWithName(NAME1));
    	Answer answer = buildAnswer(ANSW1, question);
    	
    	ResponseEntity<Answer> deleteResponse = answerController.delete(answer.getId());
    	
    	assertThat(deleteResponse.getStatusCode()).isEqualTo(OK);    	    	
    }
    
    @Test
    public void updateTest() {
    	createAndLoginUserWithEmail(EMAIL1);
    	Question question = buildQuestionWithNameAndAssessment(QUESTION1, buildAssessmentWithName(NAME1));
    	Answer answer = buildAnswer(ANSW1, question);
    	
    	answer.setAnswer(ANSW2);
    	
    	Answer updated = answerController.update(answer.getId(), answer).getBody();
    	
    	assertThat(updated.getAnswer()).isEqualTo(ANSW2);
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
    
    private Answer buildAnswer(String name, Question question) {
    	Answer answer = new Answer();
    	answer.setAnswer(name);
    	return answerController.create(answer, question.getId()).getBody();
    }
    
    private Question buildQuestionWithNameAndAssessment(String name, Assessment assessment) {
    	Question question = new Question();
    	question.setCategory(CAT1);
    	question.setQuestion(name);
    	question.setAssessment(assessment);
    	
    	return questionController.create(question, assessment.getId()).getBody();
    }
}
