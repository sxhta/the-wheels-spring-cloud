package com.sxhta.cloud.common.web.controller;

import com.github.pagehelper.PageHelper;
import com.sxhta.cloud.common.utils.CommonDateUtil;
import com.sxhta.cloud.common.web.page.PageRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.beans.PropertyEditorSupport;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * web层通用数据处理
 */
public abstract class BaseController {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 将前台传递过来的日期格式的字符串，自动转化为Date类型
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        //Date 类型转换
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(CommonDateUtil.parseDate(text));
            }
        });

        //LocalDateTime 类型转换
        binder.registerCustomEditor(LocalDateTime.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(CommonDateUtil.parseDate(text));
            }
        });

    }

    /**
     * 设置请求分页数据
     */
    protected void startPage(PageRequest pageRequest) {
        PageHelper.startPage(pageRequest.getPageNum(), pageRequest.getPageSize());
    }

    /**
     * 清理分页的线程变量
     */
    protected void clearPage() {
        PageHelper.clearPage();
    }
}