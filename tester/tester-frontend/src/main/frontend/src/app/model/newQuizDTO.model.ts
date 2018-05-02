import { Question } from './question.model';

export class newQuizDTO {
  constructor(
      public name: string,
      public questions: Question[],
  ){}
}