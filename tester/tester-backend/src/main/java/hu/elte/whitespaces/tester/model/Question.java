package hu.elte.whitespaces.tester.model;



import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
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
public class Question extends BaseEntity{
	
	// TODO Enum vagy String?
	@Column(nullable = false)
	private String category;
		
	@Column(nullable = false)
	private String question;
	
    @JsonIgnore
    @ManyToOne(targetEntity = Test.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "TEST_ID")
    private Test test;
	
	@ElementCollection
    @MapKeyColumn(name="ANSWER")
    @Column(name="CORRECT_ANSWER")
    @CollectionTable(name="ANSWERS")
    Map<String, Boolean> answers;
	
}
