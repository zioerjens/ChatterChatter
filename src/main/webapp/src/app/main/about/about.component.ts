import {Component, OnInit} from '@angular/core';
import {faChevronLeft} from "@fortawesome/free-solid-svg-icons";
import {AuthenticationService} from "../../service/authentication.service";

@Component({
  selector: 'app-about',
  templateUrl: './about.component.html',
  styleUrls: ['./about.component.scss']
})
export class AboutComponent implements OnInit {
  faChevronLeft = faChevronLeft;
  isBackButtonVisible: boolean = false;

  constructor(private authenticationService:AuthenticationService) {
  }

  ngOnInit(): void {
    this.isBackButtonVisible = !this.authenticationService.isTokenValid();
  }

}
