import {Component, OnDestroy, OnInit} from '@angular/core';
import {faEnvelope, faKey, faSpinner, faUser} from "@fortawesome/free-solid-svg-icons";
import {HttpErrorResponse, HttpResponse} from "@angular/common/http";
import {User} from "../../model/User";
import {NotificationTypeEnum} from "../../model/enum/notification-type.enum";
import {Subscription} from "rxjs";
import {AuthenticationService} from "../../service/authentication.service";
import {NotificationService} from "../../utils/notification/notification.service";
import {Router} from "@angular/router";
import {UserRegistration} from "../../model/UserRegistration";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit, OnDestroy {
  faUser = faUser;
  faPassword = faKey;
  faSpinner = faSpinner;
  faEmail = faEnvelope;
  showLoading: boolean = false;

  private subs: Subscription[] = [];

  constructor(private authenticationService: AuthenticationService,
              private notificationService: NotificationService,
              private router: Router) {
  }

  ngOnInit(): void {
  }

  onRegister(user: UserRegistration) {
    this.showLoading = true;
    this.subs.push(
      this.authenticationService.register(user).subscribe(
        (res: HttpResponse<User>) => {
          this.router.navigateByUrl('/login')
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

  ngOnDestroy(): void {
    this.subs.forEach(sub => sub.unsubscribe());
  }
}
