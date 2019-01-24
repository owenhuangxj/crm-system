package com.ss.service.impl;

import com.ss.dao.MonitorMapper;
import com.ss.entity.Search;
import com.ss.entity.Student;
import com.ss.entity.TrackInfo;
import com.ss.entity.User;
import com.ss.service.MonitorService;
import com.ss.util.ChartData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MonitorServiceImp implements MonitorService {
    @Autowired
    private MonitorMapper monitorMapper;
    @Override
    public List<User> selectAllWorker(Integer currentPage, Integer currentPageSize) {
        System.out.println("start:"+currentPage+" currentPageSize:"+currentPageSize );
        return monitorMapper.selectAllWorker((currentPage-1)*currentPageSize,currentPageSize);
    }

    @Override
    public Integer selectWorkerNum() {
        return monitorMapper.selectWorkerNum();
    }

    @Override
    public List<Student> selectWorkerOfStu(Integer userId, Integer currentPage, Integer currentPageSize) {
        return monitorMapper.selectWorkerOfStu(userId,(currentPage-1)*currentPageSize,currentPageSize);
    }

    @Override
    public ChartData selectWorkerOfBusiness(Integer userId) {
        Integer  alreadZhouWord=  monitorMapper.selectAlreadZhouWork(userId);//查询周完成的工作量
        Integer  noZhouWord=   monitorMapper.selectNoZhouWork(userId);//查询周未完成的工作量
        Integer  alreadYueWord=    monitorMapper.selectAlreadYueWork(userId);//查询周完成的工作量
        Integer  NoYueWord=  monitorMapper.selectNoYueWork(userId);//查询周未完成的工作量
        System.out.println("serviceimpl获得的数据是："+alreadZhouWord+noZhouWord+alreadYueWord+NoYueWord);
         List<Integer> dataAlread = new ArrayList<>();
        List<Integer> dataNo = new ArrayList<>();
        dataAlread.add(alreadYueWord);
        dataAlread.add(alreadZhouWord);
        dataNo.add(NoYueWord);
        dataNo.add(noZhouWord);
        ChartData chartData = ChartData.chartData(dataAlread, dataNo);

        return chartData;
    }
    @Override
    public List<TrackInfo> selectWorkerOfStuTrak(String stuNumber) {

        return monitorMapper.selectWorkerOfStuTrak(stuNumber);
    }

    @Override
    public Integer selectWorkerOfStuNum(Integer userId) {
        return monitorMapper.selectWorkerOfStuNum(userId);
    }

    @Override
    public User selectSomeWorker(Search search) {
        User user = new User();

//        switch (search.getSelectCate()){
//            case "姓名":
//                user.(search.getSelectWord());
//                break;
//            case "编号":
//                String userId=search.getSelectWord();
//                int userid = Integer.parseInt(userId);
//                user.setUserId(userid);
//                break;
//            case "手机号":
//                user.setPhone(search.getSelectWord());
//                break;
//            case "邮箱":
//                user.setEmail(search.getSelectWord());
//                break;
//                default:
//                    break;
//            }
        return monitorMapper.selectSomeWorker(user);
    }
}
