package com.project.SmartExaminerBackend.PlagiarismDetection;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Service;

@Service
public class TextExtraction {
	
    public String detectAndExtract(File file) throws IOException {
        String fileName = file.getName().toLowerCase();
        if (fileName.endsWith(".pdf")) {
            return extractTextFromPDF(file);
        } else if (fileName.endsWith(".docx")) {
            return extractTextFromWord(file);
        } else {
            throw new IOException("Unsupported file type for file: " + fileName);
        }
    }
	// yes
    // yes
	public String extractTextFromWord(File file) throws IOException {
	    try (FileInputStream fis = new FileInputStream(file);
	         XWPFDocument doc = new XWPFDocument(fis);
	         XWPFWordExtractor extractor = new XWPFWordExtractor(doc)) {
	        return extractor.getText();
	    }
	}
	
	public String extractTextFromPDF(File file) throws IOException {
		PDDocument doc =  PDDocument.load(file);
		PDFTextStripper stripper = new PDFTextStripper();
		return stripper.getText(doc);
	}
}
