package com.sxhta.cloud.common.component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

public interface FileComponent {

    /**
     * 字符常量：斜杠 {@code '/'}
     */
    char SLASH = '/';

    /**
     * 字符常量：反斜杠 {@code '\\'}
     */
    char BACKSLASH = '\\';

    String FILENAME_PATTERN = "[a-zA-Z0-9_\\-|.\\u4e00-\\u9fa5]+";

    /**
     * 输出指定文件的byte数组
     *
     * @param filePath 文件路径
     * @param os       输出流
     */
    void writeBytes(String filePath, OutputStream os) throws IOException;

    /**
     * 删除文件
     *
     * @param filePath 文件
     */
    Boolean deleteFile(String filePath);

    /**
     * 文件名称验证
     *
     * @param filename 文件名称
     * @return true 正常 false 非法
     */
    Boolean isValidFilename(String filename);

    /**
     * 检查文件是否可下载
     *
     * @param resource 需要下载的文件
     * @return true 正常 false 非法
     */
    Boolean checkAllowDownload(String resource);

    /**
     * 下载文件名重新编码
     *
     * @param request  请求对象
     * @param fileName 文件名
     * @return 编码后的文件名
     */
    String setFileDownloadHeader(HttpServletRequest request, String fileName) throws UnsupportedEncodingException;

    /**
     * 返回文件名
     *
     * @param filePath 文件
     * @return 文件名
     */
    String getName(String filePath);

    /**
     * 是否为Windows或者Linux（Unix）文件分隔符<br>
     * Windows平台下分隔符为\，Linux（Unix）为/
     *
     * @param c 字符
     * @return 是否为Windows或者Linux（Unix）文件分隔符
     */
    Boolean isFileSeparator(char c);

    /**
     * 下载文件名重新编码
     *
     * @param response     响应对象
     * @param realFileName 真实文件名
     */
    void setAttachmentResponseHeader(HttpServletResponse response, String realFileName) throws UnsupportedEncodingException;

    /**
     * 百分号编码工具方法
     *
     * @param s 需要百分号编码的字符串
     * @return 百分号编码后的字符串
     */
    String percentEncode(String s) throws UnsupportedEncodingException;
}
