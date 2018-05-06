import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { HashLocationStrategy, LocationStrategy } from '@angular/common';
import { NgxPaginationModule } from 'ngx-pagination';
import { NgxDatatableModule } from '@swimlane/ngx-datatable';

import { AppComponent } from './app.component';
import { SignInComponent } from './component/user/sign-in/sign-in.component';
import { SignUpComponent } from './component/user/sign-up/sign-up.component';
import { AppRoutingModule } from './routing/app-routing.module';
import { UserService } from './service/user.service';
import { QuizService } from './service/quiz.service';
import { HeaderComponent } from './component/header/header.component';
import { QuizListComponent } from './component/user/teacher/quiz-list/quiz-list.component';
import { NewQuizComponent } from './component/user/teacher/new-quiz/new-quiz.component';
import { ViewQuizComponent } from './component/user/teacher/view-quiz/view-quiz.component';


@NgModule({
  declarations: [
    AppComponent,
    SignInComponent,
    SignUpComponent,
    HeaderComponent,
    QuizListComponent,
    NewQuizComponent,
    ViewQuizComponent,
  ],
  imports: [
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
    {
      provide: LocationStrategy,
      useClass: HashLocationStrategy
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
