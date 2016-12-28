package com.github.henryorz.excel4j.config;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 周恒睿 on 2016/12/28.
 */
public class SheetConfig {
    private String sheetName;
    private boolean hasTitle;
    private int rowHead;
    private int rowNum;
    private Map<Integer, String> colMap = new HashMap<Integer, String>();
}
