package com.github.henryorz.excel4j.excel;

import com.github.henryorz.excel4j.exceptions.ExcelException;
import com.github.henryorz.excel4j.type.ExcelFormat;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by 周恒睿 on 2017/1/4.
 */
public interface ExcelDataSource {
    Object get(String sheetName, int row, int column, ExcelFormat excelFormat, Class<?> javaType) throws ExcelException;
    void put(String sheetName, int row, int column, Object val);
    void write(OutputStream outputStream) throws IOException;
}
