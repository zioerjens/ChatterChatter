import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {RouterModule} from "@angular/router";
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {JwtInterceptor} from "./interceptor/jwt.interceptor";
import {AuthenticationGuard} from "./guard/authentication.guard";
import {NotificationModule} from "./utils/notification/notification.module";
import {LoginComponent} from './main/login/login.component';
import {RegisterComponent} from './main/register/register.component';
import {FontAwesomeModule} from "@fortawesome/angular-fontawesome";
import {FormsModule} from "@angular/forms";
import {LogoutComponent} from './main/logout/logout.component';
import {NavigationComponent} from './utils/navigation/navigation.component';
import {UserManagementModule} from "./main/user-management/user-management.module";
import {SubjectComponent} from './main/subject/subject.component';
import {ChatComponent} from './main/chat/chat.component';
import {ErrorInterceptor} from "./interceptor/error.interceptor";
import {AboutComponent} from './main/about/about.component';
import {AdminGuard} from "./guard/admin.guard";
import {PageNotFoundComponent} from './main/page-not-found/page-not-found.component';

@NgModule({
  declarations: [
    AppComponent,
    NavigationComponent,
    LoginComponent,
    RegisterComponent,
    LogoutComponent,
    SubjectComponent,
    ChatComponent,
    AboutComponent,
    PageNotFoundComponent
  ],
  imports: [
    UserManagementModule,
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    HttpClientModule,
    NgbModule,
    NotificationModule,
    FontAwesomeModule
  ],
  exports: [
    RouterModule
  ],
  providers: [
    AuthenticationGuard,
    AdminGuard,
    {provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true},
    {provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true}
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
