package com.example.demo.controller;

import com.example.demo.model.Analysis;
import com.example.demo.service.UptakeService;
import com.example.demo.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.curs.xylophone.XML2SpreadSheetError;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/immulite")
public class ImmuliteController {
    String code;
    List<Analysis> analysisList = new ArrayList<>();
    List<Analysis> checkAnalysisList = new ArrayList<>();
    List<Analysis> dist = new ArrayList<>();
    //            analysisList.stream().distinct().collect(Collectors.toList());
    UptakeService uptakeService;
    UptakeController uptakeController;
    RestTemplate template = new RestTemplate();
    @Autowired
    public ImmuliteController(UptakeService uptakeService, UptakeController uptakeController) {
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
//    @GetMapping("/setserver/{code}")
//    public void setCode1(@PathVariable("code") String code) {
//        Constants.setSERVERENDPOINT(code);
//    }

    @GetMapping(value = "/gormonu/{code}/{done}")
    public List<Analysis> getGormonu(@PathVariable("code") String code, @PathVariable String done) throws SQLException {
        analysisList.addAll(getAnalysisList(code, done, "VIEW_GRPPRM.GRPPRM_ID in (351) and"));

        dist=analysisList.stream().distinct().collect(Collectors.toList());
        RequestEntity request = RequestEntity
                .get("http://"+Constants.SERVERENDPOINT+"/update/setcode").build();
        ResponseEntity<String> response = template.exchange(request, String.class);
        return dist;
    }
    @GetMapping("/run")
    public void exportResult() throws InterruptedException {
        RequestEntity request = RequestEntity
                .get("http://"+Constants.SERVERENDPOINT+"/update/runGorm").build();
        ResponseEntity<String> response = template.exchange(request, String.class);
//        Thread.sleep(15000);
    }

    @GetMapping("/chekGormonu")
    public List<Analysis> checkGormonu() throws SQLException {
        for (Analysis a: dist) {
            checkAnalysisList.addAll(getAnalysisList(a.getCode(), "1", "VIEW_GRPPRM.GRPPRM_ID in (351) and"));

        }
        return checkAnalysisList.stream().distinct().collect(Collectors.toList());
    }

    @GetMapping("/writeGormonu")
    public void writeGormonu() throws XML2SpreadSheetError, IOException {
        uptakeController.writeImm(analysisList);
    }

    public void setAnalysisList(List<Analysis> analysisList) {
        this.analysisList = analysisList;
    }


    public void setCode(String code) {
        this.code = code;
    }

    public List<Analysis> getAnalysisList(String code, String done, String GPRM) throws SQLException {
        List<Analysis> data = new ArrayList<>();
        Analysis analysis = new Analysis();
        for (Object[] o: uptakeService.getDataGormonu(code,  done, GPRM)) {


            try {
                analysis.setEmc(o[0].toString());
                analysis.setFio(o[1].toString());



                if (o[5].toString().equals("Эстрадиол(Е2)")) {
                    analysis.setHiv("1");
                    if (o[2] != null) {
                        analysis.setResultHiv(o[2].toString());
                    }
                } else {
                    if (analysis.getHiv() == null) {
                        analysis.setHiv("");
                    }
                }
                if (o[5].toString().equals("ФСГ")) {
                    analysis.setHbsAg("1");
                    if (o[2] != null) {
                        analysis.setResultHbsAg(o[2].toString());
                    }
                } else {
                    if (analysis.getHbsAg() == null) {
                        analysis.setHbsAg("");
                    }
                }
                if (o[5].toString().equals("ТТГ-тиреотропный гормон")) {
                    analysis.setAtHCV("1");
                    if (o[2] != null) {
                        analysis.setResultatHCV(o[2].toString());
                    }
                } else { if (analysis.getAtHCV() == null) {
                    analysis.setAtHCV("");
                }
                }
                if (o[5].toString().equals("АТ к ТПО")) {
                    analysis.setSyphIFA("1");
                    if (o[2] != null) {
                        analysis.setResultSyphIfa(o[2].toString());
                    }
                } else { if (analysis.getSyphIFA() == null)
                    analysis.setSyphIFA("");
                }

                if (o[5].toString().equals("Т4 тироксин")) {
                    analysis.setRubM("1");
                    if (o[2] != null) {
                        analysis.setResultRubM(o[2].toString());
                    }
                } else { if (analysis.getRubM() == null)
                    analysis.setRubM("");
                }

                if (o[5].toString().equals("Пролактин")) {
                    analysis.setRubG("1");
                    if (o[2] != null) {
                        analysis.setResultRubG(o[2].toString());
                    }
                } else { if (analysis.getRubG() == null)
                    analysis.setRubG("");
                }

                if (o[5].toString().equals("ЛГ")) {
                    analysis.setClamG("1");
                    if (o[2] != null) {
                        analysis.setResultClamG(o[2].toString());
                    }
                } else { if (analysis.getClamG() == null)
                    analysis.setClamG("");
                }

                if (o[5].toString().equals("Прогестерон")) {
                    analysis.setSyphMRP("1");
                    if (o[2] != null) {
                        analysis.setResultMRP(o[2].toString());
                    }
                } else { if (analysis.getSyphMRP() == null)
                    analysis.setSyphMRP("");
                }

                if (o[5].toString().equals("Тестостерон общий")) {
                    analysis.setClamA("1");
                    if (o[2] != null) {
                        analysis.setResultClamA(o[2].toString());
                    }
                } else { if (analysis.getClamA() == null)
                    analysis.setClamA("");
                }

                if (o[5].toString().equals("СССГ")) {
                    analysis.setSbg("1");
                    if (o[2] != null) {
                        analysis.setResultHSP60(o[2].toString());
                    }
                } else { if (analysis.getSbg() == null)
                    analysis.setSbg("");
                }

                if (o[5].toString().equals("ДГА-S")) {
                    analysis.setDga("1");
                    if (o[2] != null) {
                        analysis.setResultDGA(o[2].toString());
                    }
                } else { if (analysis.getDga() == null)
                    analysis.setDga("");
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
//                if (analysis.getHiv().equals("1")||analysis.getHbsAg().equals("1")||analysis.getAtHCV().equals("1")||analysis.getSyphIFA().equals("1")) {

//                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (analysis.getCode()!=null) {
            data.add(analysis);
        }
        return data;
    }
    @PostMapping("/delete")
    public void delete(@RequestBody String delete) {
        analysisList.removeIf(e ->e.getEmc().equals(delete));
    }
    @GetMapping("/clear")
    public void clearList() {
        analysisList.clear();
    }

}
