package com.drpeng.worklog.dao;


import com.drpeng.worklog.model.DailyReportEmp;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
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

    DailyReportEmp selectByLoginName(@Param("loginName")String loginName, @Param("password")String password);

    int selectLoginNameCount(String loginName);

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