package com.henryorz.excel4j.annotation;

import com.henryorz.excel4j.config.Operation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by 周恒睿 on 2016/12/26.
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Sheet {

    String sheetName();

    Operation operation();

    boolean hasTitle();

    int rowHead() default 0;

    int rowNum();

    int colHead() default 0;

    int colNum();
}
