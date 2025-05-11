package com.project.SmartExaminerBackend.QuestionPaperGeneration.MySQL.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.project.SmartExaminerBackend.QuestionPaperGeneration.MySQL.Entities.Subject;
import com.project.SmartExaminerBackend.QuestionPaperGeneration.MySQL.Entities.Topic;
import com.project.SmartExaminerBackend.QuestionPaperGeneration.MySQL.Repositories.TopicRepo;

@Service
public class TopicService {

	private TopicRepo topicRepo;

	public TopicService(TopicRepo topicRepo) {
		super();
		this.topicRepo = topicRepo;
	}
	
	public List<Topic> retriveAllTopics(){
		return topicRepo.findAll();
	}
	
	public Topic retriveTopicById(Long topicId) {
		Optional<Topic> topic = topicRepo.findById(topicId);
		if(topic != null) {
			return topic.get();
		}
		return null;
	}
	
	public Topic findByTopicName(String topicName) {
		return topicRepo.findByTopicName(topicName);
	}
	
	public Topic findByTopicNameAndSubjectName(String topicName , Subject subject) {
		return topicRepo.findByTopicNameAndSubject(topicName, subject);
	}
	
}
