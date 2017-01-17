package com.github.henryorz.excel4j.exceptions;

/**
 * Created by 周恒睿 on 2017/1/3.
 */
public class DataValidateException extends DataException{

    private static final long serialVersionUID = -457974693623842935L;

    public DataValidateException(){
        super();
    }

    public DataValidateException(String message) {
        super(message);
    }

    public DataValidateException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataValidateException(Throwable cause) {
        super(cause);
    }
}
