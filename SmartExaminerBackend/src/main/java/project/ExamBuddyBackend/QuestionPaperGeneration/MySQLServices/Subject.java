package project.ExamBuddyBackend.QuestionPaperGeneration.MySQLServices;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Subject {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long subjectId;
	
	@Column(nullable = false, unique = true)
	private String subjectName;
	
	@OneToMany(mappedBy = "subject", cascade = CascadeType.ALL)
	private List<Topic> topics;

	public Long getSubjectId() {
	    return subjectId;
	}

	public void setSubjectId(Long subjectId) {
	    this.subjectId = subjectId;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public List<Topic> getTopics() {
		return topics;
	}

	public void setTopics(List<Topic> topics) {
		this.topics = topics;
	}
	
	
	
	
}
