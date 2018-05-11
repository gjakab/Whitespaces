import { Question } from './question.model';

export class NewQuizDTO {
  constructor(
      public name: string,
      public questions: Question[],
  ){}
}