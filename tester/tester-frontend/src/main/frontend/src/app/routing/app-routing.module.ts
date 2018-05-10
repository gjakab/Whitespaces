import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { SignUpComponent } from "../component/user/sign-up/sign-up.component";
import { SignInComponent } from "../component/user/sign-in/sign-in.component";
import { QuizListComponent } from "../component/user/teacher/quiz-list/quiz-list.component";
import { NewQuizComponent } from "../component/user/teacher/new-quiz/new-quiz.component";

const appRoutes: Routes = [
    { path: 'users/register', component: SignUpComponent},
    { path: 'users/login', component: SignInComponent},
    { path: 'users/quizlist', component: QuizListComponent},
    { path: 'users/quizlist/:quizId', component: QuizListComponent},
    { path: 'users/newquiz', component: NewQuizComponent},
];

@NgModule({
    imports: [RouterModule.forRoot(appRoutes)],
    exports: [RouterModule]
})
export class AppRoutingModule {

}