package com.maza_ai.image;

import org.springframework.stereotype.Service;

@Service
public class ImageService {

    public String getResponse(String mess)
    {

        //String ans=chatClient.prompt(mess).call().content();
        //  System.out.println("gogle search");
        // String searchResult = googleSearchService.search(mess);
        //     String enhancedQuery = mess + "\n" +"Additional context:  " + searchResult;
        // ans = chatClient.prompt(enhancedQuery).call().content();
        String ans="hi";
        return ans;
    }

}
