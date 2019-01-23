package com.ss.service.impl;

import com.ss.util.PageData;
import com.ss.entity.TrackModel;
import com.ss.dao.TrackMapper;
import com.ss.dao.TrackModelMapper;
import com.ss.service.TrackModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName TrackModelServiceImpl
 * @Description TODO
 * @Author Jack Hu
 * @Date 2019/1/18 11:16
 */
@Service
public class TrackModelServiceImpl implements TrackModelService {

    @Autowired
    private TrackModelMapper tmm;
    @Autowired
    private TrackMapper tm;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public PageData<TrackModel> getTrackModel(String input, String value, Integer pageNum, Integer pageSize) {
        Integer start = (pageNum - 1) * pageSize;
        List<TrackModel> track = null;
        Integer total = null;
        // 判断用户在下拉框选择的搜索类型
        if ("1".equals(value)) {
            total = tmm.getTotal(input,null,null);
            track = tmm.getTrackModel(input, null, null, start, pageSize);
        } else if ("2".equals(value)) {
            total = tmm.getTotal(null,input,null);
            track = tmm.getTrackModel(null, input, null, start, pageSize);
        } else {
            total = tmm.getTotal(null,null,input);
            track = tmm.getTrackModel(null, null, input, start, pageSize);
        }
        PageData<TrackModel> pageData = new PageData<>(switchStatus(track), total);
        return pageData;
    }

    /**
     * 转换学员跟踪信息状态
     * @param trackModels
     * @return
     */
    public static List<TrackModel> switchStatus(List<TrackModel> trackModels){
        for (TrackModel tm : trackModels) {
            // 转换学员优先级
            switch (tm.getStuLevel()){
                case "2":
                    tm.setStuLevel("低");
                    break;
                case "3":
                    tm.setStuLevel("中");
                    break;
                case "4":
                    tm.setStuLevel("高");
                    break;
                default:
                    tm.setStuLevel("无");
            }
            // 转换学员的进度
            switch (tm.getStuStatus()) {
                case "1":
                    tm.setStuStatus("新增");
                    break;
                case "2":
                    tm.setStuStatus("跟踪中");
                    break;
                case "3":
                    tm.setStuStatus("待面试");
                    break;
                case "4":
                    tm.setStuStatus("面试未通过");
                    break;
                case "5":
                    tm.setStuStatus("面试通过");
                    break;
                case "6":
                    tm.setStuStatus("已缴未清");
                    break;
                case "7":
                    tm.setStuStatus("已缴费");
                    break;
                case "8":
                    tm.setStuStatus("入学");
                    break;
                case "9":
                    tm.setStuStatus("放弃入学");
                    break;
                case "10":
                    tm.setStuStatus("退学");
                    break;
                case "11":
                    tm.setStuStatus("已退费");
                    break;
                case "12":
                    tm.setStuStatus("放弃");
                    break;
                default:
                    tm.setStuStatus("未分配");
            }
        }
        return trackModels;
    }



}
