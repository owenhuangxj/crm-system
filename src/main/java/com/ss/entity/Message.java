
package com.ss.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
/**
 * 消息实体类
 */
public class Message {
    private Integer id;
    /**
     * 发送者的登录帐号
     */
    private String msgFrom;
    /**
     * 接收者的登录帐号
     */
    private String msgTo;
    /**
     * 标题
     */
    private String title;
    /**
     * 内容
     */
    private String text;
    /**
     * 状态。
     * 0 未读
     * 1 已读
     * 2 已删
     */
    private Integer status;
    /**
     * 时间戳
     */
    private String timeStap;
    /**
     * 消息类型
     */
    private String kind;
}
