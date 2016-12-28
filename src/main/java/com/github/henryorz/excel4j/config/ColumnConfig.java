package com.github.henryorz.excel4j.config;

import com.github.henryorz.excel4j.format.DataValidator;
import com.github.henryorz.excel4j.format.ExcelFormat;
import com.github.henryorz.excel4j.format.TypeHandler;

/**
 * Created by 周恒睿 on 2016/12/28.
 */
public class ColumnConfig {
    private String property;
    private ExcelFormat excelFormat;
    private Class<?> javaType;
    private TypeHandler<?> typeHandler;
    private DataValidator<?> validator;
}
