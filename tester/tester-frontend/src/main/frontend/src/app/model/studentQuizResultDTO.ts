import { Question } from './question.model';

export class StudentQuizResultDTO {
  constructor(
    public quizName: string,
    public score: number,
    public stats: number
  ){}
}