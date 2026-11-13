package io.github.azizgasimov94.dockerdemo.client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = "greeting-service.url=http://greeting.test")
@AutoConfigureMockMvc
class ClientControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private RestTemplate restTemplate;

    private MockRestServiceServer greetingService;

    @BeforeEach
    void setUp() {
        greetingService = MockRestServiceServer.bindTo(restTemplate).build();
    }

    @Test
    void forwardsGreetingFromGreetingService() throws Exception {
        greetingService.expect(requestTo("http://greeting.test/greeting"))
                .andRespond(withSuccess("Hello from greeting-service", MediaType.TEXT_PLAIN));

        mvc.perform(get("/greeting"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello from greeting-service"));

        greetingService.verify();
    }

    @Test
    void returns502WhenGreetingServiceFails() throws Exception {
        greetingService.expect(requestTo("http://greeting.test/greeting"))
                .andRespond(withStatus(HttpStatus.INTERNAL_SERVER_ERROR));

        mvc.perform(get("/greeting"))
                .andExpect(status().isBadGateway());
    }
}
