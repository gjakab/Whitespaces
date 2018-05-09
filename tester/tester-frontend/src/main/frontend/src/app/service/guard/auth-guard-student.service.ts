import { Injectable } from "@angular/core";
import { CanActivate } from "@angular/router";
import { Observable } from "rxjs/Observable";

import { UserService } from "../user.service";

@Injectable()
export class AuthGuardStudent implements CanActivate {
    constructor(private userService: UserService) {}

    canActivate(): boolean | Observable<boolean> | Promise<boolean> {
        return this.userService.isStudent();
    }
}