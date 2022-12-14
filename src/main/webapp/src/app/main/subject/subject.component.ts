import {Component, OnDestroy, OnInit} from '@angular/core';
import {SubjectDTO} from "../../model/SubjectDTO";
import {SubjectService} from "../../service/subject.service";
import {faPlus, faTrash} from '@fortawesome/free-solid-svg-icons';
import {interval, Observable, Subject, takeUntil} from "rxjs";
import {isEmpty, isNotEmpty, mapById} from "../../../util/util";
import {Router} from "@angular/router";

@Component({
  selector: 'app-chats',
  templateUrl: './subject.component.html',
  styleUrls: ['./subject.component.scss']
})
export class SubjectComponent implements OnInit, OnDestroy {

  constructor(
    private router: Router,
    private subjectService: SubjectService,
  ) {
  }

  subjects: SubjectDTO[] = [];
  createSubjectViewVisible = false;
  faPlus = faPlus;
  faTrash = faTrash;
  private refresher: Observable<any> | undefined;
  private onDestroy$ = new Subject<void>();

  ngOnInit(): void {
    this.loadSubjects();
    this.refresher = interval(1000).pipe(
      takeUntil(this.onDestroy$)
    )
    this.refresher.subscribe(() => {
      this.loadSubjects();
    })
  }

  ngOnDestroy() {
    this.onDestroy$.next();
  }

  loadSubjects() {
    this.subjectService.getAll().subscribe((result) => {
      if (isNotEmpty(result.body)) {
        mapById(this.subjects, result.body!);
      }
    });
  }

  onDeleteSubject(subjectId: number | undefined) {
    if (isEmpty(subjectId)) {
      return;
    }
    this.subjectService.delete(subjectId!).subscribe(() => {
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

  navigateToChat(subjectId: number | undefined) {
    if (isEmpty(subjectId)) {
      return;
    }
    this.router.navigateByUrl(`/chat/${subjectId}`);
  }
}
