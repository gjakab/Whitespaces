import { Injectable } from "@angular/core";
import { Http, Response } from "@angular/http";
import { Observable } from 'rxjs/Observable';
import 'rxjs/Rx';

import { Quiz } from "../model/quiz.model";

@Injectable()
export class QuizService{

  constructor(private http: Http) {}

  getQuizzesOfUser() {
    return this.http.get('/api/users/assessments')
      .map((response: Response) => {
        const quizzes: Quiz[] = response.json();
        return quizzes;
      }).catch(
        (error: Response) => {
          return Observable.throw(error);
        }
      )
  }
}