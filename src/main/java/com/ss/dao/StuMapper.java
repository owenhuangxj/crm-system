package com.ss.dao;

import com.ss.config.RedisCacheMybatis;
import com.ss.entity.Student;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
@CacheNamespace(implementation = RedisCacheMybatis.class)

public interface StuMapper {

    /**
     * 添加学生简历
     * @param student 学生数据
     * @return 改变行数
     */
    Integer insertStuResume(Student student);

    /**
     * 修改学生信息
     * @param student 学生数据
     * @return 改变行数
     */
    Integer updateStuInfo(Student student);

    /**
     * 通过编号查询学生信息
     * @param stuNumber 学生编号
     * @return 学生信息
     */
    Student getStuInfoByStuNumber(@Param("stuNumber") String stuNumber);

    /**
     * 查询学生信息
     * @return 学生信息
     */
    Student getStuInfoByStu();

    /**
     * 修改状态
     * @param student
     * @return
     */
    Integer updateStuStatus(Student student);

}
