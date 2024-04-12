package com.sxhta.cloud.content.service;

import com.sxhta.cloud.content.response.UploadResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ContentUploadService {

    UploadResponse uploadFile(MultipartFile file, String folder);
}
