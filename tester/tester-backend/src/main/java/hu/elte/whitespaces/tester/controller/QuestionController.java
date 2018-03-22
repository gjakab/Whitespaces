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

import hu.elte.whitespaces.tester.model.Question;
import hu.elte.whitespaces.tester.service.QuestionService;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("api/users/assessments/{aId}")
public class QuestionController {
	private final static String QUESTION_ID = "/question/{qId}";
	private final static String QUESTION_LIST = "/questionlist";
	
	private final QuestionService questionService;
	
	@Autowired
	public QuestionController(QuestionService questionService) {
		this.questionService = questionService;
	}
	
	@GetMapping(QUESTION_ID)
	public ResponseEntity<Question> getQuestionById(@PathVariable Integer qId) {
		Question response = questionService.getQuestionById(qId);
		
		if (response != null) {
			return ResponseEntity.ok(response);
		}
		
		return ResponseEntity.status(NOT_FOUND).build();
	}
	
	@GetMapping(QUESTION_LIST)
	public ResponseEntity<List<Question>> getAllQuestionsByAssessment(@PathVariable Integer aId) {
		return ResponseEntity.ok(questionService.getAllQuestionsByAssessmentId(aId));
	}
	
	@PostMapping("")
	public ResponseEntity<Question> create(@Valid @RequestBody Question question, @PathVariable Integer aId) {
		Question saved = questionService.create(question, aId);
		
		if (saved != null) {
			return ResponseEntity.ok(saved);
		}
		
		return ResponseEntity.status(NOT_FOUND).build();
	}
	
	@DeleteMapping(QUESTION_ID)
	public ResponseEntity<Question> delete(@PathVariable Integer qId) {
		if (questionService.delete(qId)) {
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.status(NOT_FOUND).build();
	}
	
	@PatchMapping(QUESTION_ID)
	public ResponseEntity<Question> update(@PathVariable Integer qId, @Valid @RequestBody Question question) {
		Question updated = questionService.update(qId, question);
		
		if (updated != null) {
			return ResponseEntity.ok(updated);
		}
		
		return ResponseEntity.status(NOT_FOUND).build();
	}
}
