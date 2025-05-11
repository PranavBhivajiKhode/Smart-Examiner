package com.project.SmartExaminerBackend.PlagiarismDetection;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class CosineSimilarityUtil {
    
    public double calculateCosineSimilarity(String text1, String text2) {
        Map<String, Integer> freq1 = getWordFreq(text1);
        Map<String, Integer> freq2 = getWordFreq(text2);
        
        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;
        
        for (String word : freq1.keySet()) {
            int count1 = freq1.getOrDefault(word, 0);
            int count2 = freq2.getOrDefault(word, 0);
            dotProduct += count1 * count2;
            normA += Math.pow(count1, 2);
        }
        
        for (int count : freq2.values()) {
            normB += Math.pow(count, 2);
        }
        
        return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB) + 1e-10); // avoid divide by zero
    }
    
    private Map<String, Integer> getWordFreq(String text) {
        Map<String, Integer> freq = new HashMap<>();
        String[] words = text.toLowerCase().split("\\W+");
        for (String word : words) {
            freq.put(word, freq.getOrDefault(word, 0) + 1);
        }
        return freq;
    }
}

