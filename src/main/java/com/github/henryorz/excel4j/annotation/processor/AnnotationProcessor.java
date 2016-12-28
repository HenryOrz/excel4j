package com.github.henryorz.excel4j.annotation.processor;

import com.github.henryorz.excel4j.config.ColumnConfig;
import com.github.henryorz.excel4j.config.SheetConfig;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by 周恒睿 on 2016/12/28.
 */
public interface AnnotationProcessor {
    SheetConfig getSheetConfig (Method method);
}
