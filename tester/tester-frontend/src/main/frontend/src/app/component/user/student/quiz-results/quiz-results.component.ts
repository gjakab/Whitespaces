import { Component, OnInit, ViewChild, ViewEncapsulation } from '@angular/core';
import { QuizResultService } from '../../../../service/quiz-result.service';
import { DatatableComponent } from '@swimlane/ngx-datatable';
import { StudentQuizResultDTO } from '../../../../model/studentQuizResultDTO';

@Component({
  selector: 'app-view-results',
  templateUrl: './quiz-results.component.html',
  styleUrls: [
    '../../../../app.component.css',
    '../../../../../../node_modules/@swimlane/ngx-datatable/release/themes/bootstrap.css',
    '../../../../../../node_modules/@swimlane/ngx-datatable/release/index.css',
    '../../../../../../node_modules/@swimlane/ngx-datatable/release/assets/icons.css'
  ],
  encapsulation: ViewEncapsulation.None
})
export class QuizResultsComponent implements OnInit {
  @ViewChild(DatatableComponent) table: DatatableComponent;
  quizResults: StudentQuizResultDTO[];
  temp: StudentQuizResultDTO[];
  private messages = {
    emptyMessage: `
      <div class="mt-15 mb-15 text-center">
        Nincs a keresésnek megfelelő quiz eredmény
      </div>
    `
  }
  constructor(
    private quizResultService: QuizResultService,
  ) { }

  ngOnInit() {
    this.quizResultService.getQuizResultsOfCurrentUser()
      .subscribe(
        (quizResults: StudentQuizResultDTO[]) => {
          this.quizResults = quizResults;
          this.temp = [...quizResults];
          console.log("RESPONSE", this.quizResults)
          },
        (error) => console.log(error)
      )
  }

  updateFilter(event) {
    const value = event.target.value.toLowerCase();
    const temp = this.temp.filter(function(quizResult) {
      return quizResult.quizName.toLowerCase().indexOf(value) !== -1 || !value;
    });

    this.quizResults = temp;
    this.table.offset = 0;
  }

}
