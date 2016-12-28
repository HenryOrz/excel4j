package com.github.henryorz.excel4j.annotation;

import com.github.henryorz.excel4j.format.DefaultTypeHandler;
import com.github.henryorz.excel4j.format.ExcelFormat;
import com.github.henryorz.excel4j.format.TypeHandler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by 周恒睿 on 2016/12/26.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {

    int column();

    String property();

    ExcelFormat excelFormat();

    Class<?> javaType() default void.class;

    Class<? extends TypeHandler<?>> typeHandler() default DefaultTypeHandler.class;

}
