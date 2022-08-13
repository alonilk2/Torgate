package com.appoint.service;

import com.appoint.entity.InputFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {

    List<InputFile> uploadFiles(MultipartFile[] files);
}