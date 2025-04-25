package project.ExamBuddyBackend.QuestionPaperGeneration.MySQLServices;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<Topic, Long>{

	Topic findByTopicNameAndSubject(String topicName, Subject subject);


}
