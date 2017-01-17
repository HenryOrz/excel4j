package com.github.henryorz.excel4j.excel;

import com.github.henryorz.excel4j.config.SheetConfig;
import com.github.henryorz.excel4j.exceptions.ExcelException;
import com.github.henryorz.excel4j.exceptions.ExcelGetValueException;
import com.github.henryorz.excel4j.type.ExcelFileType;
import com.github.henryorz.excel4j.type.ExcelFormat;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PoiDataSource implements ExcelDataSource {

    private static Logger logger = LoggerFactory.getLogger(PoiDataSource.class);

    private Workbook workbook;
    private Map<String, Sheet> sheetMap = new HashMap<String, Sheet>();

    public static PoiDataSource newInstance(InputStream inputStream) throws Exception {
        return new PoiDataSource(inputStream);
    }

    public static PoiDataSource newInstance(SheetConfig sheetConfig) throws Exception {
        return new PoiDataSource(sheetConfig);
    }

    private PoiDataSource(InputStream inputStream) throws Exception {
        try {
            workbook = new XSSFWorkbook(inputStream);
        } catch (Exception e1) {
            logger.warn("open as xlsx failed, cause: {}", e1.getCause());
            try {
                workbook = new HSSFWorkbook(inputStream);
            } catch (Exception e2) {
                logger.warn("open as xls failed, cause: {}", e1.getCause());
                throw e2;
            }
        }
        for (Sheet sheet : workbook) {
            sheetMap.put(sheet.getSheetName(), sheet);
        }
    }

    private PoiDataSource(SheetConfig sheetConfig) {
        if (sheetConfig.getFileType() == ExcelFileType.XLS) {
            this.workbook = new HSSFWorkbook();
        } else {
            this.workbook = new XSSFWorkbook();
        }
        Sheet sheet = workbook.createSheet(sheetConfig.getSheetName());
        sheetMap.put(sheetConfig.getSheetName(), sheet);
    }

    public Object get(String sheetName, int row, int column, ExcelFormat excelFormat, Class<?> javaType) throws ExcelException {
        Sheet sheet = sheetMap.get(sheetName);
        if (sheet == null) {
            throw new ExcelException("No such sheet: " + sheetName);
        }
        Row rowData = sheet.getRow(row);
        if (rowData == null) {
            throw new ExcelException("No such row: " + row);
        }
        Cell cell = rowData.getCell(column);
        if (cell == null) {
            throw new ExcelException("No such cell: (" + row + ", " + column + ")");
        }
        if (javaType == String.class) {
            return getStringValue(cell, excelFormat);
        }
        if (javaType == Date.class) {
            return getDateValue(cell, excelFormat);
        }
        if (javaType == Character.TYPE) {
            String res = getStringValue(cell, excelFormat);
            if (res.length() == 1) {
                return res.charAt(0);
            } else {
                throw new ExcelGetValueException("value= " + res + " is not char");
            }
        }
        if (javaType == Boolean.TYPE) {
            return getBooleanValue(cell, excelFormat);
        }
        if (javaType == Integer.TYPE) {
            return getNumericValue(cell, excelFormat).intValue();
        }
        if (javaType == Short.TYPE) {
            return getNumericValue(cell, excelFormat).shortValue();
        }
        if (javaType == Long.TYPE) {
            return getNumericValue(cell, excelFormat).longValue();
        }
        if (javaType == Double.TYPE) {
            return getNumericValue(cell, excelFormat);
        }
        if (javaType == Float.TYPE) {
            return getNumericValue(cell, excelFormat).floatValue();
        }
        throw new ExcelGetValueException("UnsupportedJavaType: " + javaType.getName());
    }

    public void put(String sheetName, int row, int column, Object val) {
        Sheet sheet = sheetMap.get(sheetName) == null ? workbook.createSheet(sheetName) : sheetMap.get(sheetName);
        Row rowData = sheet.getRow(row) == null ? sheet.createRow(row) : sheet.getRow(row);
        Cell cell = rowData.getCell(column) == null ? rowData.createCell(column) : rowData.getCell(column);
        if (val != null) {
            if (val instanceof Date) {
                cell.setCellValue((Date) val);
            } else if (val instanceof Number) {
                cell.setCellValue(((Number) val).doubleValue());
            } else {
                cell.setCellValue(String.valueOf(val));
            }
        } else {
            logger.warn("val put in sheet is null");
        }
    }

    public void write(OutputStream outputStream) throws IOException {
        workbook.write(outputStream);
    }

    private String getStringValue(Cell cell, ExcelFormat excelFormat) throws ExcelGetValueException {
        if (excelFormat != ExcelFormat.STRING && excelFormat != ExcelFormat.DEFAULT) {
            logger.warn("ExcelFormat not match: ({}, {})", cell.getRowIndex(), cell.getColumnIndex());
        }
        try {
            return cell.getStringCellValue();
        } catch (Exception e) {
            throw new ExcelGetValueException(cell.getRowIndex(), cell.getColumnIndex(), e);
        }
    }

    private Double getNumericValue(Cell cell, ExcelFormat excelFormat) throws ExcelGetValueException {
        if (excelFormat != ExcelFormat.NUMERIC && excelFormat != ExcelFormat.DEFAULT) {
            logger.warn("ExcelFormat not match: " + cell.getRowIndex() + cell.getColumnIndex());
        }
        try {
            return cell.getNumericCellValue();
        } catch (Exception e) {
            throw new ExcelGetValueException(cell.getRowIndex(), cell.getColumnIndex(), e);
        }
    }

    private Date getDateValue(Cell cell, ExcelFormat excelFormat) throws ExcelGetValueException {
        if (excelFormat != ExcelFormat.DATE && excelFormat != ExcelFormat.DEFAULT) {
            logger.warn("ExcelFormat not match: " + cell.getRowIndex() + cell.getColumnIndex());
        }
        try {
            return cell.getDateCellValue();
        } catch (Exception e) {
            throw new ExcelGetValueException(cell.getRowIndex(), cell.getColumnIndex(), e);
        }
    }

    private Boolean getBooleanValue(Cell cell, ExcelFormat excelFormat) throws ExcelGetValueException {
        if (!(excelFormat.equals(ExcelFormat.BOOLEAN) && excelFormat.equals(ExcelFormat.DEFAULT))) {
            logger.warn("ExcelFormat not match: " + cell.getRowIndex() + cell.getColumnIndex());
        }
        try {
            return cell.getBooleanCellValue();
        } catch (Exception e) {
            throw new ExcelGetValueException(cell.getRowIndex(), cell.getColumnIndex(), e);
        }
    }
}
