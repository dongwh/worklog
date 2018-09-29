package com.drpeng.worklog.service.impl;

import com.drpeng.worklog.dao.DailyReportMapper;
import com.drpeng.worklog.model.DailyReport;
import com.drpeng.worklog.service.IReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author miracle_wzx
 * @Package com.drpeng.worklog.service.impl
 * @Description:
 * @date 2018/9/29下午3:05
 */
@Service
public class ReportServiceImpl implements IReportService {

    @Autowired
    private DailyReportMapper dailyReportMapper;
    @Override
    public List<DailyReport> queryReport(String curdate){

        List<DailyReport> reportList = dailyReportMapper.selectByCreateDate(curdate);
        return reportList;

    }

}
