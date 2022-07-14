package com.example.demo.controller;

import com.example.demo.model.CommonAnalysis;
import com.example.demo.service.UptakeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/common")
public class CommonController {

    UptakeService uptakeService;
    @Autowired
    public CommonController(UptakeService uptakeService) {
        this.uptakeService=uptakeService;
    }

    @GetMapping("/")
    public void hello(){
        System.out.println("Hi!");
    }

    @GetMapping("/{code}/{done}/{GPRM}")
    public CommonAnalysis getAssignment(@PathVariable("code") String bio_code, @PathVariable("done") String done, @PathVariable("GPRM") String GPRM) throws SQLException {
        HashMap<String,String> assignment = new HashMap<>();
         new CommonAnalysis();
       List<Object[]> objects= uptakeService.getDataGormonu(bio_code, done, GPRM);
        CommonAnalysis cma =  objects.stream()
                .findFirst()
                .map(o-> new CommonAnalysis(o[0].toString(), o[1].toString(), o[6].toString(), o[4].toString(),  o[3].toString(),assignment))
                .orElseThrow();

        objects.stream()
                        .map(o->o[5]!=null?assignment.put(o[5].toString(), "1"):"null")
                                .collect(Collectors.toList());
        cma.setAssignments(assignment);
       System.out.println(cma);
        return cma;

    }




}
