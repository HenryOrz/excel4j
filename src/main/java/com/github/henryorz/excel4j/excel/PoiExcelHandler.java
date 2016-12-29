package com.github.henryorz.excel4j.excel;
import com.github.henryorz.excel4j.config.SheetConfig;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by 周恒睿 on 2016/12/29.
 */
public class PoiExcelHandler extends ExcelHandler {

    private static final Logger logger = LoggerFactory.getLogger(PoiExcelHandler.class);

    public static ExcelHandler newInstance(SheetConfig config, Class<?> returnType) {
        return new PoiExcelHandler(config, returnType);
    }

    private PoiExcelHandler(SheetConfig config, Class<?> returnType){
        super(config, returnType);
    }

    @Override
    protected Object importExcel(InputStream inputStream) throws IOException {
        List<Object> returnList = new ArrayList();
        Workbook wb = new XSSFWorkbook(inputStream);
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

    @Override
    protected InputStream exportExcel(){
        // TODO
        return null;
    }

    private Object getBeanFromRow(Row row){
        // TODO
        return row.toString();
    }
}
