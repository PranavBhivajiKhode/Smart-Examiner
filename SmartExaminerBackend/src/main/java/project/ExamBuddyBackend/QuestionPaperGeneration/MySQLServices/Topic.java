package project.ExamBuddyBackend.QuestionPaperGeneration.MySQLServices;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Topic {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long topicId;
	
	@Column(nullable = false)
	private String topicName;
	
	@ManyToOne
	@JoinColumn(name = "subject_id" , nullable = false)
	private Subject subject;
	
	@OneToMany(mappedBy = "topic" , cascade = CascadeType.ALL)
	private List<Question> questions;

	public Long getTopicId() {
		return topicId;
	}

	public void setTopicId(Long topicId) {
		this.topicId = topicId;
	}

	public String getTopicName() {
		return topicName;
	}

	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}
	
	
	
	
	

}
