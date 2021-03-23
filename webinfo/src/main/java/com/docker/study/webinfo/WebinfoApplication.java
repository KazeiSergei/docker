package com.docker.study.webinfo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@SpringBootApplication
public class WebinfoApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(WebinfoApplication.class, args);
    }

}

@RestController
class MainController {

    UUID uuid = UUID.randomUUID();

    @Value("${rand.host}")
    private String host;

    @Value("${rand.port}")
    private String port;


    @GetMapping("/info")
    Map<String, Object> info() {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("uuid", uuid);
        map.put("time", LocalDateTime.now());
        map.put("random", getRand());
        map.put("ip", getIp());
        map.put("host", getHost());
        return map;
    }

    private String getRand() {
        try {
            HttpClient httpClient = HttpClient.newBuilder()
                    .connectTimeout(Duration.ofMillis(300))
                    .build();
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create("http://" + host + ":" + port + "/rand"))
                    .build();

            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return "Exception";
    }

    private String getHost() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return "unknown host";
    }

    private String getIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return "exception thrown";
    }

}
