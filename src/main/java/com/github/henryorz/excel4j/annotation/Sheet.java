package com.github.henryorz.excel4j.annotation;

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

    boolean hasTitle();

    String sheetName();

    int rowHead() default 0;

    int rowNum();
}
