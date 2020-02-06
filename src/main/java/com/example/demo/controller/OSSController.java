package com.example.demo.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
public class OSSController {

    @GetMapping(value = "/oss/{name}", produces = MediaType.IMAGE_JPEG_VALUE)
    public Object getOSS(@PathVariable("name") String name) {
        Path path = Paths.get("E:", "\\oss", "\\upload\\", name);
        try {
            return Files.readAllBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "GET OK";
    }

    @PostMapping("/oss/upload")
    public Object multiUpload(HttpServletRequest request) {
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        for (int i = 0; i < files.size(); i++) {
            MultipartFile file = files.get(i);
            if (file.isEmpty()) {
                return "上传第" + (i++) + "个文件失败";
            }
            Path path = Paths.get("E:", "\\oss", "\\upload\\", file.getOriginalFilename());
            try {
                file.transferTo(path);
            } catch (IOException e) {
                return "上传第" + (i++) + "个文件失败";
            }
        }
        return "PUT OK";
    }


    @PostMapping("/oss/multiUpload")
    public Object multiUpload(@RequestParam("file") MultipartFile multipartFile) {
        try {
            Path path = Paths.get("E:", "\\oss", "\\upload", multipartFile.getOriginalFilename());
            multipartFile.transferTo(path);
        } catch (IOException e) {
            e.printStackTrace();
            return "上传文件失败";
        }
        return "PUT OK";
    }
}

