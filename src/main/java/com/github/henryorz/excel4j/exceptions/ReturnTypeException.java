package com.github.henryorz.excel4j.exceptions;

/**
 * Created by 周恒睿 on 2017/1/5.
 */
public class ReturnTypeException extends Excel4jException{

    private static final long serialVersionUID = -2988002591011512023L;

    public ReturnTypeException(){
        super();
    }

    public ReturnTypeException(String message) {
        super(message);
    }

    public ReturnTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReturnTypeException(Throwable cause) {
        super(cause);
    }
}
