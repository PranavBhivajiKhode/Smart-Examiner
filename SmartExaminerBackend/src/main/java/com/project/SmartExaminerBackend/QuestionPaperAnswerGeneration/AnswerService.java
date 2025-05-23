package com.project.SmartExaminerBackend.QuestionPaperAnswerGeneration;


import org.springframework.stereotype.Service;

import com.project.SmartExaminerBackend.Gemini.GeminiService;

@Service
public class AnswerService {
	
	private GeminiService geminiService;
	
	public AnswerService(GeminiService geminiService) {
		super();
		this.geminiService = geminiService;
	}

	public String getAnswer(String question) {
		String answer = geminiService.getAnswer(question);
		return answer;
	}

}

