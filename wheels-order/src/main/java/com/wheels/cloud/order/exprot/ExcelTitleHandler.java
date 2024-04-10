package com.wheels.cloud.order.exprot;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.util.ObjectUtils;
import org.springframework.util.PropertyPlaceholderHelper;

import java.util.List;
import java.util.Properties;


public class ExcelTitleHandler implements CellWriteHandler {
    /**
     * 错误信息处理时正则表达式的格式
     */
    private final String EXCEL_ERROR_REG = "^(.*)(\\(错误:)(.*)(\\))$";

    private String titleName;

    private String createTime;

    PropertyPlaceholderHelper placeholderHelper = new PropertyPlaceholderHelper("${", "}");

    public ExcelTitleHandler(String titleName, String createTime) {
        this.titleName = titleName; //表头1
        this.createTime = createTime;  //表头2
    }


    @Override
    public void beforeCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row, Head head, Integer columnIndex, Integer relativeRowIndex, Boolean isHead) {
        // 动态设置表头字段
        if (!ObjectUtils.isEmpty(head)) {
            List<String> headNameList = head.getHeadNameList();
            if (CollectionUtils.isNotEmpty(headNameList)) {
                Properties properties = new Properties();
                properties.setProperty("titleName", titleName);
                properties.setProperty("createTime", createTime);
                for (int i = 0; i < headNameList.size(); i++) {
                    // 循环遍历替换
                    headNameList.set(i, placeholderHelper.replacePlaceholders(headNameList.get(i), properties));
                }
            }
        }
    }

}


