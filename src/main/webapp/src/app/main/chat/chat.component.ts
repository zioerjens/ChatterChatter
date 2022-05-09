import {Component, OnInit} from '@angular/core';
import {SubjectDTO} from "../../model/SubjectDTO";
import {MessageDTO} from "../../model/MessageDTO";
import {SubjectService} from "../../service/subject.service";
import {MessageService} from "../../service/message.service";
import {faPlus, faTrash} from '@fortawesome/free-solid-svg-icons';
import {interval} from "rxjs";

@Component({
  selector: 'app-chats',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.scss']
})
export class ChatComponent implements OnInit {

  constructor(
    private subjectService: SubjectService,
    private messageService: MessageService
  ) {
  }

  subjects: SubjectDTO[] = [];
  messages: MessageDTO[] = [];
  selectedSubject: SubjectDTO | null | undefined;
  createSubjectViewVisible = false;
  faPlus = faPlus;
  faTrash = faTrash;

  ngOnInit(): void {
    interval(1000).subscribe(() => {
      this.loadSubjects();
      if (this.selectedSubject !== null && this.selectedSubject !== undefined) {
        this.loadSubjectMessages(this.selectedSubject.id);
      }
    })
  }

  loadSubjects() {
    this.subjectService.getAll().subscribe((result) => {
      if (result.body !== undefined) {
        result.body!.forEach(subject => {
          if (this.subjects.find(s => s.id === subject.id) === undefined) {
            this.subjects.push(subject);
          }
        })
        // TODO @Sven extract method
        if (this.subjects.length > result.body!.length) {
          this.subjects.forEach(subject => {
            if (result.body?.find(s => s.id === subject.id) === undefined) {
              this.subjects.splice(this.subjects.indexOf(subject), 1);
            }
          })
        }
      }
    })
  }

  onDeleteSubject(subjectId: number | undefined) {
    if (subjectId === undefined) {
      return;
    }
    this.subjectService.delete(subjectId).subscribe(() => {
      const deletedSubject = this.subjects.find(subject => subject.id === subjectId);
      if (deletedSubject === undefined) {
        throw new Error('onDeleteSubject - subject could not be found');
      }
      this.subjects.splice(this.subjects.indexOf(deletedSubject), 1);
    });
  }

  onOpenCreateSubjectView() {
    this.createSubjectViewVisible = true;
  }

  onCloseCreateSubjectView() {
    this.createSubjectViewVisible = false;
  }

  onCreateSubject(subject: SubjectDTO) {
    this.subjectService.create(subject).subscribe(result => {
      this.subjects.push(result);
      this.onCloseCreateSubjectView();
    });
  }

  onShowSubject(subjectId: number | undefined) {
    if (subjectId === undefined) {
      throw new Error('id should not be null');
    }
    this.selectedSubject = this.subjects.find(s => s.id === subjectId);
    this.loadSubjectMessages(subjectId);
  }

  loadSubjectMessages(subjectId: number | undefined) {
    if (subjectId === undefined) {
      throw new Error('id should not be null');
    }
    this.messageService.getBySubjectId(subjectId).subscribe((result) => {
      this.messages = result.body ?? [];
    })
  }

  onCreateMessage(message: MessageDTO) {
    message.subjectId = <number>this.subjects[0]?.id;
    this.messageService.create(message).subscribe();
  }
}
