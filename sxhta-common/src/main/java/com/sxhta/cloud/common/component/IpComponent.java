package com.sxhta.cloud.common.component;

import jakarta.servlet.http.HttpServletRequest;

public interface IpComponent {

    /**
     * 获取客户端IP
     *
     * @return IP地址
     */
    String getIpAddr();

    /**
     * 获取客户端IP
     *
     * @param request 请求对象
     * @return IP地址
     */
    String getIpAddr(HttpServletRequest request);

    /**
     * 检查是否为内部IP地址
     *
     * @param ip IP地址
     * @return 结果
     */
    Boolean internalIp(String ip);

    /**
     * 检查是否为内部IP地址
     *
     * @param addr byte地址
     * @return 结果
     */
    Boolean internalIp(byte[] addr);

    /**
     * 将IPv4地址转换成字节
     *
     * @param text IPv4地址
     * @return byte 字节
     */
    byte[] textToNumericFormatV4(String text);

    /**
     * 获取IP地址
     *
     * @return 本地IP地址
     */
    String getHostIp();

    /**
     * 获取主机名
     *
     * @return 本地主机名
     */
    String getHostName();

    /**
     * 从多级反向代理中获得第一个非unknown IP地址
     *
     * @param ip 获得的IP地址
     * @return 第一个非unknown IP地址
     */
    String getMultistageReverseProxyIp(String ip);

    /**
     * 检测给定字符串是否为未知，多用于检测HTTP请求相关
     *
     * @param checkString 被检测的字符串
     * @return 是否未知
     */
    Boolean isUnknown(String checkString);

    /**
     * 是否为IP
     */
    Boolean isIP(String ip);

    /**
     * 是否为IP，或 *为间隔的通配符地址
     */
    Boolean isIpWildCard(String ip);

    /**
     * 检测参数是否在ip通配符里
     */
    Boolean ipIsInWildCardNoCheck(String ipWildCard, String ip);

    /**
     * 是否为特定格式如:“10.10.10.1-10.10.10.99”的ip段字符串
     */
    Boolean isIPSegment(String ipSeg);

    /**
     * 判断ip是否在指定网段中
     */
    Boolean ipIsInNetNoCheck(String iparea, String ip);

    /**
     * 校验ip是否符合过滤串规则
     *
     * @param filter 过滤IP列表,支持后缀'*'通配,支持网段如:`10.10.10.1-10.10.10.99`
     * @param ip     校验IP地址
     * @return Boolean 结果
     */
    Boolean isMatchedIp(String filter, String ip);
}
