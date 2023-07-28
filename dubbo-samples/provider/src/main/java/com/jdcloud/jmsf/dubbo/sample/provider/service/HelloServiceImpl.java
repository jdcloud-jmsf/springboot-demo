package com.jdcloud.jmsf.dubbo.sample.provider.service;

import com.jdcloud.jmsf.dubbo.sample.HelloService;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService(version = "${demo.service.version}")
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String name) {
        return "hello service: " +  name;
    }
}
