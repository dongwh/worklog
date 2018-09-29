package com.drpeng.worklog.util;

import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dongwh on 17/6/30.
 */
public class ParameterUtil {
    public static String getParameter(String name, HttpServletRequest request) {
        return request.getParameter(name);
    }

    public static Map<String,Object> getRequestObject(HttpServletRequest request) {
        Map<String,Object> theRequestData = new HashMap<String,Object>();
        Enumeration<String> names = request.getParameterNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement().toString();
            theRequestData.put(name,getParameter(name,request));
        }
        return theRequestData;
    }

    public static String getQueryParam(HttpServletRequest request){
        Map<String,Object> params = getRequestObject(request);
        return getQueryParam(params);
    }

    public static String getQueryParam(Map<String,Object> params){
        StringBuffer paramStr = new StringBuffer();
        int i=0;
        if (null!=params && params.size() > 0) {

            for (String key : params.keySet()) {
                if(i==0){
                    paramStr.append("?"+key + "={" + key + "}");
                }else {
                    paramStr.append("&"+key + "={" + key + "}");
                }

                i++;
            }

        }
        return paramStr.toString();
    }
    public static String dealUrlAndParamQuery(String restApiUrl, Map<String,Object> params, RestTemplate restTemplate) {
        String result = "";
        try {
            StringBuffer url = new StringBuffer(restApiUrl);
            if (null!=params && params.size() > 0) {
                url.append("?");
                for (String key : params.keySet()) {
                    url.append(key + "={" + key + "}&");
                }
            }
            if(null==params || params.size()==0){
                result = restTemplate.getForEntity(url.toString(),String.class).getBody();
            }else{
                result = restTemplate.getForEntity(url.substring(0,url.length() - 1).toString(),String.class,params).getBody();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
}
