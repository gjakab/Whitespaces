import { Injectable } from "@angular/core";
import { Http, Response } from "@angular/http";
import { Observable } from 'rxjs/Observable';
import 'rxjs/Rx';

import { User } from "../model/user.model";
import { LoginUser } from "../model/login-user.model";

@Injectable()
export class UserService{

    constructor(private http: Http) {}

    registerUser(user: User) {
        return this.http.post('api/users', user)
            .map(
                (response: Response) => {
                        const user: User = response.json();
                        return user;
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
                    const user: User = response.json();
                    return user;
                }
            )
            .catch(
                (error: Response) => {
                    return Observable.throw(error);
                }
            );
    }
}