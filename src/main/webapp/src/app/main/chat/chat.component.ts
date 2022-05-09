import {Component, OnDestroy, OnInit} from '@angular/core';
import {MessageDTO} from "../../model/MessageDTO";
import {MessageService} from "../../service/message.service";
import {SubjectDTO} from "../../model/SubjectDTO";
import {ActivatedRoute} from "@angular/router";
import {SubjectService} from "../../service/subject.service";
import {interval, Observable} from "rxjs";
import {dateToTime, isEmpty, isNotEmpty, mapById} from "../../../util/util";
import {NgForm} from "@angular/forms";
import {User} from "../../model/User";
import {AuthenticationService} from "../../service/authentication.service";

@Component({
  selector: 'app-chat-component',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.scss']
})
export class ChatComponent implements OnInit, OnDestroy {

  subject: SubjectDTO | undefined | null;
  messages: MessageDTO[] = [];
  private refresher: Observable<any> | undefined;

  constructor(
    private route: ActivatedRoute,
    private messageService: MessageService,
    private subjectService: SubjectService,
    private authService: AuthenticationService
  ) {
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.subjectService.getById(params['id']).subscribe(res => {
        this.subject = res.body;
        this.loadMessages();
        this.refresher = interval(1000);
        this.refresher.subscribe(() => {
          this.loadMessages();
        })
      })
    });
  }

  // TODO @Sven needed?
  ngOnDestroy() {
    this.refresher = undefined;
  }

  private loadMessages() {
    if (isNotEmpty(this.subject?.id))
      this.messageService.getBySubjectId(this.subject!.id!).subscribe(res => {
        if (isNotEmpty(res.body)) {
          mapById(this.messages, res.body!);
        }
      });
  }

  onCreateMessage(messageForm: NgForm, message: MessageDTO) {
    message.subjectId = this.subject?.id!;
    this.messageService.create(message).subscribe(result => {
      this.messages.push(result);
      messageForm.reset({});
    });
  }

  isSubjectIdLoaded(): boolean {
    return isNotEmpty(this.subject?.id);
  }

  toMessageName(sender: User | undefined): string {
    if (isEmpty(sender?.firstName)
      || isEmpty(sender?.lastName)
      || sender!.firstName === ''
      || sender!.lastName === '') {
      return sender!.username;
    }
    return sender!.firstName + ' ' + sender!.lastName;
  }

  toMessageTime(time: string | undefined): string {
    if (isEmpty(time)) {
      return '';
    }
    const timeObject = new Date(time!);
    return dateToTime(timeObject);
  }

  isLoggedIn(sender: User | undefined) {
    if (isEmpty(sender)) {
      return false;
    }
    return this.authService.isLoggedInUser(sender!);
  }
}
