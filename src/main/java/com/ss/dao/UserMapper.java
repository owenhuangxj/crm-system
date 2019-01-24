package com.ss.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ss.config.RedisCacheMybatis;
import com.ss.entity.User;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
@CacheNamespace(implementation = RedisCacheMybatis.class)
public interface UserMapper extends BaseMapper<User> {

    /**
     * 查询用户姓名
     *
     * @param userId 用户id
     * @return 用户姓名
     */
    User getUserByUid(@Param("userId") int userId);

}
