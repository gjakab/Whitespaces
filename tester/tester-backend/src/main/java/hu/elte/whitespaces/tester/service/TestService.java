package hu.elte.whitespaces.tester.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.elte.whitespaces.tester.model.Test;
import hu.elte.whitespaces.tester.model.User;
import hu.elte.whitespaces.tester.repository.TestRepository;

@Service
public class TestService {
	
	private TestRepository testRepository;
	
	@Autowired
	public TestService(TestRepository testRepository) {
		this.testRepository = testRepository;
	}
	
	public Test getTestById(Integer Id) {
		Optional<Test> result = testRepository.findById(Id);
		
		if (result.isPresent()) {
			return result.get();
		}
		
		return null;
	}
	
	public List<Test> getAllTestsByUserId(Integer userId) {
		Iterable<Test> tests = testRepository.findAllByUserId(userId);
		List<Test> results = new ArrayList<>();
		
		tests.forEach(test -> results.add(test));
		return results;
	}
	
	public List<Test> getAllTests() {
		Iterable<Test> tests = testRepository.findAll();
		List<Test> results = new ArrayList<>();
		
		tests.forEach(test -> results.add(test));
		return results;
	}
	
	@Transactional
	public Test create(Test test, User user) {
		test.setUser(user);
		return testRepository.save(test);
	}
	
	@Transactional
	public Test update(int Id, Test test) {
		Optional<Test> result = testRepository.findById(Id);
		
		if (result.isPresent()) {
			Test currentTest = result.get();
			currentTest.setName(test.getName());
			currentTest.setStat(test.getStat());
			
			return testRepository.save(currentTest);
		}
		
		return null;
	}
	
	@Transactional
	public boolean delete(int Id) {
		Optional<Test> result = testRepository.findById(Id);
		
		if (result.isPresent()) {
			testRepository.delete(result.get());
			return true;
		}
		
		return false;
	}
	
}
