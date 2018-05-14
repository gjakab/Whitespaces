import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgForm } from '@angular/forms';

import { Quiz } from '../../../../model/quiz.model';
import { QuizService } from '../../../../service/quiz.service';
import { Question } from '../../../../model/question.model';
import { UserService } from '../../../../service/user.service';
import { QuizResultDTO } from '../../../../model/quizResultDTO';
import { QuizResult } from '../../../../model/quiz-result.model';
import { QuizResultService } from '../../../../service/quiz-result.service';

@Component({
  selector: 'app-quiz-fill',
  templateUrl: './quiz-fill.component.html',
  styleUrls: ['./quiz-fill.component.css']
})
export class QuizFillComponent implements OnInit {

  public quiz: Quiz;
  private quizId: number;
  private score: number;

  constructor(private route: ActivatedRoute,
              private quizService: QuizService,
              private userService: UserService,
              private quizResultService: QuizResultService) {
    this.quizId = this.route.snapshot.params['quizId'];
    this.score = 0;
  }

  ngOnInit() {
    this.quizService.getQuizById(this.quizId)
        .subscribe(
          (quiz: Quiz) => {
            this.quiz = quiz;
          },
          (error) => console.log(error)
        )
  }

  onFillQuiz(form: NgForm) {
    const value = form.value;
    let qi:number = 0;
    for(let question of this.quiz.questions) {

      let ai:number = 0;
      let answerScore:number = 0;

      for(let answer of question.answers) {
        let id:string = "q-" + qi + "-a-" + ai;
        if(answer.rightAnswer === value[id]){
          answerScore++;
        }
        ai++;
      }
      if(question.answers.length === answerScore) {
        this.score++;
      }
      qi++;
    }
    let newQuizResult = new QuizResult(this.score, ((this.score/this.quiz.questions.length)*100));
    this.quizResultService.createQuizResult(newQuizResult, this.quizId).subscribe();
  }
}
