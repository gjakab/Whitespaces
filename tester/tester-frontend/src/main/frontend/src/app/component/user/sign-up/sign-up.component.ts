import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';

import { User } from '../../../model/user.model';
import { UserService } from '../../../service/user.service';

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['../../../app.component.css']
})
export class SignUpComponent implements OnInit {

  public exampleUser: User;

  constructor(private userService: UserService) { }

  ngOnInit() {
  }

  onRegister(form: NgForm){
    const value = form.value;
    const newUser = new User(value.password,
                             value.email,
                             value.role,
                             value.firstName,
                             value.lastName,
                             value.school);
    
    this.userService.registerUser(newUser)
      .subscribe(
          (user: User) => this.exampleUser = user,
          (error) => console.log(error)
      );
  }
}
