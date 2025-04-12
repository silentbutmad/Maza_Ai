package com.maza_ai.chat;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.io.FileWriter;
import java.io.IOException;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class ChatController {
    ChatService chatService;


    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/ask-ai")
    public String getResponse(@RequestParam String input) {

        String result=chatService.getResponse(input);
        String modifide=result.replaceAll("\\*\\*(.*?)\\*\\*", " <b><div style=\"color:blue;\" >$1</div></b>");
        String output=modifide.replace("*","<br>");
        return output;


     }






    
}
