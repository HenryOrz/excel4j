package com.henryorz.excel4j.test;

import com.henryorz.excel4j.proxy.MapperProxy;
import com.henryorz.excel4j.proxy.MapperProxyFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by 周恒睿 on 2016/12/29.
 */
public class TestMain {

    public static void main(String[] args) throws FileNotFoundException {
        MapperProxyFactory<TestInterface> factory = new MapperProxyFactory(TestInterface.class);
        TestInterface testInterface = factory.newInstance(new MapperProxy());
        InputStream in = new FileInputStream("E:/test.xls");
        Object res = testInterface.testMethod(in);
        System.out.println(res);
    }
}
