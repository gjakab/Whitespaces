package hu.elte.whitespaces.tester.model;

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
@Table(name = "QUESTIONS")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class Question extends BaseEntity {
	
	// TODO Enum vagy String?
	@Column(nullable = false)
	private String category;
		
	@Column(nullable = false)
	private String question;
	
    @JsonIgnore
    @ManyToOne(targetEntity = Assessment.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "ASSESSMENT_ID")
    private Assessment assessment;
	
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "QUESTION_ID")
    private List<Answer> answers;
    
    @Override
    public String toString() {
    	return "question: " + this.question + ", category: " + this.category;
    }
	
}
