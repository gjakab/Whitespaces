import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { HashLocationStrategy, LocationStrategy } from '@angular/common';
import { NgxPaginationModule } from 'ngx-pagination';
import { NgxDatatableModule } from '@swimlane/ngx-datatable';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { AppComponent } from './app.component';
import { SignInComponent } from './component/user/sign-in/sign-in.component';
import { SignUpComponent } from './component/user/sign-up/sign-up.component';
import { AppRoutingModule } from './routing/app-routing.module';
import { UserService } from './service/user.service';
import { QuizService } from './service/quiz.service';
import { QuestionService } from './service/question.service';
import { HeaderComponent } from './component/header/header.component';
import { QuizListComponent } from './component/user/teacher/quiz-list/quiz-list.component';
import { NewQuizComponent } from './component/user/teacher/new-quiz/new-quiz.component';
import { ViewQuizComponent } from './component/user/teacher/view-quiz/view-quiz.component';
import { EditDataModalComponent } from './component/user/edit-data-modal/edit-data-modal.component';
import { AnswerService } from './service/answer.service';
import { ViewResultsComponent } from './component/user/teacher/view-results/view-results.component';
import { QuizResultService } from './service/quiz-result.service';
import { FindQuizComponent } from './component/user/student/find-quiz/find-quiz.component';
import { QuizResultsComponent } from './component/user/student/quiz-results/quiz-results.component';
import { AuthGuardTeacher } from './service/guard/auth-guard-teacher.service';
import { AuthGuardStudent } from './service/guard/auth-guard-student.service';
import { QuizFillComponent } from './component/user/student/quiz-fill/quiz-fill.component';

@NgModule({
  declarations: [
    AppComponent,
    SignInComponent,
    SignUpComponent,
    HeaderComponent,
    QuizListComponent,
    NewQuizComponent,
    ViewQuizComponent,
    EditDataModalComponent,
    ViewResultsComponent,
    FindQuizComponent,
    QuizResultsComponent,
    QuizFillComponent,
  ],
  imports: [
    NgbModule.forRoot(),
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpModule,
    NgxPaginationModule,
    NgxDatatableModule
  ],
  providers: [
    UserService,
    QuizService,
    QuestionService,
    AnswerService,
    QuizResultService,
    AuthGuardTeacher,
    AuthGuardStudent,
    {
      provide: LocationStrategy,
      useClass: HashLocationStrategy
    }
  ],
  bootstrap: [AppComponent],
  entryComponents: [
    EditDataModalComponent,
  ]
})
export class AppModule { }
