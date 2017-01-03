package com.henryorz.excel4j.exceptions;

/**
 * Created by 周恒睿 on 2016/12/29.
 */
public class ExcelException extends Excel4jException{

    private static final long serialVersionUID = -9017095356068590860L;

    public ExcelException(){
        super();
    }

    public ExcelException(String message) {
        super(message);
    }

    public ExcelException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExcelException(Throwable cause) {
        super(cause);
    }
}
