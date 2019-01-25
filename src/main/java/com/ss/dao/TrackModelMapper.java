package com.ss.dao;

import com.ss.config.RedisCacheMybatis;
import com.ss.entity.SysUser;
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

    /**
     * 获取全部咨询师
     * @return 咨询师对象的集合
     */
    List<SysUser> getUserNames();

    /**
     * 修改咨询师
     * @return 受影响行数
     */
    Integer updateNick(@Param("nick") String nick, @Param("stuNumber") List<String> stuNumber);

    /**
     * 修改学员状态（撤回简历）
     * @return
     */
    Integer updateStuStatus(@Param("stuNumber") List<String> stuNumber);
}
