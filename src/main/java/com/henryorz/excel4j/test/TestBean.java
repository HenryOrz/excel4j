package com.henryorz.excel4j.test;

/**
 * Created by 周恒睿 on 2016/12/29.
 */
public class TestBean {
    private String name;
    private String descp;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescp() {
        return descp;
    }

    public void setDescp(String descp) {
        this.descp = descp;
    }

    @Override
    public String toString() {
        return "TestBean{" +
                "name='" + name + '\'' +
                ", descp='" + descp + '\'' +
                '}';
    }
}
