package com.maza_ai.chat;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.fasterxml.jackson.databind.JsonNode;

@Service
public class GoogleSearchService {
    private static final String API_KEY = "AIzaSyCDL-jyY9nVmpcYrLH-WnPe9g_1dFiNzBQ";
    private static final String CX = "7576d45c732874017";
    private static final String SEARCH_URL = "https://www.googleapis.com/customsearch/v1";

    private final WebClient webClient;

    public GoogleSearchService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(SEARCH_URL).build();
    }

    public String search(String query) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("key", API_KEY)
                        .queryParam("cx", CX)
                        .queryParam("q", query)
                        .build())
                .retrieve()
                .bodyToMono(JsonNode.class)
                .map(response -> response.get("items").get(0).get("snippet").asText()) // Extract first result
                .block();
    }
}

