package com.example.demo.controller;

import com.example.demo.model.Analysis;
import com.example.demo.model.User;
import com.example.demo.service.UptakeService;
import com.example.demo.utils.DesktopApi;
import com.example.demo.utils.Test;
import com.example.demo.utils.XLConstructor;
import org.hibernate.type.LocalDateTimeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.curs.xylophone.XML2SpreadSheetError;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
//@RequestMapping("/")

public class UptakeController {

    UptakeService uptakeService;
//    XLConstructor xlConstructor;
//    BCScaner bcScaner;
    List<Analysis> uptakeByCode = new ArrayList<>();
    List<Analysis> uptakeProof = new ArrayList<>();
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
//        this.bcScaner = bcScaner;
    }


//    @GetMapping(value = {"/"})
//    public String getAl(ModelMap modelMap){
//           modelMap.addAttribute("contingents", contService.getAll());
//            return  "contingent";
//    }
    @PostMapping(value = "/scan")
    public String getScan(@RequestParam(value = "code") String code) {
        setCode(code);
        setProofActive(false);
//        System.out.println(code);
        return "redirect:/divrefresh";
    }

    @PostMapping(value = "/scanProof")
    public String getScanProof(@RequestParam(value = "codeProof") String codeProof) {
        setCodeProof(codeProof);
//        System.out.println(code1);
        setProofActive(true);
        return "redirect:/divrefresh";
    }

    @GetMapping(value = "/divrefresh")
    public String redirect(ModelMap modelMap) {
//        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Analysis> dist = uptakeByCode
                .stream()
                .distinct()
                .collect(Collectors.toList());
        List<Analysis> distProof = uptakeProof
                .stream()
                .distinct()
                .collect(Collectors.toList());
        modelMap.addAttribute("data1", code);
        modelMap.addAttribute("redir1", redirmanual);
        if (!proofActive) {
            modelMap.addAttribute("selected1", dist);
        } else {
            modelMap.addAttribute("selected1", distProof);
        }
        modelMap.addAttribute("codeProof", code1);
        modelMap.addAttribute("user", getUserName());
        modelMap.addAttribute("proofActive", proofActive);


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

    Test.getScan();
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

    @GetMapping(value = "/proofCode")
    public String getProofs(ModelMap model)  {
//        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Test.getScan();
        List<Analysis> dist = uptakeProof
                .stream()
                .distinct()
                .collect(Collectors.toList());

        model.addAttribute("data1", code);
        model.addAttribute("proofs", dist);
        model.addAttribute("redir", redirProof);
        model.addAttribute("user", getUserName());
//        System.out.println(dist);
        return "proofCode";
    }
    @GetMapping(value = "/chek")
    public String getCheked (ModelMap model) throws SQLException {
//        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        chekAnalysis();
        List<Analysis> dist = uptakeByCode
                .stream()
                .distinct()
                .collect(Collectors.toList());

        model.addAttribute("chekedAnalysis", dist);
        model.addAttribute("user", getUserName());
        return "chek";

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
//        if (chekByCode.size()!=0) {chekByCode.clear();}
        return "redirect:/code";
    }
    @PostMapping(value = "/write")
    public String write(@RequestParam(value = "redir") String redir) throws IOException, XML2SpreadSheetError {
//        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        String userName = userDetails.getUsername();
        List<Analysis> dist = uptakeByCode
                .stream()
                .distinct()
                .collect(Collectors.toList());

//        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));

        deleteAllFilesFolder("");
        try(
                FileOutputStream fos=new FileOutputStream("", true);
                FileOutputStream fosB=new FileOutputStream("", true);
                FileOutputStream fosC=new FileOutputStream("", true);
                FileOutputStream fosSyf=new FileOutputStream("", true);
                FileOutputStream fosMRP=new FileOutputStream("" +
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
        XLConstructor.xml2XLSX();

        return redir;


    }
    @PostMapping(value = "/writeProof")
    public String writeProof(@RequestParam (value = "redir") String redirect,
                             @RequestParam ("count") String count) throws IOException, XML2SpreadSheetError {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName = getUserName();
        List<Analysis> dist = uptakeProof
                .stream()
                .distinct()
                .collect(Collectors.toList());

//        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));

        deleteAllFilesFolder("//192.168.7.100/ifa/подтверждающие");
        try(
                FileOutputStream fosB=new FileOutputStream("", true);
                FileOutputStream fosC=new FileOutputStream("", true);
                FileOutputStream fos=new FileOutputStream("", true);
                FileOutputStream fosSyf=new FileOutputStream("", true);
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
        XLConstructor.xml2XLSX();

        return redirect;


    }

    @PostMapping(value = "/code")
    public String updateUser(ModelMap model, @RequestParam(value = "codeInt") String codeInt,
                             @RequestParam(value ="redir") String redir,
                             @RequestParam(value="proofCode")String proof,
                             @RequestParam(value = "done") String done
    ) throws SQLException {
//        chek.add(codeInt);
//        System.out.println(proof);

            Analysis analysis = new Analysis();
            List<Object[]> data = new ArrayList<>();
            if (proof.equals("")) {
                data = uptakeService.getData(done, codeInt);
            } else { data = uptakeService.getData(done, proof);}
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
                if (proof.equals("")) {
                    uptakeByCode.add(analysis);
                } else {uptakeProof.add(analysis);}
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
        return redir;
    }
    @PostMapping(value = "/exportresult")
    public String exportResult() {
        File expres = new File("C:/Users/IFA.jar");
        DesktopApi.open(expres);
        return "redirect:/chek";
    }
//    @PostMapping(value = "/chek")
    public String chekAnalysis () throws SQLException {
//        List<Analysis> dist = uptakeByCode
//                .stream()
//                .distinct()
//                .collect(Collectors.toList());
//        chek.add("3856");
        List<Object[]> data = new ArrayList<>();
        for (Analysis upt:uptakeByCode) {

        data = uptakeService.chek(" ",upt.getCode());
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
    public String delete(@RequestParam(value = "delete") int delete, @RequestParam(value = "deleteProof") int deleteProof) {
        String redir = "redirect:/code";
        if (delete != 0) {
            uptakeByCode.remove(delete);
        } else {
            uptakeProof.remove(deleteProof);
            redir=redirProof;
        }
        return redir;
    }

    @PostMapping(value = "/deletemanual")
    public String deletemanual(@RequestParam(value = "delete") String delete, @RequestParam(value = "deleteProof") String deleteProof) {
        if (!delete.equals("")) {
            uptakeByCode.remove(Integer.parseInt(delete));
        } else {
            uptakeProof.remove(Integer.parseInt(deleteProof));

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
