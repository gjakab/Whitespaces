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

import hu.elte.whitespaces.tester.model.Answer;
import hu.elte.whitespaces.tester.service.AnswerService;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api/users/assessments/questions/{qId}")
public class AnswerController {
    private static final String ANSWER_ID = "/{anId}";
    private static final String ANSWER_LIST = "/answers";

    private final AnswerService answerService;

    @Autowired
    public AnswerController(final AnswerService answerService) {
        this.answerService = answerService;
    }

    @GetMapping(ANSWER_ID)
    public final ResponseEntity<Answer> getAnswerById(@PathVariable final Integer qId, @PathVariable final Integer anId) {
        Answer response = answerService.getAnswerByQuestionId(anId);

        if (response != null) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(NOT_FOUND).build();
    }

    @GetMapping(ANSWER_LIST)
    public final ResponseEntity<List<Answer>> getAllAnswersByQuestionId(@PathVariable final Integer qId) {
        return ResponseEntity.ok(answerService.getAllAnswersByQuestion(qId));
    }

    @PostMapping("")
    public final ResponseEntity<Answer> create(@Valid @RequestBody final Answer answer, final Integer qId) {
        Answer saved = answerService.create(answer, qId);

        if (saved != null) {
            return ResponseEntity.ok(saved);
        }
        return ResponseEntity.status(NOT_FOUND).build();
    }

    @DeleteMapping(ANSWER_ID)
    public final ResponseEntity<Answer> delete(@PathVariable final Integer anId) {
        if (answerService.delete(anId)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(NOT_FOUND).build();
    }

    @PatchMapping(ANSWER_ID)
    public final ResponseEntity<Answer> update(@PathVariable final Integer anId, @Valid @RequestBody final Answer answer) {
        Answer updated = answerService.update(anId, answer);

        if (updated != null) {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.status(NOT_FOUND).build();
    }
}
