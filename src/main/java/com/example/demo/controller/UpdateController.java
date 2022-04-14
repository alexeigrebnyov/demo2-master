package com.example.demo.controller;

import com.example.demo.utils.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/update")
public class UpdateController {

    String code;
    @Autowired
    public UpdateController() {
    }

//    @GetMapping("/time")
//    public ResponseEntity<List<Long>> getTime() {
//        List<Long> times =new ArrayList<>();
//        times.add(System.currentTimeMillis());
//        return ResponseEntity.ok(times);
//    }
    @GetMapping("/code")
    public ResponseEntity<List<String>> getCode() {
        List<String> codes =new ArrayList<>();
        Test.getScan();
        codes.add(code);
        return ResponseEntity.ok(codes);

    }
    @GetMapping("/{code}")
    public void setCode1(@PathVariable("code") String code) {
        setCode(code);
    }



    public void setCode(String code) {
        this.code = code;
    }
}
