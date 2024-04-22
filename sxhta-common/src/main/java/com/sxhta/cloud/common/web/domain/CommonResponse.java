package com.sxhta.cloud.common.web.domain;


import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageInfo;
import com.sxhta.cloud.common.web.page.TableDataInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * 操作消息提醒
 */
@Data
@EqualsAndHashCode(callSuper = false)
public final class CommonResponse<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Integer code;

    private String msg;

    private static final Integer warnCode = 601;

    private T data;

    /**
     * 初始化一个新创建的 AjaxResult 对象，使其表示一个空消息。
     */
    public CommonResponse() {
    }

    /**
     * 初始化一个新创建的 AjaxResult 对象
     *
     * @param code 状态码
     * @param msg  返回内容
     */
    public CommonResponse(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 初始化一个新创建的 AjaxResult 对象
     *
     * @param code 状态码
     * @param msg  返回内容
     * @param data 数据对象
     */
    public CommonResponse(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        if (ObjectUtil.isNotNull(data)) {
            this.data = data;
        }
    }

    /**
     * 返回成功消息
     *
     * @return 成功消息
     */
    public static <T> CommonResponse<T> success() {
        return new CommonResponse<>(HttpStatus.OK.value(), "操作成功", null);
    }

    /**
     * 返回成功数据
     *
     * @return 成功消息
     */
    public static <T> CommonResponse<T> success(T data) {
        return new CommonResponse<>(HttpStatus.OK.value(), "操作成功", data);
    }

    /**
     * 返回成功消息
     *
     * @param msg 返回内容
     * @return 成功消息
     */
    public static <T> CommonResponse<T> success(String msg) {
        return new CommonResponse<>(HttpStatus.OK.value(), msg, null);
    }

    public static <T> TableDataInfo<T> list(List<T> list) {
        return getDataTable(list);
    }

    private static <T> TableDataInfo<T> getDataTable(List<T> list) {
        final var rspData = new TableDataInfo<T>();
        rspData.setCode(HttpStatus.OK.value());
        rspData.setRows(list);
        rspData.setMsg("查询成功");
        final var total = new PageInfo<>(list).getTotal();
        rspData.setTotal(total);
        return rspData;
    }

    /**
     * 返回成功消息
     *
     * @param msg  返回内容
     * @param data 数据对象
     * @return 成功消息
     */
    public static <T> CommonResponse<T> success(String msg, T data) {
        return new CommonResponse<>(HttpStatus.OK.value(), msg, data);
    }

    public static CommonResponse<Boolean> result(Boolean status) {
        return result(status, null);
    }

    public static CommonResponse<Boolean> result(Boolean status, String msg) {
        if (status.equals(Boolean.TRUE)) {
            return success(msg, Boolean.TRUE);
        } else {
            return error(msg, Boolean.FALSE);
        }
    }

    /**
     * 返回警告消息
     *
     * @param msg 返回内容
     * @return 警告消息
     */
    public static <T> CommonResponse<T> warn(String msg) {
        return new CommonResponse<>(warnCode, msg, null);
    }

    /**
     * 返回警告消息
     *
     * @param msg  返回内容
     * @param data 数据对象
     * @return 警告消息
     */
    public static <T> CommonResponse<T> warn(String msg, T data) {
        return new CommonResponse<>(warnCode, msg, data);
    }

    /**
     * 返回错误消息
     *
     * @return 错误消息
     */
    public static <T> CommonResponse<T> error() {
        return new CommonResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "操作失败", null);
    }

    /**
     * 返回错误消息
     *
     * @param msg 返回内容
     * @return 错误消息
     */
    public static <T> CommonResponse<T> error(String msg) {
        return new CommonResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg, null);
    }

    /**
     * 返回错误消息
     *
     * @param msg  返回内容
     * @param data 数据对象
     * @return 错误消息
     */
    public static <T> CommonResponse<T> error(String msg, T data) {
        return new CommonResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg, data);
    }

    /**
     * 返回错误消息
     *
     * @param code 状态码
     * @param msg  返回内容
     * @return 错误消息
     */
    public static <T> CommonResponse<T> error(Integer code, String msg) {
        return new CommonResponse<>(code, msg, null);
    }

    /**
     * 是否为成功消息
     *
     * @return 结果
     */
    public Boolean isSuccess() {
        return Objects.equals(HttpStatus.OK.value(), code);
    }

    /**
     * 是否为警告消息
     *
     * @return 结果
     */
    public Boolean isWarn() {
        return Objects.equals(warnCode, code);
    }

    /**
     * 是否为错误消息
     *
     * @return 结果
     */
    public Boolean isError() {
        return Objects.equals(HttpStatus.INTERNAL_SERVER_ERROR.value(), code);
    }
}