package com.project.SmartExaminerBackend.QuestionPaperGeneration.MySQL.Repositories;

import org.springframework.data.domain.Pageable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.SmartExaminerBackend.QuestionPaperGeneration.MySQL.Entities.Question;
import com.project.SmartExaminerBackend.QuestionPaperGeneration.MySQL.Entities.Topic;

public interface QuestionRepo extends JpaRepository<Question, Long>{

	Question findByQuestionAndTopic(String question, Topic topic);
	
	@Query("SELECT q FROM Question q WHERE q.topic IN :topics AND q.difficulty = :difficulty AND q.type = :type AND q.marks = :marks ORDER BY function('RAND')")
    List<Question> findRandomQuestionsByCriteria(
        @Param("topics") List<Topic> topics,
        @Param("difficulty") String difficulty,
        @Param("type") String type,
        @Param("marks") int marks,
        Pageable pageable
    );
}

