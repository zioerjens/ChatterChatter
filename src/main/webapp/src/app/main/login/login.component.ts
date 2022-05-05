import {Component, OnDestroy, OnInit} from '@angular/core';
import {faKey, faSpinner, faUser} from "@fortawesome/free-solid-svg-icons";
import {Router} from "@angular/router";
import {AuthenticationService} from "../../service/authentication.service";
import {NotificationService} from "../../utils/notification/notification.service";
import {UserLogin} from "../../model/UserLogin";
import {Subscription} from "rxjs";
import {HttpErrorResponse, HttpResponse} from "@angular/common/http";
import {User} from "../../model/User";
import {NotificationTypeEnum} from "../../model/enum/notification-type.enum";
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
              private authenticationService: AuthenticationService,
              private notificationService: NotificationService) {
  }

  ngOnInit(): void {
    this.authenticationService.isTokenValid();
    if (this.authenticationService.isTokenValid()) {
      this.router.navigateByUrl('/users/management');
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

          if(this.authenticationService.isTokenValid()){
            this.router.navigateByUrl('/users/management')
          }
          this.showLoading = false;
        },
        (errorRes: HttpErrorResponse) => {
          console.log(errorRes);
          this.sendErrorNotification(errorRes.message);
          this.showLoading = false;
        })
    )
  }

  private sendErrorNotification(message: string) {
    if (message) {
      this.notificationService.notify(NotificationTypeEnum.ERROR, message);
    } else {
      this.notificationService.notify(NotificationTypeEnum.ERROR, 'An unexpected error occurred.');
    }
  }
}
