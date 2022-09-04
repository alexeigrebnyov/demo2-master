package com.example.demo.controller;

import com.example.demo.model.Analysis;
import com.example.demo.model.Params;
import com.example.demo.model.User;
import com.example.demo.service.UptakeService;
import com.example.demo.utils.Constants;
//import com.example.demo.utils.Test;
import com.example.demo.utils.XLConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import ru.curs.xylophone.XML2SpreadSheetError;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
//@RequestMapping("/")

public class UptakeController {

    UptakeService uptakeService;
//    UpdateController updateController;
//    XLConstructor xlConstructor;
//    BCScaner bcScaner;
    List<Analysis> uptakeByCode = new ArrayList<>();
    List<Analysis> uptakeProof = new ArrayList<>();
    List<Analysis> uptakeTORCH = new ArrayList<>();
//    List<Analysis> distinct = uptakeByCode
//            .stream().distinct().collect(Collectors.toList());
//    List<Analysis> chekByCode = new ArrayList<>();
//    List<String> chek = new ArrayList<>();

    String code;
    String code1;
    String redirect = "redirect:/code";
    String redirmanual = "redirect:/divrefresh";
    String redirProof = "redirect:/proofCode";
    boolean proofActive;
    boolean torch;
    RestTemplate template = new RestTemplate();

    public String getUserName() {
        String s = null;
        try {
             UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
            s = userDetails.getUsername();
        } catch (Exception ex) {}
        return s;
    }
    @Autowired
    public UptakeController(UptakeService uptakeService
//     BCScaner bcScaner
    ) {
        this.uptakeService = uptakeService;
//        this.updateController = updateController;
//        this.bcScaner = bcScaner;
    }


//    @GetMapping(value = {"/"})
//    public String getAl(ModelMap modelMap){
//           modelMap.addAttribute("contingents", contService.getAll());
//            return  "contingent";
//    }
//    @PostMapping(value = "/scan")
//    public String getScan(@RequestParam(value = "code") String code, @RequestParam(value = "GRPPRM") Integer gprm) {
//        setCode(code);
//        setProofActive(false);
//        if (gprm==1) {
//            setTorch(true);
//        } else {
//            setTorch(false);
//        }
//        System.out.println(torch);
//        return "redirect:/divrefresh";
//    }

    @PostMapping(value = "/scanProof")
    public String getScanProof(@RequestParam(value = "codeProof") String codeProof) {
        setCodeProof(codeProof);
//        System.out.println(code1);
        setProofActive(true);
        setTorch(false);
        return "redirect:/divrefresh";
    }

    @GetMapping(value = "/divrefresh")
    public String redirect(ModelMap modelMap) {
//        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        modelMap.addAttribute("data1", code);
        modelMap.addAttribute("redir1", redirmanual);


            modelMap.addAttribute("selected2", uptakeTORCH
                    .stream()
                    .distinct()
                    .collect(Collectors.toList()));
            if (!proofActive) {
                modelMap.addAttribute("selected1", uptakeByCode
                        .stream()
                        .distinct()
                        .collect(Collectors.toList()));
            } else {
                modelMap.addAttribute("selected1", uptakeProof
                        .stream()
                        .distinct()
                        .collect(Collectors.toList()));
            }
        modelMap.addAttribute("codeProof", code1);
        modelMap.addAttribute("user", getUserName());
        modelMap.addAttribute("proofActive", proofActive);
        modelMap.addAttribute("torch", torch);
        modelMap.addAttribute("time", System.currentTimeMillis());


//        Test.data.forEach(e -> uptakeByCode.add(contService
//                .getByCode(Integer.parseInt(e.trim()))));
//        System.out.println(proofActive);


        return "divRefresh";

    }
//    @GetMapping(value = "/code/codeInt")
//    public String updateInput(ModelMap modelMap) {
//        modelMap.addAttribute("data1", code);
//        return "byCode";
//
//    }
    @GetMapping(value = "/code")
    public String updateUser1(ModelMap model)  {
//        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

//    Test.getScan();
        List<Analysis> dist = uptakeByCode
                .stream()
                .distinct()
                .collect(Collectors.toList());


        model.addAttribute("data1", code);
        model.addAttribute("selected", dist);
        model.addAttribute("redir", redirect);
        model.addAttribute("user", getUserName());
        return "byCode";
    }

    @GetMapping(value = "/codeTorch")
    public String uptakeTORCH(ModelMap model)  {
        List<Analysis> dist = uptakeTORCH
                .stream()
                .distinct()
                .collect(Collectors.toList());


        model.addAttribute("data1", code);
        model.addAttribute("selected", dist);
        model.addAttribute("redir", redirect);
        model.addAttribute("user", getUserName());
        return "byCodeTorch";
    }

    @GetMapping(value = "/proofCode")
    public String getProofs(ModelMap model)  {
//        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

//        Test.getScan();
        List<Analysis> dist = uptakeProof
                .stream()
                .distinct()
                .collect(Collectors.toList());

//        model.addAttribute("data1", code);
        model.addAttribute("proofs", dist);
//        model.addAttribute("redir", redirProof);
        model.addAttribute("user", getUserName());
//        System.out.println(dist);
        return "proofCode";
    }
    @GetMapping("/gormonu")
    public String getGorm() {
        return "byCodeGormonu";
    }

    @GetMapping("/immulite")
    public String getImmulite() {
        return "byCodeImmulite";
    }

    @GetMapping("/commonPost")
    public String getCommon(ModelMap map) {
        String user = getUserName();
        String server;
        switch (user) {
            case "Alex": server="localhost:8099";
            break;
            case "Rif": server="localhost:8084";
            break;
            case "ifa": server=Constants.SERVERENDPOINT;
                break;
            default: server="localhost:8099";
        }
        map.addAttribute("user", user);
        map.addAttribute("server", server);
        return "commonPostKazan.html";
    }
    @GetMapping("/reportPage")
    public String getReportPage(ModelMap map) {
        map.addAttribute("user", getUserName());
        return "reportMaker";
    }
    @GetMapping(value = "/chek")
    public String getCheked (ModelMap model) throws SQLException {
//        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        chekAnalysis(uptakeByCode, 350);
        List<Analysis> dist = uptakeByCode
                .stream()
                .distinct()
                .collect(Collectors.toList());

        model.addAttribute("chekedAnalysis", dist);
        model.addAttribute("user", getUserName());
        return "chek";

    }

    @GetMapping(value = "/chekTorch")
    public String getChekedTorch (ModelMap model) throws SQLException {
//        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        chekAnalysis(uptakeTORCH, 1);
        List<Analysis> dist = uptakeTORCH
                .stream()
                .distinct()
                .collect(Collectors.toList());

        model.addAttribute("chekedAnalysis", dist);
        model.addAttribute("user", getUserName());
        return "chekTorch";

    }
    @GetMapping(value = "/admin")
    public String adminPage(ModelMap modelMap) {
//        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<User> users = uptakeService.getAllUsers();
        modelMap.addAttribute("users", users);
        modelMap.addAttribute("user", getUserName());

        return "admin";
    }
    @PostMapping(value = "/saveuser")
    public String saveUser(@RequestParam(value = "name") String name,
                           @RequestParam(value = "pass") String password,
                           @RequestParam(value = "role") String role) {
        uptakeService.saveUser(name, password, role);
        return "redirect:/admin";
    }
    @PostMapping(value = "/delete")
    public String deleteUser(@RequestParam(value = "id") Long id){
        uptakeService.removeUserById(id);
        return "redirect:/admin";
    }
    @PostMapping(value = "/refresh")
    public String refresh() throws SQLException {

        if (uptakeByCode.size()!=0) {uptakeByCode.clear();}
        if (uptakeProof.size()!=0) {uptakeProof.clear();}
//        if (chekByCode.size()!=0) {chekByCode.clear();}
        return "redirect:/code";
    }

    @PostMapping(value = "/refreshTorch")
    public String refreshTorch() throws SQLException {

        if (uptakeTORCH.size()!=0) {uptakeTORCH.clear();}
//        if (chekByCode.size()!=0) {chekByCode.clear();}
        return "redirect:/codeTorch";
    }
    @PostMapping(value = "/write")
    public String write(@RequestParam(value = "redir") String redir) throws IOException, XML2SpreadSheetError {
        List<Analysis> dist = uptakeByCode
                .stream()
                .distinct()
                .collect(Collectors.toList());

//        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));

        deleteAllFilesFolder("//192.168.7.100/ifa/МРП");
        try(
                FileOutputStream fos=new FileOutputStream("//192.168.7.100/ifa/МРП/HIVList.txt", true);
                FileOutputStream fosB=new FileOutputStream("//192.168.7.100/ifa/МРП/HBsList.txt", true);
                FileOutputStream fosC=new FileOutputStream("//192.168.7.100/ifa/МРП/HCVList.txt", true);
                FileOutputStream fosSyf=new FileOutputStream("//192.168.7.100/ifa/МРП/SyfList.txt", true);
                FileOutputStream fosMRP=new FileOutputStream("//192.168.7.100/ifa/МРП/MRPList" +
//                        ""+date+"" +
                        ".txt", true);
                ) {
            for (Analysis data:
                    dist) {
                if (data.getHiv().equals("1")) {
                    String item =getUserName()+" "+ data.getEmc()+ System.lineSeparator();
                    fos.write(item.getBytes());
//                    System.out.println(data.getHiv());
                }
                if (data.getHbsAg().equals("1")) {
                    String item =getUserName()+" "+  data.getEmc()+ System.lineSeparator();

                    fosB.write(item.getBytes());
                }
                if (data.getAtHCV().equals("1")) {
                    String item =getUserName()+" "+  data.getEmc()+ System.lineSeparator();
                    fosC.write(item.getBytes());
                }
                if (data.getSyphIFA().equals("1")) {
                    String item =getUserName()+" "+  data.getEmc()+ System.lineSeparator();
                    fosSyf.write(item.getBytes());
                }
                if (data.getSyphMRP().equals("1")) {
                    String item =getUserName()+" "+  data.getEmc()+" "+ "-" + System.lineSeparator();
                    fosMRP.write(item.getBytes());
                }

            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        XLConstructor.writeXML(dist);
        XLConstructor.xml2XLSX("//192.168.7.100/ifa/ifaList/report.xlsx");
        RequestEntity request = RequestEntity
                .get("http://"+ Constants.SERVERENDPOINT+"/update/openVich").build();
        ResponseEntity<String> response = template.exchange(request, String.class);

        return redir;


    }
    @PostMapping(value = "/writeProof")
    public String writeProof(@RequestParam (value = "redir") String redirect,
                             @RequestParam ("count") String count) throws IOException, XML2SpreadSheetError {
        List<Analysis> dist = uptakeProof
                .stream()
                .distinct()
                .collect(Collectors.toList());

//        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));

        deleteAllFilesFolder("//192.168.7.100/ifa/подтверждающие");
        try(
                FileOutputStream fosB=new FileOutputStream("//192.168.7.100/ifa/подтверждающие/HBsProofList.txt", true);
                FileOutputStream fosC=new FileOutputStream("//192.168.7.100/ifa/подтверждающие/HCVProofList.txt", true);
                FileOutputStream fos=new FileOutputStream("//192.168.7.100/ifa/подтверждающие/HIVProofList.txt", true);
                FileOutputStream fosSyf=new FileOutputStream("//192.168.7.100/ifa/подтверждающие/SyfProofList.txt", true);
        ) {


                for (Analysis data :
                        dist) {
                    String dataitem = getUserName() + " " + data.getEmc();
                    byte[] datas = (dataitem+ System.lineSeparator()).getBytes(StandardCharsets.UTF_8);
                        if (data.getHiv().equals("1")) {
//                    String item =getUserName()+" "+ data.getEmc()+ System.lineSeparator();
                            fos.write(datas);
                            fos.write(datas);
//                    System.out.println(data.getHiv());
                        }
                        if (data.getHbsAg().equals("1")) {
                            String[] countmass = count.split("-");
                            for (int i = 0; i < countmass.length; i++) {

                                String HBsitem = dataitem+" "+countmass[i]+" "+System.lineSeparator();
//                                HBsitem+=HBsitem;
                                byte[] dataHBs = HBsitem.getBytes(StandardCharsets.UTF_8);
//                    String item =userName+" "+  data.getEmc()+ System.lineSeparator();

                                fosB.write(dataHBs);
                                fosB.write(dataHBs);
                            }
                        }
                        if (data.getAtHCV().equals("1")) {
//                    String item =userName+" "+  data.getEmc()+ System.lineSeparator();
                            fosC.write(datas);
                            fosC.write(datas);
                        }

                        if (data.getSyphIFA().equals("1")) {
//                    String item =getUserName()+" "+  data.getEmc()+ System.lineSeparator();
                            fosSyf.write(datas);
                            fosSyf.write(datas);
                        }
                }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        XLConstructor.writeXML(dist);
        XLConstructor.xml2XLSX("//192.168.7.100/ifa/ifaList/ProofReport.xlsx");
        RequestEntity request = RequestEntity
                .get("http://"+Constants.SERVERENDPOINT+"/update/openProof").build();
        ResponseEntity<String> response = template.exchange(request, String.class);

        return redirect;


    }

    @PostMapping(value = "/writeTorch")
    public String writeTorch(@RequestParam (value = "redir") String redirect) throws IOException, XML2SpreadSheetError {
        List<Analysis> dist = uptakeTORCH
                .stream()
                .distinct()
                .collect(Collectors.toList());

//        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));

        deleteAllFilesFolder("//192.168.7.100/ifa/torch");
        try(
                FileOutputStream fosB=new FileOutputStream("//192.168.7.100/ifa/torch/RubGList.txt", true);
                FileOutputStream fosC=new FileOutputStream("//192.168.7.100/ifa/torch/RubMList.txt", true);
                FileOutputStream fos=new FileOutputStream("//192.168.7.100/ifa/torch/ChlamGList.txt", true);
                FileOutputStream fosSyf=new FileOutputStream("//192.168.7.100/ifa/torch/ChlamAList.txt", true);
                FileOutputStream fosHSP=new FileOutputStream("//192.168.7.100/ifa/torch/HSP60List.txt", true);
        ) {


            for (Analysis data:
                    dist) {
                if (data.getRubG().equals("1")) {
                    String item =getUserName()+" "+ data.getEmc()+ System.lineSeparator();
                    fosB.write(item.getBytes());
//                    System.out.println(data.getHiv());
                }
                if (data.getRubM().equals("1")) {
                    String item =getUserName()+" "+  data.getEmc()+ System.lineSeparator();

                    fosC.write(item.getBytes());
                }
                if (data.getClamG().equals("1")) {
                    String item =getUserName()+" "+  data.getEmc()+ System.lineSeparator();
                    fos.write(item.getBytes());
                }
                if (data.getClamA().equals("1")) {
                    String item =getUserName()+" "+  data.getEmc()+ System.lineSeparator();
                    fosSyf.write(item.getBytes());
                }
                if (data.getHSP60().equals("1")) {
                    String item =getUserName()+" "+  data.getEmc()+" "+  System.lineSeparator();
                    fosHSP.write(item.getBytes());
                }

            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        XLConstructor.writeTORCHXML(dist);
        XLConstructor.xml2XLSX("//192.168.7.100/ifa/ifaList/TorchReport.xlsx");
        RequestEntity request = RequestEntity
                .get("http://"+Constants.SERVERENDPOINT+"/update/openTorch").build();
        ResponseEntity<String> response = template.exchange(request, String.class);

        return redirect;


    }

    public void writeGormonu(List<Analysis> analysisList) throws IOException, XML2SpreadSheetError {
        List<Analysis> dist = analysisList
                .stream()
                .distinct()
                .collect(Collectors.toList());

//        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));

        deleteAllFilesFolder("//192.168.7.100/ifa/Гормоны");
        try(
                FileOutputStream fosB=new FileOutputStream("//192.168.7.100/ifa/Гормоны/AMGList.txt", true);
                FileOutputStream fosC=new FileOutputStream("//192.168.7.100/ifa/Гормоны/17List.txt", true);
                FileOutputStream fos=new FileOutputStream("//192.168.7.100/ifa/Гормоны/CAList.txt", true);
//                FileOutputStream fosSyf=new FileOutputStream("//192.168.7.100/ifa/Гормоны/E2List.txt", true);
        ) {


            for (Analysis data:
                    dist) {
                if (data.getHiv().equals("1")) {
                    String item =getUserName()+" "+ data.getEmc()+ System.lineSeparator();
                    fosB.write(item.getBytes());
//                    System.out.println(data.getHiv());
                }
                if (data.getHbsAg().equals("1")) {
                    String item =getUserName()+" "+  data.getEmc()+ System.lineSeparator();

                    fosC.write(item.getBytes());
                }
                if (data.getAtHCV().equals("1")) {
                    String item =getUserName()+" "+  data.getEmc()+ System.lineSeparator();
                    fos.write(item.getBytes());
                }
//                if (data.getSyphIFA().equals("1")) {
//                    String item =getUserName()+" "+  data.getEmc()+ System.lineSeparator();
//                    fosSyf.write(item.getBytes());
//                }

            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        XLConstructor.writeGormonuXML(dist);
        XLConstructor.xml2XLSX("//192.168.7.100/ifa/ifaList/GormonuReport.xlsx");
        RequestEntity request = RequestEntity
                .get("http://"+Constants.SERVERENDPOINT+"/update/openGormonu").build();
        ResponseEntity<String> response = template.exchange(request, String.class);



    }

    public void writeImm(List<Analysis> analysisList) throws IOException, XML2SpreadSheetError {
        List<Analysis> dist = analysisList
                .stream()
                .distinct()
                .collect(Collectors.toList());

//        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));

        deleteAllFilesFolder("//192.168.7.100/ifa/Имммулайт");
        try(
                FileOutputStream fosB=new FileOutputStream("//192.168.7.100/ifa/Имммулайт/E2List.txt", true);
                FileOutputStream fosC=new FileOutputStream("//192.168.7.100/ifa/Имммулайт/FSGList.txt", true);
                FileOutputStream fos=new FileOutputStream("//192.168.7.100/ifa/Имммулайт/TTGList.txt", true);
                FileOutputStream fosSyf=new FileOutputStream("//192.168.7.100/ifa/Имммулайт/ATTPOList.txt", true);
                FileOutputStream fosT4=new FileOutputStream("//192.168.7.100/ifa/Имммулайт/T4List.txt", true);
                FileOutputStream fosPRL=new FileOutputStream("//192.168.7.100/ifa/Имммулайт/PRLList.txt", true);
                FileOutputStream fosLG=new FileOutputStream("//192.168.7.100/ifa/Имммулайт/LGList.txt", true);
                FileOutputStream fosPRG=new FileOutputStream("//192.168.7.100/ifa/Имммулайт/PRGList.txt", true);
                FileOutputStream fosTes=new FileOutputStream("//192.168.7.100/ifa/Имммулайт/TESTList.txt", true);
                FileOutputStream fosSBG=new FileOutputStream("//192.168.7.100/ifa/Имммулайт/SBGList.txt", true);
                FileOutputStream fosDGA=new FileOutputStream("//192.168.7.100/ifa/Имммулайт/DGAList.txt", true);
        ) {


            for (Analysis data:
                    dist) {
                String item =getUserName()+" "+ data.getEmc()+ System.lineSeparator();
                if (data.getHiv().equals("1")) {
                    fosB.write(item.getBytes());
//                    System.out.println(data.getHiv());
                }
                if (data.getHbsAg().equals("1")) {
                    fosC.write(item.getBytes());
                }
                if (data.getAtHCV().equals("1")) {
                    fos.write(item.getBytes());
                }
                if (data.getSyphIFA().equals("1")) {
                    fosSyf.write(item.getBytes());
                }

                if (data.getRubM().equals("1")) {
                    fosT4.write(item.getBytes());
                }

                if (data.getRubG().equals("1")) {
                    fosPRL.write(item.getBytes());
                }
                if (data.getClamG().equals("1")) {
                    fosLG.write(item.getBytes());
                }
                if (data.getSyphMRP().equals("1")) {
                    fosPRG.write(item.getBytes());
                }
                if (data.getClamA().equals("1")) {
                    fosTes.write(item.getBytes());
                }
                if (data.getSbg().equals("1")) {
                    fosSBG.write(item.getBytes());
                }
                if (data.getDga().equals("1")) {
                    fosDGA.write(item.getBytes());
                }


            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        XLConstructor.writeImmXML(dist);
        XLConstructor.xml2XLSX("//192.168.7.100/ifa/ifaList/ImmReport.xlsx");
        RequestEntity request = RequestEntity
                .get("http://"+Constants.SERVERENDPOINT+"/update/openImm").build();
        ResponseEntity<String> response = template.exchange(request, String.class);



    }
    @PostMapping(value = "/code")
    public String updateUser(ModelMap model,
//                             @RequestParam(value = "codeInt") String codeInt,
//                             @RequestParam(value ="redir") String redir,
//                             @RequestParam(value="proofCode")String proof,
//                             @RequestParam(value = "done") String done,
//                             @RequestParam(value = "GRPPRM") Integer GRPPRM
                             @RequestBody Params params
                             ) throws SQLException {
//        chek.add(codeInt);
//        System.out.println(proof);

            Analysis analysis = new Analysis();
            List<Object[]> data = new ArrayList<>();
            if (params.getProofCode().equals("")) {
                data = uptakeService.getData(params.getDone(), params.getCodeInt(), Integer.parseInt(params.getGrpprm()));
                setProofActive(false);
            } else { data = uptakeService.getData(params.getDone(), params.getProofCode(), Integer.parseInt(params.getGrpprm()));
                        setProofActive(true);}
//        try {
//            Object[] data1 = data
//                    .stream()
//                    .findAny().get();
//
//
//        } catch (Exception ignored) {}

            if (data.size() != 0) {
                for (Object[] data1 : data) {
//            System.out.println(data1[0] + " " + data1[1] + " " + data1[2] + " " + data1[3] + " " + data1[4] + " "
//                    + data1[5] + data1[6] + " " + data1[7] + " " + data1[8] + " " + data1[9]
//                    + "" + data1[10]
//            );
                    try {
                        analysis.setEmc(data1[0].toString());
                        analysis.setFio(data1[1].toString());


                        analysis.setMain_org_id(data1[5].toString());
                        analysis.setLabel(data1[6].toString());
                        analysis.setPatdirect_id(data1[7].toString());
                        analysis.setDate_bio(data1[8].toString());
                        analysis.setCode(data1[10].toString());
                        analysis.setSex(data1[11].toString());


                        if (data1[9].toString().equals("А/т к ВИЧ 1,2 +А/г")) {
                            analysis.setHiv("1");
                            if (data1[4] != null) {
                                analysis.setResultHiv(data1[4].toString());
                            }
                        } else {
                            if (analysis.getHiv() == null)
                                analysis.setHiv("");
                        }
                        if (data1[9].toString().equals("HBsAg")) {
                            analysis.setHbsAg("1");
                            if (data1[4] != null) {
                                analysis.setResultHbsAg(data1[4].toString());
                            }
                        } else {
                            if (analysis.getHbsAg() == null)
                                analysis.setHbsAg("");
                        }
                        if (data1[9].toString().equals("Ат .к. HCV")) {
                            analysis.setAtHCV("1");
                            if (data1[4] != null) {
                                analysis.setResultatHCV(data1[4].toString());
                            }
                        } else {
                            if (analysis.getAtHCV() == null)
                                analysis.setAtHCV("");
                        }
                        if (data1[9].toString().equals("Сифилис МРП")) {
                            analysis.setSyphMRP("1");
                            if (data1[4] != null) {
                                analysis.setResultMRP(data1[4].toString());
                            }
                        } else {
                            if (analysis.getSyphMRP() == null)
                                analysis.setSyphMRP("");
                        }
                        if (data1[9].toString().equals("Syphilis ИФА")) {
                            analysis.setSyphIFA("1");
                            if (data1[4] != null) {
                                analysis.setResultSyphIfa(data1[4].toString());
                            }
                        } else {
                            if (analysis.getSyphIFA() == null)
                                analysis.setSyphIFA("");
                        }
                        if (data1[9].toString().equals("Rub-G")) {
                            analysis.setRubG("1");
                            if (data1[4] != null) {
                                analysis.setResultRubG(data1[4].toString());
                            }
                        } else {
                            if (analysis.getRubG() == null)
                                analysis.setRubG("");
                        }

                        if (data1[9].toString().equals("Rub- M")) {
                            analysis.setRubM("1");
                            if (data1[4] != null) {
                                analysis.setResultRubM(data1[4].toString());
                            }
                        } else {
                            if (analysis.getRubM() == null)
                                analysis.setRubM("");
                        }
                        if (data1[9].toString().equals("Clam-A")) {
                            analysis.setClamA("1");
                            if (data1[4] != null) {
                                analysis.setResultClamA(data1[4].toString());
                            }
                        } else {
                            if (analysis.getClamA() == null)
                                analysis.setClamA("");
                        }

                        if (data1[9].toString().equals("Chlam-G")) {
                            analysis.setClamG("1");
                            if (data1[4] != null) {
                                analysis.setResultClamG(data1[4].toString());
                            }
                        } else {
                            if (analysis.getClamG() == null)
                                analysis.setClamG("");
                        }

                        if (data1[9].toString().equals("cHSP60-Ig G(белок тепл.шока и")) {
                            analysis.setHSP60("1");
                            if (data1[4] != null) {
                                analysis.setResultHSP60(data1[4].toString());
                            }
                        } else {
                            if (analysis.getHSP60() == null)
                                analysis.setHSP60("");
                        }


                    } catch (Exception ignored) {
                    }

                    try { int i = 0;
                        if (data1[2] != null) {
                                analysis.setKontengent(data1[2].toString());}
                        else {
                            if (analysis.getKontengent()==null)analysis.setKontengent("");}
                    } catch (Exception ex) {

                    }

                    try {
                        analysis.setMotconsu_resp_id(data1[3].toString());

                    } catch (Exception ex) {
                    }
                    try {
                        if (data1[12] != null) {
                            analysis.setAdres(data1[12].toString());
                        } else {analysis.setAdres("");}

                    } catch (Exception ex) {
                    }

//            try {
//                if (data1[9].toString().equals("А/т к ВИЧ 1,2 +А/г"))
//                analysis.setResultHiv(data1[4].toString());
//
//            } catch (Exception ex) {}
//
                }
//                uptakeByCode.removeIf(n -> (n.getEmc().equals(analysis.getEmc())&&n.getCode().equals(analysis.getCode())));
                if (params.getGrpprm().equals("350")) {
                    setTorch(false);
                    if (params.getProofCode().equals("")) {
                        uptakeByCode.add(analysis);

                    } else {
                        uptakeProof.add(analysis);
                    }
                } else {
                    uptakeTORCH.add(analysis);
                    setTorch(true);
                }
//                chekByCode.add(analysis);
//                chekByCode.add(analysis);
//        chek.add(analysis.getCode());

            }
//            System.out.println("prof: "+proof);
//            System.out.println("profActive: "+proofActive);
//            System.out.println("Analysis: "+analysis);
//            System.out.println("uptakeProof:");
//            uptakeProof.forEach(System.out::println);


//           uptakeByCode.add(contService.getByCode(Integer.parseInt(codeInt.trim())));
//        model.addAttribute("selected", uptakeByCode);
//        System.out.println(proof);
//        System.out.println(codeInt);
       code = null;
       code1 = null;
//       updateController.setCode(null);
        RequestEntity request = RequestEntity
                .get("http://"+Constants.SERVERENDPOINT+"/update/setcode").build();
        ResponseEntity<String> response = template.exchange(request, String.class);


        return params.getRedir();
    }

//    @PostMapping(value = "/gormonucode")
//    public String getGormonu(@RequestBody Params params) throws SQLException {
//        uptakeService.getDataGormonu(params.getCodeInt(), Integer.parseInt(params.getGrpprm()), done);
//        return  params.getRedir();
//    }
    @PostMapping(value = "/exportresult")
    public String exportResult(@RequestParam("redir") String redir) {
//        File expres = new File("C:/Users/Grebnev_A/IFA.jar");
//       System.out.println( DesktopApi.open(expres));
        RequestEntity request = RequestEntity
                .get("http://"+Constants.SERVERENDPOINT+"/update/run").build();
        ResponseEntity<String> response = template.exchange(request, String.class);
        return redir;
    }

    @PostMapping(value = "/exporttorch")
    public String exportTorch(@RequestParam("redir") String redir) {
//        File expres = new File("C:/Users/Grebnev_A/IFA.jar");
//       System.out.println( DesktopApi.open(expres));
        RequestEntity request = RequestEntity
                .get("http://"+Constants.SERVERENDPOINT+"/update/runTORCH").build();
        ResponseEntity<String> response = template.exchange(request, String.class);
        return redir;
    }

    //    @PostMapping(value = "/chek")
    public String chekAnalysis (List<Analysis> chekAnalysis, Integer GRPPRM) throws SQLException {
//        List<Analysis> dist = uptakeByCode
//                .stream()
//                .distinct()
//                .collect(Collectors.toList());
//        chek.add("3856");
        List<Object[]> data = new ArrayList<>();
        for (Analysis upt:chekAnalysis) {

        data = uptakeService.chek(" ",upt.getCode(), GRPPRM);
//		System.out.println(data.size());
//        try {
//            Object[] data1 = data
//                    .stream()
//                    .findAny().get();
//
//
//        } catch (Exception ignored) {}


        for (Object[] data1 : data) {

//            System.out.println(data1[0] + " " + data1[1] + " " + data1[2] + " " + data1[3] + " " + data1[4] + " "
//                    + data1[5] + data1[6] + " " + data1[7] + " " + data1[8] + " " + data1[9]
//                    + "" + data1[10]
//            );
            try {
                if (data1[1].toString().equals("А/т к ВИЧ 1,2 +А/г")) {
//                    upt.setHiv("1");
                    if (data1[0] != null ) {upt.setResultHiv(data1[0].toString());}
                    else {upt.setResultHiv("");}
                }
                if (data1[1].toString().equals("HBsAg")){
//                    upt.setHbsAg("1");
                    if (data1[0] != null) {upt.setResultHbsAg(data1[0].toString());}
                    else {upt.setResultHbsAg("");}
                }

                if (data1[1].toString().equals("Ат .к. HCV")){
//                    upt.setAtHCV("1");
                    if (data1[0] != null) {upt.setResultatHCV(data1[0].toString());}
                    else {upt.setResultatHCV("");}

                }

                if (data1[1].toString().equals("Сифилис МРП")) {
//                    upt.setSyphMRP("1");
                    if (data1[0] != null) {upt.setResultMRP(data1[0].toString());}
                    else {upt.setResultMRP("");}
                }
                if (data1[1].toString().equals("Syphilis ИФА")) {
//                    upt.setSyphIFA("1");
                    if (data1[0] != null) {upt.setResultSyphIfa(data1[0].toString());}
                    else {upt.setResultSyphIfa("");}
                }
                if (data1[1].toString().equals("Rub-G")) {
//                    upt.setHiv("1");
                    if (data1[0] != null ) {upt.setResultRubG(data1[0].toString());}
                    else {upt.setResultRubG("");}
                }
                if (data1[1].toString().equals("Rub- M")) {
//                    upt.setHiv("1");
                    if (data1[0] != null ) {upt.setResultRubM(data1[0].toString());}
                    else {upt.setResultRubM("");}
                }
                if (data1[1].toString().equals("cHSP60-Ig G(белок тепл.шока и")) {
//                    upt.setHiv("1");
                    if (data1[0] != null ) {upt.setResultHSP60(data1[0].toString());}
                    else {upt.setResultHSP60("");}
                }
                if (data1[1].toString().equals("Clam-A")) {
//                    upt.setHiv("1");
                    if (data1[0] != null ) {upt.setResultClamA(data1[0].toString());}
                    else {upt.setResultClamA("");}
                }
                if (data1[1].toString().equals("Chlam-G")) {
//                    upt.setHiv("1");
                    if (data1[0] != null ) {upt.setResultClamG(data1[0].toString());}
                    else {upt.setResultClamG("");}
                }

//                System.out.println(Arrays.toString(data1));

            } catch (Exception ignored) {}

//                analysis.setResultHiv(data1[4].toString());
//
//            } catch (Exception ex) {}

//
        }
//        System.out.println(analysis);
//        System.out.println(data.size());

//            chekByCode.add(upt);
//            System.out.println(upt.chekHiv());
//            System.out.println(upt.chekatHCV());
//            System.out.println(upt.chekHbs());
//            System.out.println(upt.chekSyphIfa());
//            System.out.println(upt.chek());

        }

//           uptakeByCode.add(contService.getByCode(Integer.parseInt(codeInt.trim())));
//        model.addAttribute("selected", uptakeByCode);
        return "redirect:/chek";
    }
    @PostMapping(value = "/deleteanalysis")
    public String delete(@RequestParam(value = "delete") String delete, @RequestParam(value = "deleteProof") String deleteProof) {
        String redir="redirect:/code";

        switch (deleteProof) {
            case "vich" : {
                uptakeByCode.removeIf(e ->e.getEmc().equals(delete));
                break;
            }
            case "torch" : {
                uptakeTORCH.removeIf(e ->e.getEmc().equals(delete));
                redir = "redirect:/codeTorch";
                break;
            }
            case "proof": {
                uptakeProof.removeIf(e ->e.getEmc().equals(delete));
                redir="redirect:/proofCode";
                break;
            }
        }
//        if (delete != 0) {
//            uptakeByCode.remove(delete);
//        } else {
//            uptakeProof.remove(deleteProof);
//            redir=redirProof;
//        }
        return redir;
    }

    @PostMapping(value = "/deletemanual")
    public String deletemanual(@RequestParam(value = "delete") String delete, @RequestParam(value = "deleteProof") String deleteProof) {

        switch (deleteProof) {
            case "vich" : {
                uptakeByCode.removeIf(e ->e.getEmc().equals(delete));
                setTorch(false);
                break;
            }
            case "torch" : {
                uptakeTORCH.removeIf(e ->e.getEmc().equals(delete));
                setTorch(true);
                break;
            }
            case "proof": {
                uptakeProof.removeIf(e ->e.getEmc().equals(delete));
                setTorch(false);
                break;
            }
        }
        return "redirect:/divrefresh";
    }


    public void setCode(String code) {
        this.code = code;
    }

    public void setCodeProof(String codeProof) {
        this.code1 = codeProof;
    }

    public void setProofActive(boolean proofActive) {
        this.proofActive = proofActive;
    }

    public void setTorch(boolean torch) {
        this.torch = torch;
    }

    public List<Analysis> getUptakeByCode() {
        return uptakeByCode
                .stream()
                .distinct()
                .collect(Collectors.toList());
    }

    public static void deleteAllFilesFolder(String path) {
        for (File myFile : Objects.requireNonNull(new File(path).listFiles()))
            if (myFile.isFile()) myFile.delete();
    }
}
