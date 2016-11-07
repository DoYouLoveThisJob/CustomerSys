package com.lesso.entity;

/**
 * Created by 0003 on 2016/8/11.
 */
public class TestClass {
    private int testFlied;
    protected double testFlied2;
    public  long testFlied3;
    String testFlied4;

    public void function(){
        System.out.println("function");
    }
    private void function2(){
        System.out.println("function2");
    }
    protected void function3(){
        System.out.println("function3");
    }
    void function4(){
        System.out.println("function4");
    }

    public int function5(){
        System.out.println("function");
        return this.testFlied;
    }

    public int getTestFlied() {
        return testFlied;
    }

    public void setTestFlied(int testFlied) {
        this.testFlied = testFlied;
    }

    public double getTestFlied2() {
        return testFlied2;
    }

    public void setTestFlied2(double testFlied2) {
        this.testFlied2 = testFlied2;
    }

    public long getTestFlied3() {
        return testFlied3;
    }

    public void setTestFlied3(long testFlied3) {
        this.testFlied3 = testFlied3;
    }

    public String getTestFlied4() {
        return testFlied4;
    }

    public void setTestFlied4(String testFlied4) {
        this.testFlied4 = testFlied4;
    }
}
