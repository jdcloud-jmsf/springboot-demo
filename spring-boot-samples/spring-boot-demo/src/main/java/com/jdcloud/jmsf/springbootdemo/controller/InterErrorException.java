package com.jdcloud.jmsf.springbootdemo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE, reason = "not health")
public class InterErrorException extends RuntimeException {

}
