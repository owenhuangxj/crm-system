package com.ss.config;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import org.apache.ibatis.cache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisServerCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class RedisCacheMybatis  implements Cache {
    /**
     * 读写锁：与synchronized的区别：
     *      synchronized : 读写竞争==>读的时候不能写，返回亦然
     *      ReentrantReadWriteLock ： 读写不竞争，读读不竞争，写写竞争
     */
    private ReadWriteLock lock = new ReentrantReadWriteLock(true);
    // 缓存的id
    private String id;
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    public String getId() {
        return this.id;
    }

    public RedisCacheMybatis(String id) {
        this.id = id;
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return this.lock;
    }

    @Override
    public void putObject(Object key, Object value) {
        injectTemplate();
        redisTemplate.opsForValue().set(key.toString(),value);
    }

    @Override
    public Object getObject(Object key) {
        injectTemplate();
        return redisTemplate.opsForValue().get(key.toString());
    }

    @Override
    public Object removeObject(Object key) {
        return redisTemplate.delete(key.toString());
    }

    @Override
    public void clear() {
        injectTemplate();
        Set<String> keys = redisTemplate.keys("*".concat( this.id).concat("*")); //包含改id的所有key
        if(!CollectionUtils.isEmpty(keys)) redisTemplate.delete(keys);
    }

    @Override
    public int getSize() {
        injectTemplate();
        return redisTemplate.execute((RedisCallback<Long>) (RedisServerCommands::dbSize)).intValue();
    }
    private void injectTemplate(){
        if(null == redisTemplate) this.redisTemplate = (RedisTemplate<String, Object>) ApplicationContextHolder.getBean("redisJsonTemplate");
    }
}
