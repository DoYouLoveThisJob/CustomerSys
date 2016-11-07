package com.lesso.util;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

/**
 * Created by 0003 on 2016/7/26.
 */
public class SolrUtil {
    public static void  queryAllList() throws IOException, SolrServerException {
        String baseUrl="http://127.0.0.1:8080/solr/czx";
        SolrClient solr = new HttpSolrClient.Builder(baseUrl).build();
        SolrQuery query = new SolrQuery();
        query.set("q", "*:*");
        QueryResponse response = solr.query(query);
        SolrDocumentList list = response.getResults();
        long number= list.getNumFound();
        System.out.println("总数："+number);
        for(SolrDocument solrDocument:list){
         Collection<String> fieldNames=solrDocument.getFieldNames();
            for(String fieldName :fieldNames){
                System.out.println(fieldName +":"+solrDocument.get(fieldName));
            }
            System.out.println("================================================");
        }
    }
   public static void main(String[] args){
       try {
           SolrUtil.queryAllList();
       } catch (IOException e) {
           e.printStackTrace();
       } catch (SolrServerException e) {
           e.printStackTrace();
       }
   }

}
