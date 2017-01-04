package com.henryorz.excel4j.excel;

import com.henryorz.excel4j.type.ExcelFormat;

/**
 * Created by 周恒睿 on 2017/1/4.
 */
public interface ExcelDataSource {
    Object getValue(int row, int column, ExcelFormat excelFormat, Class<?> javaType);
}
