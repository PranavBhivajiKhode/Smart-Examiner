package com.project.SmartExaminerBackend.QuestionPaperClassification;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.project.SmartExaminerBackend.Gemini.GeminiService;
import com.project.SmartExaminerBackend.QuestionExtraction.DocxExtractor;
import com.project.SmartExaminerBackend.QuestionExtraction.PdfExtractor;

@Service
public class ClassificationService {

	private PdfExtractor pdfExtractor;
	private DocxExtractor docxExtractor;
	private GeminiService geminiService;

	public ClassificationService(PdfExtractor pdfExtractor, DocxExtractor docxExtractor, GeminiService geminiService) {
		super();
		this.pdfExtractor = pdfExtractor;
		this.docxExtractor = docxExtractor;
		this.geminiService = geminiService;
	}

	@SuppressWarnings("null")
	public List<String> classifyQuestionsIntoTopics(MultipartFile file, String subject, String[] topics)
			throws IOException {

		String text = null;

		if (file.getOriginalFilename().endsWith(".pdf")) {
			text = pdfExtractor.extractText(file);
		} else if (file.getOriginalFilename().endsWith(".docx")) {
			text = docxExtractor.extractText(file);
		}

//        List<String> questions = QuestionSplitter.splitQuestions(text);
		String prompt = "Extract all the single-line and multi-line, programming and non-programming questions from the following text. "
				+ "Return the result as a numbered list, and for each question, also mention its classified topic from the following list of topics:\n"
				+ String.join(", ", topics) + ".\n\n" + "Format the response as:\n\n"
				+ "1. <question text>\nTopic: <topic name>\n\n" + "2. <question text>\nTopic: <topic name>\n\n"
				+ "Now, here is the input text:\n\n" + text;

		String[] questions = geminiService.getQuestions(prompt).split("\n");
		for (String question : questions) {
			System.out.println(question);
		}

		return null;
	}

}
