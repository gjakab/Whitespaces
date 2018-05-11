import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";

import { SignUpComponent } from "../component/user/sign-up/sign-up.component";
import { SignInComponent } from "../component/user/sign-in/sign-in.component";
import { QuizListComponent } from "../component/user/teacher/quiz-list/quiz-list.component";
import { NewQuizComponent } from "../component/user/teacher/new-quiz/new-quiz.component";
import { ViewQuizComponent } from "../component/user/teacher/view-quiz/view-quiz.component";
import { ViewResultsComponent } from "../component/user/teacher/view-results/view-results.component";
import { FindQuizComponent } from "../component/user/student/find-quiz/find-quiz.component";
import { QuizResultsComponent } from "../component/user/student/quiz-results/quiz-results.component";

const appRoutes: Routes = [
    { path: 'users/register', component: SignUpComponent},
    { path: 'users/login', component: SignInComponent},
    { path: 'users/teacher/quizlist', component: QuizListComponent},
    { path: 'users/teacher/quizlist/:quizId', component: ViewQuizComponent},
    { path: 'users/teacher/quizlist/:quizId/results', component: ViewResultsComponent},
    { path: 'users/teacher/newquiz', component: NewQuizComponent},
    { path: 'users/student/findquiz', component: FindQuizComponent},
    { path: 'users/student/findquiz/:quizId', component: FindQuizComponent},
    { path: 'users/student/results', component: QuizResultsComponent},
];

@NgModule({
    imports: [RouterModule.forRoot(appRoutes)],
    exports: [RouterModule]
})
export class AppRoutingModule {

}