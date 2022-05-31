import {Component, OnDestroy, OnInit} from '@angular/core';
import {faKey, faSpinner, faUser} from "@fortawesome/free-solid-svg-icons";
import {Router} from "@angular/router";
import {AuthenticationService} from "../../service/authentication.service";
import {UserLogin} from "../../model/UserLogin";
import {Subscription} from "rxjs";
import {HttpResponse} from "@angular/common/http";
import {User} from "../../model/User";
import {HeaderTypeEnum} from "../../model/enum/header-type.enum";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit, OnDestroy {
  faUser = faUser;
  faPassword = faKey;
  faSpinner = faSpinner;
  showLoading: boolean = false;

  private subs: Subscription[] = [];

  constructor(private router: Router,
              private authenticationService: AuthenticationService) {
  }

  ngOnInit(): void {
    if (this.authenticationService.isTokenValid()) {
      this.router.navigateByUrl('/chat');
    } else {
      this.router.navigateByUrl('/login');
    }

  }

  ngOnDestroy(): void {
    this.subs.forEach(sub => sub.unsubscribe());
  }

  onLogin(user: UserLogin): void {
    this.showLoading = true;
    this.subs.push(
      this.authenticationService.login(user).subscribe(
        (res: HttpResponse<User>) => {
          const token = res.headers.get(HeaderTypeEnum.JWT_TOKEN);
          this.authenticationService.addTokenToLocalStorage(token);
          this.authenticationService.addUserToLocalStorage(res.body);

          if (this.authenticationService.isTokenValid()) {
            this.router.navigateByUrl('/chat')
          }
          this.showLoading = false;
        },
        () => {
          this.showLoading = false;
        })
    )
  }
}
