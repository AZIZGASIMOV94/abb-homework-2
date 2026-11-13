package io.github.azizgasimov94.dockerdemo.greeting;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    static final String GREETING = "Hello from greeting-service";

    @GetMapping("/greeting")
    public String greeting() {
        return GREETING;
    }
}
