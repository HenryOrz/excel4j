package com.henryorz.excel4j.test;

import com.henryorz.excel4j.proxy.MapperProxy;
import com.henryorz.excel4j.proxy.MapperProxyFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;


public class TestMain {

    public static void main(String[] args) throws FileNotFoundException {
        MapperProxyFactory<TestInterface> factory = new MapperProxyFactory(TestInterface.class);
        TestInterface testInterface = factory.newInstance(new MapperProxy());
        InputStream in = new FileInputStream("E:/test.xlsx");
        List<TestBean> res = testInterface.testMethod(in);
        System.out.println(res);
    }
}
