package com.lesso.base;

/**
 * Created by Administrator on 2016/5/10.
 */
public class MsgBo {
    private String msg;//消息
    private boolean isSucc;//是否成功
    private Object obj;//返回数据
    public MsgBo(boolean isSucc,String msg,Object obj){
        this.isSucc=isSucc;
        this.msg=msg;
        this.obj=obj;
    }
    public MsgBo(){}
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean getIsSucc() {
        return isSucc;
    }

    public void setIsSucc(boolean succ) {
        isSucc = succ;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }
}
