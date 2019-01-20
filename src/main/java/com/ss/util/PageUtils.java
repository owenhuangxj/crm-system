package com.ss.util;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
/**
 * created by CaiBaoHong at 2018/5/16 10:15<br>
 */
public class PageUtils {
    /**
     * 获取分页参数
     */
    public static Page getPageParam(JSONObject json) {
        int current = json.getIntValue("current");
        int size = json.getIntValue("size");
        if (current == 0) current = 1;
        if (size == 0) size = 10;
        return new Page(current, size);
    }
}