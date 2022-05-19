import {
  AfterViewChecked,
  AfterViewInit,
  Component,
  ElementRef,
  HostListener,
  OnDestroy,
  OnInit,
  Renderer2,
  ViewChild
} from '@angular/core';
import {MessageDTO} from "../../model/MessageDTO";
import {MessageService} from "../../service/message.service";
import {SubjectDTO} from "../../model/SubjectDTO";
import {ActivatedRoute, Router} from "@angular/router";
import {SubjectService} from "../../service/subject.service";
import {interval, Observable, Subject, takeUntil} from "rxjs";
import {dateToTime, isEmpty, isNotEmpty, mapById} from "../../../util/util";
import {NgForm} from "@angular/forms";
import {User} from "../../model/User";
import {AuthenticationService} from "../../service/authentication.service";
import {faChevronLeft, faPaperPlane} from "@fortawesome/free-solid-svg-icons";

@Component({
  selector: 'app-chat-component',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.scss']
})
export class ChatComponent implements OnInit, OnDestroy, AfterViewInit, AfterViewChecked {

  @ViewChild('messageContainer') messageContainer!: ElementRef;

  @HostListener('window:resize', ['$event'])
  onResize() {
    this.updateChatHeight();
  }

  subject: SubjectDTO | undefined | null;
  messages: MessageDTO[] = [];

  faChevronLeft = faChevronLeft;
  faPaperPlane = faPaperPlane;

  private refresher: Observable<any> | undefined;
  private bottomScrollLocked = true;
  private onDestroy$ = new Subject<void>();

  constructor(
    private route: ActivatedRoute,
    private messageService: MessageService,
    private subjectService: SubjectService,
    private authService: AuthenticationService,
    private router: Router,
    private renderer: Renderer2
  ) {
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.subjectService.findById(params['id']).subscribe(res => {
          this.subject = res.body;
          this.loadMessages();
          this.refresher = interval(1000).pipe(
            takeUntil(this.onDestroy$)
          )
          this.refresher.subscribe(() => {
            this.loadMessages();
          })
          this.updateChatHeight();
        },
        () => this.router.navigateByUrl('chat')
      )
    });
  }

  ngAfterViewInit() {
    this.updateChatHeight();
    this.scrollToBottom();
  }

  ngAfterViewChecked() {
    this.scrollToBottom();
  }

  ngOnDestroy(): void {
    this.onDestroy$.next();
  }

  private updateChatHeight() {
    const nav = window.getComputedStyle(document.getElementsByTagName("nav")[0]);
    const navHeight = parseInt(nav.height) + parseInt(nav.marginBottom) + parseInt(nav.marginTop);
    const row = window.getComputedStyle(document.getElementsByClassName("row")[0]);
    const rowHeight = parseInt(row.height) + parseInt(row.marginTop) + parseInt(row.marginBottom);
    const form = window.getComputedStyle(document.getElementsByTagName("form")[0]);
    const formHeight = parseInt(form.height) + parseInt(form.marginTop) + parseInt(form.marginBottom);

    const desiredHeight = `${window.innerHeight
    - navHeight
    - rowHeight
    - formHeight}px`;

    console.log(navHeight)
    console.log(rowHeight)
    console.log(formHeight)
    console.log(desiredHeight)

    this.renderer.setStyle(this.messageContainer.nativeElement, "height", desiredHeight);
  }

  updateScrollLock(): void {
    const element = this.messageContainer.nativeElement;
    this.bottomScrollLocked = element.offsetHeight + element.scrollTop >= element.scrollHeight;
  }

  private scrollToBottom(): void {
    if (this.bottomScrollLocked) {
      this.messageContainer.nativeElement.scrollTop = this.messageContainer.nativeElement.scrollHeight;
    }
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
