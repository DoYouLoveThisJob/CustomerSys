package com.lesso.util;



/**
 * Created by czx on 2016/10/25.
 */
public class ReadXml{

//    public static Map<String, String> getXmlElmentValue(String xml) {
//        System.out.print(xml);
//        Map<String, String> map = new HashMap<String, String>();
//        try {
//            Document doc = DocumentHelper.parseText(xml);
//            Element el = doc.getRootElement();
//            return recGetXmlElementValue(el, map);        }
//        catch (DocumentException e) {
//            e.printStackTrace();
//            return null;        }
//    }
//
//    private static Map<String, String> recGetXmlElementValue(Element ele, Map<String, String> map) {
//        List<Element> eleList = ele.elements();
//        if (eleList.size() == 0) {
//            map.put(ele.getName(), ele.getTextTrim());
//            return map;        }
//        else {
//            for (Iterator<Element> iter = eleList.iterator(); iter.hasNext();)
//            {
//                org.dom4j.Element innerEle = iter.next();
//                recGetXmlElementValue(innerEle, map);            }
//            return map;
//        }
//       }

}
