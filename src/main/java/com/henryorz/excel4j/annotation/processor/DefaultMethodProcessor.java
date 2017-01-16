package com.henryorz.excel4j.annotation.processor;

import com.henryorz.excel4j.annotation.Sheet;
import com.henryorz.excel4j.annotation.Column;
import com.henryorz.excel4j.annotation.Columns;
import com.henryorz.excel4j.config.ColumnConfig;
import com.henryorz.excel4j.config.SheetConfig;

import java.lang.reflect.*;
import java.util.*;

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

        Type genericReturnType = method.getGenericReturnType();
        if (genericReturnType != null && genericReturnType instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) genericReturnType;
            Class<?> rawType = (Class<?>) parameterizedType.getRawType();
            if (Collection.class.isAssignableFrom(rawType)) {
                Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                if (actualTypeArguments != null && actualTypeArguments.length == 1) {
                    Type returnTypeParameter = actualTypeArguments[0];
                    if (returnTypeParameter instanceof Class<?>) {
                        Class<?> parameterizedReturnType = (Class<?>) returnTypeParameter;
                        sheetConfig.setParameterizedReturnType(parameterizedReturnType);
                    }
                }
            }
        }

        //TODO
        Type[] genericParameterTypes = method.getGenericParameterTypes();
        List<Class<?>> paramTypeParameterList = new ArrayList<Class<?>>();
        for(Type item : genericParameterTypes){
            if (item != null && item instanceof ParameterizedType) {
                ParameterizedType type = (ParameterizedType) item;
                Class<?> rawType = (Class<?>) type.getRawType();
                if (Collection.class.isAssignableFrom(rawType)) {
                    Type[] actualTypeArguments = type.getActualTypeArguments();
                    if (actualTypeArguments != null && actualTypeArguments.length == 1) {
                        Type paramTypeParameter = actualTypeArguments[0];
                        if (paramTypeParameter instanceof Class<?>) {
                            Class<?> parameterizedParmType = (Class<?>) paramTypeParameter;
                            paramTypeParameterList.add(parameterizedParmType);
                        }
                    }
                }
            }
        }
        sheetConfig.setParameterizedReturnType(paramTypeParameter);

        sheetConfig.setHasTitle(sheetAnn.hasTitle());
        sheetConfig.setSheetName(sheetAnn.sheetName());
        sheetConfig.setRowHead(sheetAnn.rowHead());
        sheetConfig.setRowNum(sheetAnn.rowNum());
        sheetConfig.setColHead(sheetAnn.colHead());
        sheetConfig.setColNum(sheetAnn.colNum());
        sheetConfig.setOperation(sheetAnn.operation());
        sheetConfig.setFileType(sheetAnn.fileType());

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
