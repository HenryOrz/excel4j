package com.github.henryorz.excel4j.exceptions;

/**
 * Created by 周恒睿 on 2016/12/29.
 */
public class ArgsNumberException extends ArgsException {

    private static final long serialVersionUID = 5472058875789068525L;

    public ArgsNumberException(){
        super();
    }

    public ArgsNumberException(int expectedNum, int actualNum){
        this("expectedNum="+expectedNum+",    args_num="+actualNum);
    }

    public ArgsNumberException(String message) {
        super(message);
    }

    public ArgsNumberException(String message, Throwable cause) {
        super(message, cause);
    }

    public ArgsNumberException(Throwable cause) {
        super(cause);
    }
}
