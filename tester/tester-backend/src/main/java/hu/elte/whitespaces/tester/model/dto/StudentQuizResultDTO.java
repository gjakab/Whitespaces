package hu.elte.whitespaces.tester.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentQuizResultDTO {

    private int score;
    private double stats;
    private String quizName;

}
