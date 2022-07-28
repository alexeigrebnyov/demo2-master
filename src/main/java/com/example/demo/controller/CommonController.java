package com.example.demo.controller;

import com.example.demo.model.Assignment;
import com.example.demo.model.CommonAnalysis;
import com.example.demo.service.UptakeService;
import com.example.demo.utils.Constants;
import com.example.demo.utils.XLConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import ru.curs.xylophone.XML2SpreadSheetError;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/common")
public class CommonController {

    UptakeService uptakeService;
    RestTemplate template = new RestTemplate();


    @Autowired
    public CommonController(UptakeService uptakeService) {
        this.uptakeService=uptakeService;
    }

    @GetMapping("/")
    public void hello(){
        System.out.println("Hi!");
    }

    @GetMapping("/{code}/{done}/{GPRM}")
    public List<CommonAnalysis> getAssignment(@PathVariable("code") String bio_code, @PathVariable("done") String done, @PathVariable("GPRM") String GPRM) throws SQLException {
        Set<Assignment> assignment = new HashSet<>();
        List<CommonAnalysis> cmaList = new ArrayList<>();

         new CommonAnalysis();
       List<Object[]> objects= uptakeService.getDataGormonu(bio_code, done, GPRM);
        RequestEntity request = RequestEntity
                .get("http://"+ Constants.SERVERENDPOINT+"/update/setcode").build();
        ResponseEntity<String> response = template.exchange(request, String.class);
//        Set<Assignment> finalAssignment = assignment;

        CommonAnalysis cma =  objects.stream()
                .findFirst()
                .map(o-> new CommonAnalysis(o[0].toString(), o[1].toString(), o[6].toString(), o[4].toString(),  o[3].toString(),assignment))
                .orElseThrow();

        objects.stream()
                .distinct()
                .map(o-> assignment.add(new Assignment(o[5].toString(), o[4].toString(), o[0].toString(), cma)))
                .collect(Collectors.toList());

        cma.setAssignments(assignment);
        uptakeService.saveCommon(cma);
        cmaList.add(cma);



//       System.out.println(cma);
        return cmaList;

    }

    @GetMapping("/getAll")
    public List<CommonAnalysis> getAll() {
      return   uptakeService.getCommon();
    }

//    @Transactional
    @GetMapping("/delete/{id}")
    public void delete(@PathVariable("id") Long id) {
        uptakeService.deleteCommon(id);
    }

    @GetMapping("/deleteAll")
    public void deleteAll() {
        for (CommonAnalysis ca: uptakeService.getCommon()) {
            uptakeService.deleteCommon(ca.getId());
        }
    }

    @GetMapping("/getDocs")
    public List<Document> getDocument() throws XML2SpreadSheetError, IOException {
        Set<String> indets = new HashSet<>(uptakeService.getBCAsignments("351"));
        Set<String> torch = new HashSet<>(uptakeService.getBCAsignments("1"));
        Set<String> vich = new HashSet<>(uptakeService.getBCAsignments("350"));
        Set<String> bc = new HashSet<>(uptakeService.getBCAsignments( "1793, 337, 340"));
        Set<String> ifaGorm = new HashSet<>(uptakeService.getBCAsignments( "1836"));
//        System.out.println(bc);

//        indets.add("AMG");
//        indets.add("CA-125");
//        indets.add("RTH");
//        indets.add("FBC");
//        indets.add("PAA");
//        indets.add("17-OH");
//        indets.add("LH");
//        indets.add("FSH");
//
//
//        torch.add("E2");
//        torch.add("Rub-G");
//        torch.add("Chlam-G");
//        torch.add("Clam-A");
//        torch.add("Rub- M");
//        torch.add("cHSP60-Ig G(белок тепл.шока и");




        DocumentBuilderFactory dbf = null;
        DocumentBuilder db  = null;
//        Document docHiv = null;
//        Document docTorch = null;
//        Document docGorm = null;

        List<Document> documents = new ArrayList<>();
        List<CommonAnalysis> commonAnalyses = uptakeService.getCommon().stream().distinct().collect(Collectors.toList());
//        Set<CommonAnalysis> analysisSet = new HashSet<>(commonAnalyses);
//        analysisSet.forEach(System.out::println);
        List<Set<String>> commonList = List.of(indets,torch, vich, bc, ifaGorm);
        List<String> paths = List.of(
                "//192.168.7.100/ifa/ifaList/reportCommon.xlsx"
                ,"//192.168.7.100/ifa/ifaList/reportCommonTorch.xlsx"
                ,"//192.168.7.100/ifa/ifaList/reportCommonVich.xlsx"
                ,"//192.168.7.100/ifa/ifaList/reportCommonBc.xlsx"
                ,"//192.168.7.100/ifa/ifaList/reportCommonIfaGorm.xlsx"
        );


        dbf = DocumentBuilderFactory.newInstance();
        try {
            db  = dbf.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
//        docHiv = db.newDocument();
//        docTorch = db.newDocument();
//        docGorm = db.newDocument();
        for (int i = 0; i < commonList.size(); i++) {

            XLConstructor.writeDocument(uptakeService.getDoc(commonAnalyses, commonList.get(i), db.newDocument()));
            XLConstructor.xml2XLSX(paths.get(i));
        }

//        documents.add(uptakeService.getDoc(commonAnalyses, indets, docGorm));
//        for (Document d:documents) {
//            XLConstructor.writeDocument(d);
//            XLConstructor.xml2XLSX("//192.168.7.100/ifa/ifaList/reportCommon.xlsx");
//        }


        return documents;
    }





}
