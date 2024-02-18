package com.example.videoExample.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;

import java.io.File;

@Repository
@RequiredArgsConstructor
public class StaticVideoRepository implements VideoRepository {

    private final ResourceLoader resourceLoader;

    @Override
    public File getVideo(String name) {
        Resource resource = resourceLoader.getResource("classpath:static/" + name + ".mp4");
        if(resource.exists()) {
            try {
                return resource.getFile();
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
