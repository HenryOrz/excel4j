package com.henryorz.excel4j.excel;

import com.henryorz.excel4j.exceptions.ExcelException;
import com.henryorz.excel4j.type.ExcelFormat;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;

/**
 * Created by 周恒睿 on 2017/1/4.
 */
public class PoiExcelDataSource implements ExcelDataSource{

    private static Logger logger = LoggerFactory.getLogger(PoiExcelDataSource.class)

    private InputStream inputStream;
    private Workbook workbook;
    private Sheet sheet;

    public PoiExcelDataSource(InputStream inputStream, String sheetName) throws Exception {
        this.inputStream = inputStream;
        try{
            workbook = new XSSFWorkbook(inputStream);
        } catch (Exception e1) {
            logger.warn("open as xlsx failed, cause: {}" + e1.getCause());
            try {
                workbook = new HSSFWorkbook(inputStream);
            } catch (Exception e2) {
                logger.warn("open as xls failed, cause: {}" + e1.getCause());
                throw e2;
            }
        }
        if(workbook == null){
            throw new ExcelException("open excel failed");
        }
        this.sheet = workbook.getSheet(sheetName);
    }

    public Object getValue(int row, int column, ExcelFormat excelFormat, Class<?> javaType) throws Exception{
        Cell cell = sheet.getRow(row).getCell(column);
        if(javaType == String.class){
            // TODO
            return null;
        }
        if(javaType == String.class){
            // TODO
            return null;
        }
        if(javaType == String.class){
            // TODO
            return null;
        }
        if(javaType == String.class){
            // TODO
            return null;
        }
        if(javaType == String.class){
            // TODO
            return null;
        }
        if(javaType == String.class){
            // TODO
            return null;
        }
        if(javaType == String.class){
            // TODO
            return null;
        }
    }
}
