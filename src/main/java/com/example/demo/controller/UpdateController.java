package com.example.demo.controller;

//import com.example.demo.utils.Test;
import com.example.demo.model.Analysis;
import com.example.demo.service.UptakeService;
import com.example.demo.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/update")
public class UpdateController {

    String code;
    List<Analysis> analysisList = new ArrayList<>();
    UptakeService uptakeService;
    RestTemplate template = new RestTemplate();
    @Autowired
    public UpdateController(UptakeService uptakeService) {
        this.uptakeService=uptakeService;
    }

//    @GetMapping("/time")
//    public ResponseEntity<List<Long>> getTime() {
//        List<Long> times =new ArrayList<>();
//        times.add(System.currentTimeMillis());
//        return ResponseEntity.ok(times);
//    }
    @GetMapping("/code")
    public ResponseEntity<List<String>> getCode() {
        RequestEntity request = RequestEntity
                .get("http://"+ Constants.SERVERENDPOINT+" /update/code").build();
        ResponseEntity<String> response = new RestTemplate().exchange(request, String.class);
        List<String> codes =new ArrayList<>();
//        Test.getScan();
        codes.add(response.getBody());
        return ResponseEntity.ok(codes);

    }
    @GetMapping("/setserver/{code}")
    public void setCode1(@PathVariable("code") String code) {
        Constants.setSERVERENDPOINT(code);
    }

    @GetMapping(value = "/gormonu/{code}/{grp}/{done}")
    public List<Analysis> getGormonu(@PathVariable("code") String code, @PathVariable String grp, @PathVariable String done) throws SQLException {
        List<String> data = new ArrayList<>();
        for (Object[] o: uptakeService.getDataGormonu(code, Integer.parseInt(grp), done)) {
            Analysis analysis = new Analysis();

            try {
                analysis.setEmc(o[0].toString());
                analysis.setFio(o[1].toString());



                if (o[5].toString().equals("AMG")) {
                    analysis.setHiv("1");
                    if (o[2] != null) {
                        analysis.setResultHiv(o[2].toString());
                    }
                } else {
                    if (analysis.getHiv() == null)
                    analysis.setHiv("");
                }
                if (o[5].toString().equals("17-OH")) {
                    analysis.setHbsAg("1");
                    if (o[2] != null) {
                        analysis.setResultHbsAg(o[2].toString());
                    }
                } else {
                    if (analysis.getHbsAg() == null)
                    analysis.setHbsAg("");
                }
                if (o[5].toString().equals("CA-125")) {
                    analysis.setAtHCV("1");
                    if (o[2] != null) {
                        analysis.setResultatHCV(o[2].toString());
                    }
                } else { if (analysis.getAtHCV() == null)
                        analysis.setAtHCV("");
                    }


                analysis.setLabel(o[3].toString());
                analysis.setDate_bio(o[4].toString());
                analysis.setCode(o[6].toString());
                analysis.setSex(o[7].toString());
                analysis.setAdres(o[8].toString());
                analysisList.add(analysis);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        RequestEntity request = RequestEntity
                .get("http://"+Constants.SERVERENDPOINT+"/update/setcode").build();
        ResponseEntity<String> response = template.exchange(request, String.class);
        System.out.println(analysisList.stream().distinct().collect(Collectors.toList()));
        return analysisList.stream().distinct().collect(Collectors.toList());
    }





    public void setCode(String code) {
        this.code = code;
    }
}
