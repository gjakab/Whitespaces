package hu.elte.whitespaces.tester.controller;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hu.elte.whitespaces.tester.config.Role;
import hu.elte.whitespaces.tester.model.Question;
import hu.elte.whitespaces.tester.model.User;
import hu.elte.whitespaces.tester.service.QuestionService;

/**
 * Controller class for questions
 * 
 * @author WhiteSpaces
 *
 */
@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("api/users/quizzes/{qId}")
public class QuestionController {
    private static final String QUESTION_ID = "/question/{qId}";
    private static final String QUESTION_LIST = "/questionlist";

    private QuestionService questionService;

    /**
     * Constructor
     * 
     * @param questionService Service for Question
     */
    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    /**
     * Get Question by ID
     * 
     * @param qId Question ID referring to the Question
     * @return Question or NOT_FOUND if not found
     */
    @GetMapping(QUESTION_ID)
    public ResponseEntity<Question> getQuestionById(@PathVariable Integer qId) {
        Question response = questionService.getQuestionById(qId);

        if (response != null) {
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(NOT_FOUND).build();
    }

    /**
     * List all Questions belonging to specified Quiz
     * 
     * @param qId Quiz ID the questions belong to
     * @return List of Questions
     */
    @Role({ User.Role.TEACHER, User.Role.STUDENT })
    @GetMapping(QUESTION_LIST)
    public ResponseEntity<List<Question>> getAllQuestionsByAssessment(@PathVariable Integer qId) {
        return ResponseEntity.ok(questionService.getAllQuestionsByAssessmentId(qId));
    }

    /**
     * Create new Question
     * 
     * @param question Question to be created
     * @param qId ID of Question to be created
     * @return Created Question or NOT_FOUND if creation failed
     */
    @Role(User.Role.TEACHER)
    @PostMapping("")
    public ResponseEntity<Question> create(@Valid @RequestBody Question question, @PathVariable Integer qId) {
        Question saved = questionService.create(question, qId);

        if (saved != null) {
            return ResponseEntity.ok(saved);
        }

        return ResponseEntity.status(NOT_FOUND).build();
    }

    /**
     * Delete Question
     * 
     * @param qId ID of Question to be deleted
     * @return OK response or NOT_FOUND if not found
     */
    @Role(User.Role.TEACHER)
    @DeleteMapping(QUESTION_ID)
    public ResponseEntity<Question> delete(@PathVariable Integer qId) {
        if (questionService.delete(qId)) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.status(NOT_FOUND).build();
    }

    /**
     * Update existing Question
     * 
     * @param qId ID of Question to be updated
     * @param question Question data to update Question with
     * @return Updated Question or NOT_FOUND if not found
     */
    @Role(User.Role.TEACHER)
    @PatchMapping(QUESTION_ID)
    public ResponseEntity<Question> update(@PathVariable Integer qId, @Valid @RequestBody Question question) {
        Question updated = questionService.update(qId, question);

        if (updated != null) {
            return ResponseEntity.ok(updated);
        }

        return ResponseEntity.status(NOT_FOUND).build();
    }
}
