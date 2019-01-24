package com.ss.service.impl;

import com.ss.entity.Message;
import com.ss.entity.User;
import com.ss.service.MessageService;
import com.ss.util.DateUtil;
import com.ss.util.Websocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @program: crm23
 * @description: 消息服务层实现类
 * redis中存放消息对象的方式：
 * 每个用户有三个zset集合，分别为unreadmsg，readmsg和deletemsg。
 * 集合名后跟用户登录帐号实现唯一。
 * 集合中score值对应消息的msgid，以便查找与操作
 * @author: August
 * @create: 2019-01-12 18:18
 **/
@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private RedisTemplate<String, Object> rt;
    @Autowired
    private Websocket ws;
    @Override
    public Set<Object> getMessageList(String uname, Integer status) {
        String ZsetPreName = "";
        switch (status) {
            case 0:
                ZsetPreName = "unreadmsg";
                break;
            case 1:
                ZsetPreName = "readmsg";
                break;
            case 2:
                ZsetPreName = "deletemsg";
                break;
            default:
                break;
        }
        Set<Object> messages = rt.opsForZSet().range(ZsetPreName + uname, 0, -1);
        System.out.println("从" + ZsetPreName + uname + "中获取了" + messages.size() + "条");
        return messages;
    }

    @Override
    public Integer insertMessage(List<Message> msgList, String uname) {
        //zset集合中，score = msgid。
//        if (rt.opsForZSet().zCard("unreadmsg" + user.getUserId()) + msgList.size() >= 9999) {
//            System.out.println("用户" + user.getUserName() + "信息过多，添加失败");
//            return 0;
//        }

        //
        String date = DateUtil.getDateTime();
        int i = 0;
        // 获取缓存中消息score最大值，在此基础上+1赋值给下一条要插入的消息
        Double score = getMaxScoreInZsets(uname) + 1;
        for (; i < msgList.size(); i++) {
            Message message = msgList.get(i);
            message.setId(score.intValue());
            //状态设置为未读
            message.setStatus(0);
            //设置时间
            message.setTimeStap(date);
            rt.opsForZSet().add("unreadmsg" + message.getMsgTo(), message, message.getId());
            score++;
        }

//        for (; i < msgList.size(); i++) {
//            rt.opsForZSet().add("unreadmsg" + user.getUserId(), msgList.get(i), user.getUserId() * 10000 + msgList.get(i).getId());
//        }
        System.out.println("添加了" + i + "条到unreadmsg" + uname);
        return i;
    }

    @Override
    public Boolean setMessageStatus(Integer msgId, Integer oldStatus, String uname) {
        String oldZset = "";
        String newZset = "";
        Integer newStatus = 0;
        switch (oldStatus) {
            case 0:
                // 将未读消息移到已读消息
                oldZset = "unreadmsg";
                newZset = "readmsg";
                newStatus = 1;
                break;
            case 1:
                // 将已读消息移到回收站
                oldZset = "readmsg";
                newZset = "deletemsg";
                newStatus = 2;
                break;
            case 2:
                // 将回收站消息还原到已读消息
                oldZset = "deletemsg";
                newZset = "readmsg";
                newStatus = 1;
        }
        oldZset += uname;
        newZset += uname;
        // 将消息从旧集合拿出，并从旧集合删除
        Set<Object> set1 = rt.opsForZSet().rangeByScore(oldZset, msgId, msgId);

        Message msg = (Message) set1.toArray()[0];
        // 更新消息状态
        msg.setStatus(newStatus);
        // 放入新集合
        Boolean add = rt.opsForZSet().add(newZset, msg, msgId);
        //删除旧集合中的该条消息
        rt.opsForZSet().removeRangeByScore(oldZset, msgId, msgId);
        return add;
    }

    @Override
    public Double getMaxScoreInZsets(String uname) {
        String[] zsetArr = {"readmsg" + uname, "unreadmsg" + uname, "deletemsg" + uname};
        List<Integer> scores = new ArrayList<>();
        for (int i = 0; i < zsetArr.length; i++) {
            // 获取该zset中score最大的对象
            Set<Object> set = rt.opsForZSet().range(zsetArr[i], -1, -1);
            System.out.println("最大对象 ： "+set);
            // 通过对象获取score最大值
            if (set.size() > 0) {
                Message message = (Message) (set.toArray()[0]);
                scores.add(message.getId());
            } else {
                scores.add(0);
            }
        }
        System.out.println("scores : "+scores);
        Integer max = Collections.max(scores);
//        System.out.println("最大的score值 ：" + max);
        return max.doubleValue();
    }

    @Override
    public Integer setAllStatus(String func, String uname) {
        switch (func) {
            case "readAll":
                // 全部标为已读（unreadmsg集合中的所有消息转移到readmsg中）
                Set<Object> range = rt.opsForZSet().range("unreadmsg" + uname, 0, -1);
                for (Object object : range) {
                    Message msg = (Message) object;
                    msg.setStatus(1);
                    rt.opsForZSet().add("readmsg" + uname, msg, msg.getId());
                }
                System.out.println("从unreadmsg中转移了" + range.size() + "条消息到readmsg中");
                rt.delete("unreadmsg" + uname);
                return range.size();
            case "delAll":
                // 删除全部（readmsg集合中的所有消息转移到deletemsg中）
                Set<Object> range1 = rt.opsForZSet().range("readmsg" + uname, 0, -1);
                for (Object object : range1) {
                    Message msg = (Message) object;
                    msg.setStatus(2);
                    rt.opsForZSet().add("deletemsg" + uname, msg, msg.getId());
                }
                System.out.println("从readmsg中转移了" + range1.size() + "条消息到deletemsg中");
                rt.delete("readmsg" + uname);
                return range1.size();
            case "clearAll":
                // 清空回收站（清空deletemsg集合中的所有数据）
                Boolean delete = rt.delete("deletemsg" + uname);
                System.out.println("删除集合deletemsg"+delete);
                return 1;

        }
        return null;
    }
}
