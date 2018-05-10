package hu.elte.whitespaces.tester.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuizResultDTO {
	
	private int id;
	private int score;
	private double stats;
	private String userName;
	private int quizId; 

}
