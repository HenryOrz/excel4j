package com.github.henryorz.excel4j.exceptions;

/**
 * Created by 周恒睿 on 2016/12/29.
 */
public class ExcelOpenExcption extends ExcelException {

    private static final long serialVersionUID = -253113672850268867L;

    public ExcelOpenExcption(){
        super();
    }

    public ExcelOpenExcption(String message) {
        super(message);
    }

    public ExcelOpenExcption(String message, Throwable cause) {
        super(message, cause);
    }

    public ExcelOpenExcption(Throwable cause) {
        super(cause);
    }
}
