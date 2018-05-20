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
import hu.elte.whitespaces.tester.model.Assessment;
import hu.elte.whitespaces.tester.model.User;
import hu.elte.whitespaces.tester.service.AssessmentService;
import hu.elte.whitespaces.tester.service.UserService;

/**
 * Controller class for quizzes
 *
 * @author WhiteSpaces
 *
 */
@CrossOrigin(origins = { "http://localhost:4200" }) // This is need for development
@RestController
@RequestMapping("/api/users/quizzes")
public class AssessmentController {

    private static final String QUIZ_ID = "/{quizId}";
    private static final String TEACHER_QUIZ_LIST = "/teacher";
    private static final String STUDENT_QUIZ_LIST = "/student";

    private AssessmentService assessmentService;
    private UserService userService;

    /**
     * Constructor
     *
     * @param assessmentService Service for Quizzes
     * @param userService Service for Users
     */
    @Autowired
    public AssessmentController(AssessmentService assessmentService, UserService userService) {
        this.assessmentService = assessmentService;
        this.userService = userService;
    }

    /**
     * List all Quizzes created by calling user
     *
     * @return List of Quizzes
     */
    @Role(User.Role.TEACHER)
    @GetMapping("")
    public ResponseEntity<List<Assessment>> getAllAssessmentsByUserId() {
        return ResponseEntity.ok(assessmentService.getAllAssessmentsByUserId(userService.getCurrentUser().getId()));
    }

    /**
     * Get a Quiz by specified ID
     *
     * @param quizId Quiz ID to be looked up
     * @return Quiz or NOT_FOUND if not found
     */
    @Role({ User.Role.TEACHER, User.Role.STUDENT })
    @GetMapping(QUIZ_ID)
    public ResponseEntity<Assessment> getAssessmentById(@PathVariable Integer quizId) {
        Assessment response = assessmentService.getAssessmentById(quizId);
        if (response != null) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(NOT_FOUND).build();
    }

    /**
     * List all Quizzes created
     *
     * @return List of Quizzes
     */
    @Role(User.Role.TEACHER)
    @GetMapping(TEACHER_QUIZ_LIST)
    public ResponseEntity<List<Assessment>> getAllAssessments() {
        return ResponseEntity.ok(assessmentService.getAllAssessments());
    }

    /**
     * List available Quizzes for calling user
     *
     * @return List of Quizzes
     */
    @Role(User.Role.STUDENT)
    @GetMapping(STUDENT_QUIZ_LIST)
    public ResponseEntity<List<Assessment>> getAvaiableQuizzesForUser() {
        return ResponseEntity.ok(assessmentService.getAvaiableQuizzesForUser(userService.getCurrentUser().getId()));
    }

    /**
     * Create new Quiz
     *
     * @param assessment Quiz data to be created
     * @return Quiz or NOT_FOUND if creation failed
     */
    @Role(User.Role.TEACHER)
    @PostMapping("")
    public ResponseEntity<Assessment> create(@Valid @RequestBody Assessment assessment) {
        Assessment saved = assessmentService.create(assessment, userService.getCurrentUser());
        if (saved != null) {
            return ResponseEntity.ok(saved);
        }
        return ResponseEntity.status(NOT_FOUND).build();
    }

    /**
     * Delete Quiz
     *
     * @param quizId ID of Quiz to be deleted
     * @return OK response or NOT_FOUND if not found
     */
    @Role(User.Role.TEACHER)
    @DeleteMapping(QUIZ_ID)
    public ResponseEntity<Assessment> delete(@PathVariable Integer quizId) {
        if (assessmentService.delete(quizId)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(NOT_FOUND).build();
    }

    /**
     * Update existing Quiz
     *
     * @param quizId ID of Quiz to be updated
     * @param assessment Quiz Data to update Quiz with
     * @return Updated Quiz or NOT_FOUND if not found
     */
    @Role(User.Role.TEACHER)
    @PatchMapping(QUIZ_ID)
    public ResponseEntity<Assessment> update(@PathVariable Integer quizId, @Valid @RequestBody Assessment assessment) {
        Assessment updated = assessmentService.update(quizId, assessment);

        if (updated != null) {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.status(NOT_FOUND).build();
    }
}
