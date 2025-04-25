package project.ExamBuddyBackend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import project.ExamBuddyBackend.QuestionPaperGeneration.MySQLServices.Subject;
import project.ExamBuddyBackend.QuestionPaperGeneration.MySQLServices.SubjectRepository;

@SpringBootApplication
public class ExamBuddyBackendApplication {

    @Autowired
    private SubjectRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(ExamBuddyBackendApplication.class, args);
    }

    @Bean
    public CommandLineRunner demoData() {
        return args -> {
            Subject sub = repository.findById(1L).orElse(null);
            if (sub != null) {
                System.out.println("Subject: " + sub.getSubjectName());
            } else {
                System.out.println("Subject not found");
            }
        };
    }
}
