package com.maza_ai.image;

import org.springframework.ai.image.*;

import org.springframework.ai.stabilityai.api.StabilityAiImageOptions;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ImageController {



    private ImageModel stabilityaiImageModel;

    public ImageController(@Qualifier("imageModel") ImageModel stabilityaiImageModel) {
        this.stabilityaiImageModel = stabilityaiImageModel;
    }


    @GetMapping("/image")
    public String getResponse()
    {
        ImageResponse response = stabilityaiImageModel.call(
                new ImagePrompt("A light cream colored mini golden doodle",
                        StabilityAiImageOptions.builder()
                                .stylePreset("cinematic")
                                .N(4)
                                .height(1024)
                                .width(1024)
                                .build())

                );

        System.out.println("Ji");
        return "Hi" +response.getResult().getOutput().getUrl();

    }
}
