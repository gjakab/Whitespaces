import { Component, OnInit, ViewChild, ViewEncapsulation } from '@angular/core';
import { QuizResultService } from '../../../../service/quiz-result.service';
import { ActivatedRoute } from '@angular/router';
import { QuizResultDTO } from '../../../../model/quizResultDTO';
import { DatatableComponent } from '@swimlane/ngx-datatable';

@Component({
  selector: 'app-view-results',
  templateUrl: './view-results.component.html',
  styleUrls: [
    '../../../../app.component.css',
    '../../../../../../node_modules/@swimlane/ngx-datatable/release/themes/bootstrap.css',
    '../../../../../../node_modules/@swimlane/ngx-datatable/release/index.css',
    '../../../../../../node_modules/@swimlane/ngx-datatable/release/assets/icons.css'
  ],
  encapsulation: ViewEncapsulation.None
})
export class ViewResultsComponent implements OnInit {
  @ViewChild(DatatableComponent) table: DatatableComponent;
  quizId: number;
  quizResults: QuizResultDTO[];
  temp: QuizResultDTO[];
  private messages = {
    emptyMessage: `
      <div class="mt-15 mb-15 text-center">
        Nincs a keresésnek megfelelő quiz eredmény
      </div>
    `
  }
  constructor(
    private quizResultService: QuizResultService,
    private activatedRoute: ActivatedRoute
  ) { }

  ngOnInit() {
    this.quizId = parseInt(this.activatedRoute.snapshot.paramMap.get('quizId'));
    this.quizResultService.getQuizResultsOfQuiz(this.quizId)
      .subscribe(
        (quizResults: QuizResultDTO[]) => {
          this.quizResults = quizResults;
          this.temp = [...quizResults];
          console.log("RESPONSE", this.quizResults)
          },
        (error) => console.log(error)
      )
  }

  delete(quizResult: QuizResultDTO) {
    this.quizResultService.deleteQuizResult(this.quizId, quizResult.id).subscribe(() => {
      var newArray = this.quizResults.slice();
      var index = newArray.indexOf(quizResult);
      newArray.splice(index, 1);
  
      this.quizResults = newArray.slice();
    });
  }

  updateFilter(event) {
    const value = event.target.value.toLowerCase();
    const temp = this.temp.filter(function(quizResult) {
      return quizResult.userName.toLowerCase().indexOf(value) !== -1 || !value;
    });

    this.quizResults = temp;
    this.table.offset = 0;
  }

}
