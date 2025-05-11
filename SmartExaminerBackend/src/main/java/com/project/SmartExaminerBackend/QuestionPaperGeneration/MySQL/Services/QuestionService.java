package com.project.SmartExaminerBackend.QuestionPaperGeneration.MySQL.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;


import com.project.SmartExaminerBackend.QuestionPaperGeneration.MySQL.Entities.Question;
import com.project.SmartExaminerBackend.QuestionPaperGeneration.MySQL.Entities.Topic;
import com.project.SmartExaminerBackend.QuestionPaperGeneration.MySQL.Repositories.QuestionRepo;

@Service
public class QuestionService {
	
	private QuestionRepo questionRepo;

	public QuestionService(QuestionRepo questionRepo) {
		super();
		this.questionRepo = questionRepo;
	}
	
	public List<Question> retriveAllQuestions(){
		return questionRepo.findAll();
	}
	
	public Optional<Question> retriveQuestionById(Long questionId) {
		return questionRepo.findById(questionId);
	}
	
	public List<Question> findRandomQuestionsByCriteria(List<Topic> topics , String difficulty ,
			String type , int marks , Pageable pageable){
		return questionRepo.findRandomQuestionsByCriteria(topics , difficulty , type , marks , pageable );
	}
	
}
