package com.example.consumer.controller;

import com.jdcloud.jmsf.dubbo.sample.DemoService;
import com.jdcloud.jmsf.dubbo.sample.HelloService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
public class DemoController {
    @DubboReference(version = "${demo.service.version}", url = "${demo.service.url}")
    private DemoService demoService;
    @DubboReference(version = "${demo.service.version}", url = "${demo.service.url}")
    private HelloService helloService;

    @GetMapping("/call")
    public String call () {
        Random random = new Random();
        int turn = random.nextInt(10);
        if (turn % 2 == 0) {
            return demoService.sayHello("test");
        }
        return helloService.sayHello("test");
    }
}
