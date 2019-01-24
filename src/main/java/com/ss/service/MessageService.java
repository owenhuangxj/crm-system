package com.ss.service;

import com.ss.entity.Message;
import com.ss.entity.User;

import java.util.List;
import java.util.Set;

/**
 * @program: crm23
 * @description: 消息服务层
 * @author: August
 * @create: 2019-01-12 18:11
 **/
public interface MessageService {
    /**
     * 根据状态获取缓存中的消息
     * @param uname  登录帐号
     * @return
     */
//    List<Message> getMessageByUserId(Integer userId);
    Set<Object> getMessageList(String uname, Integer msgStatus);

    /**
     * 将消息存入缓存
     * @param msgList
     * @param uname  消息接收者登录帐号
     * @return
     */
    Integer insertMessage(List<Message> msgList, String uname);

    /**
     *
     * @param msgId
     * @param oldStatus
     * @param uname  登录帐号
     * @return
     */
    Boolean setMessageStatus(Integer msgId, Integer oldStatus, String uname);

    /**
     * 获取多个zset中score最大值
     * @param uname
     * @return
     */
    Double getMaxScoreInZsets(String uname);

    Integer setAllStatus(String func, String uname);
}
