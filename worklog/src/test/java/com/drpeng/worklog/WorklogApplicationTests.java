package com.drpeng.worklog;

import com.drpeng.worklog.model.DailyReport;
import com.drpeng.worklog.service.IReportService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WorklogApplicationTests {

	@Autowired
	IReportService reportService;

	@Test
	public void contextLoads() {
		/*List<DailyReport> list = reportService.queryReport("2018-09-30");
		for (DailyReport dailyReport : list) {
			System.out.println(dailyReport.getContent());
		}*/
	}

}
