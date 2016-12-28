package com.github.henryorz.excel4j.annotation.processor;

import com.github.henryorz.excel4j.annotation.Column;
import com.github.henryorz.excel4j.annotation.Columns;
import com.github.henryorz.excel4j.annotation.Sheet;
import com.github.henryorz.excel4j.config.ColumnConfig;
import com.github.henryorz.excel4j.config.SheetConfig;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 周恒睿 on 2016/12/28.
 */
public class Defaultprocessor implements AnnotationProcessor{

    public SheetConfig getSheetConfig(Method method) {

        SheetConfig sheetConfig = new SheetConfig();

        if(method == null){
            return null;
        }

        Sheet sheetAnn = method.getAnnotation(Sheet.class);

        sheetConfig.setHasTitle(sheetAnn.hasTitle());
        sheetConfig.setSheetName(sheetAnn.sheetName());
        sheetConfig.setRowHead(sheetAnn.rowHead());
        sheetConfig.setRowNum(sheetAnn.rowNum());

        Map<Integer, ColumnConfig> colMap = new HashMap<Integer, ColumnConfig>();
        Columns columnsAnn = method.getAnnotation(Columns.class);
        for(Column colAnn : columnsAnn.value()){
            ColumnConfig columnConfig = parseColumnConfig(colAnn);
            colMap.put(colAnn.column(), columnConfig);
        }
        sheetConfig.setColMap(colMap);

        return sheetConfig;
    }

    private ColumnConfig parseColumnConfig(Column colAnn){

        ColumnConfig columnConfig = new ColumnConfig();

        columnConfig.setColumn(colAnn.column());
        columnConfig.setProperty(colAnn.property());
        columnConfig.setJavaType(colAnn.javaType());
        columnConfig.setTypeHandler(colAnn.typeHandler());
        columnConfig.setDataValidator(colAnn.dataValidator());

        return columnConfig;
    }
}
