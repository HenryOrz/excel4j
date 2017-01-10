package com.henryorz.excel4j.excel;

import com.henryorz.excel4j.config.SheetConfig;
import com.henryorz.excel4j.type.ExcelFileType;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class PoiExportDataSource implements ExcelExportDataSource {

    private Workbook workbook;

    public PoiExportDataSource(SheetConfig sheetConfig){
        if(sheetConfig.getFileType() == ExcelFileType.XLS){
            this.workbook = new HSSFWorkbook();
        }else {
            this.workbook = new XSSFWorkbook();
        }
        workbook.createSheet(sheetConfig.getSheetName());
    }
}
