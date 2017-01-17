package com.github.henryorz.excel4j.config;

import com.github.henryorz.excel4j.type.DataValidator;
import com.github.henryorz.excel4j.type.ExcelFormat;

/**
 * Created by 周恒睿 on 2016/12/28.
 */
public class ColumnConfig {
    private int column;
    private String property;
    private ExcelFormat excelFormat;
    private Class<?> javaType;
    private Class<? extends DataValidator> dataValidator;

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public ExcelFormat getExcelFormat() {
        return excelFormat;
    }

    public void setExcelFormat(ExcelFormat excelFormat) {
        this.excelFormat = excelFormat;
    }

    public Class<?> getJavaType() {
        return javaType;
    }

    public void setJavaType(Class<?> javaType) {
        this.javaType = javaType;
    }

    public Class<? extends DataValidator> getDataValidator() {
        return dataValidator;
    }

    public void setDataValidator(Class<? extends DataValidator> dataValidator) {
        this.dataValidator = dataValidator;
    }
}
