import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { HashLocationStrategy, LocationStrategy } from '@angular/common';
import { NgxPaginationModule } from 'ngx-pagination';

import { AppComponent } from './app.component';
import { SignInComponent } from './component/user/sign-in/sign-in.component';
import { SignUpComponent } from './component/user/sign-up/sign-up.component';
import { AppRoutingModule } from './routing/app-routing.module';
import { UserService } from './service/user.service';
import { QuizService } from './service/quiz.service';
import { HeaderComponent } from './component/header/header.component';
import { QuizListComponent } from './component/user/teacher/quiz-list/quiz-list.component';


@NgModule({
  declarations: [
    AppComponent,
    SignInComponent,
    SignUpComponent,
    HeaderComponent,
    QuizListComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpModule,
    NgxPaginationModule
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
