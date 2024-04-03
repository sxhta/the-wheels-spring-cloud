package com.sxhta.cloud.storage.component.impl;

import com.sxhta.cloud.common.exception.file.FileException;
import com.sxhta.cloud.common.exception.file.FileNameLengthLimitExceededException;
import com.sxhta.cloud.common.exception.file.FileSizeLimitExceededException;
import com.sxhta.cloud.common.exception.file.InvalidExtensionException;
import com.sxhta.cloud.common.utils.CommonDateUtil;
import com.sxhta.cloud.common.utils.CommonStringUtil;
import com.sxhta.cloud.common.utils.file.FileTypeUtils;
import com.sxhta.cloud.common.utils.file.MimeTypeUtils;
import com.sxhta.cloud.common.utils.uuid.Seq;
import com.sxhta.cloud.storage.component.FileUploadComponent;
import jakarta.inject.Singleton;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * 文件上传工具类
 */
@Singleton
@Component
public class FileUploadComponentImpl implements FileUploadComponent, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 默认大小 50M
     */
    private static final Long DEFAULT_MAX_SIZE = 10 * 1024 * 1024L;

    /**
     * 默认的文件名最大长度 100
     */
    private static final Integer DEFAULT_FILE_NAME_LENGTH = 100;

    /**
     * 根据文件路径上传
     *
     * @param baseDir 相对应用的基目录
     * @param file    上传的文件
     * @return 文件名称
     */
    @Override
    public String upload(String baseDir, MultipartFile file) throws IOException {
        try {
            return upload(baseDir, file, MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION);
        } catch (FileException fe) {
            throw new IOException(fe.getDefaultMessage(), fe);
        } catch (Exception e) {
            throw new IOException(e.getMessage(), e);
        }
    }

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
    @Override
    public String upload(String baseDir, MultipartFile file, String[] allowedExtension)
            throws FileSizeLimitExceededException, IOException, FileNameLengthLimitExceededException,
            InvalidExtensionException {
        final var fileNameLength = Objects.requireNonNull(file.getOriginalFilename()).length();
        if (fileNameLength > FileUploadComponentImpl.DEFAULT_FILE_NAME_LENGTH) {
            throw new FileNameLengthLimitExceededException(FileUploadComponentImpl.DEFAULT_FILE_NAME_LENGTH);
        }

        assertAllowed(file, allowedExtension);

        final var fileName = extractFilename(file);

        final var absPath = getAbsoluteFile(baseDir, fileName).getAbsolutePath();
        file.transferTo(Paths.get(absPath));
        return getPathFileName(fileName);
    }

    /**
     * 编码文件名
     */
    @Override
    public String extractFilename(MultipartFile file) {
        return CommonStringUtil.format("{}/{}_{}.{}", CommonDateUtil.datePath(),
                FilenameUtils.getBaseName(file.getOriginalFilename()), Seq.getId(Seq.uploadSeqType), FileTypeUtils.getExtension(file));
    }

    @Override
    public File getAbsoluteFile(String uploadDir, String fileName) throws IOException {
        final var desc = new File(uploadDir + File.separator + fileName);

        if (!desc.exists()) {
            if (!desc.getParentFile().exists()) {
                final var result = desc.getParentFile().mkdirs();
                if (!result) {
                    throw new IOException("文件目录创建失败");
                }
            }
        }
        return desc.isAbsolute() ? desc : desc.getAbsoluteFile();
    }

    @Override
    public String getPathFileName(String fileName) {
        return "/" + fileName;
    }

    /**
     * 文件大小校验
     *
     * @param file 上传的文件
     * @throws FileSizeLimitExceededException 如果超出最大大小
     * @throws InvalidExtensionException      文件校验异常
     */
    @Override
    public void assertAllowed(MultipartFile file, String[] allowedExtension)
            throws FileSizeLimitExceededException, InvalidExtensionException {
        final var size = file.getSize();
        if (size > DEFAULT_MAX_SIZE) {
            throw new FileSizeLimitExceededException(DEFAULT_MAX_SIZE / 1024 / 1024);
        }
        final var fileName = file.getOriginalFilename();
        final var extension = FileTypeUtils.getExtension(file);
        if (allowedExtension != null && !isAllowedExtension(extension, allowedExtension)) {
            if (allowedExtension == MimeTypeUtils.IMAGE_EXTENSION) {
                throw new InvalidExtensionException.InvalidImageExtensionException(allowedExtension, extension,
                        fileName);
            } else if (allowedExtension == MimeTypeUtils.FLASH_EXTENSION) {
                throw new InvalidExtensionException.InvalidFlashExtensionException(allowedExtension, extension,
                        fileName);
            } else if (allowedExtension == MimeTypeUtils.MEDIA_EXTENSION) {
                throw new InvalidExtensionException.InvalidMediaExtensionException(allowedExtension, extension,
                        fileName);
            } else if (allowedExtension == MimeTypeUtils.VIDEO_EXTENSION) {
                throw new InvalidExtensionException.InvalidVideoExtensionException(allowedExtension, extension,
                        fileName);
            } else {
                throw new InvalidExtensionException(allowedExtension, extension, fileName);
            }
        }
    }

    /**
     * 判断MIME类型是否是允许的MIME类型
     *
     * @param extension        上传文件类型
     * @param allowedExtension 允许上传文件类型
     * @return true/false
     */
    @Override
    public Boolean isAllowedExtension(String extension, String[] allowedExtension) {
        for (final var str : allowedExtension) {
            if (str.equalsIgnoreCase(extension)) {
                return true;
            }
        }
        return false;
    }
}