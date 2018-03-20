package hu.elte.whitespaces.tester.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ASSESSMENT_RESULTS")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class AssessmentResult extends BaseEntity {
	
	@Column(nullable = false)
	private int score;
	
	@Column(nullable = false)
	private double stats;
	
    @JsonIgnore
    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;
    
    @JsonIgnore
    @ManyToOne(targetEntity = Assessment.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "ASSESSMENT_ID")
    private Assessment assessment;
	
}
