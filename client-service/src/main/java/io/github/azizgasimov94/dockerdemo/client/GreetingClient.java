package io.github.azizgasimov94.dockerdemo.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/** Calls greeting-service. Its base URL comes from configuration, so it works in Docker and locally. */
@Component
public class GreetingClient {

    private final RestTemplate restTemplate;
    private final String baseUrl;

    public GreetingClient(RestTemplate restTemplate,
                          @Value("${greeting-service.url}") String baseUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
    }

    public String fetchGreeting() {
        return restTemplate.getForObject(baseUrl + "/greeting", String.class);
    }
}
