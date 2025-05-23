package com.project.SmartExaminerBackend.QuestionExtraction;

import java.io.IOException;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class DocxExtractor {
    public String extractText(MultipartFile file) throws IOException {
        XWPFDocument doc = new XWPFDocument(file.getInputStream());
        StringBuilder sb = new StringBuilder();
        for (XWPFParagraph p : doc.getParagraphs()) {
            sb.append(p.getText()).append("\n");
        }
        doc.close();
        return sb.toString();
    }
}

