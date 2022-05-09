import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {UserListComponent} from "./user-list/user-list.component";
import {UserCreateComponent} from "./user-create/user-create.component";
import {UserEditComponent} from "./user-edit/user-edit.component";
import {FormsModule} from "@angular/forms";
import {BrowserModule} from "@angular/platform-browser";
import {AppRoutingModule} from "../../app-routing.module";
import {HttpClientModule} from "@angular/common/http";
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";
import {NotificationModule} from "../../utils/notification/notification.module";
import {FontAwesomeModule} from "@fortawesome/angular-fontawesome";

@NgModule({
  declarations: [
    UserListComponent,
    UserCreateComponent,
    UserEditComponent
  ],
  imports: [
    FormsModule,
    CommonModule,
    AppRoutingModule,
    HttpClientModule,
    NotificationModule,
    FontAwesomeModule
  ],
  exports: [
    UserListComponent,
    UserCreateComponent,
    UserEditComponent
  ]
})
export class UserManagementModule { }
