import { Component, OnInit, ViewEncapsulation, ViewChild } from '@angular/core';
import { DatatableComponent } from '@swimlane/ngx-datatable';
import { Quiz } from '../../../../model/quiz.model';
import { QuizService } from '../../../../service/quiz.service';
import { subscribeOn } from 'rxjs/operator/subscribeOn';

import * as moment from 'moment';

@Component({
  selector: 'app-quiz-list',
  templateUrl: './quiz-list.component.html',
  styleUrls: [
    '../../../../app.component.css',
    '../../../../../../node_modules/@swimlane/ngx-datatable/release/themes/bootstrap.css',
    '../../../../../../node_modules/@swimlane/ngx-datatable/release/index.css',
    '../../../../../../node_modules/@swimlane/ngx-datatable/release/assets/icons.css'
  ],
  encapsulation: ViewEncapsulation.None
})
export class QuizListComponent implements OnInit {
  @ViewChild(DatatableComponent) table: DatatableComponent;
  private quizzes: Quiz[];
  private messages = {
    emptyMessage: `
      <div class="mt-15 mb-15 text-center">
        Nincs a keresésnek megfelelő quiz
      </div>
    `
  }
  temp: Quiz[];

  constructor(private quizService: QuizService) { }

  getDate(date: Date) {
    return moment(date).format('YYYY/MM/DD');
  }

  delete(quiz: Quiz) {
    this.quizService.deleteQuiz(quiz.id).subscribe(() => {
      var newArray = this.quizzes.slice();
      var index = newArray.indexOf(quiz);
      newArray.splice(index, 1);
  
      this.quizzes = newArray.slice();
    });

/*     var newArray = this.quizzes.slice();
    var index = newArray.indexOf(quiz);
    newArray.splice(index, 1);

    this.quizzes = newArray.slice(); */
  }

  updateFilter(event) {
    const value = event.target.value.toLowerCase();

    // filter our data
    const temp = this.temp.filter(function(quiz) {
      return quiz.name.toLowerCase().indexOf(value) !== -1 || !value;
    });

    // update the rows
    this.quizzes = temp;
    // Whenever the filter changes, always go back to the first page
    this.table.offset = 0;
  }

  ngOnInit() {
    this.quizService.getQuizzesOfUser()
      .subscribe(
        (quizzes: Quiz[]) => {
          this.quizzes = quizzes;
          this.temp = [...quizzes];
          console.log("RESPONSE", quizzes)
          },
        (error) => console.log(error)
      )
  }

}
