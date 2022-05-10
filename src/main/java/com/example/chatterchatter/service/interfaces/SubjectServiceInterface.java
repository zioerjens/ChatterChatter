package com.example.chatterchatter.service.interfaces;

import com.example.chatterchatter.model.domain.Subject;
import com.example.chatterchatter.model.dto.SubjectDTO;

import java.util.List;
import java.util.Optional;

public interface SubjectServiceInterface {

    List<Subject> findAll();

    Subject create(SubjectDTO subjectDTO);

    void delete(Long subjectId);

    Optional<Subject> findById(Long id);
}
