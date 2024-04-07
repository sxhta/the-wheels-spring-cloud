package com.sxhta.cloud.storage.component;

import com.sxhta.cloud.common.exception.file.FileNameLengthLimitExceededException;
import com.sxhta.cloud.common.exception.file.FileSizeLimitExceededException;
import com.sxhta.cloud.common.exception.file.InvalidExtensionException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public interface FileUploadComponent {

    /**
     * 根据文件路径上传
     *
     * @param baseDir 相对应用的基目录
     * @param file    上传的文件
     * @return 文件名称
     */
    String upload(String baseDir, MultipartFile file) throws IOException;


    /**
     * 文件上传
     *
     * @param baseDir          相对应用的基目录
     * @param file             上传的文件
     * @param allowedExtension 上传文件类型
     * @return 返回上传成功的文件名
     * @throws FileSizeLimitExceededException       如果超出最大大小
     * @throws FileNameLengthLimitExceededException 文件名太长
     * @throws IOException                          比如读写文件出错时
     * @throws InvalidExtensionException            文件校验异常
     */
    String upload(String baseDir, MultipartFile file, String[] allowedExtension)
            throws FileSizeLimitExceededException, IOException, FileNameLengthLimitExceededException,
            InvalidExtensionException;

    /**
     * 编码文件名
     */
    String extractFilename(MultipartFile file);

    File getAbsoluteFile(String uploadDir, String fileName) throws IOException;

    String getPathFileName(String fileName);

    /**
     * 文件大小校验
     *
     * @param file 上传的文件
     * @throws FileSizeLimitExceededException 如果超出最大大小
     * @throws InvalidExtensionException      文件校验异常
     */
    void assertAllowed(MultipartFile file, String[] allowedExtension)
            throws FileSizeLimitExceededException, InvalidExtensionException;

    /**
     * 判断MIME类型是否是允许的MIME类型
     *
     * @param extension        上传文件类型
     * @param allowedExtension 允许上传文件类型
     * @return true/false
     */
    boolean isAllowedExtension(String extension, String[] allowedExtension);
}
