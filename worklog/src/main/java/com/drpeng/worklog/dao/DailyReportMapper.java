package com.drpeng.worklog.dao;


import com.drpeng.worklog.model.DailyReport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DailyReportMapper {

    int deleteByPrimaryKey(Integer id);


    int insert(DailyReport record);


    int insertSelective(DailyReport record);


    DailyReport selectByPrimaryKey(Integer id);

    List<DailyReport> selectByCreateDate(String curdate);


    int updateByPrimaryKeySelective(DailyReport record);


    int updateByPrimaryKeyWithBLOBs(DailyReport record);


    int updateByPrimaryKey(DailyReport record);

    int delLog(String id);
}