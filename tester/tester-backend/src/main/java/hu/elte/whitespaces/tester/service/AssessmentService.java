package hu.elte.whitespaces.tester.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import hu.elte.whitespaces.tester.model.Assessment;
import hu.elte.whitespaces.tester.model.User;
import hu.elte.whitespaces.tester.repository.AssessmentRepository;

@Service
@SessionScope
public class AssessmentService {
	
	private AssessmentRepository assessmentRepository;
	
	@Autowired
	public AssessmentService(AssessmentRepository assessmentRepository) {
		this.assessmentRepository = assessmentRepository;
	}
	
	public Assessment getAssessmentById(Integer Id) {
		Optional<Assessment> result = assessmentRepository.findById(Id);
		
		if (result.isPresent()) {
			return result.get();
		}
		
		return null;
	}
	
	public List<Assessment> getAllAssessmentsByUserId(Integer userId) {
		Iterable<Assessment> assessments = assessmentRepository.findAllByUserId(userId);
		List<Assessment> results = new ArrayList<>();
		
		assessments.forEach(assessment -> results.add(assessment));
		return results;
	}
	
	public List<Assessment> getAllAssessments() {
		Iterable<Assessment> assessments = assessmentRepository.findAll();
		List<Assessment> results = new ArrayList<>();
		
		assessments.forEach(assessment -> results.add(assessment));
		return results;
	}
	
	@Transactional
	public Assessment create(Assessment assessment, User user) {
		assessment.setUser(user);
		return assessmentRepository.save(assessment);
	}
	
	@Transactional
	public Assessment update(int Id, Assessment assessment) {
		Optional<Assessment> result = assessmentRepository.findById(Id);
		
		if (result.isPresent()) {
			Assessment currentAssessment = result.get();
			currentAssessment.setName(assessment.getName());
			currentAssessment.setStat(assessment.getStat());
			
			return assessmentRepository.save(currentAssessment);
		}
		
		return null;
	}
	
	@Transactional
	public boolean delete(int Id) {
		Optional<Assessment> result = assessmentRepository.findById(Id);
		
		if (result.isPresent()) {
			assessmentRepository.delete(result.get());
			return true;
		}
		
		return false;
	}
	
}