package com.project.SmartExaminerBackend.QuestionPaperGeneration.MySQL.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.SmartExaminerBackend.QuestionPaperGeneration.MySQL.Entities.Subject;
import com.project.SmartExaminerBackend.QuestionPaperGeneration.MySQL.Entities.Topic;

public interface TopicRepo extends JpaRepository<Topic, Long>{

	Topic findByTopicNameAndSubject(String topicName, Subject subject);

	Topic findByTopicName(String topicName);
}