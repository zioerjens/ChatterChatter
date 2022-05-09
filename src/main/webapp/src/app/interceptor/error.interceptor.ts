import {Injectable} from '@angular/core';
import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {catchError, Observable, throwError} from 'rxjs';
import {NotificationService} from "../utils/notification/notification.service";
import {Router} from "@angular/router";
import {NotificationTypeEnum} from "../model/enum/notification-type.enum";

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
    if (Array.isArray(error.error.errors)) {
      error.error.errors.forEach((e: any) => {
          if (e.hasOwnProperty('defaultMessage')) {
            this.notificationService.notify(NotificationTypeEnum.ERROR, e.defaultMessage);
            errorNotificationSent = true;
          }

        }
      )
      ;
    }
    if (!errorNotificationSent) {
      this.generateDefaultErrorNotification()
    }
  }

  private generateDefaultErrorNotification() {
    this.notificationService.notify(NotificationTypeEnum.ERROR, "An error occured on your request");
  }
}
