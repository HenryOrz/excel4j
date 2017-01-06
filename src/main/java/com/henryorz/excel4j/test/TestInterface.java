package com.henryorz.excel4j.test;

import com.henryorz.excel4j.annotation.Column;
import com.henryorz.excel4j.annotation.Sheet;
import com.henryorz.excel4j.config.Operation;
import com.henryorz.excel4j.type.ExcelFormat;
import com.henryorz.excel4j.annotation.Columns;

import java.io.InputStream;
import java.util.List;

/**
 * Created by 周恒睿 on 2016/12/29.
 */
public interface TestInterface {

    @Sheet(
            hasTitle = true,
            sheetName = "Sheet1",
            operation = Operation.IMPORT,
            rowHead = 1,
            rowNum = 3,
            colHead = 0,
            colNum = 2)
    @Columns({
            @Column(column = 0, property = "name", excelFormat = ExcelFormat.DEFAULT, javaType = String.class),
            @Column(column = 1, property = "descp", excelFormat = ExcelFormat.DEFAULT, javaType = String.class),
    })
    List<TestBean> testMethod(InputStream in);
}
