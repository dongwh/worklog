package com.drpeng.worklog.service;

import com.drpeng.worklog.model.DailyReportEmp;

/**
 * @author miracle_wzx
 * @Package com.drpeng.worklog.service.impl
 * @Description:
 * @date 2018/9/30上午10:47
 */
public interface ILoginService {
   public DailyReportEmp login(String username, String password) ;
}
