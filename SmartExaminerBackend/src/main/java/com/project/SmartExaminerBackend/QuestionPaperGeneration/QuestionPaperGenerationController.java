package com.project.SmartExaminerBackend.QuestionPaperGeneration;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.SmartExaminerBackend.QuestionPaperGeneration.DTO.QuestionPaperRequest;
import com.project.SmartExaminerBackend.QuestionPaperGeneration.MySQL.Entities.Question;
import com.project.SmartExaminerBackend.QuestionPaperGeneration.MySQL.Entities.Subject;
import com.project.SmartExaminerBackend.QuestionPaperGeneration.MySQL.Entities.Topic;
import com.project.SmartExaminerBackend.QuestionPaperGeneration.MySQL.Services.ExcelQuestionUploadService;
import com.project.SmartExaminerBackend.QuestionPaperGeneration.MySQL.Services.QuestionService;
import com.project.SmartExaminerBackend.QuestionPaperGeneration.MySQL.Services.SubjectService;
import com.project.SmartExaminerBackend.QuestionPaperGeneration.MySQL.Services.TopicService;


@RestController
public class QuestionPaperGenerationController {
	
	private ExcelQuestionUploadService excelQuestionUploadService;
	
	private QuestionPaperGenerationService questionPaperGenerationService;
	
	private SubjectService subjectService;
	
	private TopicService topicService;
	
	private QuestionService questionService;
	
	public QuestionPaperGenerationController(ExcelQuestionUploadService excelQuestionUploadService,
			SubjectService subjectService , TopicService topicService , QuestionService questionService,
			QuestionPaperGenerationService questionPaperGenerationService) {
		super();
		this.excelQuestionUploadService = excelQuestionUploadService;
		this.subjectService = subjectService;
		this.topicService = topicService;
		this.questionService = questionService;
		this.questionPaperGenerationService = questionPaperGenerationService;
	}




	@PostMapping("upload/excel/questions")
	public ResponseEntity<String> uploadExcel(@RequestParam("file") MultipartFile file){
		try {
			excelQuestionUploadService.importQuestionsFromExcel(file);
			return ResponseEntity.ok("Questions imported successfully.");
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to import questions : " + e.getMessage());
		}
	}
	
	@GetMapping("subjects")
	public List<Subject> retriveAllSubjects(){
		return subjectService.retriveAllSubjects();
	}
	
	@GetMapping("subjects/{subjectId}")
	public Subject retriveSubject(@PathVariable Long subjectId) {
		return subjectService.retriveSubject(subjectId);
	}
	
	@GetMapping("topics")
	public List<Topic> retriveAllTopics(){
		return topicService.retriveAllTopics();
	}
	
	@GetMapping("topics/{topicId}")
	public Topic retriveTopic(@PathVariable Long topicId) {
		return topicService.retriveTopicById(topicId);
	}
	
	@GetMapping("questions")
	public List<Question> retriveAllQuestions(){
		return questionService.retriveAllQuestions();
	}
	
	@GetMapping("questions/{questionId}")
	public Optional<Question> retriveQuestion(@PathVariable Long questionId) {
		return questionService.retriveQuestionById(questionId);
	}
	


	@PostMapping("/generate-paper")
	public ResponseEntity<?> generateQuestionPaper( @RequestBody QuestionPaperRequest request, BindingResult result) {
	    if (result.hasErrors()) {
	        return ResponseEntity.badRequest().body(result.getAllErrors());
	    }
	    try {
	        List<List<String>> generatedQuestions = questionPaperGenerationService.generateQuestions(request);
	        return ResponseEntity.ok(generatedQuestions);
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("Error generating question paper: " + e.getMessage());
	    }
	}


}

