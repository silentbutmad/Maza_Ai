package com.maza_ai.chat;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.image.ImageModel;
import org.springframework.ai.stabilityai.StabilityAiImageModel;
import org.springframework.ai.stabilityai.api.StabilityAiApi;
import org.springframework.ai.vertexai.gemini.VertexAiGeminiChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfig {

    @Bean
    public ChatClient geminiAiChatClient(ChatClient.Builder builder)
    {

        return builder.defaultAdvisors(new MessageChatMemoryAdvisor(new InMemoryChatMemory())).build();
    }

    @Bean
    public ImageModel imageModel(@Value("sk-wRhTqEIDKkcuzASUhz6AgemeygxjI80sn2Qrg9l9r2Rw9r3d") String apiKey) {
        StabilityAiApi stabilityAiApi = new StabilityAiApi(apiKey);
        return new StabilityAiImageModel(stabilityAiApi);
    }

}
