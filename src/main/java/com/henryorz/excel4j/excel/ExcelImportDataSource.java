package com.henryorz.excel4j.excel;

import com.henryorz.excel4j.exceptions.ExcelException;
import com.henryorz.excel4j.type.ExcelFormat;

/**
 * Created by 周恒睿 on 2017/1/4.
 */
public interface ExcelImportDataSource {
    Object getValue(String sheetName, int row, int column, ExcelFormat excelFormat, Class<?> javaType) throws ExcelException;
}
