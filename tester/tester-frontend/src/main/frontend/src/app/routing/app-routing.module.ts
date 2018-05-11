import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";

import { SignUpComponent } from "../component/user/sign-up/sign-up.component";
import { SignInComponent } from "../component/user/sign-in/sign-in.component";
import { QuizListComponent } from "../component/user/teacher/quiz-list/quiz-list.component";
import { NewQuizComponent } from "../component/user/teacher/new-quiz/new-quiz.component";
import { ViewQuizComponent } from "../component/user/teacher/view-quiz/view-quiz.component";
import { ViewResultsComponent } from "../component/user/teacher/view-results/view-results.component";
import { FindQuizComponent } from "../component/user/student/find-quiz/find-quiz.component";

const appRoutes: Routes = [
    { path: 'users/register', component: SignUpComponent},
    { path: 'users/login', component: SignInComponent},
    { path: 'users/quizlist', component: QuizListComponent},
    { path: 'users/quizlist/:quizId', component: ViewQuizComponent},
    { path: 'users/quizlist/:quizId/results', component: ViewResultsComponent},
    { path: 'users/newquiz', component: NewQuizComponent},
    { path: 'users/findquiz', component: FindQuizComponent},
    { path: 'users/findquiz/:quizId', component: FindQuizComponent},
];

@NgModule({
    imports: [RouterModule.forRoot(appRoutes)],
    exports: [RouterModule]
})
export class AppRoutingModule {

}