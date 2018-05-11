package hu.elte.whitespaces.tester.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizResultDTO {

    private int id;
    private int score;
    private double stats;
    private String userName;
    private int quizId;

}
