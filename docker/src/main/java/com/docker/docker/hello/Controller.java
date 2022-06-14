package com.docker.docker.hello;
//SERVER
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @RequestMapping("/")//8081
    public String home() {
        return "hello from app called docker";
    }
}
