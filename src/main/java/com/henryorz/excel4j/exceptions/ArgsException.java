package com.henryorz.excel4j.exceptions;

/**
 * Created by 周恒睿 on 2016/12/29.
 */
public class ArgsException extends Excel4jException{

    private static final long serialVersionUID = 7443366074216259059L;

    public ArgsException(){
        super();
    }

    public ArgsException(String message) {
        super(message);
    }

    public ArgsException(String message, Throwable cause) {
        super(message, cause);
    }

    public ArgsException(Throwable cause) {
        super(cause);
    }
}
