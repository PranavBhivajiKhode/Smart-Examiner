package com.project.SmartExaminerBackend.QuestionPaperClassification;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ClassificationController {
	
	private ClassificationService classificationService;

	public ClassificationController(ClassificationService classificationService) {
		super();
		this.classificationService = classificationService;
	}

	@PostMapping("classification/upload")
	public ResponseEntity<?> UploadQuestionPapre(@RequestParam MultipartFile QuestionPaper, String subject, String[] topics) throws IOException {
		List<String> questions = classificationService.classifyQuestionsIntoTopics(QuestionPaper , subject , topics);
		return ResponseEntity.ok(questions);
		
	}
}
