import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['../../../app.component.css']
})
export class HomePageComponent implements OnInit {
	teacherStuff: String[] = [
		"Új quizek létrehozása",
		"Quzijeid törlése",
		"Quizjeid kérdéseinek, válaszainak a kezelése",
		"Quizjeidhez tartozó eredmények megtekintése, törlése",
	];
	studentStuff: String[] = [
		"Quizek keresése",
		"Quizek kitöltése",
		"Quiz eredményeid megtekintése",
	]

  constructor() { }

  ngOnInit() {
  }

}
