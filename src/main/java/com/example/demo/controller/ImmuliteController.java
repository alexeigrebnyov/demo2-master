package com.example.demo.controller;

import com.example.demo.model.Analysis;
import com.example.demo.model.ReqDataTransfer;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/immulite")
public class ImmuliteController implements UniversalController {
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
//    @GetMapping("/setserver/{code}")
//    public void setCode1(@PathVariable("code") String code) {
//        Constants.setSERVERENDPOINT(code);
//    }

    @Override
    @GetMapping(value = "/gormonu/{code}/{done}")
    public List<Analysis> getGormonu(@PathVariable("code") String code, @PathVariable String done) throws SQLException {
        analysisList.addAll(getAnalysisList(code, done, "VIEW_GRPPRM.GRPPRM_ID in (351) and"));

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
                .get("http://"+Constants.SERVERENDPOINT+"/update/runGorm").build();
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
            checkAnalysisList.addAll(getAnalysisList(a.getCode(), "1", "VIEW_GRPPRM.GRPPRM_ID in (351) and"));

        }
        return checkAnalysisList.stream().distinct().collect(Collectors.toList());
    }

    @Override
    @GetMapping("/writeGormonu")
    public void writeGormonu() throws XML2SpreadSheetError, IOException {
        uptakeController.writeImm(analysisList);
    }

    @Override
    public void setAnalysisList(List<Analysis> analysisList) {
        this.analysisList = analysisList;
    }


    @Override
    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public List<Analysis> getAnalysisList(String code, String done, String GPRM) throws SQLException {
//        long _st= System.currentTimeMillis();
        List<Analysis> data = new ArrayList<>();
        Analysis analysis = new Analysis("","","","","","","",
                "","","","","","","","","",
                "","","","","","","","","",
                "","","","","","","","","", "","",
                "","","","","","","","","",
                "","","","","");
        for (String[] o: uptakeService.getDataGormonu(code,  done, GPRM)) {


            try {
                analysis.setEmc(o[0]);
                analysis.setFio(o[1]);



                if (o[5].equals("1512"))
//                if (o.getLab_methods_id()==1512)
                {
//                    analysis.setHiv("1");
                    if (o[2] != null) {
                        analysis.setResultHiv(o[2]);
                        analysis.setHiv("2");
                    } else {
                        analysis.setHiv("1");
                        analysis.setE2Patdirect_id(o[9]);

                    }
                }
//                else {
//                    if (analysis.getHiv() == null) {
//                        analysis.setHiv("");
//                    }
//                }
                if (o[5].equals("1511"))
//                if (o.getLab_methods_id()==1511)
                {
//                    analysis.setHbsAg("1");
                    if (o[2] != null) {
                        analysis.setResultHbsAg(o[2]);
                        analysis.setHbsAg("2");
                    } else {
                        analysis.setHbsAg("1");
                        analysis.setFsgPatdirect_id(o[9]);
                    }
                }
//                else {
//                    if (analysis.getHbsAg() == null) {
//                        analysis.setHbsAg("");
//                    }
//                }
                if (o[5].equals("1504"))
//                if (o.getLab_methods_id()==1504)
                {
//                    analysis.setAtHCV("1");
                    if (o[2] != null) {
                        analysis.setResultatHCV(o[2]);
                        analysis.setAtHCV("2");
                    } else {
                        analysis.setAtHCV("1");
                        analysis.setTtgPatdirect_id(o[9]);
                    }
                }
//                else { if (analysis.getAtHCV() == null) {
//                    analysis.setAtHCV("");
//                }
//                }
                if (o[5].equals("1510"))
//                if (o.getLab_methods_id()==1510)
                {
//                    analysis.setSyphIFA("1");
                    if (o[2] != null) {
                        analysis.setResultSyphIfa(o[2]);
                        analysis.setSyphIFA("2");
                    } else {
                        analysis.setSyphIFA("1");
                        analysis.setTpoPatdirect_id(o[9]);
                    }
                }
//                else { if (analysis.getSyphIFA() == null)
//                    analysis.setSyphIFA("");
//                }

                if (o[5].toString().equals("1505"))
//                if (o.getLab_methods_id()==1505)
                {
//                    analysis.setRubM("1");
                    if (o[2] != null) {
                        analysis.setResultRubM(o[2]);
                        analysis.setRubM("2");
                    } else {
                        analysis.setRubM("1");
                        analysis.setT4Patdirect_id(o[9]);
                    }
                }
//                else { if (analysis.getRubM() == null)
//                    analysis.setRubM("");
//                }

                if (o[5].equals("1500")) {
//                    analysis.setRubG("1");
                    if (o[2] != null) {
                        analysis.setResultRubG(o[2]);
                        analysis.setRubG("2");
                    } else {
                        analysis.setRubG("1");
                        analysis.setPrlPatdirect_id(o[9]);
                    }
                }
//                else { if (analysis.getRubG() == null)
//                    analysis.setRubG("");
//                }

                if (o[5].equals("1507")) {
//                    analysis.setClamG("1");
                    if (o[2] != null) {
                        analysis.setResultClamG(o[2]);
                        analysis.setClamG("2");
                    } else {
                        analysis.setClamG("1");
                        analysis.setLgPatdirect_id(o[9]);
                    }
                }
//                else { if (analysis.getClamG() == null)
//                    analysis.setClamG("");
//                }

//                if (o[5].toString().equals("Прогестерон")) {
////                    analysis.setSyphMRP("1");
//                    if (o[2] != null) {
//                        analysis.setResultMRP(o[2].toString());
//                        analysis.setSyphMRP("2");
//                    } else {
//                        analysis.setSyphMRP("1");
//                    }
//                } else { if (analysis.getSyphMRP() == null)
//                    analysis.setSyphMRP("");
//                }

                if (o[5].equals("1506")) {
//                    analysis.setClamA("1");
                    if (o[2] != null) {
                        analysis.setResultClamA(o[2]);
                        analysis.setClamA("2");
                    } else {
                        analysis.setClamA("1");
                        analysis.setTesPatdirect_id(o[9]);
                    }
                }
//                else { if (analysis.getClamA() == null)
//                    analysis.setClamA("");
//                }

                if (o[5].equals("1518")) {
//                    analysis.setSbg("1");
                    if (o[2] != null) {
                        analysis.setResultHSP60(o[2]);
                        analysis.setSbg("2");

                    } else {
                        analysis.setSbg("1");
                        analysis.setSbgPatdirect_id(o[9]);
                    }
                }
//                else { if (analysis.getSbg() == null)
//                    analysis.setSbg("");
//                }

                if (o[5].equals("1516")) {
//                    analysis.setDga("1");
                    if (o[2] != null) {
                        analysis.setResultDGA(o[2]);
                        analysis.setDga("2");
                    } else {
                        analysis.setDga("1");
                        analysis.setDgaPatdirect_id(o[9]);
                    }
                }
//                else { if (analysis.getDga() == null)
//                    analysis.setDga("");
//                }

                if (o[5].equals("409")) {
//                    analysis.setDga("1");
                    if (o[2] != null) {
                        analysis.setResult_psa_total(o[2]);
                        analysis.setPsa_total("2");
                    } else {
                        analysis.setPsa_total("1");
                        analysis.setPsa_total_Patdirect_id(o[9]);
                    }
                }
//                else { if (analysis.getDga() == null)
//                    analysis.setDga("");
//                }
                if (o[5].equals("724")) {
//                    analysis.setDga("1");
                    if (o[2] != null) {
                        analysis.setResult_psa_free(o[2]);
                        analysis.setPsa_free("2");
                    } else {
                        analysis.setPsa_free("1");
                        analysis.setPsa_free_Patdirect_id(o[9]);
                    }
                }
//                else { if (analysis.getDga() == null)
//                    analysis.setDga("");
//                }


                analysis.setLabel(o[3]);
                analysis.setDate_bio(o[4]);
                analysis.setCode(o[6]);
                analysis.setSex(o[7]);
                try {
                    analysis.setAdres(o[8]);
                } catch (NullPointerException e) {
                    analysis.setAdres("");
                }
                try {
                    analysis.setPatdirect_id(o[9]);
                } catch (NullPointerException e) {
                    analysis.setPatdirect_id("");
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
//        long _nd= System.currentTimeMillis();
//        System.out.println(data);
//        System.out.println(_nd-_st);
//        System.out.println(data);
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

    @GetMapping("/create")
    public void create() {
        List<Integer[]> createData = new ArrayList<>();
        for (Analysis a: checkAnalysisList) {
            if (!a.chekHiv()) {
                createData.add(new Integer[]{7428, 17228, Integer.valueOf(a.getE2Patdirect_id())});
            }
            if (!a.chekatHCV()) {
                createData.add(new Integer[]{13007, 17251, Integer.valueOf(a.getTtgPatdirect_id())});
            }

            if (createData.size()>0) {
                String emc = a.getEmc();
                String medicine = uptakeController.getUserName();
              Integer motconsu = uptakeService.createMotconsu(emc, medicine);
                for (Integer[] intrger:createData ) {
                    uptakeService.createPatdirec(emc, medicine, String.valueOf(motconsu),intrger[0], intrger[1], intrger[2] );

                }
            }

        }
    }

}
