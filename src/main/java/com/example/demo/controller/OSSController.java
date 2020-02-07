package com.example.demo.controller;

import com.example.demo.exception.DemoException;
import com.example.demo.model.DirMgtPO;
import com.example.demo.service.OSSService;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Objects;

@RestController
public class OSSController {

    @Autowired
    public OSSService ossService;

    @GetMapping(value = "/oss/{name}", produces = MediaType.IMAGE_JPEG_VALUE)
    public Object getOSS(@PathVariable("name") String name) {
        DirMgtPO dirMgtPO = ossService.get(name);
        if(Objects.isNull(dirMgtPO)){
            return null;
        }
        Path path = Paths.get("E:", "\\oss", "\\upload\\", name);
        try {
            return Files.readAllBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "GET OK";
    }

    @PostMapping("/oss/multiUpload")
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


    @PostMapping("/oss/singleUpload")
    public Object singleUpload(@RequestParam("file") MultipartFile multipartFile) throws DemoException {
        ossService.save(multipartFile.getOriginalFilename());
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

