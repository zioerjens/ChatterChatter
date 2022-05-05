import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from "@angular/common/http";
import {Observable} from "rxjs";
import {MessageDTO} from "../model/MessageDTO";

@Injectable({
  providedIn: 'root'
})
export class MessageService {

  constructor(private http: HttpClient) {
  }

  getAll(): Observable<HttpResponse<MessageDTO[]>> {
    return this.http.get<MessageDTO[]>('/api/message/all', {observe: 'response'});
  }

  create(message: MessageDTO): Observable<MessageDTO> {
    return this.http.post<MessageDTO>('/api/message/create', message);
  }
}
