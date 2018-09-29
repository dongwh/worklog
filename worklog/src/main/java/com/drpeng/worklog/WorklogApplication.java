package com.drpeng.worklog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;

@SpringBootApplication
@MapperScan("com.drpeng.worklog.dao")
public class WorklogApplication {

	@Bean
	public RestTemplate restTemplate() {
		StringHttpMessageConverter m = new StringHttpMessageConverter(Charset.forName("UTF-8"));
		RestTemplate restTemplate = new RestTemplateBuilder().additionalMessageConverters(m).build();
		return restTemplate;
	}

	public static void main(String[] args) {
		SpringApplication.run(WorklogApplication.class, args);
	}
}
