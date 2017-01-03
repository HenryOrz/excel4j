package com.henryorz.excel4j.exceptions;

import java.util.Arrays;

/**
 * Created by 周恒睿 on 2016/12/29.
 */
public class ArgsTypeException extends ArgsException{

    private static final long serialVersionUID = 5522713896178082555L;

    public ArgsTypeException(){
        super();
    }

    public ArgsTypeException(Class<?>[] expectArgs, Class<?>[] actualArgs){
        this("ExpectArgs:"+Arrays.toString(expectArgs)+";    ActualArgs:"+Arrays.toString(actualArgs) );
    }

    public ArgsTypeException(String message) {
        super(message);
    }

    public ArgsTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ArgsTypeException(Throwable cause) {
        super(cause);
    }
}
