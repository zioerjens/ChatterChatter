import { Component, OnInit } from '@angular/core';
import {HelloWorldService} from "./hello-world.service";

@Component({
  selector: 'app-hello-world',
  templateUrl: './hello-world.component.html',
  styleUrls: ['./hello-world.component.scss']
})
export class HelloWorldComponent implements OnInit {

  private hello:string = '';

  constructor(private helloService:HelloWorldService) {}

  ngOnInit(): void {
    this.helloService.getHelloWorld().subscribe(res => {
      this.hello = res;
    });
  }

}
