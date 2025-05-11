package com.project.SmartExaminerBackend.QuestionPaperGeneration.MySQL.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.project.SmartExaminerBackend.QuestionPaperGeneration.MySQL.Entities.Subject;
import com.project.SmartExaminerBackend.QuestionPaperGeneration.MySQL.Repositories.SubjectRepo;

@Service
public class SubjectService {
	
	private SubjectRepo subjectRepo;

	public SubjectService(SubjectRepo subjectRepo) {
		super();
		this.subjectRepo = subjectRepo;
	}
	
	public List<Subject> retriveAllSubjects(){
		List<Subject> subjects = new ArrayList<>();
		subjects = subjectRepo.findAll();
		return subjects;
	}
	
	public Subject retriveSubject(Long subjectId) {
		Optional<Subject> subject = subjectRepo.findById(subjectId);
		if(subject != null) {
			return subject.get();
		}
		return null;
	}
	
	public Subject findSubjectByName(String subjectName) {
		return subjectRepo.findBySubjectName(subjectName);
	}


	
}
