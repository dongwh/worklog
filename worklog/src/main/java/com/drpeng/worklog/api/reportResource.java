package com.drpeng.worklog.api;

import com.drpeng.worklog.model.DailyReport;
import com.drpeng.worklog.service.IReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * @author miracle_wzx
 * @Package com.drpeng.worklog.api
 * @Description:
 * @date 2018/9/29下午2:53
 */
@RestController
public class ReportResource {

    @Autowired
    private IReportService reportService;

    @GetMapping(value = "/report/{curdate}")
    public String queryReportByCreatDate(String curdate){
        HashMap<String,Object> param = new HashMap<String,Object>();
        List<DailyReport> reportData = reportService.queryReport(curdate);
        param.clear();
        param.put("data",reportData);
        param.put("total",reportData.size());
        return com.drpeng.worklog.util.JsonUtil.toJson(param);

    }

}
