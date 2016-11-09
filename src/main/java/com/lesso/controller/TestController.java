package com.lesso.controller;

import com.alibaba.fastjson.JSON;
import com.lesso.base.MsgBo;
import com.lesso.entity.ZtreeNode;
import com.lesso.service.ITestService;
import org.apache.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/24.
 */
@Controller
@RequestMapping("/test")
public class TestController {
    @Autowired
    @Qualifier(value="testService")
    private ITestService testService;
    public void task() throws InterruptedException {
        System.out.println("----------------------------------------Thread"+Thread.currentThread().getId()+" is start----------------------------------------");
        Thread.sleep(30000);
        System.out.println("----------------------------------------Thread"+Thread.currentThread().getId()+" is over----------------------------------------");
    }
    public void task2() throws InterruptedException {
       this.testService.task2();
    }


    @RequestMapping("/getZtreeNodesJOSNP")
    public void getZtreeNodesJOSNP(HttpServletRequest request, HttpServletResponse res) {
        List<ZtreeNode> ztreeNodes=new ArrayList<ZtreeNode>();
       for(int i=0;i<10;i++){
           ZtreeNode parentZtreeNode=new ZtreeNode();
           parentZtreeNode.setChecked(false);
           parentZtreeNode.setpId("0");
           parentZtreeNode.setId((i+1)+"");
           parentZtreeNode.setName("父亲节点"+(i+1));
           parentZtreeNode.setParent(true);
           parentZtreeNode.setOpen(false);
           ztreeNodes.add(parentZtreeNode);
           for(int j=0;j<3;j++){
               ZtreeNode childrenZtreeNode=new ZtreeNode();
               childrenZtreeNode.setChecked(false);
               childrenZtreeNode.setpId((i+1)+"");
               childrenZtreeNode.setId((i+1)*10+j+1+"");
               childrenZtreeNode.setName("子节点节点"+((i+1)*10+j+1));
               childrenZtreeNode.setParent(false);
               childrenZtreeNode.setOpen(false);
               ztreeNodes.add(childrenZtreeNode);
           }
       }
        String jsonp=request.getParameter("callback");
        String jsonpResult=JSON.toJSONString(ztreeNodes);
        res.setContentType("application/json;charset=UTF-8");
        try {
            res.getWriter().write(jsonp+"("+jsonpResult+")");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // return ztreeNodes;
    }

    @ResponseBody
    @RequestMapping("/getZtreeNodes")
    public List<ZtreeNode> getZtreeNodes(HttpServletRequest request, HttpServletResponse res) {
        List<ZtreeNode> ztreeNodes=new ArrayList<ZtreeNode>();
        for(int i=0;i<10;i++){
            ZtreeNode parentZtreeNode=new ZtreeNode();
            parentZtreeNode.setChecked(false);
            parentZtreeNode.setpId("0");
            parentZtreeNode.setId((i+1)+"");
            parentZtreeNode.setName("父亲节点"+(i+1));
            parentZtreeNode.setParent(true);
            parentZtreeNode.setOpen(false);
            ztreeNodes.add(parentZtreeNode);
            for(int j=0;j<3;j++){
                ZtreeNode childrenZtreeNode=new ZtreeNode();
                childrenZtreeNode.setChecked(false);
                childrenZtreeNode.setpId((i+1)+"");
                childrenZtreeNode.setId((i+1)*10+j+1+"");
                childrenZtreeNode.setName("子节点节点"+((i+1)*10+j+1));
                childrenZtreeNode.setParent(false);
                childrenZtreeNode.setOpen(false);
                ztreeNodes.add(childrenZtreeNode);
            }
        }
        return ztreeNodes;
    }
}
