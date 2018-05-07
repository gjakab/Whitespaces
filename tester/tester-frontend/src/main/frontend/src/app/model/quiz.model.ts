import { Question } from './question.model';

export class Quiz {
  public questions: Question[];

  constructor(
      public id: number,
      public name: string,
      public creationDate: Date,
      public stat: number
  ){}
}