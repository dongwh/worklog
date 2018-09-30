package com.drpeng.worklog.controller;


import com.drpeng.worklog.model.DailyReport;
import com.drpeng.worklog.service.IReportService;
import com.drpeng.worklog.util.JsonUtil;
import com.drpeng.worklog.util.PageData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * Created by lihf on 2018-09-29.
 */
@RestController
public class ReportController {

    public static Logger logger = LoggerFactory.getLogger(ReportController.class);

    @Autowired
    private IReportService reportService;


    @RequestMapping("/dailyReport")
    public ModelAndView dailyReport(ModelMap map) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("dailyReport");
        //return模板文件的名称，对应src/main/resources/templates/index.html
        return modelAndView;
    }


    @PostMapping(value = "/saveReport", produces = "application/json")
    @ResponseBody
    public String saveReport(HttpServletRequest request, HttpSession session) {
        PageData pageData = new PageData(request);
        String curDate = request.getParameter("curDate");
        String content = request.getParameter("content");
        String code = request.getParameter("code");
        String userName = request.getParameter("userName");



        if (null == curDate) {
            pageData.put("result", "提报时间不能为空,请更正后,重新提交!");
            pageData.put("result_code", "error");
            //map转json
            return JsonUtil.toJson(pageData);
        }

        if (null == content) {
            pageData.put("result", "日报内容不能为空,请更正后,重新提交!");
            pageData.put("result_code", "error");
            return JsonUtil.toJson(pageData);
        }

        if (null == code) {
            pageData.put("result", "填报人账号不能为空,请更正后,重新提交!");
            pageData.put("result_code", "error");
            return JsonUtil.toJson(pageData);
        }
        if(null == userName) {

            pageData.put("result", "填报人不能为空,请更正后,重新提交!");
            pageData.put("result_code", "error");
            return JsonUtil.toJson(pageData);
        } else {

            Map<String, Object> result = new HashMap<>();
            pageData.clear();

            result = reportService.saveReport(pageData);

            if(null == result || result.size() == 0) {
                pageData.put("result", "日报新增失败,请联系系统管理员!");
                pageData.put("result_code", "error");
            }
            String flag = null == result.get("FLAG") ? "NO" : String.valueOf(result.get("FLAG"));
            if("OK".equals(flag)) {
                pageData.put("result_code", "success");
                pageData.put("result", null == result.get("INFO") ? "日报新增成功!" : String.valueOf(result.get("INFO")));
            } else {
                pageData.put("result", "日报新增失败,请联系系统管理员!");
                pageData.put("result_code", "error");
            }
            return JsonUtil.toJson(pageData);
        }

    }
    @GetMapping("/report")
    public String queryReportByCreatDate (@RequestParam(value = "curdate", required = false) String curdate){
        HashMap<String, Object> param = new HashMap<String, Object>();
        List<DailyReport> reportData = reportService.queryReport(curdate);
        param.clear();
        param.put("data", reportData);
        param.put("total", reportData.size());
        return JsonUtil.toJson(param);

    }
}
