import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";

import { SignUpComponent } from "../component/user/sign-up/sign-up.component";
import { SignInComponent } from "../component/user/sign-in/sign-in.component";
import { QuizListComponent } from "../component/user/teacher/quiz-list/quiz-list.component";
import { NewQuizComponent } from "../component/user/teacher/new-quiz/new-quiz.component";
import { AuthGuardTeacher } from "../service/guard/auth-guard-teacher.service";
import { ViewQuizComponent } from "../component/user/teacher/view-quiz/view-quiz.component";
import { ViewResultsComponent } from "../component/user/teacher/view-results/view-results.component";
import { FindQuizComponent } from "../component/user/student/find-quiz/find-quiz.component";
import { QuizResultsComponent } from "../component/user/student/quiz-results/quiz-results.component";
import { AuthGuardStudent } from "../service/guard/auth-guard-student.service";
import { QuizFillComponent } from "../component/user/student/quiz-fill/quiz-fill.component";

const appRoutes: Routes = [
    { path: 'users/register', component: SignUpComponent},
    { path: 'users/login', component: SignInComponent},
    { path: 'users/teacher/quizlist', canActivate: [AuthGuardTeacher], component: QuizListComponent},
    { path: 'users/teacher/quizlist/:quizId', canActivate: [AuthGuardTeacher], component: ViewQuizComponent},
    { path: 'users/teacher/quizlist/:quizId/results', canActivate: [AuthGuardTeacher], component: ViewResultsComponent},
    { path: 'users/teacher/newquiz', canActivate: [AuthGuardTeacher], component: NewQuizComponent},
    { path: 'users/student/findquiz', canActivate: [AuthGuardStudent], component: FindQuizComponent},
    { path: 'users/student/findquiz/:quizId', canActivate: [AuthGuardStudent], component: QuizFillComponent},
    { path: 'users/student/results', canActivate: [AuthGuardStudent], component: QuizResultsComponent},
];

@NgModule({
    imports: [RouterModule.forRoot(appRoutes)],
    exports: [RouterModule]
})
export class AppRoutingModule {

}