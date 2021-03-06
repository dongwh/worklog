package com.drpeng.worklog.service.impl;

import com.drpeng.worklog.dao.DailyReportEmpMapper;
import com.drpeng.worklog.model.DailyReportEmp;
import com.drpeng.worklog.service.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author miracle_wzx
 * @Package com.drpeng.worklog.service.impl
 * @Description:
 * @date 2018/9/30上午10:49
 */
@Service
public class LoginServiceImpl implements ILoginService {
    @Autowired
    private DailyReportEmpMapper dailyReportEmpMapper;
    public DailyReportEmp login(String username, String password) {

        DailyReportEmp data=dailyReportEmpMapper.selectByLoginName(username,password);

        return data;
    }

    public int register(String username){

        //检查是否重复
        int num =dailyReportEmpMapper.selectLoginNameCount(username);
        //TODO 插入没写
        return num;
    }

}
