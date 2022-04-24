import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {HelloWorld} from "src/app/model/HelloWorld";

@Injectable({
  providedIn: 'root'
})
export class HelloWorldService {

  constructor(private http: HttpClient) {}

  getHelloWorld(): Observable<HelloWorld>{
    return this.http.get<HelloWorld>('/api/hello');
  }
}
