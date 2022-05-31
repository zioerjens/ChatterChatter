import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from "./main/login/login.component";
import {RegisterComponent} from "./main/register/register.component";
import {UserListComponent} from "./main/user-management/user-list/user-list.component";
import {LogoutComponent} from "./main/logout/logout.component";
import {UserEditComponent} from "./main/user-management/user-edit/user-edit.component";
import {SubjectComponent} from "./main/subject/subject.component";
import {ChatComponent} from "./main/chat/chat.component";
import {UserCreateComponent} from "./main/user-management/user-create/user-create.component";
import {AboutComponent} from "./main/about/about.component";
import {PageNotFoundComponent} from "./main/page-not-found/page-not-found.component";
import {AuthenticationGuard} from "./guard/authentication.guard";
import {AdminGuard} from "./guard/admin.guard";

const routes: Routes = [
  {path: '404', component: PageNotFoundComponent},
  {path: '', redirectTo: 'login', pathMatch: 'full'},
  {path: 'about', component: AboutComponent},
  {path: 'chat', component: SubjectComponent, canActivate: [AuthenticationGuard]},
  {path: 'chat/:id', component: ChatComponent, canActivate: [AuthenticationGuard]},
  {path: 'login', component: LoginComponent},
  {path: 'logout', component: LogoutComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'users', component: UserListComponent, canActivate: [AdminGuard, AuthenticationGuard]},
  {path: 'users/management', component: UserListComponent, canActivate: [AdminGuard, AuthenticationGuard]},
  {path: 'users/create', component: UserCreateComponent, canActivate: [AdminGuard, AuthenticationGuard]},
  {path: 'users/:id/edit', component: UserEditComponent, canActivate: [AdminGuard, AuthenticationGuard]},
  {path: '**', pathMatch: 'full', component: PageNotFoundComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
