import { Injectable } from "@angular/core";
import { Http, Response } from "@angular/http";
import { Router } from "@angular/router";
import { Observable } from 'rxjs/Observable';
import 'rxjs/Rx';

import { QuizResultDTO } from "../model/quizResultDTO";
import { QuizResult } from "../model/quiz-result.model";

@Injectable()
export class QuizResultService{

  constructor(private http: Http,
              private router: Router) {}

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

  createQuizResult(quizResult: QuizResult, quizId: number) {
    return this.http.post('/api/users/quizresults/' + quizId, quizResult)
        .map(
            (response: Response) => {
                    this.router.navigate(['users/student/findquiz']);
                }
            )
        .catch(
            (error: Response) => {
                return Observable.throw(error);
            }
        );
}

}