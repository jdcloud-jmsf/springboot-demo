package com.jdcloud.jmsf.dubbo.sample.provider.service;

import com.jdcloud.jmsf.dubbo.sample.DemoService;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService(version = "${demo.service.version}")
public class DemoServiceImpl implements DemoService {
    @Override
    public String sayHello(String name) {
        return "demo service: " +  name;
    }
}
