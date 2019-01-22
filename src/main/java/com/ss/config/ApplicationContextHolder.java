package com.ss.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
@Component
public class  ApplicationContextHolder  implements ApplicationContextAware {
    private static ApplicationContext applicationContext;
    //容器通过setApplicationContext方法注入应用的上下文
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public static <T>T getBean(String name){
        return (T)applicationContext.getBean(name);
    }
    public static <T>T getBean(Class clz){
        return (T)applicationContext.getBean(clz);
    }
}
