import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';

import { UserService } from '../../../service/user.service';
import { LoginUser } from '../../../model/login-user.model';
import { User } from '../../../model/user.model';

@Component({
  selector: 'app-sign-in',
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.css']
})
export class SignInComponent implements OnInit {

  public exampleUser: User;

  constructor(private userService: UserService) { }

  ngOnInit() {
  }

  onRegister(form: NgForm){
    const value = form.value;
    const newUser = new LoginUser(value.email, value.password);
    this.userService.loginUser(newUser)
      .subscribe(
        (user: User) => this.exampleUser = user,
        (error) => console.log(error)
      );
  }
}
