package org.mcallydevelops;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationTest {

    RestTemplate restTemplate;

    static Thread thread;

    @BeforeAll
    static void beforeAll() {
        thread = new Thread(() -> {
            try {
                new Server(Context.createDefaultContext()).run();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        });
        thread.start();
    }

    @BeforeEach
    void setup() {
        restTemplate = new RestTemplate();
    }

    @Test
    void getIndexTest() throws Exception {
        String result = restTemplate.getForObject("http://localhost:8080", String.class);
        assertEquals("Hello World!", result);
    }

    @Test
    void getNotFoundPage() throws Exception {
        assertThrows(HttpClientErrorException.NotFound.class, () -> restTemplate.getForObject("http://localhost:8080/nonindex", String.class));
    }

    @Test
    void postIndexTest() throws Exception {
        Map<String, Object> request = new HashMap<>() {{
            put("data", "data");
        }};
       assertThrows(HttpServerErrorException.InternalServerError.class, () -> restTemplate.postForObject("http://localhost:8080", request, String.class));
    }

}