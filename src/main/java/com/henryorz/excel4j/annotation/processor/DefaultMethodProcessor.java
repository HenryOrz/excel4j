package com.henryorz.excel4j.annotation.processor;

import com.henryorz.excel4j.annotation.Sheet;
import com.henryorz.excel4j.annotation.Column;
import com.henryorz.excel4j.annotation.Columns;
import com.henryorz.excel4j.config.ColumnConfig;
import com.henryorz.excel4j.config.SheetConfig;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 周恒睿 on 2016/12/28.
 */
public class DefaultMethodProcessor implements MethodProcessor {

    private Method method;

    public DefaultMethodProcessor(Method method){
        this.method = method;
    }

    public SheetConfig getSheetConfig() {

        SheetConfig sheetConfig = new SheetConfig();

        if(method == null){
            return null;
        }

        Sheet sheetAnn = method.getAnnotation(Sheet.class);

        sheetConfig.setHasTitle(sheetAnn.hasTitle());
        sheetConfig.setSheetName(sheetAnn.sheetName());
        sheetConfig.setRowHead(sheetAnn.rowHead());
        sheetConfig.setRowNum(sheetAnn.rowNum());
        sheetConfig.setColHead(sheetAnn.colHead());
        sheetConfig.setColNum(sheetAnn.colNum());
        sheetConfig.setOperation(sheetAnn.operation());
        sheetConfig.setReturnType(method.getReturnType());

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
