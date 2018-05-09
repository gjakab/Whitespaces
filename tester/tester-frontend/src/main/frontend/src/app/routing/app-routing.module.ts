import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { SignUpComponent } from "../component/user/sign-up/sign-up.component";
import { SignInComponent } from "../component/user/sign-in/sign-in.component";
import { QuizListComponent } from "../component/user/teacher/quiz-list/quiz-list.component";
import { NewQuizComponent } from "../component/user/teacher/new-quiz/new-quiz.component";
import { AuthGuardTeacher } from "../service/guard/auth-guard-teacher.service";

const appRoutes: Routes = [
    { path: 'users/register', canActivate: [AuthGuardTeacher], component: SignUpComponent},
    { path: 'users/login', component: SignInComponent},
    { path: 'users/quizlist', canActivate: [AuthGuardTeacher], component: QuizListComponent},
    { path: 'users/quizlist/:quizId', canActivate: [AuthGuardTeacher], component: QuizListComponent},
    { path: 'users/newquiz', canActivate: [AuthGuardTeacher], component: NewQuizComponent},
];

@NgModule({
    imports: [RouterModule.forRoot(appRoutes)],
    exports: [RouterModule]
})
export class AppRoutingModule {

}