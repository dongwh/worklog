package com.drpeng.worklog.controller;

import com.drpeng.worklog.model.DailyReport;
import com.drpeng.worklog.service.IReportService;
import com.drpeng.worklog.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;


/**
 * Created by lihf on 2018-09-29.
 */
@RestController
public class ReportController {

    public  static Logger logger = LoggerFactory.getLogger(ReportController.class);

    @Autowired
    private IReportService reportService;

    @RequestMapping("/dailyReport")
    public ModelAndView index(ModelMap map) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("dailyReport");
        // return模板文件的名称，对应src/main/resources/templates/index.html
        return modelAndView;
    }

    @GetMapping("/report")
    public String queryReportByCreatDate(@RequestParam(value ="curdate", required = false) String curdate){
        HashMap<String,Object> param = new HashMap<String,Object>();
        List<DailyReport> reportData = reportService.queryReport(curdate);
        param.clear();
        param.put("data",reportData);
        param.put("total",reportData.size());
        return JsonUtil.toJson(param);

    }
}
