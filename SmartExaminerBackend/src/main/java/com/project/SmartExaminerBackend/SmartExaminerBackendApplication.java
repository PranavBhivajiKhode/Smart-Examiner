package com.project.SmartExaminerBackend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.project.SmartExaminerBackend.QuestionPaperGeneration.MySQL.Entities.Subject;
import com.project.SmartExaminerBackend.QuestionPaperGeneration.MySQL.Repositories.SubjectRepo;

@SpringBootApplication
public class SmartExaminerBackendApplication {

	@Autowired
    private SubjectRepo repository;
	
	public static void main(String[] args) {
		SpringApplication.run(SmartExaminerBackendApplication.class, args);
	}
	
	


//    @Bean
//    public CommandLineRunner demoData() {
//        return args -> {
//            Subject sub = repository.findById(6L).orElse(null);
//            if (sub != null) {
//                System.out.println("Subject: " + sub.getSubjectName());
//            } else {
//                System.out.println("Subject not found");
//            }
//        };
//    }

}
