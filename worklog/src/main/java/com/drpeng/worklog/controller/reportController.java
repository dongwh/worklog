package com.drpeng.worklog.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;


/**
 * Created by lihf on 2018-09-29.
 */
@RestController
public class reportController {

    public  static Logger logger = LoggerFactory.getLogger(reportController.class);

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/dailyReport")
    public ModelAndView index(ModelMap map) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("dailyReport");
        // return模板文件的名称，对应src/main/resources/templates/index.html
        return modelAndView;
    }
}
