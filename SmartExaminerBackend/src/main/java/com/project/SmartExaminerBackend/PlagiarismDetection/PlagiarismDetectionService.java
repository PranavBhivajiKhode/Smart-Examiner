package com.project.SmartExaminerBackend.PlagiarismDetection;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class PlagiarismDetectionService {

	private FileStorageService fileStorageService;
	private TextExtraction textExtraction;
	private CosineSimilarityUtil cosineSimilarityUtil;

	public PlagiarismDetectionService(FileStorageService fileStorageService, TextExtraction textExtraction,
			CosineSimilarityUtil cosineSimilarityUtil) {
		super();
		this.fileStorageService = fileStorageService;
		this.textExtraction = textExtraction;
		this.cosineSimilarityUtil = cosineSimilarityUtil;
	}

	public List<String> plagiarismDetection(String batchId) throws IOException {
		File[] files = fileStorageService.getFiles(batchId);
		HashMap<String, String> fileData = new HashMap<>();

		for (File file : files) {
			String fileName = file.getName();
			String data = textExtraction.detectAndExtract(file); 
			fileData.put(fileName, data);
		}

		List<String> results = new ArrayList<>();
		List<String> fileNames = new ArrayList<>(fileData.keySet());

		for (int i = 0; i < fileNames.size(); i++) {
			
			String file1 = fileNames.get(i);
			String text1 = fileData.get(file1);
			
			for (int j = i + 1; j < fileNames.size(); j++) {
				
				String file2 = fileNames.get(j);
				String text2 = fileData.get(file2);

				// Cosine Similarity calculation
				double similarity = cosineSimilarityUtil.calculateCosineSimilarity(text1, text2);
				double percentage = similarity * 100;

				String result = file1 + " vs " + file2 + " => " + String.format("%.2f", percentage) + "% similarity";
				results.add(result);
			}
		}

		return results;
	}
}
