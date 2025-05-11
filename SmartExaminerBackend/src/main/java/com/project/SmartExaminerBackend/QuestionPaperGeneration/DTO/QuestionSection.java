package com.project.SmartExaminerBackend.QuestionPaperGeneration.DTO;

import java.util.List;

public class QuestionSection {
	
	private String sectionTitle;
    
	private String questionType;
    
	private String difficultyLevel;
    
	private int marksPerQuestion;
    
	private int numberOfQuestions;
    
	private List<String> topics;
    
	public QuestionSection(String sectionTitle, String questionType, String difficultyLevel, int marksPerQuestion,
			int numberOfQuestions, List<String> topics) {
		super();
		this.sectionTitle = sectionTitle;
		this.questionType = questionType;
		this.difficultyLevel = difficultyLevel;
		this.marksPerQuestion = marksPerQuestion;
		this.numberOfQuestions = numberOfQuestions;
		this.topics = topics;
	}

	public String getSectionTitle() {
		return sectionTitle;
	}

	public void setSectionTitle(String sectionTitle) {
		this.sectionTitle = sectionTitle;
	}

	public String getQuestionType() {
		return questionType;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}

	public String getDifficultyLevel() {
		return difficultyLevel;
	}

	public void setDifficultyLevel(String difficultyLevel) {
		this.difficultyLevel = difficultyLevel;
	}

	public int getMarksPerQuestion() {
		return marksPerQuestion;
	}

	public void setMarksPerQuestion(int marksPerQuestion) {
		this.marksPerQuestion = marksPerQuestion;
	}

	public int getNumberOfQuestions() {
		return numberOfQuestions;
	}

	public void setNumberOfQuestions(int numberOfQuestions) {
		this.numberOfQuestions = numberOfQuestions;
	}

	public List<String> getTopics() {
		return topics;
	}

	public void setTopics(List<String> topics) {
		this.topics = topics;
	}
    
	
    
}
