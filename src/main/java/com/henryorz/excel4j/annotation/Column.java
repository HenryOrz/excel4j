package com.henryorz.excel4j.annotation;

import com.henryorz.excel4j.type.*;

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

    Class<?> javaType();

    Class<? extends DataValidator> dataValidator() default DefaultDataValidator.class;

}
