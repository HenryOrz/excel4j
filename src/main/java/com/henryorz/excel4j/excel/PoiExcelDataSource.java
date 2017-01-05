package com.henryorz.excel4j.excel;

import com.henryorz.excel4j.exceptions.ExcelException;
import com.henryorz.excel4j.exceptions.ExcelGetValueException;
import com.henryorz.excel4j.type.ExcelFormat;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class PoiExcelDataSource implements ExcelDataSource{

    private static Logger logger = LoggerFactory.getLogger(PoiExcelDataSource.class);

    private InputStream inputStream;
    private Workbook workbook;
//    private Sheet sheet;
    private Map<String, Sheet> sheetMap = new HashMap<String, Sheet>();

    public PoiExcelDataSource(InputStream inputStream) throws Exception {
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
        for(Sheet sheet: workbook){
            sheetMap.put(sheet.getSheetName(), sheet);
        }
    }

    public Object getValue(String sheetName, int row, int column, ExcelFormat excelFormat, Class<?> javaType) throws ExcelException {
        Sheet sheet = sheetMap.get(sheetName);
        if(sheet == null){
            throw new ExcelException("No such sheet: " + sheetName);
        }
        Cell cell = sheet.getRow(row).getCell(column);
        if(javaType == String.class){
            String res = getStringValue(cell, excelFormat);
            return res;
        }
        if(javaType == Date.class){
            Date res = getDateValue(cell, excelFormat);
            return res;
        }
        if(javaType == Character.TYPE){
            String res = getStringValue(cell, excelFormat);
            if(res.length()==1){
                return res.charAt(0);
            }else {
                throw new ExcelGetValueException("value= " + res + " is not char");
            }
        }
        if(javaType == Boolean.TYPE){
            Boolean res = getBooleanValue(cell, excelFormat);
            return res;
        }
        if(javaType == Integer.TYPE){
            Integer res = getNumericValue(cell, excelFormat).intValue();
            return res;
        }
        if(javaType == Short.TYPE){
            Short res = getNumericValue(cell, excelFormat).shortValue();
            return res;
        }
        if(javaType == Long.TYPE){
            Long res = getNumericValue(cell, excelFormat).longValue();
            return res;
        }
        if(javaType == Double.TYPE){
            Double res = getNumericValue(cell, excelFormat).doubleValue();
            return res;
        }
        if(javaType == Float.TYPE) {
            Float res = getNumericValue(cell, excelFormat).floatValue();
            return res;
        }
        throw new ExcelGetValueException("UnsupportedJavaType: " + javaType.getName());
    }

    private String getStringValue(Cell cell, ExcelFormat excelFormat) throws ExcelGetValueException{
        if(!(excelFormat.equals(ExcelFormat.STRING)&&excelFormat.equals(ExcelFormat.DEFAULT))) {
            logger.warn("ExcelFormat not match: " + cell.getRowIndex() + cell.getColumnIndex());
        }
        try{
            String cellValue = cell.getStringCellValue();
            return cellValue;
        }catch (Exception e){
            throw new ExcelGetValueException(cell.getRowIndex(),cell.getColumnIndex(),e);
        }
    }

    private Double getNumericValue(Cell cell, ExcelFormat excelFormat) throws ExcelGetValueException{
        if(!(excelFormat.equals(ExcelFormat.NUMERIC)&&excelFormat.equals(ExcelFormat.DEFAULT))) {
            logger.warn("ExcelFormat not match: " + cell.getRowIndex() + cell.getColumnIndex());
        }
        try{
            Double cellValue = cell.getNumericCellValue();
            return cellValue;
        }catch (Exception e){
            throw new ExcelGetValueException(cell.getRowIndex(),cell.getColumnIndex(),e);
        }
    }

    private Date getDateValue(Cell cell, ExcelFormat excelFormat) throws ExcelGetValueException{
        if(!(excelFormat.equals(ExcelFormat.DATE)&&excelFormat.equals(ExcelFormat.DEFAULT))) {
            logger.warn("ExcelFormat not match: " + cell.getRowIndex() + cell.getColumnIndex());
        }
        try{
            Date cellValue = cell.getDateCellValue();
            return cellValue;
        }catch (Exception e){
            throw new ExcelGetValueException(cell.getRowIndex(),cell.getColumnIndex(),e);
        }
    }

    private Boolean getBooleanValue(Cell cell, ExcelFormat excelFormat) throws ExcelGetValueException{
        if(!(excelFormat.equals(ExcelFormat.DATE)&&excelFormat.equals(ExcelFormat.DEFAULT))) {
            logger.warn("ExcelFormat not match: " + cell.getRowIndex() + cell.getColumnIndex());
        }
        try{
            Boolean cellValue = cell.getBooleanCellValue();
            return cellValue;
        }catch (Exception e){
            throw new ExcelGetValueException(cell.getRowIndex(),cell.getColumnIndex(),e);
        }
    }
}
