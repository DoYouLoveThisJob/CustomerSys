package com.lesso.util;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.util.*;
import org.bson.Document;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/5/11.
 */
public class BehaviorDataUtil {
    /**
     * 获取行为类型
     * **/
    public static List<String>  getBehaviorType(Document doc) {
        String  code="";
        String content="";
        List<String> regResult=null;
        List<String> result=new ArrayList<String>();
        if(doc==null){
            return result;
        }
        //获取body成员
        Document body =(Document)DocumentUtil.getValue("meta.body",doc);
        if(body==null){
            return result;
        }
        //获取query成员
        String query=(String)DocumentUtil.getValue("query",body);
        if(query==null){
            return result;
        }
        if (query.indexOf("mutation SignUpMutation")!=-1) {
            code = "U_S_1096";
            content = JSON.toJSONString(DocumentUtil.getValue("variables.input_0",body));
        }else if (query.indexOf("mutation SignInMutation")!=-1) {
            code = "U_S_1097";
        }else if (query.indexOf("mutation AddAddressMutation")!=-1) {
            code = "S_O_1019";
            content = JSON.toJSONString(DocumentUtil.getValue("variables.input_0",body));
        }else if(query.indexOf("mutation UpdateAddressMutation")!=-1) {
            code = "S_O_1020";
            content = JSON.toJSONString(DocumentUtil.getValue("variables.input_0",body));
        }else if(query.indexOf("mutation DeleteAddressMutation")!=-1) {
            code = "S_O_1022";
            content = JSON.toJSONString(DocumentUtil.getValue("variables.input_0",body));
        }
//        else if (query.indexOf("mutation ChangePasswordMutation")!=-1) {
//            code = "S_U_1036";
//            content = JSON.toJSONString(DocumentUtil.getValue("variables.input_0",body));
//        }
//        else if (query.indexOf("query CategoryQueries")!=-1) {
//            code = "S_P_2006";
//            String regStr="activeCategory\\(categoryCode\\:\\\"(.*)\\\"\\)";
//            regResult=getValueByRegExp(query,regStr);
//            if (regResult!=null &&regResult.size()>0) {
//                content = "{ \"categoryCode\": \""+regResult.get(0)+"\"}";
//            }
//        }
        else if (query.indexOf("query SearchQueries")!=-1) {
            code="S_P_1035";
            String regStr="results\\(text\\:\\\"(.*)\\\",first\\:\\d+\\)";
            regResult=getValueByRegExp(query,regStr);
            if (regResult!=null &&regResult.size()>0) {
                content = "{ \"text\": \""+regResult.get(0)+"\"}";
            } else {
                regStr="results\\(categoryCode\\:\\\"(.*)\\\",first\\:\\d+\\)";
                regResult=getValueByRegExp(query,regStr);
                if (regResult!=null &&regResult.size()>0) {
                    content = "{ \"categoryCode\": \"" + regResult.get(0) + "\"}";
                }
            }
        }else if (query.indexOf("query ProductQueries{product")!=-1) {
            code = "U_P_1110";
            String regStr="product\\(productCode\\:\\\"(.*)\\\"\\)";
            regResult=getValueByRegExp(query,regStr);
            if (regResult!=null &&regResult.size()>0) {
                content = "{ \"productCode\": \""+regResult.get(0)+"\"}";
            }
        }else if (query.indexOf("mutation AddToCartMutation")!=-1) {
            code = "S_O_2009";
            content = JSON.toJSONString(DocumentUtil.getValue("variables.input_0",body));
        }else if (query.indexOf("mutation SubmitOrderMutation")!=-1) {
            code="S_O_1025";
            String addressId=((String) DocumentUtil.getValue("variables.input_0.cartsInput.addressId",body));
            if(addressId!=null){
                addressId=Base64Util.getFromBASE64(addressId).trim().replace("Address:", "");
            }
            List<Document> carts=(List<Document>)DocumentUtil.getValue("variables.input_0.cartsInput.carts",body);
            if(carts!=null){
                for(Document  cart: carts){
                    cart.put("id",Base64Util.getFromBASE64(String.valueOf(cart.get("id"))).replace("Cart:", ""));
                    List<Document> entries=(List<Document>)cart.get("entries");
                    for(int i=0;i<entries.size();i++){
                        entries.get(i).put("id",Base64Util.getFromBASE64(String.valueOf(entries.get(i).get("id"))).replace("CartEntry:", ""));
                    }
                }
            }
            Map<String,Object> map=new HashMap<String,Object>();
            map.put("addressId",addressId) ;
            map.put("carts",carts) ;
            content = JSON.toJSONString(map);
        }else if (query.indexOf("mutation AddConvenientOrderMutation")!=-1) {
            code = "S_O_2012";
            content = JSON.toJSONString(DocumentUtil.getValue("variables.input_0",body));
        }else if (query.indexOf("mutation ConfirmOrderDeliveredMutation")!=-1) {
            code = "S_O_1030";
            content = JSON.toJSONString(DocumentUtil.getValue("variables.input_0",body));
        }else if (query.indexOf("mutation CancelOrderMutation")!=-1) {
            code = "S_O_1026";
            content = JSON.toJSONString(DocumentUtil.getValue("variables.input_0",body));
        }else if (query.indexOf("query OrderQueries")!=-1) {
            code = "S_O_2015";
            String regStr="order\\(orderCode\\:\\\"(.*)\\\"\\)";
             regResult=getValueByRegExp(query,regStr);
            if (regResult!=null &&regResult.size()>0) {
                content = "{ \"orderCode\": \""+regResult.get(0)+"\"}";
            }
        }else if (query.indexOf("mutation AddFavoriteMutation")!=-1) {
            code = "S_O_1017";
            String id=(String)DocumentUtil.getValue("variables.input_0.productCode",body);
            if(id==null){
                id= (String)DocumentUtil.getValue("variables.input_0.id",body);
                id= Base64Util.getBASE64(id).trim();
            }
            if(id!=null){
                id=id.replace("Product:", "");
            }
            content = "{ \"productCode\": \""+id+"\"}";
        }else if (query.indexOf("mutation DeleteFavoriteMutation")!=-1) {
            code = "S_O_1018";
            String id=((String)DocumentUtil.getValue("variables.input_0.id",body));
            id= Base64Util.getBASE64(id).trim().replace("Product:", "");
            content = "{ \"productCode\": \""+id+"\"}";
        }else if (query.indexOf("mutation CheckoutOrderMutation")!=-1) {
            code="S_O_1029";
            Document input_0=(Document)DocumentUtil.getValue("variables.input_0",body);
            input_0.put("orderId",Base64Util.getBASE64((String)input_0.get("orderId")).trim().replace("Order:", ""));
            Map<String,Object> map=new HashMap<String,Object>();
            map.put("bankAccountId",(String.valueOf(input_0.get("bankAccountId"))).trim().replace("BankAccount:", ""));
            map.put("orderId",((String)input_0.get("orderId")).trim().replace("BankAccount:", ""));
            map.put("images",input_0.get("images"));
            content = JSON.toJSONString(map);
        }else if (query.indexOf("mutation UpdateCheckStatusOfCartMutation")!=-1) {
            code = "S_O_2019";
            String cartId=(String)DocumentUtil.getValue("variables.input_0.id",body);
            Boolean isChecked=(Boolean)DocumentUtil.getValue("variables.input_0.isChecked",body);
            Map<String,Object> map=new HashMap<String,Object>();
            map.put("cartId", Base64Util.getBASE64(cartId).trim().replace("Cart:", ""));
            map.put("isChecked",isChecked);
            content = JSON.toJSONString(map);
        }else if (query.indexOf("mutation UpdateCheckStatusOfCartEntryMutation")!=-1) {
            code = "S_O_2020";
            String cartId=(String)DocumentUtil.getValue("variables.input_0.cartId",body);
            Boolean isChecked=(Boolean)DocumentUtil.getValue("variables.input_0.isChecked",body);
            String cartEntryId=(String)DocumentUtil.getValue("variables.input_0.cartEntryId",body);
            Map<String,Object> map=new HashMap<String,Object>();
            map.put("cartId",Base64Util.getFromBASE64(cartId).trim().replace("Cart:", ""));
            map.put("isChecked",isChecked);
            map.put("cartEntryId",Base64Util.getFromBASE64(cartEntryId).trim().replace("CartEntry:", ""));
            content = JSON.toJSONString(map);
        }else if (query.indexOf("mutation DeleteCartEntryMutation")!=-1) {
            code = "S_O_2021";
            String cartEntryId=(String)DocumentUtil.getValue("variables.input_0.id",body);
            content = "{ \"cartEntryId\": \""+Base64Util.getFromBASE64(cartEntryId).trim().replace("CartEntry:", "")+"\"}";
        }else if (query.indexOf("mutation DeleteCartMutation")!=-1) {
            code = "S_O_2022";
            String cartId=(String)DocumentUtil.getValue("variables.input_0.id",body);
            content = "{ \"cartId\": \""+Base64Util.getFromBASE64(cartId).trim().replace("Cart:", "")+"\"}";
        }else if (query.indexOf("mutation UpdateCartEntryQuantityMutation")!=-1) {
            code = "S_O_2023";
            Integer quantity=(Integer)DocumentUtil.getValue("variables.input_0.quantity",body);
            String productCode=(String)DocumentUtil.getValue("variables.input_0.productCode",body);
            String cartId=(String)DocumentUtil.getValue("variables.input_0.cartId",body);
            String cartEntryId=(String)DocumentUtil.getValue("variables.input_0.id",body);
            Map<String,Object> map=new HashMap<String,Object>();
            map.put("quantity",quantity);
            map.put("productCode",productCode);
            map.put("cartId",Base64Util.getFromBASE64(cartId).trim().trim().replace("Cart:", ""));
            map.put("cartEntryId",Base64Util.getFromBASE64(cartEntryId).trim().trim().replace("CartEntry:", ""));
            content = JSON.toJSONString(map);
        }else if(query.indexOf("muation ApplyVIPMutation")!=-1){
            code = "S_U_1118";
            content = JSON.toJSONString(DocumentUtil.getValue("variables",body));
        }else{
            String referer =(String)DocumentUtil.getValue("meta.headers.referer",doc);
            if(referer.indexOf("http://weixin.lessomall.com/distributor/")!=-1 ||referer.indexOf("http://wxtest.lessomall.com/distributor/")!=-1){
                code = "S_U_1119";
                String regStr="distributor/(\\w+)";
                List<Integer>  groupNumber = Arrays.asList(new Integer[]{1});
                List<String> vesions=RegExpUtil.getValueByRegExp(referer,regStr,groupNumber);
                if(vesions==null || vesions.size()==0){
                    content="";
                }else{
                    content = "{ \"distributorId\": \""+vesions.get(0)+"\"}";
                }
            }


        }
        result.add(code);
        result.add(content);
        return result;
    }

    public static String  getBrowserCode(String name) {
        String  code = "312";
        if("Chrome".equals(name)){
                code = "301";
        }else if( "Safari".equals(name)){
            code = "310";
        }else if(  "MQQBrowser".equals(name)){
            code =  "313 ";
        }else if ("MicroMessenger ".equals(name)){
            code =  "314";
        }
        return code;
    }
    public static String  getOSCode(String name) {
        String  code = "447";
        if("Mac OS".equals(name)){
            code = "445";
        } else if( "iOS".equals(name)){
            code = "411";
        }else if( "Android".equals(name)){
            code =  "412 ";
        }
        return code;
    }
    public static List<String> getValueByRegExp(String str,String regExpregExp){
        List<String> matcherArray=new ArrayList<String>();
        if(str==null || str.equals("") ||str.trim().equals("")){
            return matcherArray;
        }
        if(regExpregExp==null || regExpregExp.equals("") ||regExpregExp.trim().equals("")){
            return matcherArray;
        }
        Pattern pattern = Pattern.compile(regExpregExp);
        Matcher matcher = pattern.matcher(str);
        while(matcher.find()) {
            matcherArray.add(matcher.group(1));
        }
        return matcherArray;
    }
    public static String getIpAddress(String xForwardFor){
        String ipAddress="";
        if(xForwardFor==null || xForwardFor.equals("") ||xForwardFor.trim().equals("")){
            return "";
        }
        String regStr="((\\d+)\\.){3}\\d+(?=\\,)";
        Pattern pattern = Pattern.compile(regStr);
        Matcher matcher = pattern.matcher(xForwardFor);
        while(matcher.find()) {
            ipAddress=matcher.group(0);
        }
        return ipAddress;
    }




    public static void  main(String[] args){

        String regStr="iPhone OS (\\d+)_(\\d+)";
        StringBuffer buffer=new StringBuffer();
        Pattern pattern = Pattern.compile(regStr);
        Matcher matcher = pattern.matcher("Mozilla/5.0 (iPhone; CPU iPhone OS 9_3 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Mobile/13E238 MicroMessenger/6.3.15 NetType/WIFI Language/zh_CN");

        while(matcher.find()) {
            System.out.println(matcher.group(0));
            System.out.println(matcher.group(1));
            System.out.println(matcher.group(2));

        }


        String content = JSON.toJSONString(null);
        System.out.println(content);
    }
}
