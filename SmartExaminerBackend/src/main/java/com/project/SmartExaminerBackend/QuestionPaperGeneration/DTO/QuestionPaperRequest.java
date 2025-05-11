package com.project.SmartExaminerBackend.QuestionPaperGeneration.DTO;

import java.util.List;

public class QuestionPaperRequest {
	
	private String subjectName;
	
	private List<QuestionSection> questionSections;

	public QuestionPaperRequest(String subjectName, List<QuestionSection> questionSections) {
		super();
		this.subjectName = subjectName;
		this.questionSections = questionSections;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public List<QuestionSection> getQuestionSections() {
		return questionSections;
	}

	public void setQuestionSections(List<QuestionSection> questionSections) {
		this.questionSections = questionSections;
	}

	
}
