import { Answer } from './answer.model'

export class Question {
  constructor(
      public question: number,
      public category: string,
      public answers: Answer[]
  ){}
}