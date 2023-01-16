package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/chart")


public class ChartController {
    @GetMapping("/getChart")
    public Map<String, Integer> datas(){
        Map<String, Integer> m = new HashMap<>();
        m.put("01.01.2023", 4);
        m.put("02.01.2023", 5);
        m.put("03.01.2023", 6);
        m.put("04.01.2023", 3);
        return m;
    }
}
