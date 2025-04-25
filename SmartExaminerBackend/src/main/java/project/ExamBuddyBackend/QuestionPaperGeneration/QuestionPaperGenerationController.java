package project.ExamBuddyBackend.QuestionPaperGeneration;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import project.ExamBuddyBackend.QuestionPaperGeneration.MySQLServices.ExcelQuestionUploadService;

@RestController
public class QuestionPaperGenerationController {
	
	private ExcelQuestionUploadService excelQuestionUploadService;
	
	public QuestionPaperGenerationController(ExcelQuestionUploadService excelQuestionUploadService) {
		super();
		this.excelQuestionUploadService = excelQuestionUploadService;
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
	
}
