
/*
package com.maza_ai.chat;



import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class ChatService {

    private final ChatClient chatClient;

    @Autowired
    private GoogleSearchService googleSearchService;

    public ChatService(@Qualifier("geminiAiChatClient") ChatClient chatClient) {
        this.chatClient = chatClient;
    }


    public String getResponse(String mess)
    {

                String ans=chatClient.prompt(mess).call().content();
                if(ans.contains("tell me more ")||ans.contains("more context")||ans.contains("tell me")) {
                    System.out.println("gogle search");
                    String searchResult = googleSearchService.search(mess);
                    System.out.println(searchResult+searchResult);
                    String enhancedQuery = mess + "\n" + "Additional context:  " + searchResult;
                    ans = chatClient.prompt(enhancedQuery).call().content();
                    //ans = chatClient.prompt(searchResult).call().content();

                }
                return ans;
    }

}

 */

package com.maza_ai.chat;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class ChatService {

    private final ChatClient chatClient;

    @Autowired
    private GoogleSearchService googleSearchService;

    public ChatService(@Qualifier("geminiAiChatClient") ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public String getResponse(String mess) {
        // Get initial AI response
        String aiResponse = chatClient.prompt(mess).call().content();

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
                aiResponse = chatClient.prompt(enhancedQuery).call().content();
            } else {
                System.out.println("No relevant Google Search results found. Using AI-only response.");
            }
        }

        return aiResponse;
    }
}


