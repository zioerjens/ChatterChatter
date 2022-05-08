package com.example.chatterchatter.web.rest;

import com.example.chatterchatter.model.dto.HelloWorldDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldResource {

    @GetMapping("/api/hello")
    public ResponseEntity<HelloWorldDTO> helloWorld(){
        HelloWorldDTO hello = new HelloWorldDTO();
        hello.setHello("Hello World");
        return new ResponseEntity<>(hello, HttpStatus.OK);
    }
}
