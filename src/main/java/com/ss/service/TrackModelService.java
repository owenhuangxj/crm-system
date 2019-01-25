package com.ss.service;

import com.ss.entity.TrackModel;
import com.ss.vo.Json;

import java.util.List;

public interface TrackModelService {

    /**
     * 查询学员跟踪信息
     * @param input    用户输入的信息
     * @param value    用户选择了通过什么来查询，例如：姓名、咨询师...
     * @param pageNum  分页参数：当前页
     * @param pageSize 分页参数：一页显示多少条
     * @return 返回前端需要的指定格式的数据
     */
    Json getTrackModel(String input, String value, Integer pageNum, Integer pageSize);

    /**
     * 获取全部咨询师
     * @return
     */
    Json getUserNames();

    /**
     * 修改咨询师
     * @param nick
     * @param trackModel
     * @return
     */
    Json updateNick(String nick, List<TrackModel> trackModel);

    /**
     * 修改学员状态（撤回简历）
     * @return
     */
    Json updateStuStatus(List<TrackModel> trackModel);
}
