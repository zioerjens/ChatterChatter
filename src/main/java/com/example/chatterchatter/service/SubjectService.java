package com.example.chatterchatter.service;

import com.example.chatterchatter.model.domain.Subject;
import com.example.chatterchatter.model.dto.SubjectDTO;
import com.example.chatterchatter.repository.SubjectRepository;
import com.example.chatterchatter.service.interfaces.SubjectServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class SubjectService implements SubjectServiceInterface {

    @Autowired
    private SubjectRepository subjectRepository;

    @Override
    public List<Subject> findAll() {
        return subjectRepository.findAll();
    }

    @Override
    public Subject create(SubjectDTO subjectDTO) {
        var subject = subjectDTO.toDomain();
        return subjectRepository.save(subject);
    }

    @Override
    public void delete(Long subjectId) {
        subjectRepository.deleteById(subjectId);
    }

    @Override
    public Subject getById(Long id) {
        return subjectRepository.getById(id);
    }
}
