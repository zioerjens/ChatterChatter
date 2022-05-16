import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from '@angular/router';
import {AuthenticationService} from "../service/authentication.service";
import {NotificationService} from "../utils/notification/notification.service";
import {NotificationTypeEnum} from "../model/enum/notification-type.enum";
import {UserRoleEnum} from "../model/enum/user-role.enum";

@Injectable()
export class AdminGuard implements CanActivate {

  constructor(private authenticationService: AuthenticationService,
              private router: Router,
              private notificationService: NotificationService) {
  }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): boolean {
    return this.isAdmin();
  }

  private isAdmin(): boolean {
    if (this.authenticationService.isTokenValid()) {
      if(this.authenticationService.hasRole(UserRoleEnum.ADMIN)){
        return true;
      }
    }
    this.router.navigate(['/']);
    this.notificationService.notify(NotificationTypeEnum.ERROR, 'You dont have permission to view this page');
    return false;
  }

}
