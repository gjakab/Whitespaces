export class QuizResultDTO {
  constructor(
      public score: number,
      public stats: number,
      public userName: string,
      public quizId: number,
      public id = null
  ){}
}