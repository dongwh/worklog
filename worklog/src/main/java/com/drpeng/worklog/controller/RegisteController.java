package com.drpeng.worklog.controller;

import com.drpeng.worklog.model.DailyReportEmp;
import com.drpeng.worklog.service.RegisteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;


/**
 * Created by panjh on 2018-09-29.
 */
@Controller
public class RegisteController {

    public  static Logger logger = LoggerFactory.getLogger(RegisteController.class);

    @Autowired
    private RegisteService service;
    @ResponseBody
    @RequestMapping("/registe")
    public String registe(HttpServletRequest request) {
       String name=  request.getParameter("name");
        String userName=  request.getParameter("userName");
        String password=  request.getParameter("password");
        DailyReportEmp emp = new DailyReportEmp();
        emp.setStaffName(userName);
        emp.setState(1);
        emp.setPassword(password);
        emp.setLoginName(name);
        service.increateEmp(emp);
        // return模板文件的名称，对应src/main/resources/templates/index.html
        return "ok";
    }


}
