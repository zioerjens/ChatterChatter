package com.example.chatterchatter.model.dto;

import com.example.chatterchatter.model.domain.Subject;
import lombok.Value;

import java.util.List;

@Value
public class SubjectDTO {
    Long id;
    String title;

    public static List<SubjectDTO> fromAll(List<Subject> subjects) {
        return subjects.stream()
                .map(SubjectDTO::from)
                .toList();
    }

    public static SubjectDTO from(Subject subject) {
        return new SubjectDTO(
                subject.getId(),
                subject.getTitle()
        );
    }

    public Subject toDomain() {
        var subject = new Subject();
        subject.setId(this.getId());
        subject.setTitle(this.getTitle());
        return subject;
    }
}
