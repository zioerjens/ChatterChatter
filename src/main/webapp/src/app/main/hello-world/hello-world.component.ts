import {Component, OnInit} from '@angular/core';
import {HelloWorldService} from "./hello-world.service";
import {HelloWorld} from "src/app/model/HelloWorld";

@Component({
  selector: 'app-hello-world',
  templateUrl: './hello-world.component.html',
  styleUrls: ['./hello-world.component.scss']
})
export class HelloWorldComponent implements OnInit {

  public hello:HelloWorld = {hello: ""};

  constructor(private helloService:HelloWorldService) {}

  ngOnInit(): void {
    this.helloService.getHelloWorld().subscribe((res: HelloWorld) => {
      this.hello = res;
    });
  }

}
