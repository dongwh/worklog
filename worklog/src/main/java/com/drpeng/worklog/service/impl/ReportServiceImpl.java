package com.drpeng.worklog.service.impl;

import com.drpeng.worklog.dao.DailyReportMapper;
import com.drpeng.worklog.model.DailyReport;
import com.drpeng.worklog.service.IReportService;
import com.drpeng.worklog.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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

    public List<DailyReport> queryReport(Integer empId){


        List<DailyReport> reportList = dailyReportMapper.selectByCreateDate(empId);
        return reportList;

    }

    @Override
    public Map<String ,Object> saveReport(Map<String, Object> paramInfo)
    {
        DailyReport dailyReport=new DailyReport();
        //String curdate = String.valueOf(paramInfo.get("curdate")).replace("-","");

        String curDate = String.valueOf(paramInfo.get("curDate"));

        String empId = String.valueOf(paramInfo.get("empId"));
        //String empId = "9517";
        String content = String.valueOf(paramInfo.get("content"));

        String state = "1";
        dailyReport.setContent(content);
        dailyReport.setEmpId(Integer.valueOf(empId));
        dailyReport.setState(Integer.valueOf(state));
        dailyReport.setCreateDate(DateUtil.formatDate(curDate));
        int num=0;

        if(paramInfo.get("logId")!=null&&!"".equals(paramInfo.get("logId"))){
            Integer logId = Integer.parseInt((String)paramInfo.get("logId"));
            dailyReport.setId(logId);
            num=dailyReportMapper.updateByPrimaryKeyWithBLOBs(dailyReport);
        }else{
            num=dailyReportMapper.insert(dailyReport);
        }


        paramInfo.clear();
        if (num > 0) {
            paramInfo.put("FLAG", "OK");
            paramInfo.put("INFO", "日报提交成功: " + num+"条");
        } else {
            paramInfo.put("FLAG", "NO");
            paramInfo.put("INFO", "日报提交失败,请联系系统管理员!");
        }
        return paramInfo;
    }
    @Override
    public int delLog(String id) {
        return dailyReportMapper.delLog(id);
    }

}
