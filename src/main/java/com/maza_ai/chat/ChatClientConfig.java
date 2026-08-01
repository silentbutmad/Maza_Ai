package com.maza_ai.chat;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.image.ImageModel;
import org.springframework.ai.stabilityai.StabilityAiImageModel;
import org.springframework.ai.stabilityai.api.StabilityAiApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfig {

    @Bean
    public ChatClient geminiAiChatClient(ChatClient.Builder builder, ChatMemory chatMemory)
    {
        return builder.defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build()).build();
    }

    @Bean
    public ImageModel imageModel(@Value("sk-wRhTqEIDKkcuzASUhz6AgemeygxjI80sn2Qrg9l9r2Rw9r3d") String apiKey) {
        StabilityAiApi stabilityAiApi = new StabilityAiApi(apiKey);
        return new StabilityAiImageModel(stabilityAiApi);
    }

}