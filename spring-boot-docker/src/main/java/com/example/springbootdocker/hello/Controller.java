package com.example.springbootdocker.hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
//CLIENT
@RestController
@RequiredArgsConstructor
public class Controller {

    @Autowired
    RestTemplate rest;

    @RequestMapping("/")//8080
    public String get() {
        return "hello";
    }

    @RequestMapping("/get")//8080
    public String getString() {
        String result = rest.getForObject(String.format("http://docker:8081/"), String.class);
        System.out.println("got: {}"+ result);
        return result;
    }
}
