import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from "@angular/common/http";
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

  create(subject: SubjectDTO): Observable<SubjectDTO> {
    return this.http.post<SubjectDTO>('/api/subject/create', subject);
  }

  delete(subjectId: number): Observable<HttpResponse<unknown>> {
    return this.http.delete<HttpResponse<unknown>>('/api/subject/delete/' + subjectId);
  }

  findById(subjectId: string): Observable<HttpResponse<SubjectDTO>> {
    return this.http.get<SubjectDTO>('/api/subject/' + subjectId, {observe: 'response'});
  }
}
