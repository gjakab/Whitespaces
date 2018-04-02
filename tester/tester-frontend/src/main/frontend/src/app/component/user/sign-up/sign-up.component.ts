import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';

import { User } from '../../../model/user.model';
import { UserService } from '../../../service/user.service';

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent implements OnInit {

  constructor(private userService: UserService) { }

  ngOnInit() {
  }

  onRegister(form: NgForm){
    const value = form.value;
    console.log(value);
    const newUser = new User(value.password,
                             value.email,
                             value.role,
                             value.firstName,
                             value.lastName,
                             value.school);
    console.log(newUser);
    this.userService.registerUser(newUser)
      .subscribe(
        (response) => console.log(response),
        (error) => console.log(error)
      );
  }
}
