package com.drpeng.worklog.dao;


import com.drpeng.worklog.model.DailyReportEmp;

public interface DailyReportEmpMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table daily_report_emp
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table daily_report_emp
     *
     * @mbg.generated
     */
    int insert(DailyReportEmp record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table daily_report_emp
     *
     * @mbg.generated
     */
    int insertSelective(DailyReportEmp record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table daily_report_emp
     *
     * @mbg.generated
     */
    DailyReportEmp selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table daily_report_emp
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(DailyReportEmp record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table daily_report_emp
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(DailyReportEmp record);
}