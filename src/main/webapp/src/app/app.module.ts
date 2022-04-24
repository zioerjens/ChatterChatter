import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {HomeComponent} from './main/home/home.component';
import {RouterModule, Routes} from "@angular/router";
import {HelloWorldComponent} from "./main/hello-world/hello-world.component";
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {JwtInterceptor} from "./interceptor/jwt.interceptor";
import {AuthenticationGuard} from "./guard/authentication.guard";
import {NotificationModule} from "./utils/notification/notification.module";
import {LoginComponent} from './main/login/login.component';
import {RegisterComponent} from './main/register/register.component';
import {UserListComponent} from './main/user-list/user-list.component';

const routes: Routes = [
  {path: 'home', component: HomeComponent},
  {path: 'hello', component: HelloWorldComponent}
];

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    LoginComponent,
    RegisterComponent,
    UserListComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    NgbModule,
    NotificationModule,
    AppRoutingModule
  ],
  exports: [
    RouterModule
  ],
  providers: [
    AuthenticationGuard,
    {provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true}
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
