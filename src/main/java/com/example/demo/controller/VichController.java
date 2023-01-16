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
@RequestMapping("/vich")
public class VichController implements UniversalController {

    UptakeService uptakeService;
    UptakeController uptakeController;
    String code;
    List<Analysis> analysisList = new ArrayList<>();
    List<Analysis> checkAnalysisList = new ArrayList<>();
    List<Analysis> dist = new ArrayList<>();
    RestTemplate template = new RestTemplate();

    @Autowired
    public VichController(UptakeService uptakeService, UptakeController uptakeController) {
        this.uptakeService=uptakeService;
        this.uptakeController=uptakeController;
    }

    @Override
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

    @Override
    @GetMapping(value = "/{code}/{done}")
    public List<Analysis> getGormonu(@PathVariable("code") String code, @PathVariable String done) throws SQLException {
        analysisList.addAll(getAnalysisList(code, "and PATDIREC.QUANTITY_DONE=0 ", ""));

        dist=analysisList.stream().distinct().collect(Collectors.toList());
        RequestEntity request = RequestEntity
                .get("http://"+Constants.SERVERENDPOINT+"/update/setcode").build();
        ResponseEntity<String> response = template.exchange(request, String.class);
        return dist;
    }

    @Override
    @GetMapping("/run")
    public void exportResult() throws InterruptedException {
        RequestEntity request = RequestEntity
                .get("http://"+Constants.SERVERENDPOINT+"/update/run").build();
        ResponseEntity<String> response = template.exchange(request, String.class);
//        Thread.sleep(15000);
    }

    @Override
    @GetMapping("/chekGormonu")
    public List<Analysis> checkGormonu() throws SQLException {
        if (checkAnalysisList.size()>0) {
            checkAnalysisList.clear();
        }
        for (Analysis a: dist) {
            checkAnalysisList.addAll(getAnalysisList(a.getCode(), "and PATDIREC.QUANTITY_DONE=1 ", ""));

        }
        return checkAnalysisList.stream().distinct().collect(Collectors.toList());
    }

    @Override
    @GetMapping("/writeGormonu")
    public void writeGormonu() throws XML2SpreadSheetError, IOException {
        uptakeController.writeVich(analysisList);
    }

    @Override
    public void setAnalysisList(List<Analysis> analysisList) {

    }

    @Override
    public void setCode(String code) {

    }

    @Override
    public List<Analysis> getAnalysisList(String code, String done, String GPRM) throws SQLException {
        List<Analysis> data = new ArrayList<>();
        Analysis analysis = new Analysis("","","","","","","",
                "","","","","","","","","",
                "","","","","","","","","",
                "","","","","","","","","");
        for (Object[] o: uptakeService.getData(done, code, 350)) {


            try {
                analysis.setEmc(o[0].toString());
                analysis.setFio(o[1].toString());



                if (o[9].toString().equals("А/т к ВИЧ 1,2 +А/г")) {
                    analysis.setHiv("1");
                    if (o[4] != null) {
                        analysis.setResultHiv(o[4].toString());
                    }
                }
//                else {
//                    if (analysis.getHiv() == null) {
//                        analysis.setHiv("");
//                    }
//                }
                if (o[9].toString().equals("HBsAg")) {
                    analysis.setHbsAg("1");
                    if (o[4] != null) {
                        analysis.setResultHbsAg(o[4].toString());
                    }
                }
//                else {
//                    if (analysis.getHbsAg() == null) {
//                        analysis.setHbsAg("");
//                    }
//                }
                if (o[9].toString().equals("Ат .к. HCV")) {
                    analysis.setAtHCV("1");
                    if (o[4] != null) {
                        analysis.setResultatHCV(o[4].toString());
                    }
                }
//                else { if (analysis.getAtHCV() == null) {
//                    analysis.setAtHCV("");
//                }
//                }
                if (o[9].toString().equals("Syphilis ИФА")) {
                    analysis.setSyphIFA("1");
                    if (o[4] != null) {
                        analysis.setResultSyphIfa(o[4].toString());
                    }
                }
//                else { if (analysis.getSyphIFA() == null)
//                    analysis.setSyphIFA("");
//                }

                if (o[9].toString().equals("Сифилис МРП")) {
                    analysis.setSyphMRP("1");
                    if (o[4] != null) {
                        analysis.setResultMRP(o[4].toString());
                    }
                }
//                else { if (analysis.getSyphMRP() == null)
//                    analysis.setSyphMRP("");
//                }
                if (o[9].toString().equals("Анти-HBs")) {
                    analysis.setRubM("1");
                    if (o[4] != null) {
                        analysis.setResultRubM(o[4].toString());
                    }
                }
//                else { if (analysis.getSyphMRP() == null)
//                    analysis.setSyphMRP("");
//                }




                analysis.setLabel(o[6].toString());
                analysis.setDate_bio(o[8].toString());
                analysis.setCode(o[10].toString());
                analysis.setSex(o[11].toString());
                try {
                    if (o[2] != null) {
                        analysis.setKontengent(o[2].toString());}
                    else {
                        if (analysis.getKontengent()==null)analysis.setKontengent("");}
                } catch (Exception ex) {

                }
                try {
                    analysis.setAdres(o[12].toString());
                } catch (NullPointerException e) {
                    analysis.setAdres("");
                }

//                if (analysis.getHiv().equals("1")||analysis.getHbsAg().equals("1")||analysis.getAtHCV().equals("1")||analysis.getSyphIFA().equals("1")) {

//                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (analysis.getCode()!=null&&!analysis.getCode().equals("")) {
            data.add(analysis);
        }
        return data;
    }

    @Override
    @PostMapping("/delete")
    public void delete(@RequestBody String delete) {
        analysisList.removeIf(e ->e.getEmc().equals(delete));
    }

    @Override
    @GetMapping("/clear")
    public void clearList() {
        analysisList.clear();
        checkAnalysisList.clear();
    }
}
