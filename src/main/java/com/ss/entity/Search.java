package com.ss.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Search {
    //搜索的类型
  private String  selectCate;
  //搜索的关键词
  private String   selectWord;
  //搜索开始的时间
  private String   startTime;
  //搜索结束的时间
  private String   endTime;
}
