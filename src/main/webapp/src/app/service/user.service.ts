import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {Observable} from "rxjs";
import {User} from "../model/User";
import {CustomHttpResponse} from "../model/custom-http-response";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) {
  }

  getUsers(): Observable<User[]> {
    return this.http.get<User[]>('/api/users');
  }

  getUserById(id:number): Observable<User>{
    return this.http.get<User>('/api/users/'+id);
  }

  addUser(formData: FormData): Observable<User | HttpErrorResponse> {
    return this.http.post<User | HttpErrorResponse>('/api/users/add', formData);
  }

  updateUser(user: User, userId: number): Observable<User | HttpErrorResponse> {
    return this.http.put<User | HttpErrorResponse>(`/api/users/${userId}/update`, user);
  }

  deleteUser(userId: number): Observable<CustomHttpResponse | HttpErrorResponse> {
    return this.http.delete<CustomHttpResponse | HttpErrorResponse>(`/api/user/${userId}/delete`);
  }

  addUsersToLocalStorage(users: User[]): void {
    localStorage.setItem('users', JSON.stringify(users));
  }

  getUsersFromLocalStorage(): User[] {
    if (localStorage.getItem('users')) {
      return JSON.parse(localStorage.getItem('users') || '{}')
    }
    return [];
  }

  createUserFormData(loggedInUsername: string, user:User): FormData {
    const formData = new FormData();
    formData.append('currentUsername', loggedInUsername);
    formData.append('username', user.username);
    formData.append('firstName', user.firstName);
    formData.append('lastName', user.lastName);
    formData.append('email', user.email);
    return formData;
  }

}
