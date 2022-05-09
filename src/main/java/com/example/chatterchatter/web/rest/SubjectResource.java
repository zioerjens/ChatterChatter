package com.example.chatterchatter.web.rest;

import com.example.chatterchatter.model.dto.SubjectDTO;
import com.example.chatterchatter.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/subject")
public class SubjectResource {

    @Autowired
    private SubjectService subjectService;

    @GetMapping("/all")
    public ResponseEntity<List<SubjectDTO>> getAll() {
        var subjects = subjectService.findAll();
        return new ResponseEntity<>(SubjectDTO.fromAll(subjects), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<SubjectDTO> create(@RequestBody @Valid SubjectDTO subjectDTO) {
        var subject = subjectService.create(subjectDTO);
        return new ResponseEntity<>(SubjectDTO.from(subject), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        subjectService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubjectDTO> get(@PathVariable Long id) {
        var subject = subjectService.getById(id);
        return new ResponseEntity<>(SubjectDTO.from(subject), HttpStatus.OK);
    }
}
