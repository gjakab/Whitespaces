import { Injectable } from "@angular/core";
import { Http, Response } from "@angular/http";
import { Observable } from 'rxjs/Observable';
import 'rxjs/Rx';

import { Quiz } from "../model/quiz.model";
import { newQuizDTO } from "../model/newQuizDTO.model";

@Injectable()
export class QuizService{

  constructor(private http: Http) {}

  getQuizzesOfUser() {
    return this.http.get('/api/users/quizzes')
      .map((response: Response) => {
        const quizzes: Quiz[] = response.json();
        return quizzes;
      })
      .catch(
        (error: Response) => {
          return Observable.throw(error);
        }
      )
  }

  deleteQuiz(quizId: Number) {
    console.log(quizId);
    return this.http.delete('/api/users/quizzes/' + quizId)
      .catch(
        (error: Response) => {
          return Observable.throw(error);
        }
      )
  }

  createQuiz(newQuiz: newQuizDTO) {
    console.log("UJ QUIZ", newQuiz)
    return this.http.post('/api/users/quizzes/', newQuiz)
      .catch(
        (error: Response) => {
          return Observable.throw(error);
        }
      )
  }

  getQuizById(quizId: number) {
    return this.http.get('/api/users/quizzes/' + quizId)
      .map((response: Response) => {
        const quiz: Quiz = response.json();
        return quiz;
      })
      .catch(
        (error: Response) => {
          return Observable.throw(error);
        }
      )
  }
}