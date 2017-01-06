package com.henryorz.excel4j.annotation.processor;

import com.henryorz.excel4j.annotation.Sheet;
import com.henryorz.excel4j.annotation.Column;
import com.henryorz.excel4j.annotation.Columns;
import com.henryorz.excel4j.config.ColumnConfig;
import com.henryorz.excel4j.config.SheetConfig;

import java.lang.reflect.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 周恒睿 on 2016/12/28.
 */
public class DefaultMethodProcessor implements MethodProcessor {

    private Method method;

    public DefaultMethodProcessor(Method method) {
        this.method = method;
    }

    public SheetConfig getSheetConfig() {

        SheetConfig sheetConfig = new SheetConfig();

        if (method == null) {
            return null;
        }

        Sheet sheetAnn = method.getAnnotation(Sheet.class);

        Class<?> returnType = method.getReturnType();
        sheetConfig.setReturnType(returnType);

        Type type = method.getGenericReturnType();
        if (type != null && type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Class<?> rawType = (Class<?>) parameterizedType.getRawType();
            if (Collection.class.isAssignableFrom(rawType)) {
                Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                if (actualTypeArguments != null && actualTypeArguments.length == 1) {
                    Type returnTypeParameter = actualTypeArguments[0];
                    if (returnTypeParameter instanceof Class<?>) {
                        Class<?> parameterizedReturnType = (Class<?>) returnTypeParameter;
                        sheetConfig.setParameterizedType(parameterizedReturnType);
                    }
                }
            }
        }

        sheetConfig.setHasTitle(sheetAnn.hasTitle());
        sheetConfig.setSheetName(sheetAnn.sheetName());
        sheetConfig.setRowHead(sheetAnn.rowHead());
        sheetConfig.setRowNum(sheetAnn.rowNum());
        sheetConfig.setColHead(sheetAnn.colHead());
        sheetConfig.setColNum(sheetAnn.colNum());
        sheetConfig.setOperation(sheetAnn.operation());

        Map<Integer, ColumnConfig> colMap = new HashMap<Integer, ColumnConfig>();
        Columns columnsAnn = method.getAnnotation(Columns.class);
        for (Column colAnn : columnsAnn.value()) {
            ColumnConfig columnConfig = parseColumnConfig(colAnn);
            colMap.put(colAnn.column(), columnConfig);
        }
        sheetConfig.setColMap(colMap);

        return sheetConfig;
    }

    private ColumnConfig parseColumnConfig(Column colAnn) {

        ColumnConfig columnConfig = new ColumnConfig();

        columnConfig.setColumn(colAnn.column());
        columnConfig.setProperty(colAnn.property());
        columnConfig.setExcelFormat(colAnn.excelFormat());
        columnConfig.setJavaType(colAnn.javaType());
        columnConfig.setDataValidator(colAnn.dataValidator());

        return columnConfig;
    }
}
