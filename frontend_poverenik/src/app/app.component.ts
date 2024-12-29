import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.sass']
})
export class AppComponent {

  constructor(
    private router: Router
  ) { }

  get docPage(): boolean{
    return this.router.url.includes('pdf') || this.router.url.includes('html');
  }

}
