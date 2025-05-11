package com.project.SmartExaminerBackend.QuestionPaperGeneration.MySQL.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.SmartExaminerBackend.QuestionPaperGeneration.MySQL.Entities.Subject;

public interface SubjectRepo extends JpaRepository<Subject, Long>{

	Subject findBySubjectName(String subjectName);

	
}
