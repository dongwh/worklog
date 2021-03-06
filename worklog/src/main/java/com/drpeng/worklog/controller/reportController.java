package com.drpeng.worklog.controller;


import com.drpeng.worklog.model.DailyReport;
import com.drpeng.worklog.service.IReportService;
import com.drpeng.worklog.util.JsonUtil;
import com.drpeng.worklog.util.PageData;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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


    public  static Logger logger = LoggerFactory.getLogger(ReportController.class);


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
        String empId = request.getParameter("empId");
        String logId = request.getParameter("logId");
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

/*        if (null == empId) {
            pageData.put("result", "填报人账号不能为空,请更正后,重新提交!");
            pageData.put("result_code", "error");
            return JsonUtil.toJson(pageData);
        }*/ else {

            Map<String, Object> result = new HashMap<>();
            reportService.saveReport(pageData);

            if(null == pageData || pageData.size() == 0) {
                result.put("result", "日报提交失败,请联系系统管理员!");
                result.put("result_code", "error");
            }
            String flag = null == pageData.get("FLAG") ? "NO" : String.valueOf(pageData.get("FLAG"));
            if("OK".equals(flag)) {
                result.put("result_code", "success");
                result.put("result", null == pageData.get("INFO") ? "日报提交成功!" : String.valueOf(pageData.get("INFO")));
            } else {
                result.put("result", "日报提交失败,请联系系统管理员!");
                result.put("result_code", "error");
            }
            return JsonUtil.toJson(result);
        }

    }


    @GetMapping("/report")
    @ResponseBody
    public String queryReportByCreatDate (@RequestParam(value = "curPage", required = false) String curPage,HttpServletRequest request){
        Integer empId=(Integer) request.getSession().getAttribute("empId");
        curPage = curPage == null || curPage.trim().length() == 0||curPage.equals("0") ? "1":(Integer.parseInt(curPage)+1)+"";
        Integer page = Integer.parseInt(curPage)==1?1:Integer.parseInt(curPage);
        Integer pageSize = 10;
        PageHelper.startPage(page,pageSize);//设置分页的起始码以及页面大小
        List<DailyReport> reportData = reportService.queryReport(empId);
        PageInfo pageInfo = new PageInfo(reportData);//传入list就可以了
        return JsonUtil.toJson(pageInfo);

    }

    @GetMapping("/delLog")
    @ResponseBody
    public String delLog (@RequestParam(value = "id", required = true) String id){


        int count=reportService.delLog(id);
        Map<String ,Integer> result=new HashMap<String ,Integer>();
        result.put("result",count);
        return JsonUtil.toJson(result);



    }
}
