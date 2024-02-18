package com.example.videoExample.controller;


import com.example.videoExample.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/video")
public class VideoController {

    private final ResourceLoader resourceLoader;
    private final VideoRepository videoRepository;

    @GetMapping("/get")
    public  ResponseEntity<StreamingResponseBody> get(@RequestParam String name) {
        File file = videoRepository.getVideo(name);
        if(file == null) {
            return new ResponseEntity<StreamingResponseBody>(HttpStatus.NOT_FOUND);
        }

        StreamingResponseBody streamingResponseBody = new StreamingResponseBody() {
            @Override
            public void writeTo(OutputStream outputStream) throws IOException {
                try {
                    final InputStream inputStream = new FileInputStream(file);

                    byte[] bytes = new byte[1024];
                    int length;
                    while ((length = inputStream.read(bytes)) >= 0) {
                        outputStream.write(bytes, 0, length);
                    }
                    inputStream.close();
                    outputStream.flush();

                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        };

        final HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "video/mp4");
        responseHeaders.add("Content-Length", Long.toString(file.length()));

        return ResponseEntity.ok().headers(responseHeaders).body(streamingResponseBody);
    }
}
