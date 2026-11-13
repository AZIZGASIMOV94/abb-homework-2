package io.github.azizgasimov94.dockerdemo.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

@RestController
public class ClientController {

    private static final Logger log = LoggerFactory.getLogger(ClientController.class);

    private final GreetingClient greetingClient;

    public ClientController(GreetingClient greetingClient) {
        this.greetingClient = greetingClient;
    }

    /** Fetches the greeting from greeting-service and passes it on. */
    @GetMapping("/greeting")
    public ResponseEntity<String> greeting() {
        try {
            String greeting = greetingClient.fetchGreeting();
            log.info("Received from greeting-service: {}", greeting);
            return ResponseEntity.ok(greeting);
        } catch (RestClientException e) {
            log.warn("greeting-service call failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("greeting-service is unavailable");
        }
    }
}
