package com.docker.rand.randinfo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

@SpringBootApplication
public class RandInfoApplication {

    public static void main(String[] args) {
        SpringApplication.run(RandInfoApplication.class, args);
    }
}


@RestController
class MainController {

    @GetMapping("rand")
    @CrossOrigin
    Map<String, Object> info() {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("random", new Random().nextInt());
        map.put("ip", getIp());
        map.put("host", getHost());
        return map;
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
        return "exception is thrown";
    }
}
