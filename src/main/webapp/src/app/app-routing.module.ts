import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HelloWorldComponent} from "./main/hello-world/hello-world.component";
import {LoginComponent} from "./main/login/login.component";
import {RegisterComponent} from "./main/register/register.component";
import {UserListComponent} from "./main/user-management/user-list/user-list.component";
import {LogoutComponent} from "./main/logout/logout.component";
import {UserEditComponent} from "./main/user-management/user-edit/user-edit.component";
import {SubjectComponent} from "./main/subject/subject.component";
import {ChatComponent} from "./main/chat/chat.component";
import {UserCreateComponent} from "./main/user-management/user-create/user-create.component";
import {AuthenticationGuard} from "./guard/authentication.guard";
import {AdminGuard} from "./guard/admin.guard";

const routes: Routes = [
  {path: 'chat', component: SubjectComponent, canActivate: [AuthenticationGuard]},
  {path: 'chat/:id', component: ChatComponent, canActivate: [AuthenticationGuard]},
  {path: 'hello', component: HelloWorldComponent},
  {path: 'login', component: LoginComponent},
  {path: 'logout', component: LogoutComponent, canActivate: [AuthenticationGuard]},
  {path: 'register', component: RegisterComponent, canActivate: [AuthenticationGuard]},
  {path: 'users/management', component: UserListComponent, canActivate: [AuthenticationGuard]},
  {path: 'users/create', component: UserCreateComponent, canActivate: [AuthenticationGuard, AdminGuard]},
  {path: 'users/:id/edit', component:UserEditComponent, canActivate: [AuthenticationGuard, AdminGuard]},
  {path:'', redirectTo:'/login', pathMatch:'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
