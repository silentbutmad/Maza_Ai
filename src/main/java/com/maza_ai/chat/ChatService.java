package com.maza_ai.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Service
public class ChatService {

    @Value("${groq.api-key:YOUR_GROQ_API_KEY}")
    private String apiKey;

    @Autowired
    private GoogleSearchService googleSearchService;

    private final RestClient restClient = RestClient.create();

    private String callGroqApi(String promptText) {
        String url = "https://api.groq.com/openai/v1/chat/completions";

        Map<String, Object> requestBody = Map.of(
                "model", "llama-3.3-70b-versatile",
                "messages", List.of(
                        Map.of("role", "user", "content", promptText)
                )
        );

        Map response = restClient.post()
                .uri(url)
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .body(requestBody)
                .retrieve()
                .body(Map.class);

        if (response != null && response.containsKey("choices")) {
            List choices = (List) response.get("choices");
            if (choices != null && !choices.isEmpty()) {
                Map firstChoice = (Map) choices.get(0);
                Map message = (Map) firstChoice.get("message");
                if (message != null && message.containsKey("content")) {
                    return (String) message.get("content");
                }
            }
        }
        return "No response received from Groq AI model.";
    }

    public String getResponse(String mess) {
        try {
            // Get initial AI response via Groq API
            String aiResponse = callGroqApi(mess);

            // Keywords indicating lack of sufficient context
            boolean needsMoreContext = aiResponse.toLowerCase().contains("tell me more") ||
                    aiResponse.toLowerCase().contains("more context") ||
                    aiResponse.toLowerCase().contains("tell me") ||
                    aiResponse.toLowerCase().contains("not enough information");

            // If AI response lacks context, improve it with Google Search
            if (needsMoreContext) {
                System.out.println("Performing Google Search for better response...");

                // Fetch search results
                String searchResult = googleSearchService.search(mess);

                // Ensure we have meaningful search data
                if (searchResult != null && !searchResult.trim().isEmpty()) {
                    System.out.println("Google Search Data: " + searchResult);

                    // Use structured prompt enhancement
                    String enhancedQuery = String.format(
                            "User's question: %s\n\n"
                                    + "Additional background information from Google Search:\n%s\n\n"
                                    + "Based on this new information, provide a more accurate and detailed response.",
                            mess, searchResult.trim()
                    );

                    // Get AI response with improved context
                    aiResponse = callGroqApi(enhancedQuery);
                } else {
                    System.out.println("No relevant Google Search results found. Using AI-only response.");
                }
            }

            return aiResponse;
        } catch (Exception e) {
            System.err.println("Error calling Groq API: " + e.getMessage());
            if (e.getMessage() != null && e.getMessage().contains("401")) {
                return "Invalid or missing Groq API Key. Please set groq.api-key in application.properties.";
            } else if (e.getMessage() != null && e.getMessage().contains("429")) {
                return "Groq API rate limit reached. Please retry in a few seconds.";
            }
            return "Unable to get response from Groq AI at this moment. Error: " + e.getMessage();
        }
    }
}