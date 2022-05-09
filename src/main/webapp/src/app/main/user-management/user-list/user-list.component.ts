import {Component, OnInit} from '@angular/core';
import {UserService} from "../../../service/user.service";
import {Subscription} from "rxjs";
import {User} from "../../../model/User";
import {Router} from "@angular/router";
import {NotificationService} from "../../../utils/notification/notification.service";
import {NotificationTypeEnum} from "../../../model/enum/notification-type.enum";
import {faEdit, faPlus, faTrash} from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.scss']
})
export class UserListComponent implements OnInit {
  faPlus = faPlus;
  faEdit = faEdit;
  faDelete = faTrash

  showLoading: boolean = false;
  private subs: Subscription[] = [];
  users: User[] = [];

  constructor(private userService: UserService,
              private notificationService:NotificationService,
              private router: Router) { }

  ngOnInit(): void {
    this.showLoading = true;
    this.subs.push(this.userService.getUsers().subscribe(res =>{
      this.users = res;
      this.showLoading = false;
    }));
  }

  openEdit(userId : number) : void {
    if(!userId){
      return;
    }
    this.router.navigateByUrl(`/users/${userId}/edit`);
  }

  onDelete(id: number) : void {
    if(!id){
      return;
    }
    this.userService.deleteUser(id).subscribe(res =>{
      this.notificationService.notify(NotificationTypeEnum.SUCCESS, "User successfully deleted");
      this.ngOnInit();
    })

  }

  onCreate() {
    this.router.navigateByUrl(`/users/create`);
  }
}
