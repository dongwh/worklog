package com.drpeng.worklog.controller;

import com.drpeng.worklog.model.DailyReportEmp;
import com.drpeng.worklog.service.RegisteService;
import com.sun.xml.internal.ws.api.server.SDDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @PostMapping("/register")
    @ResponseBody
    public String registe(@RequestParam("staffname")String staffname, @RequestParam("username")String userName, @RequestParam("password")String password) {
        DailyReportEmp emp = new DailyReportEmp();
        emp.setStaffName(staffname);
        emp.setState(1);
        emp.setPassword(password);
        emp.setLoginName(userName);
        service.increateEmp(emp);
        // return模板文件的名称，对应src/main/resources/templates/index.html
        return "ok";
    }


}
