package com.project.SmartExaminerBackend.QuestionExtraction;

import java.util.*;
import java.util.regex.*;
import org.springframework.stereotype.Service;

@Service
public class QuestionSplitter {

    private static final Pattern MAIN_QUESTION_PATTERN = Pattern.compile(
        "(?im)^\\s*((Q\\s?\\d+)|([0-9]+[.)\\]]))\\s+"
    );

    private static final Pattern SUBQUESTION_PATTERN = Pattern.compile(
        "(?m)(\\([a-zA-Z]\\)|\\b[a-zA-Z]\\.|\\b[a-zA-Z]\\)|\\(i+\\)|\\bi+\\))\\s+"
    );

    public static List<String> splitQuestions(String text) {
        List<String> questions = new ArrayList<>();

        Matcher mainMatcher = MAIN_QUESTION_PATTERN.matcher(text);
        List<Integer> mainIndices = new ArrayList<>();

        while (mainMatcher.find()) {
            mainIndices.add(mainMatcher.start());
        }
        mainIndices.add(text.length());

        for (int i = 0; i < mainIndices.size() - 1; i++) {
            String block = text.substring(mainIndices.get(i), mainIndices.get(i + 1)).trim();

            String mainId = extractMainQuestionId(block);
            block = removeMainMarker(block);

            // Detect subquestions
            Matcher subMatcher = SUBQUESTION_PATTERN.matcher(block);
            List<Integer> subIndices = new ArrayList<>();
            while (subMatcher.find()) {
                subIndices.add(subMatcher.start());
            }
            subIndices.add(block.length());

            if (subIndices.size() > 1) {
                for (int j = 0; j < subIndices.size() - 1; j++) {
                    String sub = block.substring(subIndices.get(j), subIndices.get(j + 1)).trim();
                    String subId = extractSubId(sub);
                    sub = removeSubMarker(sub);
                    questions.add(mainId + "." + subId + ": " + sub);
                }
            } else {
                questions.add(mainId + ": " + block.trim());
            }
        }

        return questions;
    }

    private static String extractMainQuestionId(String block) {
        Matcher matcher = MAIN_QUESTION_PATTERN.matcher(block);
        if (matcher.find()) {
            return matcher.group().replaceAll("[^\\d]", ""); // e.g., "Q1." -> "1"
        }
        return "unknown";
    }

    private static String removeMainMarker(String block) {
        return block.replaceFirst("(?i)^\\s*((Q\\s?\\d+)|([0-9]+[.)\\]]))\\s+", "");
    }

    private static String extractSubId(String subBlock) {
        Matcher matcher = SUBQUESTION_PATTERN.matcher(subBlock);
        if (matcher.find()) {
            return matcher.group().replaceAll("[^a-zA-Zi]", "");
        }
        return "sub";
    }

    private static String removeSubMarker(String subBlock) {
        return subBlock.replaceFirst(SUBQUESTION_PATTERN.pattern(), "").trim();
    }
}
