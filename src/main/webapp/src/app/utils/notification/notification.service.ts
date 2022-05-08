import { Injectable } from '@angular/core';
import {NotifierService} from "angular-notifier";
import {NotificationTypeEnum} from "../../model/enum/notification-type.enum";

@Injectable()
export class NotificationService {

  constructor(private notifier: NotifierService) { }

  notify(type:NotificationTypeEnum, message:string) {
    this.notifier.notify(type, message);
  }
}
