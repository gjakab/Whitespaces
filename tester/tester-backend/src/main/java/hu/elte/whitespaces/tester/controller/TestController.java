package hu.elte.whitespaces.tester.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hu.elte.whitespaces.tester.model.Test;
import hu.elte.whitespaces.tester.service.TestService;

@CrossOrigin(origins = {"http://localhost:4200"}) //This is need for development
@RestController
@RequestMapping("/api/users/tests")
public class TestController {
	
	private final static String TEST_ID ="/id";
	private final static String TEST_LIST ="/all";
	
	private final TestService testService;
	
	@Autowired
	public TestController(TestService testService) {
		this.testService = testService;
	}
	
	// TODO UserService-ből a beloggolt user használata
//	@GetMapping("")
//	public ResponseEntity<List<Test>> getAllTestsByUserId(int userId) {
//		return ResponseEntity.ok(testService.getAllTestsByUserId(userId));
//	}
	
	@GetMapping(TEST_ID)
	public ResponseEntity<Test> getTestById(@RequestParam(name = "testId") Integer Id) {
		Test response = testService.getTestById(Id);
		
		if (response != null) {
			return ResponseEntity.ok(response);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	
	@GetMapping(TEST_LIST)
	public ResponseEntity<List<Test>> getAllTests() {
		return ResponseEntity.ok(testService.getAllTests());
	}
	
//	@PostMapping("")
//	public ResponseEntity<Test> create(@Valid @RequestBody Test test) {
//		Test saved = testService.create(test, user)
//		
//		if (saved != null) {
//			return ResponseEntity.ok(saved);
//		}
//		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//	}
	
	@DeleteMapping(TEST_ID)
	public ResponseEntity<Test> delete(@RequestParam(name="testId") Integer Id) {
		if (testService.delete(Id)) {
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	
	@PatchMapping(TEST_ID)
	public ResponseEntity<Test> update(@RequestParam(name="testId") Integer Id, @Valid @RequestBody Test test) {
		Test updated = testService.update(Id, test);
		
		if (updated != null) {
			return ResponseEntity.ok(updated);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	
	
	
	

}
