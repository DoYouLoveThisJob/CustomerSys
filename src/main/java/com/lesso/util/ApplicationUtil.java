package com.lesso.util;

/**
 * Created by Administrator on 2016/5/24.
 */
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
 import org.springframework.context.ApplicationContextAware;
 /**
  *
  * @author Kylin
 *
  */
 public class ApplicationUtil implements ApplicationContextAware{
       private static ApplicationContext applicationContext;

        public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
               ApplicationUtil.applicationContext = applicationContext;
            }
        public static Object getBean(String name){
                return applicationContext.getBean(name);
          }
 }
