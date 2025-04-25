package project.ExamBuddyBackend.QuestionPaperGeneration.MySQLServices;

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

@Service
public class ExcelQuestionUploadService {

    private final TopicRepository topicRepository;
    private final QuestionRepository questionRepository;
    private final SubjectRepository subjectRepository;

    public ExcelQuestionUploadService(TopicRepository topicRepository, QuestionRepository questionRepository, SubjectRepository subjectRepository) {
        this.topicRepository = topicRepository;
        this.questionRepository = questionRepository;
        this.subjectRepository = subjectRepository;
    }

    public String importQuestionsFromExcel(MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0);

            List<Question> questions = new ArrayList<>();

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                String subjectName = getCellValueAsString(row.getCell(0));
                String topicName = getCellValueAsString(row.getCell(1));
                String questionText = getCellValueAsString(row.getCell(2));
                String difficulty = getCellValueAsString(row.getCell(3));
                String type = getCellValueAsString(row.getCell(4));
                String marksStr = getCellValueAsString(row.getCell(5));
                String options = getCellValueAsString(row.getCell(6));
                String answer = getCellValueAsString(row.getCell(7));

                int marks = (int) Double.parseDouble(marksStr.isEmpty() ? "0" : marksStr);

                Subject subject = subjectRepository.findBySubjectName(subjectName);
                if (subject == null) {
                    subject = new Subject();
                    subject.setSubjectName(subjectName);
                    subject = subjectRepository.save(subject);
                }

                Topic topic = topicRepository.findByTopicNameAndSubject(topicName, subject);
                if (topic == null) {
                    topic = new Topic();
                    topic.setTopicName(topicName);
                    topic.setSubject(subject);
                    topic = topicRepository.save(topic);
                }

                Question question = new Question();
                question.setQuestion(questionText);
                question.setDifficulty(difficulty);
                question.setType(type);
                question.setMarks(marks);
                question.setOptions(options);
                question.setAnswer(answer);
                question.setTopic(topic);

                questions.add(question);
            }

            questionRepository.saveAll(questions);
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
