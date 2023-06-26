package com.blogapp.services.impl;

import com.blogapp.services.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
        //file name
        String originalFileName = file.getOriginalFilename();

        // Extract the file extension
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));

        // Generate a unique filename
        String timeStamp = String.valueOf(System.currentTimeMillis());
        String randomID = String.valueOf((int) (Math.random() * 10000));
        String fileName = timeStamp + "_" + randomID + fileExtension;

        //full path
        String filePath = path+ File.separator+fileName;

        //create folder if not created
        File f = new File(path);
        if(!f.exists())
            f.mkdir();

        //file copy

        Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);



        return fileName;
    }

    @Override
    public InputStream getImage(String path, String fileName) throws FileNotFoundException {
        String fullPath = path+ File.separator+fileName;
        InputStream is = new FileInputStream(fullPath);
        //DB logic
        return is;
    }
}
