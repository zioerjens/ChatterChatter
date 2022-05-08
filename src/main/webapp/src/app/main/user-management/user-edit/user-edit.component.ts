import {Component, OnDestroy, OnInit} from '@angular/core';
import {User} from "../../../model/User";
import {ActivatedRoute} from "@angular/router";
import {Subscription} from "rxjs";
import {UserService} from "../../../service/user.service";
import {faEnvelope, faKey, faSpinner, faUser} from "@fortawesome/free-solid-svg-icons";

@Component({
  selector: 'app-user-edit',
  templateUrl: './user-edit.component.html',
  styleUrls: ['./user-edit.component.scss']
})
export class UserEditComponent implements OnInit, OnDestroy {
  user: User | null = null;

  showLoading: boolean = false;
  faUser = faUser;
  faSpinner = faSpinner;
  faEmail = faEnvelope;

  private routeSub: Subscription | undefined;
  private subs: Subscription[] = [];

  constructor(private route: ActivatedRoute,
              private userService: UserService) { }

  ngOnInit(): void {
    this.showLoading = true;
    this.routeSub = this.route.params.subscribe(params => {
      this.subs.push(this.userService.getUserById(params['id']).subscribe(res =>{
        this.user = res;
        this.showLoading = false;
      }));
    });
  }

  ngOnDestroy() {
    this.routeSub?.unsubscribe();
    this.subs.forEach(s => s.unsubscribe());
  }

  onEdit(formData: User) {

    console.log(this.user?.id);
    if(!this.user){
      return;
    }
    this.userService.updateUser(formData, this.user.id).subscribe( res => {
      console.log(res);
    });
  }
}
