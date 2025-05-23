package com.project.SmartExaminerBackend.QuestionPaperAnswerGeneration;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


//@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class AnswerController {
	
	private AnswerService answerService;
	
	public AnswerController(AnswerService answerService) {
		this.answerService = answerService;
	}
	
	@PostMapping("/question/paper/answer")
	public  ResponseEntity<String> askQuestion(@RequestBody Map<String , String> payload){
		String question = payload.get("question");
		String answer = answerService.getAnswer(question);
		return ResponseEntity.ok(answer);
	}
}
