package com.henryorz.excel4j.util;

public class TextUtil {
    public static String getterName(String propertyName){
        return  "get" + propertyName.substring(0,1).toUpperCase() + propertyName.substring(1);
    }
    public static String setterName(String propertyName){
        return  "set" + propertyName.substring(0,1).toUpperCase() + propertyName.substring(1);
    }
}
