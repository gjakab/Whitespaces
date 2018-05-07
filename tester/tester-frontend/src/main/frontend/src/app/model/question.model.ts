import { Answer } from './answer.model'

export class Question {
  constructor(
      public question: string,
      public category: string,
      public answers: Answer[],
      public id: number = null
  ){}
}