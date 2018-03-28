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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hu.elte.whitespaces.tester.model.Assessment;
import hu.elte.whitespaces.tester.service.AssessmentService;
import hu.elte.whitespaces.tester.service.UserService;

@CrossOrigin(origins = {"http://localhost:4200"}) //This is need for development
@RestController
@RequestMapping("/api/users/assessments")
public class AssessmentController {
	
	private final static String ASSESSMENT_ID ="/id";
	private final static String ASSESSMENT_LIST ="/all";
	
	private final AssessmentService assessmentService;
	private final UserService userService;
	
	@Autowired
	public AssessmentController(AssessmentService assessmentService, UserService userService) {
		this.assessmentService = assessmentService;
		this.userService = userService;
	}
	
	@GetMapping("")
	public ResponseEntity<List<Assessment>> getAllAssessmentsByUserId() {
		return ResponseEntity.ok(assessmentService.getAllAssessmentsByUserId(userService.getCurrentUser().getId()));
	}
	
	@GetMapping(ASSESSMENT_ID)
	public ResponseEntity<Assessment> getAssessmentById(@RequestParam(name = "assessmentId") Integer Id) {
		Assessment response = assessmentService.getAssessmentById(Id);
		
		if (response != null) {
			return ResponseEntity.ok(response);
		}
		return ResponseEntity.status(NOT_FOUND).build();
	}
	
	@GetMapping(ASSESSMENT_LIST)
	public ResponseEntity<List<Assessment>> getAllAssessments() {
		return ResponseEntity.ok(assessmentService.getAllAssessments());
	}
	
	@PostMapping("")
	public ResponseEntity<Assessment> create(@Valid @RequestBody Assessment assessment) {
		Assessment saved = assessmentService.create(assessment, userService.getCurrentUser());
		
		if (saved != null) {
			return ResponseEntity.ok(saved);
		}
		return ResponseEntity.status(NOT_FOUND).build();
	}
	
	@DeleteMapping(ASSESSMENT_ID)
	public ResponseEntity<Assessment> delete(@PathVariable Integer aId) {
		if (assessmentService.delete(aId)) {
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.status(NOT_FOUND).build();
	}
	
	@PatchMapping(ASSESSMENT_ID)
	public ResponseEntity<Assessment> update(@PathVariable Integer aId, @Valid @RequestBody Assessment assessment) {
		Assessment updated = assessmentService.update(aId, assessment);
		
		if (updated != null) {
			return ResponseEntity.ok(updated);
		}
		return ResponseEntity.status(NOT_FOUND).build();
	}

}
