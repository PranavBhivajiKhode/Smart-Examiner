package com.project.SmartExaminerBackend.Gemini;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class GeminiService {

	@Value("${gemini.api.url}")
	private String geminiApiUrl;

	@Value("${gemini.api.key}")
	private String geminiApiKey;

	private final WebClient webClient;

	public GeminiService(WebClient.Builder webClient) {
		this.webClient = webClient.build();
	}

	public String getAnswer(String question) {

//		Construct the requsetBody
//		"contents": [{
//		    "parts":[{"text": "Explain how AI works"}]
//		    }]
//		   }'

		Map<String, Object> requestBody = Map.of(
				"contents",new Object[] { 
						Map.of("parts", new Object[] { 
								Map.of("text", question) 
						}) 
				}
		);

		// MAke API call
		String text = webClient.post()
				.uri(geminiApiUrl + geminiApiKey)
				.header("Content-Type", "application/json")
				.bodyValue(requestBody)
				.retrieve()
				.bodyToMono(JsonNode.class)
				.map(root -> root.path("candidates").get(0).path("content").path("parts").get(0).path("text").asText())
				.block();

//		System.out.println(response);
		return text;
	}

	public String getQuestions(String prompt) {
		Map<String, Object> requestBody = Map.of(
			    "contents", new Object[] {
			        Map.of("parts", new Object[] {
			            Map.of("text", prompt)
			        })
			    }
			);
		
		ObjectMapper mapper = new ObjectMapper();

		String extractedQuestions = webClient.post()
		    .uri(geminiApiUrl + geminiApiKey)
		    .header("Content-Type", "application/json")
		    .bodyValue(requestBody)
		    .retrieve()
		    .bodyToMono(String.class)
		    .map(json -> {
		        try {
		            JsonNode root = mapper.readTree(json);
		            return root.path("candidates").get(0)
		                       .path("content")
		                       .path("parts").get(0)
		                       .path("text")
		                       .asText();
		        } catch (Exception e) {
		            throw new RuntimeException("Failed to parse response", e);
		        }
		    })
		    .block();
		return extractedQuestions;
		
	}

}
