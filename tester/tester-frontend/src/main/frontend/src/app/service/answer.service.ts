import { Injectable } from "@angular/core";
import { Http, Response } from "@angular/http";
import { Observable } from 'rxjs/Observable';
import 'rxjs/Rx';

import { Answer } from "../model/answer.model";

@Injectable()
export class AnswerService{

  constructor(private http: Http) {}

  updateAnswer(questionId: number, answer: Answer) {
    console.log("FRISSÍTETT VÁLASZ", answer)
    return this.http.patch('/api/users/assessments/questions/' + questionId + '/' + answer.id, answer)
      .catch(
        (error: Response) => {
          return Observable.throw(error);
        }
      )
  }

  deleteAnswer(questionId: number, answerId: number) {
    return this.http.delete('/api/users/assessments/questions/' + questionId + '/' + answerId)
      .catch(
        (error: Response) => {
          return Observable.throw(error);
        }
      )
  }

  
}