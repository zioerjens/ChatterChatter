import {Component, OnInit} from '@angular/core';
import {SubjectService} from "../../service/subject.service";
import {SubjectDTO} from "../../model/SubjectDTO";
import {MessageDTO} from "../../model/MessageDTO";
import {MessageService} from "../../service/message.service";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  constructor(
    private subjectService: SubjectService,
    private messageService: MessageService
  ) {
  }

  subjects: SubjectDTO[] = [];
  messages: MessageDTO[] = [];

  ngOnInit(): void {
  }

  loadSubjects() {
    this.subjectService.getAll().subscribe((result) => {
      this.subjects = result.body ?? [];
    })
  }

  onCreateSubject(subject: SubjectDTO) {
    this.subjectService.create(subject).subscribe();
  }

  loadMessages() {
    this.messageService.getAll().subscribe((result) => {
      this.messages = result.body ?? [];
    })
  }

  onCreateMessage(message: MessageDTO) {
    message.subjectId = <number>this.subjects[0]?.id;
    this.messageService.create(message).subscribe();
  }
}
