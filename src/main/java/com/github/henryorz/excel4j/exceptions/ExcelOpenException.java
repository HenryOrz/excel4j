package com.github.henryorz.excel4j.exceptions;

/**
 * Created by 周恒睿 on 2016/12/29.
 */
public class ExcelOpenException extends ExcelException {

    private static final long serialVersionUID = 4808433113983516153L;

    public ExcelOpenException(){
        super();
    }

    public ExcelOpenException(String message) {
        super(message);
    }

    public ExcelOpenException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExcelOpenException(Throwable cause) {
        super(cause);
    }
}
