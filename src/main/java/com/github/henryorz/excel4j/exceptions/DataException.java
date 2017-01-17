package com.github.henryorz.excel4j.exceptions;

/**
 * Created by 周恒睿 on 2017/1/3.
 */
public class DataException extends Excel4jException{

    private static final long serialVersionUID = -720297559467938137L;

    public DataException(){
        super();
    }

    public DataException(String message) {
        super(message);
    }

    public DataException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataException(Throwable cause) {
        super(cause);
    }
}
