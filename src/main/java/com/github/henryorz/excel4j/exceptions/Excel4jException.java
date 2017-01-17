package com.github.henryorz.excel4j.exceptions;

/**
 * Created by 周恒睿 on 2017/1/3.
 */
public class Excel4jException extends Exception{

    private static final long serialVersionUID = -1746682291401283637L;

    public Excel4jException(){
        super();
    }

    public Excel4jException(String message) {
        super(message);
    }

    public Excel4jException(String message, Throwable cause) {
        super(message, cause);
    }

    public Excel4jException(Throwable cause) {
        super(cause);
    }
}
