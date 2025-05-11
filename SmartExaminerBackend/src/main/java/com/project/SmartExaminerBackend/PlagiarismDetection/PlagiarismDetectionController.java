package com.project.SmartExaminerBackend.PlagiarismDetection;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "*")
@RestController
public class PlagiarismDetectionController {

	private FileStorageService fileStorageService;

	private PlagiarismDetectionService plagiarismDetectionService;

	public PlagiarismDetectionController(FileStorageService fileStorageService,
			PlagiarismDetectionService plagiarismDetectionService) {
		this.fileStorageService = fileStorageService;
		this.plagiarismDetectionService = plagiarismDetectionService;
	}

	@PostMapping("/plagiarism/upload")
	public ResponseEntity<String> uploadFiles(
	        @RequestParam("batchId") String batchId,  // batchId comes first
	        @RequestParam("files") MultipartFile[] files) {  // then files
	    try {
	        fileStorageService.saveFiles(batchId, files);
	        return ResponseEntity.ok("Files uploaded successfully under batch " + batchId);
	    } catch (IOException e) {
	        e.printStackTrace(); // So we see the real error
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload files.");
	    }
	}


	@GetMapping("plagiarism/detect")
	public List<String> plagiarismDetection(@RequestParam String batchId) throws IOException {
		return plagiarismDetectionService.plagiarismDetection(batchId);
	}
}
