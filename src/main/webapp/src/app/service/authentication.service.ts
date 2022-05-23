import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from "@angular/common/http";
import {Observable, ReplaySubject} from "rxjs";
import {UserLogin} from "../model/UserLogin";
import {User} from "../model/User";
import {JwtHelperService} from "@auth0/angular-jwt";
import {UserRegistration} from "../model/UserRegistration";
import {UserRoleEnum} from "../model/enum/user-role.enum";

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  private token: string | null;
  private loggedInUsername: string | null;
  private jwtHelper = new JwtHelperService();

  private $loggedIn = new ReplaySubject<boolean>(1);

  constructor(private http: HttpClient) {
    this.token = null;
    this.loggedInUsername = null;
  }

  login(credentials: UserLogin): Observable<HttpResponse<User>> {
    return this.http.post<User>('/api/auth/login', credentials, {observe: 'response'});
  }

  register(user: UserRegistration): Observable<HttpResponse<User>> {
    return this.http.post<User>('/api/auth/register', user, {observe: 'response'});
  }

  logout(): void {
    this.token = null;
    this.loggedInUsername = null;
    this.$loggedIn.next(false);
    localStorage.removeItem('user');
    localStorage.removeItem('token');
    localStorage.removeItem('users');
  }

  addTokenToLocalStorage(token: string | null): void {
    this.token = token;
    if (typeof token === "string") {
      localStorage.setItem('token', token);
    }
  }

  addUserToLocalStorage(user: User | null): void {
    if (user) {
      this.loggedInUsername = user.username;
      localStorage.setItem('user', JSON.stringify(user));
    }
  }

  getUserFromLocalStorage(): User {
    return JSON.parse(localStorage.getItem('user') || '{}');
  }

  getTokenFromLocalStorage(): string | null {
    const token = localStorage.getItem('token');
    this.token = token;
    return token;
  }

  isTokenValid(): boolean {
    this.getTokenFromLocalStorage();
    if (this.token != null && this.token !== '') {
      const decodedToken = this.jwtHelper.decodeToken(this.token);
      if (decodedToken.sub != null || decodedToken.sub != '') {
        if (!this.jwtHelper.isTokenExpired(this.token)) {
          this.loggedInUsername = decodedToken.sub;
          this.$loggedIn.next(true);
          return true;
        }
      }
    } else {
      this.logout();
    }
    this.$loggedIn.next(false);
    return false;
  }

  isLoggedIn(): Observable<boolean> {
    return this.$loggedIn.asObservable();
  }

  isLoggedInUser(user: User): boolean {
    return this.loggedInUsername === user.username;
  }

  hasRole(role: UserRoleEnum): boolean {
    return this.getUserFromLocalStorage().role === role
  }

}
