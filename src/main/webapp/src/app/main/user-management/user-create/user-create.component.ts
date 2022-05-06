import {Component, OnDestroy, OnInit} from '@angular/core';
import {faEnvelope, faKey, faPlus, faSpinner, faUser} from "@fortawesome/free-solid-svg-icons";
import {Subscription} from "rxjs";
import {NotificationTypeEnum} from "../../../model/enum/notification-type.enum";
import {UserService} from "../../../service/user.service";
import {NotificationService} from "../../../utils/notification/notification.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-user-create',
  templateUrl: './user-create.component.html',
  styleUrls: ['./user-create.component.scss']
})
export class UserCreateComponent implements OnInit,OnDestroy {

  faUser = faUser;
  faPassword = faKey;
  faSpinner = faSpinner;
  faEmail = faEnvelope;
  faPlus = faPlus;
  showLoading: boolean = false;

  private subs: Subscription[] = [];

  constructor(private userService: UserService,
              private notificationService: NotificationService,
              private router: Router) {
  }

  ngOnInit(): void {
  }

  ngOnDestroy():void {
    this.subs.forEach( s => s.unsubscribe());
  }

  onCreate(formData: FormData) {
    if (!formData) {
      return;
    }
    this.subs.push(this.userService.addUser(formData).subscribe(res => {
      this.notificationService.notify(NotificationTypeEnum.SUCCESS, 'User successfully created');
      this.router.navigateByUrl(`/users/management`);
    }));
  }

  onCancel() {
    this.router.navigateByUrl(`/users/management`);
  }
}
