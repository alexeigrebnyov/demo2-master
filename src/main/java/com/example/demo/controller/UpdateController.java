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
import ru.curs.xylophone.XML2SpreadSheetError;

import java.io.IOException;
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
    List<Analysis> checkAnalysisList = new ArrayList<>();
    List<Analysis> dist = new ArrayList<>();
//            analysisList.stream().distinct().collect(Collectors.toList());
    UptakeService uptakeService;
    UptakeController uptakeController;
    RestTemplate template = new RestTemplate();
    @Autowired
    public UpdateController(UptakeService uptakeService, UptakeController uptakeController) {
        this.uptakeService=uptakeService;
        this.uptakeController=uptakeController;
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

    @GetMapping(value = "/gormonu/{code}/{done}")
    public List<Analysis> getGormonu(@PathVariable("code") String code, @PathVariable String done) throws SQLException {
        analysisList.addAll(getAnalysisList(code, done, "VIEW_GRPPRM.GRPPRM_ID in (1836, 351) and"));

        dist=analysisList.stream().distinct().collect(Collectors.toList());
        RequestEntity request = RequestEntity
                .get("http://"+Constants.SERVERENDPOINT+"/update/setcode").build();
        ResponseEntity<String> response = template.exchange(request, String.class);
//        System.out.println(dist);
        return dist;
    }

    @GetMapping("/chekGormonu")
    public List<Analysis> checkGormonu() throws SQLException {
        for (Analysis a: dist) {
            checkAnalysisList.addAll(getAnalysisList(a.getCode(), "1", "VIEW_GRPPRM.GRPPRM_ID in (1836, 351) and"));

        }
        return checkAnalysisList.stream().distinct().collect(Collectors.toList());
    }

    @GetMapping("/writeGormonu")
    public void writeGormonu() throws XML2SpreadSheetError, IOException {
        uptakeController.writeGormonu(analysisList);
    }

    public void setAnalysisList(List<Analysis> analysisList) {
        this.analysisList = analysisList;
    }


    public void setCode(String code) {
        this.code = code;
    }

    public List<Analysis> getAnalysisList(String code, String done, String GPRM) throws SQLException {
        List<Analysis> data = new ArrayList<>();
        for (Object[] o: uptakeService.getDataGormonu(code,  done, GPRM)) {
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
                if (o[5].toString().equals("E2")) {
                    analysis.setSyphIFA("1");
                    if (o[2] != null) {
                        analysis.setResultSyphIfa(o[2].toString());
                    }
                } else { if (analysis.getSyphIFA() == null)
                    analysis.setSyphIFA("");
                }


                analysis.setLabel(o[3].toString());
                analysis.setDate_bio(o[4].toString());
                analysis.setCode(o[6].toString());
                analysis.setSex(o[7].toString());
                try {
                    analysis.setAdres(o[8].toString());
                } catch (NullPointerException e) {
                    analysis.setAdres("");
                }

                if (analysis.getHiv().equals("1")||analysis.getHbsAg().equals("1")||analysis.getAtHCV().equals("1")||analysis.getSyphIFA().equals("1")) {
                    data.add(analysis);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return data;
    }
}
