import {AfterViewInit, Component, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {AuthenticationService} from "../../service/authentication.service";

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.scss']
})
export class NavigationComponent implements OnInit {

  isLoggedId: boolean = false;

  constructor(private authService:AuthenticationService) {

  }

  ngOnInit(): void {
      console.log("init view");
      this.authService.isLoggedIn().subscribe(l =>  {
        this.isLoggedId = l;
      });
      this.authService.isTokenValid();
  }

}
