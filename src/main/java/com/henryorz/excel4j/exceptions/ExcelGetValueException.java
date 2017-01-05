package com.henryorz.excel4j.exceptions;

/**
 * Created by 周恒睿 on 2017/1/5.
 */
public class ExcelGetValueException extends ExcelException{

    private static final long serialVersionUID = -9106076867569617006L;

    public ExcelGetValueException(int row, int col){
        this("get value exception at (" + row + "," + col + "); ");
    }

    public ExcelGetValueException(int row, int col, Throwable cause){
        this("get value exception at (" + row + "," + col + "); ", cause);
    }

    public ExcelGetValueException(){
        super();
    }

    public ExcelGetValueException(String message) {
        super(message);
    }

    public ExcelGetValueException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExcelGetValueException(Throwable cause) {
        super(cause);
    }
}
