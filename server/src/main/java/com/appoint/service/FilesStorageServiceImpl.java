package com.appoint.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Stream;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;
@Service
public class FilesStorageServiceImpl implements FilesStorageService {
    private final Path root = Paths.get("src/main/uploads");
    @Override
    public void init() {
        try {
            Files.createDirectory(root);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    public static String getFileWithDate(String fileName, String fileSaperator, String dateFormat) {
        String FileNamePrefix = fileName.substring(0, fileName.lastIndexOf(fileSaperator));
        String FileNameSuffix = fileName.substring(fileName.lastIndexOf(fileSaperator)+1, fileName.length());
        String newFileName = new SimpleDateFormat("'"+FileNamePrefix+"_'"+dateFormat+"'"+fileSaperator+FileNameSuffix+"'").format(new Date());
        System.out.println("New File:"+newFileName);
        return newFileName;
    }
    @Override
    public String save(MultipartFile file) {
        try {
            String newName = getFileWithDate(file.getOriginalFilename(), ".","yyyyMMddHHmm");
            Files.copy(file.getInputStream(), this.root.resolve(newName));
            return newName;
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }
    @Override
    public Resource load(String filename) {
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }
    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(root.toFile());
    }
    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Could not load the files!");
        }
    }
}