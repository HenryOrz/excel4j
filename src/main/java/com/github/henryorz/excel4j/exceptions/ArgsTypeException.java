package com.github.henryorz.excel4j.exceptions;

import java.util.Arrays;

/**
 * Created by 周恒睿 on 2016/12/29.
 */
public class ArgsTypeException extends ArgsException{

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
