import { Component, OnInit } from '@angular/core';
import {UserService} from "../../../service/user.service";
import {Subscription} from "rxjs";
import {User} from "../../../model/User";
import {Router} from "@angular/router";

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.scss']
})
export class UserListComponent implements OnInit {

  showLoading: boolean = false;
  private subs: Subscription[] = [];
  users: User[] = [];

  constructor(private userService: UserService,
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

}
