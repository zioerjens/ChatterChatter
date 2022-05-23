import {Component} from '@angular/core';
import {AuthenticationService} from "../../service/authentication.service";

@Component({
  selector: 'app-page-not-found',
  templateUrl: './page-not-found.component.html',
  styleUrls: ['./page-not-found.component.scss']
})
export class PageNotFoundComponent {

  private isLoggedIn = false;

  constructor(
    private authService: AuthenticationService
  ) {
    this.authService.isLoggedIn().subscribe(v => {
      this.isLoggedIn = v;
    });
  }

  isNotLoggedIn(): boolean {
    return !this.isLoggedIn;
  }

}
