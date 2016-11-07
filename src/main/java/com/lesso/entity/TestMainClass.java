package com.lesso.entity;

import org.omg.Dynamic.Parameter;

/**
 * Created by czx on 2016/8/24.
 */
public class TestMainClass
{
    private TestSubClass testSubClass;
    private int number;
    private TestMainClass testMainClass;

    public TestSubClass getTestSubClass() {
        return testSubClass;
    }

    public void setTestSubClass(TestSubClass testSubClass) {
        this.testSubClass = testSubClass;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
    public Object clone() throws CloneNotSupportedException {
        super.clone();
        //this.testMainClass.clone();//这个访问没问题
        //this.testSubClass.clone();//这个访问受限
        return null;
    }
}
