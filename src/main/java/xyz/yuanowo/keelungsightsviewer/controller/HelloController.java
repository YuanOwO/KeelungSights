package xyz.yuanowo.keelungsightsviewer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HelloController {
    @GetMapping("/")
    public Map<String, String> index() {
        Map<String, String> resp = new HashMap<>();
        resp.put("message", "Hello, World!");
        return resp;
    }
}
