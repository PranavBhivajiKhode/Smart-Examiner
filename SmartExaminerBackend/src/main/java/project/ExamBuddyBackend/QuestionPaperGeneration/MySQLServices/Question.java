package project.ExamBuddyBackend.QuestionPaperGeneration.MySQLServices;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;

@Entity
public class Question {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long questionId;
	
	@ManyToOne
	@JoinColumn(name = "topic_id" , nullable = false)
	private Topic topic;
	
	@Lob
	@Column(nullable = false , columnDefinition = "TEXT")
	private String question;
	
	@Column(nullable = false)
	private String difficulty;
	
	@Column(nullable = false)
	private String type;
	
	@Column(nullable = false)
	private int marks;
	
	@Lob
	@Column(nullable = true , columnDefinition = "TEXT")
	private String options;
	
	@Lob
	@Column(nullable = false , columnDefinition = "TEXT")
	private String answer;

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getMarks() {
		return marks;
	}

	public void setMarks(int marks) {
		this.marks = marks;
	}

	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	
}
