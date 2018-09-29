package com.drpeng.worklog.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Tsiye on 17/7/17.
 */
public class RestAPI {
    private final String url;

    //private final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    private  final Map<String,String> params = new HashMap<String,String>();
    public void set(String key, String value) {
        params.put(key, value);
    }
    public void setParams(Map<String, Object> param){
        Iterator it = param.entrySet().iterator();
        String key = null;
        String value = null;
        while (it.hasNext()){
            Map.Entry entry = (Map.Entry)it.next();
            key = entry.getKey().toString();
            value=entry.getValue().toString();
            if (StringUtil.isNotEmpty(key)&&StringUtil.isNotEmpty(value)){
                params.put(key, value);
            }
        }
    }
    /**
     * 构造方法,请求url.
     *
     * @param url 请求地址
     */
    public RestAPI(String url) {
        super();
        this.url = url;
    }
    /**
     * 发送/获取 服务端数据(主要用于解决发送put,delete方法无返回值问题).
     *
     * @param url      绝对地址
     * @param method   请求方式
     * @param bodyType 返回类型
     * @param <T>      返回类型
     * @return 返回结果(响应体)
     */
    public <T> T exchange(String url, HttpMethod method, Class<T> bodyType, Map<String, Object> uriVars) {
        // 请求头
        HttpHeaders headers = new HttpHeaders();
        MimeType mimeType = MimeTypeUtils.parseMimeType("application/json");
        MediaType mediaType = new MediaType(mimeType.getType(), mimeType.getSubtype(), Charset.forName("UTF-8"));
        // 请求体
        headers.setContentType(mediaType);
        //提供json转化功能
        ObjectMapper mapper = new ObjectMapper();
        String str = null;
        try {
            if (!params.isEmpty()) {
                str = mapper.writeValueAsString(params);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        // 发送请求
        HttpEntity<String> entity = new HttpEntity<>(str, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<T> resultEntity = restTemplate.exchange(url, method, entity, bodyType,uriVars);
        return resultEntity.getBody();
    }
}
