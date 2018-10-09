package com.drpeng.worklog.controller;

import com.drpeng.worklog.model.DailyReportEmp;
import com.drpeng.worklog.service.ILoginService;
import com.drpeng.worklog.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by dongwh on 2018-09-29.
 */
@RestController
public class LoginController {

    public  static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private ILoginService loginService;

    @RequestMapping("/index")
    public ModelAndView index(ModelMap map) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        // return模板文件的名称，对应src/main/resources/templates/index.html
        return modelAndView;
    }

    @RequestMapping("/login")
    public ModelAndView goLoginPage(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @RequestMapping("/validateLogin")
    @ResponseBody
    public String login(HttpServletRequest request, HttpSession session) {
        Map<String, Object> responseResult = new HashMap<String, Object>();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        DailyReportEmp data = loginService.login(username, password);

            if(data==null||data.getPassword()==null) {
                responseResult.put("result_code", "error");
                responseResult.put("message", "账号 或 密码 输入错误,请重新输入");
            }else{
                session.setAttribute("username",data.getLoginName());
                session.setAttribute("staffName",data.getStaffName());
                session.setAttribute("empId",data.getId());

                responseResult.put("result_code", "success");
                responseResult.put("message", "登录成功");
            }

        return JsonUtil.toJson(responseResult);
    }

    @RequestMapping("/register")
    @ResponseBody
    public String register(HttpServletRequest request, HttpSession session) {
        Map<String, Object> responseResult = new HashMap<String, Object>();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String staffname = request.getParameter("staffname");

        int count = loginService.register(username);

        if(count==0) {

            responseResult.put("result_code", "success");
            responseResult.put("message", "登录成功");
        } else {
            responseResult.put("result_code", "error");
            responseResult.put("message", "登录名重复,请重新输入");
        }

        return JsonUtil.toJson(responseResult);
    }
}
