import {Injectable} from '@angular/core';
import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {catchError, Observable, throwError} from 'rxjs';
import {NotificationService} from "../utils/notification/notification.service";
import {Router} from "@angular/router";
import {NotificationTypeEnum} from "../model/enum/notification-type.enum";
import {isNotEmpty} from "../../util/util";

@Injectable()
export class ErrorInterceptor implements HttpInterceptor {

  constructor(
    private notificationService: NotificationService,
    private router: Router
  ) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    return next.handle(req).pipe(
      catchError((error) => {
        if (error instanceof HttpErrorResponse) {
          if (error.error instanceof ErrorEvent) {
            this.generateDefaultErrorNotification()
          } else {
            switch (error.status) {
              case 401:
                this.router.navigateByUrl("/login"); // TODO @Jan might now work nicely with your JWT error handling?
                break;
              case 403:
                this.notificationService.notify(NotificationTypeEnum.ERROR, 'Login failed.')
                break;
              default:
                this.handleHttpErrorResponse(error);
                break;
            }
          }
        } else {
          this.generateDefaultErrorNotification();
        }
        return throwError(error);
      })
    )
  }

  private handleHttpErrorResponse(error: HttpErrorResponse) {
    let errorNotificationSent = false;
    if (ErrorInterceptor.isValidationError(error)) {
      errorNotificationSent = this.handleValidationError(error, errorNotificationSent);
    }
    if (ErrorInterceptor.hasErrorMessage(error)) {
      errorNotificationSent = this.generateErrorMessageNotification(error, errorNotificationSent);
    }
    if (!errorNotificationSent) {
      this.generateDefaultErrorNotification()
    }
  }

  private handleValidationError(error: HttpErrorResponse, errorNotificationSent: boolean) {
    error.error.errors.forEach((e: any) => {
        if (e.hasOwnProperty('defaultMessage') && (<string>e.defaultMessage).includes('validation:_')) {
          this.notificationService.notify(NotificationTypeEnum.ERROR, (<string>e.defaultMessage).replace('validation:_', ''));
          errorNotificationSent = true;
        }
      }
    );
    return errorNotificationSent;
  }

  private generateErrorMessageNotification(error: HttpErrorResponse, errorNotificationSent: boolean) {
    if (!errorNotificationSent) {
      this.notificationService.notify(NotificationTypeEnum.ERROR, error.error.message);
      errorNotificationSent = !errorNotificationSent;
    }
    return errorNotificationSent;
  }

  private static isValidationError(error: HttpErrorResponse) {
    return Array.isArray(error.error.errors);
  }

  private static hasErrorMessage(error: HttpErrorResponse) {
    return isNotEmpty(error.error.message) && error.error.message !== '';
  }

  private generateDefaultErrorNotification() {
    this.notificationService.notify(NotificationTypeEnum.ERROR, "An error occured on your request");
  }
}
