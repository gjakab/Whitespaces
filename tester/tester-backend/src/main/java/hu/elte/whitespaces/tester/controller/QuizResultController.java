package hu.elte.whitespaces.tester.controller;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hu.elte.whitespaces.tester.config.Role;
import hu.elte.whitespaces.tester.model.AssessmentResult;
import hu.elte.whitespaces.tester.model.User;
import hu.elte.whitespaces.tester.model.dto.QuizResultDTO;
import hu.elte.whitespaces.tester.model.dto.StudentQuizResultDTO;
import hu.elte.whitespaces.tester.service.QuizResultService;
import hu.elte.whitespaces.tester.service.UserService;

/**
 * Controller class for quiz results
 * 
 * @author WhiteSpaces
 *
 */
@CrossOrigin(origins = { "http://localhost:4200" }) // This is needed for development
@RestController
@RequestMapping("/api/users/quizresults")
public class QuizResultController {

    private static final String QUIZ_ID = "/{quizId}";
    private static final String QUIZRESULT_ID = "/{quizId}/{quizResultId}";

    private QuizResultService quizResultService;
    private UserService userService;

    /**
     * Constructor
     * 
     * @param quizResultService Service for Quiz result
     * @param userService Service for User
     */
    @Autowired
    public QuizResultController(QuizResultService quizResultService, UserService userService) {
        this.quizResultService = quizResultService;
        this.userService = userService;
    }

    /**
     * List all Quiz results of caller user
     * 
     * @return List of Quiz results
     */
    @Role(User.Role.STUDENT)
    @GetMapping("")
    public ResponseEntity<List<StudentQuizResultDTO>> getAllQuizResultsByUserId() {
        return ResponseEntity.ok(quizResultService.getAllQuizResultsByUserId(userService.getCurrentUser().getId()));
    }

    /**
     * List all Quiz results by Quiz
     * 
     * @param quizId Quiz ID to look up
     * @return List of Quiz results
     */
    @Role(User.Role.TEACHER)
    @GetMapping(QUIZ_ID)
    public ResponseEntity<List<QuizResultDTO>> getAllQuizResultsByQuizId(@PathVariable Integer quizId) {
        return ResponseEntity.ok(quizResultService.getAllQuizResultsByQuiz(quizId));
    }

    /**
     * Create new Quiz result
     * 
     * @param quizResult Quiz result to be created
     * @param quizId Quiz ID to create result for
     * @return Created Quiz result or NOT_FOUND if creation failed
     */
    @Role(User.Role.STUDENT)
    @PostMapping(QUIZ_ID)
    public ResponseEntity<AssessmentResult> create(@Valid @RequestBody AssessmentResult quizResult,
            @PathVariable Integer quizId) {
        AssessmentResult saved = quizResultService.create(quizId, userService.getCurrentUser(), quizResult);
        if (saved != null) {
            return ResponseEntity.ok(saved);
        }
        return ResponseEntity.status(NOT_FOUND).build();
    }

    /**
     * Delete Quiz result
     * 
     * @param quizResultId ID of Quiz result to be deleted
     * @return OK response or NOT_FOUND if not found
     */
    @Role(User.Role.TEACHER)
    @DeleteMapping(QUIZRESULT_ID)
    public ResponseEntity<AssessmentResult> delete(@PathVariable Integer quizResultId) {
        if (quizResultService.delete(quizResultId)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(NOT_FOUND).build();
    }

}
