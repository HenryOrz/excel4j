package com.henryorz.excel4j.excel;
import com.henryorz.excel4j.config.ColumnConfig;
import com.henryorz.excel4j.config.SheetConfig;
import com.henryorz.excel4j.exceptions.ExcelException;
import com.henryorz.excel4j.type.ExcelFormat;
import com.henryorz.excel4j.util.StringUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.*;


public class PoiExcelHandler extends ExcelHandler {

    private static final Logger logger = LoggerFactory.getLogger(PoiExcelHandler.class);

    public static ExcelHandler newInstance(SheetConfig config) {
        return new PoiExcelHandler(config);
    }

    private PoiExcelHandler(SheetConfig config){
        super(config);
    }

    @Override
    protected Object importExcel(InputStream inputStream) throws Exception {
        List<Object> returnList = new ArrayList();
        Workbook wb;
        try{
            wb = new XSSFWorkbook(inputStream);
        } catch (Exception e1) {
            logger.warn("open as xlsx failed, cause: {}" + e1.getCause());
            try {
                wb = new HSSFWorkbook(inputStream);
            } catch (Exception e2) {
                logger.warn("open as xls failed, cause: {}" + e1.getCause());
                throw e2;
            }
        }
        if(wb == null){
            throw new ExcelException("open excel failed");
        }
        Sheet sheet = wb.getSheet(config.getSheetName());
        for(int i = config.getRowHead(); i < config.getRowHead()+config.getRowNum(); i++){
            Row row = sheet.getRow(i);
            try {
                Object bean = getBeanFromRow(row);
                returnList.add(bean);
            }catch (Exception e){
                logger.error("get bean from row failed: " + e.getCause());
                continue;
            }
        }
        return returnList;
    }

    protected Object getValue(int row, int col, ExcelFormat excelFormat, Class<?> javaFormat) {
        return null;
    }

    private Object getBeanFromRow(Row row) throws IllegalAccessException, InstantiationException, NoSuchMethodException {
        // TODO
        Class<?> returnType = config.getReturnType();
        Object retObj = returnType.newInstance();
        Map<Integer, ColumnConfig> configMap = config.getColMap();

        for (int i= config.getColHead(); i < config.getColHead()+config.getColNum(); i++){
            ColumnConfig cc = configMap.get(i);
            if(cc == null){
                logger.warn("None config for column " + i + 1);
                continue;
            }
            String setterName = StringUtil.setterName(cc.getProperty());
            Method method = returnType.getDeclaredMethod(setterName);
            Object arg = row.getCell(i);
            method.invoke(retObj, new Object[]{arg});
        }
        return retObj;
    }
}
