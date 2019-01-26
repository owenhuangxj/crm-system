package com.ss.service.impl;

import com.ss.entity.Student;
import com.ss.entity.Track;
import com.ss.entity.TrackInfo;
import com.ss.entity.User;
import com.ss.service.TrackInfoService;
import com.ss.service.TrackService;
import com.ss.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TrackInfoServiceImpl implements TrackInfoService {

    private User user = new User();
    @Autowired
    private TrackService ts;

    @Override
    public boolean addTrackInfoRecord(TrackInfo ti) {
        Student stu = new Student();
        stu.setStuNumber(ti.getStuNumber());
        // 获取当前时间
        String updateTime = DateUtil.getDateTime();
        // 获取对象
        Track track = new Track(ti.getTrackMethod(), ti.getPriority(), ti.getTrackTime(), ti.getCurrentStatus(), ti.getNextTrackTime(), ti.getTrackDuration(), ti.getPredictTime(), ti.getPredictTrade(), null, null, ti.getTrackDesc(), stu, getConsultId(ti), getTeacherId(ti), updateTime);
        return ts.addTrackRecord(track);
    }

    @Override
    public List<TrackInfo> getTrackInfo(String stuNumber) {
        List<TrackInfo> trackInfos = null;
        // 获取跟踪数据
        List<Track> tracks = ts.getTrackInfo(stuNumber);
        // 创建页面跟踪对象集合
        trackInfos = new ArrayList<TrackInfo>();
        // 没有数据数据时，返回null
        if (tracks.size() == 0)
            return trackInfos;
        // 创建页面跟踪对象
        TrackInfo trackInfo = null;
        // 循环遍历
        for (Track track : tracks) {
            trackInfo = new TrackInfo(track.getTrackWays(), track.getTrackPriority(), track.getTrackTime(), track.getTrackStatus(), track.getTrackNextTime(), track.getTrackDuration(), track.getTrackPredictTime(), track.getTrackTurnoverTime(), null, null, track.getTrackDetails(), track.getStuNumber().getStuNumber(), getConsultId(track).getNick(), getTeacherId(track).getNick());
            trackInfos.add(trackInfo);
        }
        System.out.println(trackInfos);
        return trackInfos;
    }

    /**
     * 获取咨询师
     *
     * @param info
     * @return
     */
    public User getConsultId(TrackInfo info) {
        // 咨询师
        if (null != info.getTrailsman()) {
            user.setUid(info.getTrailsman());
        }
        return user;
    }

    /**
     * 获取面试官
     *
     * @param info
     * @return
     */
    public User getTeacherId(TrackInfo info) {
        // 面试官
        User teacher = new User();
        if (null != info.getTechnicalInterviewer()) {
            teacher.setUid(info.getTechnicalInterviewer());
        }
        return teacher;
    }

    /**
     * 获取咨询师
     *
     * @param track
     * @return
     */
    public User getConsultId(Track track) {
        // 面试官
        if (null == track.getConsultId()) {
            user.setNick("无");
            track.setConsultId(user);
        }
        return track.getConsultId();
    }

    /**
     * 获取面试官
     *
     * @param track
     * @return
     */
    public User getTeacherId(Track track) {
        // 面试官
        if (null == track.getTeacherId()) {
            user.setNick("无");
            track.setTeacherId(user);
        }
        return track.getTeacherId();
    }
}
