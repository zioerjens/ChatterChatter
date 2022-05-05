import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from "./main/home/home.component";
import {HelloWorldComponent} from "./main/hello-world/hello-world.component";
import {LoginComponent} from "./main/login/login.component";
import {RegisterComponent} from "./main/register/register.component";
import {UserListComponent} from "./main/user-management/user-list/user-list.component";
import {LogoutComponent} from "./main/logout/logout.component";
import {UserEditComponent} from "./main/user-management/user-edit/user-edit.component";
import {UserCreateComponent} from "./main/user-management/user-create/user-create.component";

const routes: Routes = [
  {path: 'home', component: HomeComponent},
  {path: 'hello', component: HelloWorldComponent},
  {path: 'login', component: LoginComponent},
  {path: 'logout', component: LogoutComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'users/management', component: UserListComponent},
  {path: 'users/create', component: UserCreateComponent},
  {path: 'users/:id/edit', component:UserEditComponent},
  {path:'', redirectTo:'/login', pathMatch:'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
