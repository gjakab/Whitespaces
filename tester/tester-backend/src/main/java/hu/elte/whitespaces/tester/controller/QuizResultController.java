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

import hu.elte.whitespaces.tester.model.AssessmentResult;
import hu.elte.whitespaces.tester.model.dto.QuizResultDTO;
import hu.elte.whitespaces.tester.model.dto.StudentQuizResultDTO;
import hu.elte.whitespaces.tester.service.QuizResultService;
import hu.elte.whitespaces.tester.service.UserService;

@CrossOrigin(origins = { "http://localhost:4200" }) // This is needed for development
@RestController
@RequestMapping("/api/users/quizresults")
public class QuizResultController {

    private static final String QUIZ_ID = "/{quizId}";
    private static final String QUIZRESULT_ID = "/{quizId}/{quizResultId}";

    private QuizResultService quizResultService;
    private UserService userService;

    @Autowired
    public QuizResultController(QuizResultService quizResultService, UserService userService) {
        this.quizResultService = quizResultService;
        this.userService = userService;
    }

    @GetMapping("")
    public ResponseEntity<List<StudentQuizResultDTO>> getAllQuizResultsByUserId() {
        return ResponseEntity.ok(quizResultService.getAllQuizResultsByUserId(userService.getCurrentUser().getId()));
    }

    @GetMapping(QUIZ_ID)
    public ResponseEntity<List<QuizResultDTO>> getAllQuizResultsByQuizId(@PathVariable Integer quizId) {
        return ResponseEntity.ok(quizResultService.getAllQuizResultsByQuiz(quizId));
    }

    @PostMapping(QUIZ_ID)
    public ResponseEntity<AssessmentResult> create(@Valid @RequestBody AssessmentResult quizResult,
            @PathVariable Integer quizId) {
        AssessmentResult saved = quizResultService.create(quizId, userService.getCurrentUser(), quizResult);
        if (saved != null) {
            return ResponseEntity.ok(saved);
        }
        return ResponseEntity.status(NOT_FOUND).build();
    }

    @DeleteMapping(QUIZRESULT_ID)
    public ResponseEntity<AssessmentResult> delete(@PathVariable Integer quizResultId) {
        if (quizResultService.delete(quizResultId)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(NOT_FOUND).build();
    }

}
