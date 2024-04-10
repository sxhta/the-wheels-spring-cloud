package com.sxhta.cloud.wheels.remote.vo.excel;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
public class PublicExportData implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private List<?> dataList;

    private List<Integer> groupDataList;
}
