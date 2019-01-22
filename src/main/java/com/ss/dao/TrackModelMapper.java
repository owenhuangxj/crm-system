package com.ss.dao;

import com.ss.config.RedisCacheMybatis;
import com.ss.entity.TrackModel;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
@CacheNamespace(eviction = RedisCacheMybatis.class,implementation = RedisCacheMybatis.class)
public interface TrackModelMapper {

    /**
     * 多条件查询学生跟踪信息
     * @param stuName 学生姓名
     * @param userName 咨询师姓名
     * @param trackWays 跟踪方式
     * @return 跟踪信息
     */
    List<TrackModel> getTrackModel(@Param("stuName") String stuName, @Param("userName") String userName,
                                    @Param("trackWays") String trackWays, @Param("start") Integer start,
                                    @Param("end") Integer end);

    /**
     * 获取查询出来的总条数
     * @param stuName 学生姓名
     * @param userName 咨询师姓名
     * @param trackWays 跟踪方式
     * @return 总条数
     */
    Integer getTotal(@Param("stuName") String stuName, @Param("userName") String userName,
                     @Param("trackWays") String trackWays);


}
