package com.github.henryorz.excel4j.excel;

import com.github.henryorz.excel4j.config.SheetConfig;
import com.github.henryorz.excel4j.exceptions.ArgsException;
import com.github.henryorz.excel4j.exceptions.ArgsNumberException;
import com.github.henryorz.excel4j.exceptions.ArgsTypeException;
import com.github.henryorz.excel4j.exceptions.ExcelException;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by 周恒睿 on 2016/12/29.
 */
public abstract class ExcelHandler {

    protected SheetConfig config;

    protected Class<?> returnType;

    public ExcelHandler(SheetConfig config, Class<?> returnType){
        this.config = config;
        this.returnType = returnType;
    }

    public SheetConfig getConfig() {
        return config;
    }

    public void setConfig(SheetConfig config) {
        this.config = config;
    }

    public Class<?> getReturnType() {
        return returnType;
    }

    public void setReturnType(Class<?> returnType) {
        this.returnType = returnType;
    }

    public Object execute(Object[] args) throws Exception{
        switch (config.getOperation()){
            case IMPORT:
                if(args == null){
                    throw new ArgsException("args is null");
                }
                if(args.length != 1){
                    throw new ArgsNumberException(1, args.length);
                }
                if(!(args[0] instanceof InputStream)){
                    throw new ArgsTypeException(new Class[]{InputStream.class}, new Class[]{args[0].getClass()});
                }
                try{
                    Object result = importExcel((InputStream)args[0]);
                    return result;
                }catch (Exception e){
                    throw new ExcelException(e);
                }
            case EXPORT:
                return exportExcel();
        }
        return null;
    }

    protected abstract Object importExcel(InputStream inputStream) throws IOException;

    protected abstract InputStream exportExcel();
}
