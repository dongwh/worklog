package com.drpeng.worklog.Util.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.text.SimpleDateFormat;

/**
 * 定制 Jackson 的 ObjectMapper
 *
 * @author weizixuan
 * @since 2018/9/29下午4:07
 */
public class CommonObjectMapper extends ObjectMapper {

    private boolean camelCaseToLowerCaseWithUnderscores = false;
    private String dateFormatPattern;

    public void setCamelCaseToLowerCaseWithUnderscores(boolean camelCaseToLowerCaseWithUnderscores) {
        this.camelCaseToLowerCaseWithUnderscores = camelCaseToLowerCaseWithUnderscores;
    }

    public void setDateFormatPattern(String dateFormatPattern) {
        this.dateFormatPattern = dateFormatPattern;
    }

    public CommonObjectMapper() {
        // 排除值为空属性
        //setSerializationInclusion(JsonInclude.Include.NON_NULL);
        // 进行缩进输出
        configure(SerializationFeature.INDENT_OUTPUT, true);
        // 将驼峰转为下划线
      //  if (camelCaseToLowerCaseWithUnderscores) {
       //     setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
      //  }
        // 进行日期格式化
        setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }
}
