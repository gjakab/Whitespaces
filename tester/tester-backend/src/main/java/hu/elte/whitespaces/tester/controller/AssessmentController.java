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

import hu.elte.whitespaces.tester.model.Assessment;
import hu.elte.whitespaces.tester.service.AssessmentService;
import hu.elte.whitespaces.tester.service.UserService;

@CrossOrigin(origins = { "http://localhost:4200" }) // This is need for development
@RestController
@RequestMapping("/api/users/quizzes")
public class AssessmentController {

    private static final String QUIZ_ID = "/{quizId}";
    private static final String QUIZ_LIST = "/all";

    private final AssessmentService assessmentService;
    private final UserService userService;

    @Autowired
    public final AssessmentController(final AssessmentService assessmentService, final UserService userService) {
        this.assessmentService = assessmentService;
        this.userService = userService;
    }

    @GetMapping("")
    public final ResponseEntity<List<Assessment>> getAllAssessmentsByUserId() {
        return ResponseEntity.ok(assessmentService.getAllAssessmentsByUserId(userService.getCurrentUser().getId()));
    }

    @GetMapping(QUIZ_ID)
    public final ResponseEntity<Assessment> getAssessmentById(@PathVariable final Integer quizId) {
        Assessment response = assessmentService.getAssessmentById(quizId);

        if (response != null) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(NOT_FOUND).build();
    }

    @GetMapping(QUIZ_LIST)
    public final ResponseEntity<List<Assessment>> getAllAssessments() {
        return ResponseEntity.ok(assessmentService.getAllAssessments());
    }

    // @Role({TEACHER, ADMIN})
    @PostMapping("")
    public final ResponseEntity<Assessment> create(@Valid @RequestBody final Assessment assessment) {
        System.out.println("ASSESMENT: " + assessment);
        Assessment saved = assessmentService.create(assessment, userService.getCurrentUser());
        if (saved != null) {
            return ResponseEntity.ok(saved);
        }
        return ResponseEntity.status(NOT_FOUND).build();
    }

    @DeleteMapping(QUIZ_ID)
    public final ResponseEntity<Assessment> delete(@PathVariable final Integer quizId) {
        if (assessmentService.delete(quizId)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(NOT_FOUND).build();
    }

    @PatchMapping(QUIZ_ID)
    public final ResponseEntity<Assessment> update(@PathVariable final Integer quizId,
            @Valid @RequestBody final Assessment assessment) {
        Assessment updated = assessmentService.update(quizId, assessment);

        if (updated != null) {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.status(NOT_FOUND).build();
    }

}
