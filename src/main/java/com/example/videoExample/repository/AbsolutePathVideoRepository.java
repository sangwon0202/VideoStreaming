package com.example.videoExample.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Repository;

import java.io.File;

@Repository
@RequiredArgsConstructor
public class AbsolutePathVideoRepository implements VideoRepository {

    @Value("${videoFolder}")
    private String videoFolder;

    @Override
    public File getVideo(String name) {
        try {

            Resource resource = new UrlResource("file:" + videoFolder + name + ".mp4");
            return resource.getFile();
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
