package com.project.SmartExaminerBackend.QuestionPaperGeneration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import com.project.SmartExaminerBackend.QuestionPaperGeneration.DTO.QuestionPaperRequest;
import com.project.SmartExaminerBackend.QuestionPaperGeneration.DTO.QuestionSection;
import com.project.SmartExaminerBackend.QuestionPaperGeneration.MySQL.Entities.Question;
import com.project.SmartExaminerBackend.QuestionPaperGeneration.MySQL.Entities.Subject;
import com.project.SmartExaminerBackend.QuestionPaperGeneration.MySQL.Entities.Topic;
import com.project.SmartExaminerBackend.QuestionPaperGeneration.MySQL.Services.QuestionService;
import com.project.SmartExaminerBackend.QuestionPaperGeneration.MySQL.Services.SubjectService;
import com.project.SmartExaminerBackend.QuestionPaperGeneration.MySQL.Services.TopicService;

@Service
public class QuestionPaperGenerationService {
	
	private SubjectService subjectService;
	
	private TopicService topicService;
	
	private QuestionService questionService;

	public QuestionPaperGenerationService(SubjectService subjectService, TopicService topicService,
			QuestionService questionService) {
		super();
		this.subjectService = subjectService;
		this.topicService = topicService;
		this.questionService = questionService;
	}
	
	public List<List<String>> generateQuestions(QuestionPaperRequest requestBody) throws Exception{
	    List<List<String>> generatedQuestionPaper = new ArrayList<>();

	    String subjectName = requestBody.getSubjectName();
	    Subject subject = subjectService.findSubjectByName(subjectName);
	    if(subject == null) {
	        throw new Exception("Subject not found: " + subjectName);
	    }

	    List<QuestionSection> sections = requestBody.getQuestionSections();

	    for(QuestionSection section : sections) {
	        List<String> topicNames = section.getTopics();
	        List<Topic> topics = new ArrayList<Topic>();
	        
	        for(String topicName : topicNames) {
	        	Topic topic = topicService.findByTopicNameAndSubjectName(topicName, subject);
	        	if(topic != null) {
	        		topics.add(topic);
	        	}
	        }

	        Pageable pageable = PageRequest.of(0, section.getNumberOfQuestions());

	        List<Question> questions = questionService.findRandomQuestionsByCriteria(
	            topics,
	            section.getDifficultyLevel(),
	            section.getQuestionType(),
	            section.getMarksPerQuestion(),
	            pageable
	        );

	        List<String> ques = questions.stream()
	            .map(Question::getQuestion)
	            .collect(Collectors.toList());

	        generatedQuestionPaper.add(ques);
	    }

	    return generatedQuestionPaper;
	}

	
	
}
