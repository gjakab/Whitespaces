import { Injectable } from "@angular/core";
import { Http, Response } from "@angular/http";
import { Observable } from 'rxjs/Observable';
import 'rxjs/Rx';

import { Question } from "../model/question.model";

@Injectable()
export class QuestionService{

  constructor(private http: Http) {}

  updateQuestion(quizId: number, question: Question) {
    console.log("FRISSÍTETT KÉRDÉS", question)
    /*return this.http.patch('/api/users/quizzes/' + quizId + '/question/' + questionId, question)
      .catch(
        (error: Response) => {
          return Observable.throw(error);
        }
      ) */
  }

  deleteQuestion(quizId: number, questionId: Number) {
    return this.http.delete('/api/users/quizzes/' + quizId + '/question/' + questionId)
      .catch(
        (error: Response) => {
          return Observable.throw(error);
        }
      )
  }

  
}