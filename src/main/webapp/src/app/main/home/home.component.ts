import {Component, OnInit} from '@angular/core';
import {SubjectService} from "../../service/subject.service";
import {SubjectDTO} from "../../model/SubjectDTO";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  constructor(private subjectService: SubjectService) {
  }

  subjects: SubjectDTO[] = [];

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
}
