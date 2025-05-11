package com.project.SmartExaminerBackend.PlagiarismDetection;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {

	private final String UPLOAD_DIR = "E:\\Data";

    public void saveFiles(String batchId, MultipartFile[] files) throws IOException {
        for (MultipartFile file : files) {
            // Define batch folder
            File batchFolder = new File(UPLOAD_DIR + File.separator + batchId);
            if (!batchFolder.exists()) {
                batchFolder.mkdirs();  // ✅ Create directories if missing
            }

            // Define the destination file path
            File destFile = new File(batchFolder, file.getOriginalFilename());

            // ✅ Save file (this will now succeed because folders exist)
            file.transferTo(destFile);
        }
    }

//     Get all files for batch
    public File[] getFiles(String batchId) {
        Path batchPath = Paths.get(UPLOAD_DIR, batchId);
        File batchFolder = batchPath.toFile();
        if (batchFolder.exists() && batchFolder.isDirectory()) {
            return batchFolder.listFiles();
        }
        return new File[0];
    }
}




