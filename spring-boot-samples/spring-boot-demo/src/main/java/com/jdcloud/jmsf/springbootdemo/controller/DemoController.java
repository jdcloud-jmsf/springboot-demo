package com.jdcloud.jmsf.springbootdemo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Map;

@Slf4j
@RestController
public class DemoController {
    @Value("${provider.server.name:-}")
    private String privider;
    private boolean health = true;

    private int sleepTimeLocal = 0;
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/call")
    public String info(ServletRequest request) throws InterruptedException {
        log.info("receive request, time: {}", System.currentTimeMillis());
        String appNameEnv = "APP_NAME";
        String appGroupEnv = "APP_GROUP";
        if (health) {
            Thread.sleep(sleepTimeLocal * 1000);
            if ("-".equals(privider)) {
                return String.format("%s-%s", System.getenv(appNameEnv), System.getenv(appGroupEnv));
            }
            String result;
            result = request(request, String.format("http://%s/call", privider), HttpMethod.GET, null);
            return String.format("%s-%s, %s", System.getenv(appNameEnv), System.getenv(appGroupEnv), result);
        } else {
            throw new InterErrorException();
        }
    }

    @GetMapping("/health/{status}")
    public boolean changeStatus(@PathVariable boolean status){
        health = status;
        return health;
    }

    @GetMapping("/sleep/{sleepTime}")
    public int changeStatus(@PathVariable int sleepTime){
        sleepTimeLocal = sleepTime;
        return sleepTime;
    }

    private String request(ServletRequest req, String url, HttpMethod method, Map<String, ?> params) {
        HttpServletRequest request = (HttpServletRequest) req;
        //获取header信息
        HttpHeaders requestHeaders = new HttpHeaders();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            if (key.startsWith("x-jfe-label")) {
                String value = request.getHeader(key);
                requestHeaders.add(key, value);
            }
            String customHeaderPrefix = System.getenv("CUSTOM_HEADER_PREFIX");
            if (customHeaderPrefix != null && key.startsWith(customHeaderPrefix)) {
                String value = request.getHeader(key);
                requestHeaders.add(key, value);
            }
        }
        if(params == null) {
            params = request.getParameterMap();
        }

        HttpEntity<String> requestEntity = new HttpEntity<String>(null, requestHeaders);
        return restTemplate.exchange(url, method, requestEntity, String.class, params).getBody();
    }
}
