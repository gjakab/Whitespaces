package hu.elte.whitespaces.tester.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ASSESSMENTS")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class Assessment extends BaseEntity {
		
	@Column(nullable = false, unique = true)
	private String name;
	
	@Column(nullable = false)
	private Timestamp creationDate;
	
	@Column(nullable = true)
	private Double stat;

    @JsonIgnore
    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;
    
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "assessment", cascade = CascadeType.ALL)
    private List<Question> questions;
    
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "assessment")
    private List<AssessmentResult> assessmentResults;
}
