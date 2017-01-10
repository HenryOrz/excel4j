package com.henryorz.excel4j.excel;

import com.henryorz.excel4j.config.ColumnConfig;
import com.henryorz.excel4j.config.SheetConfig;
import com.henryorz.excel4j.type.DataValidator;
import com.henryorz.excel4j.exceptions.*;
import com.henryorz.excel4j.util.ResultObject;
import com.henryorz.excel4j.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.*;

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
                if (args.length != 1) {
                    throw new ArgsNumberException(1, args.length);
                }
                try {
                    OutputStream out = exportExcel(args[0]);
                    return out;
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
        ExcelImportDataSource excelData = new PoiImportDataSource(inputStream);

        if (List.class.isAssignableFrom(config.getReturnType()) && config.getParameterizedType()!=null) {

            List<Object> returnList = new ArrayList<Object>();
            Class<?> returnType=config.getParameterizedType();

            for (int row = config.getRowHead(); row < config.getRowHead() + config.getRowNum(); row++) {
                Map<Integer, ColumnConfig> colMap = config.getColMap();
                Object obj = returnType.newInstance();
                for (ColumnConfig colConf : colMap.values()) {
                    Object val = excelData.getValue(config.getSheetName(), row, colConf.getColumn(), colConf.getExcelFormat(), colConf.getJavaType());
                    DataValidator validator = colConf.getDataValidator().newInstance();
                    ResultObject validateResult = validator.validate(val);
                    String setterName = StringUtil.setterName(colConf.getProperty());
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
        }else if(!Collections.class.isAssignableFrom(config.getReturnType())){
            Class<?> returnType=config.getReturnType();
            int row = config.getRowHead();
            Map<Integer, ColumnConfig> colMap = config.getColMap();
            Object obj = returnType.newInstance();
            for (ColumnConfig colConf : colMap.values()) {
                Object val = excelData.getValue(config.getSheetName(), row, colConf.getColumn(), colConf.getExcelFormat(), colConf.getJavaType());
                DataValidator validator = colConf.getDataValidator().newInstance();
                ResultObject validateResult = validator.validate(val);
                String setterName = StringUtil.setterName(colConf.getProperty());
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

    protected OutputStream exportExcel(Object obj) {

        Class<?> clazz = obj.getClass();

        if (List.class.isAssignableFrom(clazz) && config.getParameterizedType()!=null) {

            List<Object> returnList = new ArrayList<Object>();
            Class<?> returnType=config.getParameterizedType();

            for (int row = config.getRowHead(); row < config.getRowHead() + config.getRowNum(); row++) {
                Map<Integer, ColumnConfig> colMap = config.getColMap();
                Object obj = returnType.newInstance();
                for (ColumnConfig colConf : colMap.values()) {
                    Object val = excelData.getValue(config.getSheetName(), row, colConf.getColumn(), colConf.getExcelFormat(), colConf.getJavaType());
                    DataValidator validator = colConf.getDataValidator().newInstance();
                    ResultObject validateResult = validator.validate(val);
                    String setterName = StringUtil.setterName(colConf.getProperty());
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
        }else if(!Collections.class.isAssignableFrom(config.getReturnType())){
            Class<?> returnType=config.getReturnType();
            int row = config.getRowHead();
            Map<Integer, ColumnConfig> colMap = config.getColMap();
            Object obj = returnType.newInstance();
            for (ColumnConfig colConf : colMap.values()) {
                Object val = excelData.getValue(config.getSheetName(), row, colConf.getColumn(), colConf.getExcelFormat(), colConf.getJavaType());
                DataValidator validator = colConf.getDataValidator().newInstance();
                ResultObject validateResult = validator.validate(val);
                String setterName = StringUtil.setterName(colConf.getProperty());
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

        return null;
    }
}
