package com.github.henryorz.excel4j.excel;

import com.github.henryorz.excel4j.config.SheetConfig;
import com.github.henryorz.excel4j.exceptions.*;
import com.github.henryorz.excel4j.type.DataValidator;
import com.github.henryorz.excel4j.config.ColumnConfig;
import com.github.henryorz.excel4j.util.ResultObject;
import com.github.henryorz.excel4j.util.TextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ExcelHandler {

    private Logger logger = LoggerFactory.getLogger(ExcelHandler.class);

    protected SheetConfig config;

    public ExcelHandler(SheetConfig config) {
        this.config = config;
    }

    public SheetConfig getConfig() {
        return config;
    }

    public void setConfig(SheetConfig config) {
        this.config = config;
    }

    public Object execute(Object[] args) throws Exception {
        switch (config.getOperation()) {
            case IMPORT:
                if (args == null) {
                    throw new ArgsException("args is null");
                }
                if (args.length != 1) {
                    throw new ArgsNumberException(1, args.length);
                }
                if (!(args[0] instanceof InputStream)) {
                    throw new ArgsTypeException(new Class[]{InputStream.class}, new Class[]{args[0].getClass()});
                }
                try {
                    Object result = importExcel((InputStream) args[0]);
                    return result;
                } catch (Exception e) {
                    throw new ExcelException(e);
                }
            case EXPORT:
                if (args == null) {
                    throw new ArgsException("args is null");
                }
                if (args.length != 2) {
                    throw new ArgsNumberException(1, args.length);
                }
                if (!(args[1] instanceof OutputStream)) {
                    throw new ArgsTypeException(new Class[]{OutputStream.class}, new Class[]{args[1].getClass()});
                }
                try {
                    exportExcel(args[0], (OutputStream) args[1]);
                } catch (Exception e) {
                    throw new ExcelException(e);
                }
        }
        return null;
    }

    protected Object importExcel(InputStream inputStream) throws Exception {
        if (inputStream == null) {
            throw new ExcelOpenException("InputStream is null");
        }
        ExcelDataSource excelData = PoiDataSource.newInstance(inputStream);

        if (List.class.isAssignableFrom(config.getReturnType()) && config.getParameterizedReturnType() != null) {

            List<Object> returnList = new ArrayList<Object>();
            Class<?> returnType = config.getParameterizedReturnType();

            for (int row = config.getRowHead(); row < config.getRowHead() + config.getRowNum(); row++) {
                Map<Integer, ColumnConfig> colMap = config.getColMap();
                Object obj = returnType.newInstance();
                for (ColumnConfig colConf : colMap.values()) {
                    Object val = excelData.get(config.getSheetName(), row, colConf.getColumn(), colConf.getExcelFormat(), colConf.getJavaType());
                    DataValidator validator = colConf.getDataValidator().newInstance();
                    ResultObject validateResult = validator.validate(val);
                    String setterName = TextUtil.setterName(colConf.getProperty());
                    Class<?> parameterType = returnType.getDeclaredField(colConf.getProperty()).getType();
                    Method setter = returnType.getDeclaredMethod(setterName, parameterType);
                    setter.invoke(obj, val);
                    if (validateResult.getFlag()) {
                        returnList.add(obj);
                    } else {
                        logger.error("Data validate failed : " + validateResult.getMessage());
                    }
                }
            }
            return returnList;
        } else if (!Collections.class.isAssignableFrom(config.getReturnType())) {
            Class<?> returnType = config.getReturnType();
            int row = config.getRowHead();
            Map<Integer, ColumnConfig> colMap = config.getColMap();
            Object obj = returnType.newInstance();
            for (ColumnConfig colConf : colMap.values()) {
                Object val = excelData.get(config.getSheetName(), row, colConf.getColumn(), colConf.getExcelFormat(), colConf.getJavaType());
                DataValidator validator = colConf.getDataValidator().newInstance();
                ResultObject validateResult = validator.validate(val);
                String setterName = TextUtil.setterName(colConf.getProperty());
                Method method = returnType.getDeclaredMethod(setterName);
                method.invoke(obj, val);
                if (validateResult.getFlag()) {
                    return obj;
                } else {
                    logger.error("Data validate failed : " + validateResult.getMessage());
                }
            }
        }
        throw new ReturnTypeException("Unsupported return type: " + config.getReturnType());
    }

    protected void exportExcel(Object obj, OutputStream outputStream) throws Exception {

        if (obj == null) {
            logger.error("object is null");
            throw new Excel4jException("object is null");
        }

        ExcelDataSource excelData = PoiDataSource.newInstance(config);

        Class<?> cl = obj.getClass();
        int rowStart = 0;
        Map<Integer, ColumnConfig> colMap = config.getColMap();

        if (config.hasTitle()) {
            for (Map.Entry<Integer, ColumnConfig> entry : colMap.entrySet()) {
                int col = entry.getKey();
                String propName = entry.getValue().getProperty();
                excelData.put(config.getSheetName(), rowStart, col, propName);
            }
            rowStart ++;
        }

        if (List.class.isAssignableFrom(cl)) {
            Class<?> paramClazz = config.getParameterizedParamTypes()[0];
            if(paramClazz == null){
                throw new ArgsTypeException("ParameterizedParamTypes is null");
            }
            int row = rowStart;
            for (Object item : ((List) obj)) {
                for (Map.Entry<Integer, ColumnConfig> entry : colMap.entrySet()) {
                    int col = entry.getKey();
                    String propName = entry.getValue().getProperty();
                    try {
                        String getterName = TextUtil.getterName(propName);
                        Method getter = paramClazz.getDeclaredMethod(getterName);
                        Object val = getter.invoke(item);
                        excelData.put(config.getSheetName(), row, col, val);
                    } catch (NoSuchMethodException e) {
                        logger.warn("No such Method : ", e.getMessage());
                    }
                }
                row++;
            }
            excelData.write(outputStream);
            return;
        } else if (!Collections.class.isAssignableFrom(config.getReturnType())) {
            for (Map.Entry<Integer, ColumnConfig> entry : colMap.entrySet()) {
                int col = entry.getKey();
                String propName = entry.getValue().getProperty();
                try {
                    Method getter = cl.getDeclaredMethod(TextUtil.getterName(propName));
                    Object val = getter.invoke(obj);
                    excelData.put(config.getSheetName(), rowStart, col, val);
                } catch (NoSuchMethodException e) {
                    logger.warn("No such Method : ", e.getMessage());
                }
            }
            excelData.write(outputStream);
            return;
        } else {
            throw new ReturnTypeException("Unsupported return type: " + config.getReturnType());
        }
    }
}
