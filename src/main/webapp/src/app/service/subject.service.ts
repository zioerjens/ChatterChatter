import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpResponse} from "@angular/common/http";
import {Observable} from "rxjs";
import {SubjectDTO} from "../model/SubjectDTO";

@Injectable({
  providedIn: 'root'
})
export class SubjectService {

  constructor(private http: HttpClient) {
  }

  getAll(): Observable<HttpResponse<SubjectDTO[]>> {
    return this.http.get<SubjectDTO[]>('/api/subject/all', {observe: 'response'});
  }

  create(subject: SubjectDTO): Observable<SubjectDTO | HttpErrorResponse> {
    return this.http.post<SubjectDTO | HttpErrorResponse>('/api/subject/create', subject);
  }
}
