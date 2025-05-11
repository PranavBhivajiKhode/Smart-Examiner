package com.project.SmartExaminerBackend.QuestionPaperGeneration.MySQL.Services;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.project.SmartExaminerBackend.QuestionPaperGeneration.MySQL.Entities.Question;
import com.project.SmartExaminerBackend.QuestionPaperGeneration.MySQL.Entities.Subject;
import com.project.SmartExaminerBackend.QuestionPaperGeneration.MySQL.Entities.Topic;
import com.project.SmartExaminerBackend.QuestionPaperGeneration.MySQL.Repositories.QuestionRepo;
import com.project.SmartExaminerBackend.QuestionPaperGeneration.MySQL.Repositories.SubjectRepo;
import com.project.SmartExaminerBackend.QuestionPaperGeneration.MySQL.Repositories.TopicRepo;

@Service
public class ExcelQuestionUploadService {

    private final TopicRepo topicRepo;
    private final QuestionRepo questionRepo;
    private final SubjectRepo subjectRepo;

    public ExcelQuestionUploadService(TopicRepo topicRepo, QuestionRepo questionRepo, SubjectRepo subjectRepo) {
        this.topicRepo = topicRepo;
        this.questionRepo = questionRepo;
        this.subjectRepo = subjectRepo;
    }

    public String importQuestionsFromExcel(MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();
            Workbook workbook = WorkbookFactory.create(inputStream);
            int numberOfSheets = workbook.getNumberOfSheets();
            
            List<Question> questions = new ArrayList<>();
            
            for(int sheetIndex = 0; sheetIndex < numberOfSheets; sheetIndex++) {
            	Sheet sheet = workbook.getSheetAt(sheetIndex);

                for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                    Row row = sheet.getRow(i);
                    if (row == null) continue;

                    String subjectName = getCellValueAsString(row.getCell(0));
                    if(subjectName.isEmpty() || subjectName.isBlank()) continue;
                    
                    String topicName = getCellValueAsString(row.getCell(1));
                    if(topicName.isEmpty() || topicName.isBlank()) continue;
                    
                    String questionText = getCellValueAsString(row.getCell(2));
                    String difficulty = getCellValueAsString(row.getCell(3));
                    String type = getCellValueAsString(row.getCell(4));
                    String marksStr = getCellValueAsString(row.getCell(5));
                    String options = getCellValueAsString(row.getCell(6));
                    String answer = getCellValueAsString(row.getCell(7));

                    int marks = (int) Double.parseDouble(marksStr.isEmpty() ? "0" : marksStr);

                    Subject subject = subjectRepo.findBySubjectName(subjectName);
                    if (subject == null) {
                        subject = new Subject();
                        subject.setSubjectName(subjectName);
                        subject = subjectRepo.save(subject);
                    }

                    Topic topic = topicRepo.findByTopicNameAndSubject(topicName, subject);
                    if (topic == null) {
                        topic = new Topic();
                        topic.setTopicName(topicName);
                        topic.setSubject(subject);
                        topic = topicRepo.save(topic);
                    }
                    
                    Question question = questionRepo.findByQuestionAndTopic(questionText, topic);
                    if(question == null) {
                    	question = new Question();
                        question.setQuestion(questionText);
                        question.setDifficulty(difficulty);
                        question.setType(type);
                        question.setMarks(marks);
                        question.setOptions(options);
                        question.setAnswer(answer);
                        question.setTopic(topic);

                        questions.add(question);
                    }
                }
            }
            questionRepo.saveAll(questions);
            workbook.close();
            return "Questions uploaded successfully.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error uploading questions: " + e.getMessage();
        }
    }

    private String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            case BLANK:
                return "";
            default:
                return "";
        }
    }
}

