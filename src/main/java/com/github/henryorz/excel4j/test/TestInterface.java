package com.github.henryorz.excel4j.test;

import com.github.henryorz.excel4j.annotation.Column;
import com.github.henryorz.excel4j.annotation.Columns;
import com.github.henryorz.excel4j.annotation.Sheet;
import com.github.henryorz.excel4j.config.Operation;
import com.github.henryorz.excel4j.type.ExcelFormat;

import java.io.InputStream;
import java.util.List;

/**
 * Created by 周恒睿 on 2016/12/29.
 */
public interface TestInterface {

    @Sheet(hasTitle = true, sheetName = "Sheet1", operation = Operation.IMPORT, rowHead = 1, rowNum = 3)
    @Columns({
            @Column(column = 0, property = "name", excelFormat = ExcelFormat.STRING),
            @Column(column = 1, property = "descp", excelFormat = ExcelFormat.STRING),
    })
    List<Object> testMethod(InputStream in);
}
