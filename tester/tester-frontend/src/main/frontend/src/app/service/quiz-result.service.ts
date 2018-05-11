import { Injectable } from "@angular/core";
import { Http, Response } from "@angular/http";
import { Observable } from 'rxjs/Observable';
import 'rxjs/Rx';

import { QuizResultDTO } from "../model/quizResultDTO";

@Injectable()
export class QuizResultService{

  constructor(private http: Http) {}

  getQuizResultsOfQuiz(quizId: number) {
    return this.http.get('/api/users/quizresults/' + quizId)
      .map((response: Response) => {
        const quizzes: QuizResultDTO[] = response.json();
        return quizzes;
      })
      .catch(
        (error: Response) => {
          return Observable.throw(error);
        }
      )
  }

  getQuizResultsOfCurrentUser() {
    return this.http.get('/api/users/quizresults')
      .map((response: Response) => {
        const quizzes: QuizResultDTO[] = response.json();
        return quizzes;
      })
      .catch(
        (error: Response) => {
          return Observable.throw(error);
        }
      )
  } 

  deleteQuizResult(quizId: number, quizResultId) {
    return this.http.delete('/api/users/quizresults/' + quizId + "/" + quizResultId)
      .catch(
        (error: Response) => {
          return Observable.throw(error);
        }
      )
  }

}