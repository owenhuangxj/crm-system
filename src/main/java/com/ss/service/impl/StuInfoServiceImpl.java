package com.ss.service.impl;

import com.ss.entity.Student;
import com.ss.entity.StudentInfo;
import com.ss.entity.User;
import com.ss.service.StuInfoService;
import com.ss.service.StuService;
import com.ss.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StuInfoServiceImpl implements StuInfoService {

    private User user = new User();
    @Autowired
    private StuService ss;

    @Override
    public Boolean addStuResume(StudentInfo info) {
        return ss.addStuResume(getStu(info)) > 0;
    }

    @Override
    public StudentInfo getStuInfo(String stuNumber) {
        Student info = ss.getStuInfo(stuNumber);
        StudentInfo stuInfo = new StudentInfo(info.getStuNumber(), info.getStuName(), info.getStuSex(), info.getStuImportance(), info.getStuPhoneNum(), info.getStuQq(), info.getStuLevel(), info.getStuAddress(), info.getStuChannel(), info.getStuSource(), info.getStuEvaluation(), info.getStuWork(), info.getStuProject(), info.getStuEducation(), info.getStuTrained(), info.getStuCredentials(), info.getStuPerformance(), getConsultId(info).getUid(), getTeacherId(info).getUid());
        return stuInfo;
    }

    @Override
    public Integer updateStuInfo(StudentInfo info) {
        return ss.updateStuInfoByStuNumber(getStu(info));
    }

    /**
     * 获取咨询师
     *
     * @param info
     * @return
     */
    public User getConsultId(StudentInfo info) {
        // 咨询师
        if (null != info.getTeacherId()) {
            user.setUid(info.getConsultId());
        }
        return user;
    }

    /**
     * 获取面试官
     *
     * @param info
     * @return
     */
    public User getTeacherId(StudentInfo info) {
        // 面试官
        if (null != info.getTeacherId()) {
            user.setUid(info.getTeacherId());
        }
        return user;
    }

    /**
     * 获取咨询师
     *
     * @param stu
     * @return
     */
    public User getConsultId(Student stu) {
        // 咨询师
        if (null == stu.getConsultId()) {
            user.setUid("0");
            stu.setConsultId(user);
        }
        return stu.getConsultId();
    }

    /**
     * 获取面试官
     *
     * @param stu
     * @return
     */
    public User getTeacherId(Student stu) {
        // 面试官
        if (null == stu.getTeacherId()) {
            user.setUid("0");
            stu.setTeacherId(user);
        }
        return stu.getTeacherId();
    }

    /**
     * 获取student对象
     *
     * @param info
     * @return
     */
    public Student getStu(StudentInfo info) {
        return new Student(info.getStuNumber(), DateUtil.getDateTime(), info.getStuName(), info.getStuSex(), info.getStuImportance(), info.getStuPhoneNum(), info.getStuQq(), info.getStuLevel(), info.getStuAddress(), info.getStuChannel(), info.getStuSource(), info.getStuEvaluation(), info.getStuWork(), info.getStuProject(), info.getStuEducation(), info.getStuTrained(), info.getStuCredentials(), info.getStuPerformance(), getConsultId(info), getTeacherId(info));
    }
}
