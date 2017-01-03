package com.henryorz.excel4j.config;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 周恒睿 on 2016/12/28.
 */
public class SheetConfig {
    private String sheetName;
    private Operation operation;
    private boolean hasTitle;
    private int rowHead;
    private int rowNum;
    private int colHead;
    private int colNum;
    private Class<?> returnType;
    private Map<Integer, ColumnConfig> colMap = new HashMap<Integer, ColumnConfig>();

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public boolean isHasTitle() {
        return hasTitle;
    }

    public void setHasTitle(boolean hasTitle) {
        this.hasTitle = hasTitle;
    }

    public int getRowHead() {
        return rowHead;
    }

    public void setRowHead(int rowHead) {
        this.rowHead = rowHead;
    }

    public int getRowNum() {
        return rowNum;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    public int getColHead() {
        return colHead;
    }

    public void setColHead(int colHead) {
        this.colHead = colHead;
    }

    public int getColNum() {
        return colNum;
    }

    public void setColNum(int colNum) {
        this.colNum = colNum;
    }

    public Class<?> getReturnType() {
        return returnType;
    }

    public void setReturnType(Class<?> returnType) {
        this.returnType = returnType;
    }

    public Map<Integer, ColumnConfig> getColMap() {
        return colMap;
    }

    public void setColMap(Map<Integer, ColumnConfig> colMap) {
        this.colMap = colMap;
    }

    public void putColMap(Integer column, ColumnConfig columnConfig) {
        this.colMap.put(column, columnConfig);
    }
}
