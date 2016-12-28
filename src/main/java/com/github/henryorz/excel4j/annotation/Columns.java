package com.github.henryorz.excel4j.annotation;

/**
 * Created by 周恒睿 on 2016/12/28.
 */
public @interface Columns {
    Column[] value() default {};
}