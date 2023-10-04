package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
//@RequestMapping("/")

public class AllController {




    @GetMapping(value = {"/", "/hello"})
    public String getAl(ModelMap modelMap){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        modelMap.addAttribute("user", userDetails);
            return  "hello";
    }
    @GetMapping("/charts")
    public String getCharts(){
//        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        modelMap.addAttribute("user", userDetails);
        return  "control_charts";
    }
    @GetMapping("/hbscharts")
    public String getHBsCharts(){
        return  "hbs_ag_charts";
    }

    @GetMapping("/hivabcharts")
    public String getHIVAbCharts(){
        return  "hiv_ab_charts";
    }

    @GetMapping("/hivagcharts")
    public String getHIVAgCharts(){
        return  "hiv_ag_charts";
    }

    @GetMapping("/syphcharts")
    public String getSyphCharts(){
        return  "syph_charts";
    }

}
