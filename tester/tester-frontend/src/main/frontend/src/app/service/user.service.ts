import { Injectable } from "@angular/core";
import { Http } from "@angular/http";
import { User } from "../model/user.model";
import { LoginUser } from "../model/login-user.model";

@Injectable()
export class UserService{

    constructor(private http: Http) {}

    registerUser(user: User) {
        return this.http.post('api/users', user);
    }

    loginUser(user: LoginUser) {
        return this.http.post('api/users/login', user);
    }
}