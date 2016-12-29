package com.github.henryorz.excel4j.exceptions;

/**
 * Created by 周恒睿 on 2016/12/29.
 */
public class ArgsException extends Exception{

    private static final long serialVersionUID = 2742234828694201536L;

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
