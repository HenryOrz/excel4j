package com.henryorz.excel4j.excel;

import com.henryorz.excel4j.config.ColumnConfig;
import com.henryorz.excel4j.config.SheetConfig;
import com.henryorz.excel4j.type.DataValidator;
import com.henryorz.excel4j.type.ExcelFormat;
import com.henryorz.excel4j.exceptions.*;
import com.henryorz.excel4j.util.ResultObject;
import com.henryorz.excel4j.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class ExcelHandler {

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
                return exportExcel();
        }
        return null;
    }

    protected Object importExcel(InputStream inputStream) throws Exception {
        if (inputStream == null) {
            throw new ExcelOpenException("InputStream is null");
        }
        ExcelDataSource excelData = new PoiExcelDataSource(inputStream);

        List<Object> returnList = new ArrayList<Object>();

        for (int row = config.getRowHead(); row < config.getRowHead() + config.getRowNum(); row++) {
            Map<Integer, ColumnConfig> colMap = config.getColMap();
            Class<?> returnType = config.getReturnType();
            Object obj = returnType.newInstance();
            for (ColumnConfig colConf : colMap.values()) {
                Object val = excelData.getValue(row, colConf.getColumn(), colConf.getExcelFormat(), colConf.getJavaType());
                DataValidator validator = colConf.getDataValidator().newInstance();
                ResultObject validateResult = validator.validate(val);
                String setterName = StringUtil.setterName(colConf.getProperty());
                Method method = returnType.getDeclaredMethod(setterName);
                method.invoke(obj, val);
                if (validateResult.getFlag()) {
                    returnList.add(obj);
                } else {
                    logger.error("Data validate failed : " + validateResult.getMessage());
                }
            }
        }

        return returnList;
    }

    protected InputStream exportExcel() {
        // TODO
        return null;
    }

    protected abstract Object getValue(int row, int col, ExcelFormat excelFormat, Class<?> javaFormat);
}
