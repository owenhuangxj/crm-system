package com.ss.util;

import java.util.UUID;

public class CreateUId {
    //使用UUID生成16位Id的方法
    public static String getOrderIdByUUId(){
        int machineId=1;
        int hashCodeV= UUID.randomUUID().toString().hashCode();
        if(hashCodeV<0){
            hashCodeV=-hashCodeV;
        }
        return machineId+ String.format("%015d", hashCodeV);
    }

}
