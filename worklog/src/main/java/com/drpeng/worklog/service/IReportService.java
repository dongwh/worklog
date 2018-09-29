package com.drpeng.worklog.service;

import com.drpeng.worklog.model.DailyReport;

import java.util.List;

/**
 * @author miracle_wzx
 * @Package com.drpeng.worklog.service
 * @Description:
 * @date 2018/9/29下午3:04
 */
public interface IReportService {
    public List<DailyReport> queryReport(String curdate);

}
