import { Injectable } from "@angular/core";
import { Http, Response } from "@angular/http";
import { Observable } from 'rxjs/Observable';
import 'rxjs/Rx';

import { User } from "../model/user.model";
import { LoginUser } from "../model/login-user.model";
import { Router } from "@angular/router";

@Injectable()
export class UserService{

    loggedInUser: User = null;

    constructor(private http: Http,
                private router: Router) {}

    registerUser(user: User) {
        return this.http.post('api/users', user)
            .map(
                (response: Response) => {
                        let user: User = response.json();
                        user.password = null;
                        this.loggedInUser = user;
                        sessionStorage.setItem('loggedInUser', JSON.stringify(this.loggedInUser));
                        this.router.navigate(['/']);
                    }
                )
            .catch(
                (error: Response) => {
                    return Observable.throw(error);
                }
            );
    }

    loginUser(user: LoginUser) {
        return this.http.post('api/users/login', user)
            .map(
                (response: Response) => {
                    let user: User = response.json();
                    user.password = null;
                    this.loggedInUser = user;
                    sessionStorage.setItem('loggedInUser', JSON.stringify(this.loggedInUser));
                    this.router.navigate(['/']);
                }
            )
            .catch(
                (error: Response) => {
                    return Observable.throw(error);
                }
            );
    }

    isLoggedIn(): boolean {
        return this.loggedInUser !== null;
    }

    isTeacher(): boolean {
        if(this.isLoggedIn()){
            return this.loggedInUser.role === 'TEACHER';
        }
        return false;
    }

    isStudent(): boolean {
        if(this.isLoggedIn()){
            return this.loggedInUser.role === 'STUDENT';
        }
        return false;
    }

    isGuest(): boolean {
        if(this.isLoggedIn()){
            return this.loggedInUser.role === 'GUEST';
        }
        return false;
    }

    getLoggedInUser(): User {
        return JSON.parse(sessionStorage.getItem('loggedInUser'));
    }

    logoutUser() {
        return this.http.post('api/users/logout', null)
            .map(
                (response) => {
                    console.log("hello");
                    this.loggedInUser = null;
                    sessionStorage.setItem('loggedInUser', JSON.stringify(this.loggedInUser));
                    this.router.navigate(['/']);   
                }
            )
            .catch(
                (error: Response) => {
                    return Observable.throw(error);
                }
            );
    }
}