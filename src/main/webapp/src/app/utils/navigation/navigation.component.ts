import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from "../../service/authentication.service";
import {UserRoleEnum} from "../../model/enum/user-role.enum";

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.scss']
})
export class NavigationComponent implements OnInit {

  isLoggedId: boolean = false;

  constructor(private authService: AuthenticationService) {

  }

  ngOnInit(): void {
    console.log("init view");
    this.authService.isLoggedIn().subscribe(l => {
      this.isLoggedId = l;
    });
    this.authService.isTokenValid();
  }

  isAdmin(): boolean {
    return this.authService.hasRole(UserRoleEnum.ADMIN);
  }
}
