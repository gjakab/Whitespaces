import { Injectable } from "@angular/core";
import { Http, Response } from "@angular/http";
import { Observable } from 'rxjs/Observable';
import 'rxjs/Rx';

import { Quiz } from "../model/quiz.model";

@Injectable()
export class QuizService{

  constructor(private http: Http) {}

  getQuizzesOfUser() {
    return this.http.get('/api/users/quizzes')
      .map((response: Response) => {
        const quizzes: Quiz[] = response.json();
        return quizzes;
      }).catch(
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
}