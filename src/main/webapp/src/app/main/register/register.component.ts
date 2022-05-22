import {Component, OnDestroy, OnInit} from '@angular/core';
import {faEnvelope, faKey, faSpinner, faUser} from "@fortawesome/free-solid-svg-icons";
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
        () => {
          this.router.navigateByUrl('/login')
          this.showLoading = false;
        },
        () => {
          this.showLoading = false;
        })
    )
  }

  ngOnDestroy(): void {
    this.subs.forEach(sub => sub.unsubscribe());
  }
}
