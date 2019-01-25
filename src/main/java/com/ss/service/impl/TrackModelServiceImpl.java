package com.ss.service.impl;

import com.ss.entity.Counselor;
import com.ss.entity.SysUser;
import com.ss.entity.TrackModel;
import com.ss.dao.TrackMapper;
import com.ss.dao.TrackModelMapper;
import com.ss.entity.User;
import com.ss.service.TrackModelService;
import com.ss.vo.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName TrackModelServiceImpl
 * @Description 查询跟踪信息的实现类
 * @Author Jack Hu
 * @Date 2019/1/18 11:16
 */
@Service
public class TrackModelServiceImpl implements TrackModelService {

    @Autowired
    private TrackModelMapper tmm;
    @Autowired
    private TrackMapper tm;

    @Override
    public Json getTrackModel(String input, String value, Integer pageNum, Integer pageSize) {
        Integer start = (pageNum - 1) * pageSize;
        List<TrackModel> track = null;
        Integer total = null;
        // 判断用户在下拉框选择的搜索类型
        if ("1".equals(value)) {
            total = tmm.getTotal(input, null, null);
            track = tmm.getTrackModel(input, null, null, start, pageSize);
        } else if ("2".equals(value)) {
            total = tmm.getTotal(null, input, null);
            track = tmm.getTrackModel(null, input, null, start, pageSize);
        } else {
            total = tmm.getTotal(null, null, input);
            track = tmm.getTrackModel(null, null, input, start, pageSize);
        }
        // 前端需要的指定格式
        Json json = new Json(null, true, total, "查询成功！", switchStatus(track));
        return json;
    }

    @Override
    public Json getUserNames() {
        List<SysUser> userNames = tmm.getUserNames();
        List<Counselor> counselor = new ArrayList<>();
        for (SysUser u : userNames) {
            counselor.add(new Counselor(u.getNick(),u.getNick()));
        }
        Json json = new Json(null, true, 0, null, counselor);
        return json;
    }

    @Override
    public Json updateNick(String nick, List<TrackModel> trackModel) {
        List<String> stuNumber = new ArrayList<>();
        for (TrackModel tm : trackModel) {
            stuNumber.add(tm.getStuNumber());
        }
        Integer rows = tmm.updateNick(nick, stuNumber);
        String msg = rows > 0 ? "成功调换" + rows + "名学员的咨询师" : "修改失败！请刷新后重试...";
        Json json = new Json(null, true, rows, msg, null);
        return json;
    }

    @Override
    public Json updateStuStatus(List<TrackModel> trackModels) {
        List<String> number = new ArrayList<>();
        for (TrackModel tm : trackModels) {
            number.add(tm.getStuNumber());
        }
        Integer rows = tmm.updateStuStatus(number);
        String msg = rows > 0 ? "成功撤回" + rows + "份简历" : "撤回失败！请刷新后重试...";
        Json json = new Json(null, true, rows, msg, null);
        return json;
    }

    /**
     * 转换学员跟踪信息（将数字转换成汉子以便在前端上显示）
     *
     * @param trackModels
     * @return
     */
    public static List<TrackModel> switchStatus(List<TrackModel> trackModels) {
        for (TrackModel tm : trackModels) {
            // 转换学员优先级
            switch (tm.getStuLevel()) {
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
