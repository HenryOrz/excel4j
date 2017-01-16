package com.henryorz.excel4j.test;

import com.henryorz.excel4j.proxy.MapperProxy;
import com.henryorz.excel4j.proxy.MapperProxyFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class TestMain {

    public static void main(String[] args) throws Exception {
        exportTest();
//        importTest();
    }

    private static void importTest() throws FileNotFoundException {
        MapperProxyFactory<TestInterface> factory = new MapperProxyFactory(TestInterface.class);
        TestInterface testInterface = factory.newInstance(new MapperProxy());
        InputStream in = new FileInputStream("E:/excel4j_test_export.xlsx");
        List<TestBean> res = testInterface.importTest(in);
        System.out.println(res);
    }

    private static void exportTest() throws FileNotFoundException {
        MapperProxyFactory<TestInterface> factory = new MapperProxyFactory(TestInterface.class);
        TestInterface testInterface = factory.newInstance(new MapperProxy());
        List<TestBean> testList = new ArrayList<TestBean>();
        testList.add(new TestBean("testName1", "testDescp1"));
        testList.add(new TestBean("testName2", "testDescp2"));
        testList.add(new TestBean("testName3", "testDescp3"));
        testList.add(new TestBean("testName4", "testDescp4"));
        OutputStream out = new FileOutputStream("E:/excel4j_test_export.xlsx");
        testInterface.exportTest(testList, out);
    }
}
