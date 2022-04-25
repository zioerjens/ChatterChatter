import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from "../../service/authentication.service";
import {NotificationService} from "../../utils/notification/notification.service";
import {Router} from "@angular/router";
import {NotificationTypeEnum} from "../../model/enum/notification-type.enum";

@Component({
  selector: 'app-logout',
  template: ''
})
export class LogoutComponent implements OnInit {

  constructor(private authenticationService: AuthenticationService,
              private notificationService: NotificationService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.authenticationService.logout();
    this.notificationService.notify(NotificationTypeEnum.SUCCESS, 'Successfully logged out');
    this.router.navigateByUrl('/login')
  }

}
