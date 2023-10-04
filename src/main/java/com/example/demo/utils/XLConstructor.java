package com.example.demo.utils;

import com.example.demo.controller.UptakeController;
import com.example.demo.model.Analysis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import ru.curs.xylophone.XML2SpreadSheetError;
import ru.curs.xylophone.XML2Spreadsheet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.awt.Desktop;


@Component
public class XLConstructor {

    private static UptakeController uptakeService;

    @Autowired
    public XLConstructor(UptakeController uptakeService) {
        this.uptakeService = uptakeService;
    }

    public static void xml2XLSX(String fileRep) throws IOException, XML2SpreadSheetError {
//        FileInputStream data = new FileInputStream("C:/Repo/Report1.xml");
//        File out = new File("C:/Users/ifa.NNPLUS/report.xlsx");
//        FileOutputStream output = new FileOutputStream(out);
//        File template = new File("C:/Repo/template.xlsx");
//        File desc = new File("C:/Repo/descriptor.xml");
//        XML2Spreadsheet.process(data, desc, template, false, output);
//        DesktopApi.open(out);
        try(FileInputStream data = new FileInputStream("//192.168.7.100/ifa/ifaList/Report1.xml");
            FileOutputStream output = new FileOutputStream(new File(fileRep));) {
//            File out = new File(fileRep);
                File template = new File("//192.168.7.100/ifa/ifaList/template.xlsx");
                File desc = new File("//192.168.7.100/ifa/ifaList/descriptor.xml");
                XML2Spreadsheet.process(data, desc, template, false, output);



//        DesktopApi.open(out);

        }
        catch (Exception e) {
            e.printStackTrace();
        }

//        FileInputStream data = new FileInputStream("//192.168.7.100/ifa/ifaList/Report1.xml");
//        File out = new File(fileRep);
//        FileOutputStream output = new FileOutputStream(out);
//        File template = new File("//192.168.7.100/ifa/ifaList/template.xlsx");
//        File desc = new File("//192.168.7.100/ifa/ifaList/descriptor.xml");
//        XML2Spreadsheet.process(data, desc, template, false, output);
//        DesktopApi.open(out);



//         final String EXPLORER_EXE = "explorer.exe";
//
//        final String command = EXPLORER_EXE + " /SELECT,\"" + out + "\"";
//        Runtime.getRuntime().exec(command);

//       Runtime rt = Runtime.getRuntime();
//      rt.exec(".\\report.xlsx");

    }
    public static void writeDocument(Document document)
            throws TransformerFactoryConfigurationError
    {
        Transformer trf = null;
        DOMSource src = null;
        FileOutputStream fos = null;
        try {
            trf = TransformerFactory.newInstance()
                    .newTransformer();
            src = new DOMSource(document);
            fos = new FileOutputStream("//192.168.7.100/ifa/ifaList/Report1.xml");

            StreamResult result = new StreamResult(fos);
            trf.transform(src, result);
        } catch (TransformerException e) {
            e.printStackTrace(System.out);
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }
    public static void writeXML( List<Analysis> users) throws IOException {
        DocumentBuilderFactory dbf = null;
        DocumentBuilder db  = null;
        Document doc = null;
        try {
            dbf = DocumentBuilderFactory.newInstance();
            db  = dbf.newDocumentBuilder();
            doc = db.newDocument();

            Element e_root   = doc.createElement("report");
//			e_root.setAttribute("lang", "en");
            Element columnId  = doc.createElement("column");
            columnId.setAttribute("data", "Фио");
            Element columnpatientId = doc.createElement("column");
            columnpatientId.setAttribute("data", "Дата забора");
            Element columnuptakeCod = doc.createElement("column");
            columnuptakeCod.setAttribute("data", "Код забора");
            Element sequenser = doc.createElement("column");
            sequenser.setAttribute("data", "№");
            Element columnnumber = doc.createElement("column");
            columnnumber.setAttribute("data", "ВИЧ");
            Element columnresult = doc.createElement("column");
            columnresult.setAttribute("data", "HBsAg");
            Element columnHCV = doc.createElement("column");
            columnHCV.setAttribute("data", "anti-HCV");
            Element columnIfa = doc.createElement("column");
            columnIfa.setAttribute("data", "SyphIFA");
            Element columnMRP = doc.createElement("column");
            columnMRP.setAttribute("data", "MRP");
            Element columnAntiHBs = doc.createElement("column");
            columnAntiHBs.setAttribute("data", "ANTI_HBS");
            Element columnKname = doc.createElement("column");
            columnKname.setAttribute("data", "Контенгент");
            Element columnKont = doc.createElement("column");
            columnKont.setAttribute("data", "Контенгент сумма");
            e_root.appendChild(columnId);
            e_root.appendChild(columnpatientId);
            e_root.appendChild(columnuptakeCod);
            e_root.appendChild(sequenser);
            e_root.appendChild(columnnumber);
            e_root.appendChild(columnresult);
            e_root.appendChild(columnHCV);
            e_root.appendChild(columnIfa);
            e_root.appendChild(columnMRP);
            e_root.appendChild(columnAntiHBs);
            e_root.appendChild(columnKname);
            e_root.appendChild(columnKont);
            doc.appendChild(e_root);
//			if (posts.size() == 0)
//				return;

//            List<Analysis> users  = uptakeService.getUptakeByCode();
//            List<String> hivs = users
//                    .stream().map(Analysis::getHiv)
//                    .collect(Collectors.toList());
            int hivIterator = 1;
            int hbsIterator=1;
            int hcvIterator=1;
            int ifaIterator = 1;
            int mrpIterator = 1;
            int aHBsIterator = 1;
            int totalIterator = 1;
            int count116F = (int) users
                    .stream()
                    .filter(e -> e.getKontengent().equals("116 б"))
                    .filter(e ->e.getSex().equals("1"))
                    .count();

            int count116M = (int) users
                    .stream()
                    .filter(e -> (e.getKontengent().equals("116 б")&& e.getSex().equals("0")))
                    .count();

            int count109a = (int) users
                    .stream()
                    .filter(e -> e.getKontengent().equals("109.а"))
                    .count();
            int count109b = (int) users
                    .stream()
                    .filter(e -> e.getKontengent().equals("109.б"))
                    .count();
            int count110 = (int) users
                    .stream()
                    .filter(e -> e.getKontengent().equals("110"))
                    .count();
            int count108 = (int) users
                    .stream()
                    .filter(e -> e.getKontengent().equals("108.б"))
                    .count();
            int count132F = (int) users
                    .stream()
                    .filter(e -> e.getKontengent().equals("132.б")&& e.getSex().equals("1"))
                    .count();
            int count132M = (int) users
                    .stream()
                    .filter(e -> e.getKontengent().equals("132.б")&& e.getSex().equals("0"))
                    .count();

            int iterator116f = 0;
            int iterator116m = 0;
            int iterator109a = 0;
            int iterator109b = 0;
            int iterator110 = 0;
            int iterator108b = 0;
            int iterator132bF = 0;
            int iterator132bM = 0;
            for (Analysis hiv : users) {
//                int hivNumber = hivCount - (hivCount-hivIterator);
//                int hbsNumber = hbsCount - (hbsCount-hbsIterator);
//                int hcvNumber = hcvCount - (hcvCount-hcvIterator);
//                int z = hivs.size()-(hivs.size()-i);
                Element item = doc.createElement("item");
                item.setAttribute("name", hiv.getEmc());
                Element id = doc.createElement("data");
                id.setAttribute("value", hiv.getFio());
                Element patientid = doc.createElement("data");
                patientid.setAttribute("value", hiv.getDate_bio());
                Element uptakeCod = doc.createElement("data");
                uptakeCod.setAttribute("value", hiv.getCode());
                Element sequenserValue = doc.createElement("data");
                sequenserValue.setAttribute("value", String.valueOf(totalIterator++));
                Element number = doc.createElement("data");
                if (hiv.getHiv().equals("1")) {
                    number.setAttribute("value", String.valueOf(hivIterator));
                    hivIterator++;
                } else {number.setAttribute("value", "");}
                Element result = doc.createElement("data");
                if (hiv.getHbsAg().equals("1")) {
                    result.setAttribute("value", String.valueOf(hbsIterator));
                    hbsIterator++;
                } else {result.setAttribute("value", "");}
                Element hcv = doc.createElement("data");
                if (hiv.getAtHCV().equals("1")) {
                    hcv.setAttribute("value", String.valueOf(hcvIterator));
                    hcvIterator++;
                } else {hcv.setAttribute("value", "");}
                Element ifa = doc.createElement("data");
                if (hiv.getSyphIFA().equals("1")) {
                    ifa.setAttribute("value", String.valueOf(ifaIterator));
                    ifaIterator++;
                } else {ifa.setAttribute("value", "");}

                Element mrp = doc.createElement("data");
                if (hiv.getSyphMRP().equals("1")) {
                    mrp.setAttribute("value", String.valueOf(mrpIterator));
                    mrpIterator++;
                } else {mrp.setAttribute("value", "");}

                Element aHBs = doc.createElement("data");
                if (hiv.getRubM().equals("1")) {
                    aHBs.setAttribute("value", String.valueOf(aHBsIterator));
                    aHBsIterator++;
                } else {aHBs.setAttribute("value", "");}

                Element kontengent = doc.createElement("data");
                kontengent.setAttribute("value", hiv.getKontengent());

                Element kont = doc.createElement("data");
                if (iterator116f <1) {
                    kont.setAttribute("value", "116 б F - " + count116F);
                    iterator116f++;
                }else if (iterator116m<1) {kont.setAttribute("value", "116 б M - " + count116M);
                iterator116m++;
                } else
                    if (iterator109a<1) {
                        kont.setAttribute("value", "109.a - " + count109a);
                        iterator109a++;
                    } else
                    if (iterator109b<1) {
                        kont.setAttribute("value", "109.б - " + count109b);
                        iterator109b++;
                    } else
                    if (iterator110<1) {
                        kont.setAttribute("value", "110 - " + count110);
                        iterator110++;
                    }else
                    if (iterator108b<1) {
                        kont.setAttribute("value", "108.б - " + count108);
                        iterator108b++;

                    }else
                    if (iterator132bF<1) {
                        kont.setAttribute("value", "132.бF - " + count132F);
                        iterator132bF++;
                    }else
                    if (iterator132bM<1) {
                        kont.setAttribute("value", "132.бM - " + count132M);
                        iterator132bM++;
                    }

                item.appendChild(id);
                item.appendChild(patientid);
                item.appendChild(uptakeCod);
                item.appendChild(sequenserValue);
                item.appendChild(number);
                item.appendChild(result);
                item.appendChild(hcv);
                item.appendChild(ifa);
                item.appendChild(mrp);
                item.appendChild(aHBs);
                item.appendChild(kontengent);
                item.appendChild(kont);


                e_root.appendChild (item);

//                if (hiv.getHiv().equals("1")) {
//                    i++;
//                }
            }
//            Element item1 = doc.createElement("item1");
//            item1.setAttribute("name1", "116 б");
//            Element kont116F = doc.createElement("data1");
//            kont116F.setAttribute("value1", String.valueOf(hivCount));
//            item1.appendChild(kont116F);
//            e_root.appendChild(item1);

//			System.out.println("    форумов : " + forums.size());
//			for (String forum : forums) {
//				Element e = doc.createElement("forum");
//				e.setTextContent(forum);
//				e_forums.appendChild (e);
//			}
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } finally {
            // Сохраняем Document в XML-файл
            if (doc != null)
                writeDocument(doc);
        }

    }

    public static void writeTORCHXML( List<Analysis> users) throws IOException {
        DocumentBuilderFactory dbf = null;
        DocumentBuilder db  = null;
        Document doc = null;
        try {
            dbf = DocumentBuilderFactory.newInstance();
            db  = dbf.newDocumentBuilder();
            doc = db.newDocument();

            Element e_root   = doc.createElement("report");
//			e_root.setAttribute("lang", "en");
            Element columnId  = doc.createElement("column");
            columnId.setAttribute("data", "Фио");
            Element columnpatientId = doc.createElement("column");
            columnpatientId.setAttribute("data", "Дата забора");
            Element columnuptakeCod = doc.createElement("column");
            columnuptakeCod.setAttribute("data", "Код забора");
            Element sequenser = doc.createElement("column");
            sequenser.setAttribute("data", "№");
            Element columnnumber = doc.createElement("column");
            columnnumber.setAttribute("data", "RubG");
            Element columnresult = doc.createElement("column");
            columnresult.setAttribute("data", "RubM");
            Element columnHCV = doc.createElement("column");
            columnHCV.setAttribute("data", "Хлам G");
            Element columnIfa = doc.createElement("column");
            columnIfa.setAttribute("data", "Хлам А");
            Element columnMRP = doc.createElement("column");
            columnMRP.setAttribute("data", "БТШ");
            e_root.appendChild(columnId);
            e_root.appendChild(columnpatientId);
            e_root.appendChild(columnuptakeCod);
            e_root.appendChild(sequenser);
            e_root.appendChild(columnnumber);
            e_root.appendChild(columnresult);
            e_root.appendChild(columnHCV);
            e_root.appendChild(columnIfa);
            e_root.appendChild(columnMRP);
            doc.appendChild(e_root);

            int hivIterator = 1;
            int hbsIterator=1;
            int hcvIterator=1;
            int ifaIterator = 1;
            int mrpIterator = 1;
            int totalIterator = 1;
            for (Analysis hiv : users) {
//                int hivNumber = hivCount - (hivCount-hivIterator);
//                int hbsNumber = hbsCount - (hbsCount-hbsIterator);
//                int hcvNumber = hcvCount - (hcvCount-hcvIterator);
//                int z = hivs.size()-(hivs.size()-i);
                Element item = doc.createElement("item");
                item.setAttribute("name", hiv.getEmc());
                Element id = doc.createElement("data");
                id.setAttribute("value", hiv.getFio());
                Element patientid = doc.createElement("data");
                patientid.setAttribute("value", hiv.getDate_bio());
                Element uptakeCod = doc.createElement("data");
                uptakeCod.setAttribute("value", hiv.getCode());
                Element sequenserValue = doc.createElement("data");
                sequenserValue.setAttribute("value", String.valueOf(totalIterator++));
                Element number = doc.createElement("data");
                if (hiv.getRubG().equals("1")) {
                    number.setAttribute("value", String.valueOf(hivIterator));
                    hivIterator++;
                } else {number.setAttribute("value", "");}
                Element result = doc.createElement("data");
                if (hiv.getRubM().equals("1")) {
                    result.setAttribute("value", String.valueOf(hbsIterator));
                    hbsIterator++;
                } else {result.setAttribute("value", "");}
                Element hcv = doc.createElement("data");
                if (hiv.getClamG().equals("1")) {
                    hcv.setAttribute("value", String.valueOf(hcvIterator));
                    hcvIterator++;
                } else {hcv.setAttribute("value", "");}
                Element ifa = doc.createElement("data");
                if (hiv.getClamA().equals("1")) {
                    ifa.setAttribute("value", String.valueOf(ifaIterator));
                    ifaIterator++;
                } else {ifa.setAttribute("value", "");}

                Element mrp = doc.createElement("data");
                if (hiv.getHSP60().equals("1")) {
                    mrp.setAttribute("value", String.valueOf(mrpIterator));
                    mrpIterator++;
                } else {mrp.setAttribute("value", "");}



                item.appendChild(id);
                item.appendChild(patientid);
                item.appendChild(uptakeCod);
                item.appendChild(sequenserValue);
                item.appendChild(number);
                item.appendChild(result);
                item.appendChild(hcv);
                item.appendChild(ifa);
                item.appendChild(mrp);

                e_root.appendChild (item);

            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } finally {
            // Сохраняем Document в XML-файл
            if (doc != null)
                writeDocument(doc);
        }

    }

    public static void writeGormonuXML( List<Analysis> users) throws IOException {
        DocumentBuilderFactory dbf = null;
        DocumentBuilder db  = null;
        Document doc = null;
        try {
            dbf = DocumentBuilderFactory.newInstance();
            db  = dbf.newDocumentBuilder();
            doc = db.newDocument();

            Element e_root   = doc.createElement("report");
//			e_root.setAttribute("lang", "en");
            Element columnId  = doc.createElement("column");
            columnId.setAttribute("data", "Фио");
            Element columnpatientId = doc.createElement("column");
            columnpatientId.setAttribute("data", "Дата забора");
            Element columnuptakeCod = doc.createElement("column");
            columnuptakeCod.setAttribute("data", "Код забора");
            Element sequenser = doc.createElement("column");
            sequenser.setAttribute("data", "№");
            Element columnnumber = doc.createElement("column");
            columnnumber.setAttribute("data", "АМГ");
            Element columnresult = doc.createElement("column");
            columnresult.setAttribute("data", "17-OH");
            Element columnHCV = doc.createElement("column");
            columnHCV.setAttribute("data", "CA-125");
            Element columnADN = doc.createElement("column");
            columnADN.setAttribute("data", "Андростендион");
//            Element columnIfa = doc.createElement("column");
//            columnIfa.setAttribute("data", "E2");
            e_root.appendChild(columnId);
            e_root.appendChild(columnpatientId);
            e_root.appendChild(columnuptakeCod);
            e_root.appendChild(sequenser);
            e_root.appendChild(columnnumber);
            e_root.appendChild(columnresult);
            e_root.appendChild(columnHCV);
            e_root.appendChild(columnADN);
//            e_root.appendChild(columnIfa);
            doc.appendChild(e_root);
//			if (posts.size() == 0)
//				return;

//            List<Analysis> users  = uptakeService.getUptakeByCode();
//            List<String> hivs = users
//                    .stream().map(Analysis::getHiv)
//                    .collect(Collectors.toList());
            int hivIterator = 1;
            int hbsIterator=1;
            int hcvIterator=1;
            int ifaIterator = 1;
            int totalIterator = 1;
            for (Analysis hiv : users) {
//                int hivNumber = hivCount - (hivCount-hivIterator);
//                int hbsNumber = hbsCount - (hbsCount-hbsIterator);
//                int hcvNumber = hcvCount - (hcvCount-hcvIterator);
//                int z = hivs.size()-(hivs.size()-i);
                Element item = doc.createElement("item");
                item.setAttribute("name", hiv.getEmc());
                Element id = doc.createElement("data");
                id.setAttribute("value", hiv.getFio());
                Element patientid = doc.createElement("data");
                patientid.setAttribute("value", hiv.getDate_bio());
                Element uptakeCod = doc.createElement("data");
                uptakeCod.setAttribute("value", hiv.getCode());
                Element sequenserValue = doc.createElement("data");
                sequenserValue.setAttribute("value", String.valueOf(totalIterator++));
                Element number = doc.createElement("data");
                if (hiv.getHiv().equals("1")) {
                    number.setAttribute("value", String.valueOf(hivIterator));
                    hivIterator++;
                } else {number.setAttribute("value", "");}
                Element result = doc.createElement("data");
                if (hiv.getHbsAg().equals("1")) {
                    result.setAttribute("value", String.valueOf(hbsIterator));
                    hbsIterator++;
                } else {result.setAttribute("value", "");}
                Element hcv = doc.createElement("data");
                if (hiv.getAtHCV().equals("1")) {
                    hcv.setAttribute("value", String.valueOf(hcvIterator));
                    hcvIterator++;
                } else {hcv.setAttribute("value", "");}
                Element ifa = doc.createElement("data");
                if (hiv.getSyphIFA().equals("1")) {
                    ifa.setAttribute("value", String.valueOf(ifaIterator));
                    ifaIterator++;
                } else {ifa.setAttribute("value", "");}


                item.appendChild(id);
                item.appendChild(patientid);
                item.appendChild(uptakeCod);
                item.appendChild(sequenserValue);
                item.appendChild(number);
                item.appendChild(result);
                item.appendChild(hcv);
                item.appendChild(ifa);

                e_root.appendChild (item);

//                if (hiv.getHiv().equals("1")) {
//                    i++;
//                }
            }
//            Element item1 = doc.createElement("item1");
//            item1.setAttribute("name1", "116 б");
//            Element kont116F = doc.createElement("data1");
//            kont116F.setAttribute("value1", String.valueOf(hivCount));
//            item1.appendChild(kont116F);
//            e_root.appendChild(item1);

//			System.out.println("    форумов : " + forums.size());
//			for (String forum : forums) {
//				Element e = doc.createElement("forum");
//				e.setTextContent(forum);
//				e_forums.appendChild (e);
//			}
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } finally {
            // Сохраняем Document в XML-файл
            if (doc != null)
                writeDocument(doc);
        }

    }

    public static void writeImmXML( List<Analysis> users) throws IOException {
        DocumentBuilderFactory dbf = null;
        DocumentBuilder db  = null;
        Document doc = null;
        try {
            dbf = DocumentBuilderFactory.newInstance();
            db  = dbf.newDocumentBuilder();
            doc = db.newDocument();

            Element e_root   = doc.createElement("report");
//			e_root.setAttribute("lang", "en");
            Element columnId  = doc.createElement("column");
            columnId.setAttribute("data", "Фио");
            Element columnpatientId = doc.createElement("column");
            columnpatientId.setAttribute("data", "Дата забора");
            Element columnuptakeCod = doc.createElement("column");
            columnuptakeCod.setAttribute("data", "Код забора");
            Element number = doc.createElement("column");
            number.setAttribute("data", "№");
            Element columnnumber = doc.createElement("column");
            columnnumber.setAttribute("data", "E2");
            Element columnresult = doc.createElement("column");
            columnresult.setAttribute("data", "ФСГ");
            Element columnHCV = doc.createElement("column");
            columnHCV.setAttribute("data", "ТТГ");
            Element columnIfa = doc.createElement("column");
            columnIfa.setAttribute("data", "АТ к ТПО");
            Element columnT4 = doc.createElement("column");
            columnT4.setAttribute("data", "Т4");
            Element columnPRL = doc.createElement("column");
            columnPRL.setAttribute("data", "PRL");
            Element columnLG = doc.createElement("column");
            columnLG.setAttribute("data", "ЛГ");
            Element columnPRG = doc.createElement("column");
            columnPRG.setAttribute("data", "ПРГ");
            Element columnTes = doc.createElement("column");
            columnTes.setAttribute("data", "TES");
            Element columnSBG = doc.createElement("column");
            columnSBG.setAttribute("data", "СССГ");
            Element columnDGA = doc.createElement("column");
            columnDGA.setAttribute("data", "ДГЭА-С");
            Element columnPSAT = doc.createElement("column");
            columnPSAT.setAttribute("data", "ПСАоб");
            Element columnPSAF = doc.createElement("column");
            columnPSAF.setAttribute("data", "ПСАсв");
            e_root.appendChild(columnId);
            e_root.appendChild(columnpatientId);
            e_root.appendChild(columnuptakeCod);
            e_root.appendChild(number);
            e_root.appendChild(columnnumber);
            e_root.appendChild(columnresult);
            e_root.appendChild(columnHCV);
            e_root.appendChild(columnIfa);
            e_root.appendChild(columnT4);
            e_root.appendChild(columnPRL);
            e_root.appendChild(columnLG);
            e_root.appendChild(columnPRG);
            e_root.appendChild(columnTes);
            e_root.appendChild(columnSBG);
            e_root.appendChild(columnDGA);
            e_root.appendChild(columnPSAT);
            e_root.appendChild(columnPSAF);
            doc.appendChild(e_root);
//			if (posts.size() == 0)
//				return;

//            List<Analysis> users  = uptakeService.getUptakeByCode();
//            List<String> hivs = users
//                    .stream().map(Analysis::getHiv)
//                    .collect(Collectors.toList());
            int hivIterator = 1;
            int hbsIterator=1;
            int hcvIterator=1;
            int ifaIterator = 1;
            int t4Iterator = 1;
            int PRLIterator = 1;
            int LGIterator = 1;
            int PRGIterator = 1;
            int TESIterator = 1;
            int SBGIterator = 1;
            int DGAIterator = 1;
            int PSATIterator = 1;
            int PSAFIterator = 1;
            int totalIterator = 1;
            for (Analysis hiv : users) {
//                int hivNumber = hivCount - (hivCount-hivIterator);
//                int hbsNumber = hbsCount - (hbsCount-hbsIterator);
//                int hcvNumber = hcvCount - (hcvCount-hcvIterator);
//                int z = hivs.size()-(hivs.size()-i);
                Element item = doc.createElement("item");
                item.setAttribute("name", hiv.getEmc());
                Element id = doc.createElement("data");
                id.setAttribute("value", hiv.getFio());
                Element patientid = doc.createElement("data");
                patientid.setAttribute("value", hiv.getDate_bio());
                Element uptakeCod = doc.createElement("data");
                uptakeCod.setAttribute("value", hiv.getCode());
                Element iterator = doc.createElement("data");
                iterator.setAttribute("value", String.valueOf(totalIterator++));
                Element totalNumber = doc.createElement("data");
                if (hiv.getHiv().equals("1")) {
                    totalNumber.setAttribute("value", String.valueOf(hivIterator++));
                } else {totalNumber.setAttribute("value", "");}
                Element result = doc.createElement("data");
                if (hiv.getHbsAg().equals("1")) {
                    result.setAttribute("value", String.valueOf(hbsIterator++));
                } else {result.setAttribute("value", "");}
                Element hcv = doc.createElement("data");
                if (hiv.getAtHCV().equals("1")) {
                    hcv.setAttribute("value", String.valueOf(hcvIterator++));
                } else {hcv.setAttribute("value", "");}
                Element ifa = doc.createElement("data");
                if (hiv.getSyphIFA().equals("1")) {
                    ifa.setAttribute("value", String.valueOf(ifaIterator++));
                } else {ifa.setAttribute("value", "");}
                Element t4 = doc.createElement("data");
                if (hiv.getRubM().equals("1")) {
                    t4.setAttribute("value", String.valueOf(t4Iterator++));
                } else {t4.setAttribute("value", "");}
                Element prl = doc.createElement("data");
                if (hiv.getRubG().equals("1")) {
                    prl.setAttribute("value", String.valueOf(PRLIterator++));
                } else {prl.setAttribute("value", "");}
                Element lg = doc.createElement("data");
                if (hiv.getClamG().equals("1")) {
                    lg.setAttribute("value", String.valueOf(LGIterator++));
                } else {lg.setAttribute("value", "");}
                Element prg = doc.createElement("data");
                if (hiv.getSyphMRP().equals("1")) {
                    prg.setAttribute("value", String.valueOf(PRGIterator++));
                } else {prg.setAttribute("value", "");}

                Element tes = doc.createElement("data");
                if (hiv.getClamA().equals("1")) {
                    tes.setAttribute("value", String.valueOf(TESIterator++));
                } else {tes.setAttribute("value", "");}
                Element sbg = doc.createElement("data");
                if (hiv.getSbg().equals("1")) {
                    sbg.setAttribute("value", String.valueOf(SBGIterator++));
                } else {sbg.setAttribute("value", "");}
                Element dga = doc.createElement("data");
                if (hiv.getDga().equals("1")) {
                    dga.setAttribute("value", String.valueOf(DGAIterator++));
                } else {dga.setAttribute("value", "");}
                Element psat = doc.createElement("data");
                if (hiv.getPsa_total().equals("1")) {
                    psat.setAttribute("value", String.valueOf(PSATIterator++));
                } else {psat.setAttribute("value", "");}
                Element psaf = doc.createElement("data");
                if (hiv.getPsa_free().equals("1")) {
                    psaf.setAttribute("value", String.valueOf(PSAFIterator++));
                } else {psaf.setAttribute("value", "");}
                item.appendChild(id);
                item.appendChild(patientid);
                item.appendChild(uptakeCod);
                item.appendChild(iterator);
                item.appendChild(totalNumber);
//                item.appendChild(number);
                item.appendChild(result);
                item.appendChild(hcv);
                item.appendChild(ifa);
                item.appendChild(t4);
                item.appendChild(prl);
                item.appendChild(lg);
                item.appendChild(prg);
                item.appendChild(tes);
                item.appendChild(sbg);
                item.appendChild(dga);
                item.appendChild(psat);
                item.appendChild(psaf);

                e_root.appendChild (item);

//                if (hiv.getHiv().equals("1")) {
//                    i++;
//                }
            }
//            Element item1 = doc.createElement("item1");
//            item1.setAttribute("name1", "116 б");
//            Element kont116F = doc.createElement("data1");
//            kont116F.setAttribute("value1", String.valueOf(hivCount));
//            item1.appendChild(kont116F);
//            e_root.appendChild(item1);

//			System.out.println("    форумов : " + forums.size());
//			for (String forum : forums) {
//				Element e = doc.createElement("forum");
//				e.setTextContent(forum);
//				e_forums.appendChild (e);
//			}
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } finally {
            // Сохраняем Document в XML-файл
            if (doc != null)
                writeDocument(doc);
        }

    }


}
