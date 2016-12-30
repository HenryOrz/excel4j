package com.github.henryorz.excel4j.proxy;

import com.github.henryorz.excel4j.annotation.processor.MethodProcessor;
import com.github.henryorz.excel4j.annotation.processor.DefaultMethodProcessor;
import com.github.henryorz.excel4j.config.SheetConfig;
import com.github.henryorz.excel4j.excel.ExcelHandler;
import com.github.henryorz.excel4j.excel.PoiExcelHandler;

import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 周恒睿 on 2016/12/29.
 */
public class MapperProxy implements InvocationHandler {

    private Map<Method, ExcelHandler> handlerMap = new HashMap<Method, ExcelHandler>();

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (Object.class.equals(method.getDeclaringClass())) {
            return method.invoke(this, args);
        }
        MethodProcessor annProcessor = new DefaultMethodProcessor(method);
        SheetConfig config = annProcessor.getSheetConfig();
        ExcelHandler handler = handlerMap.get(method);
        if(handler == null){
            handler = getDefaultExcelHandler(config);
        }
        return handler.execute(args);
    }

    protected ExcelHandler getDefaultExcelHandler(SheetConfig config){
        ExcelHandler excelHandler = PoiExcelHandler.newInstance(config);
        return excelHandler;
    }
}
