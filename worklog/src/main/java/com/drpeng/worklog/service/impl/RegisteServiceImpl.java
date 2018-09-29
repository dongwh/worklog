package com.drpeng.worklog.service.impl;

import com.drpeng.worklog.dao.DailyReportEmpMapper;
import com.drpeng.worklog.model.DailyReportEmp;
import com.drpeng.worklog.service.RegisteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RegisteServiceImpl implements RegisteService {
    @Autowired
    private DailyReportEmpMapper mapper;
    @Override
    public void increateEmp(DailyReportEmp emp) {

        int num = mapper.insert(emp);
        System.out.print(num);
    }
}
