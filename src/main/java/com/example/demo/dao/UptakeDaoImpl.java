package com.example.demo.dao;

import com.example.demo.config.Database;
import com.example.demo.model.*;
import com.example.demo.model.qc.achtv.AchtvCounter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.persistence.EntityManager;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository("UptakeDao")
public class UptakeDaoImpl implements UptakeDao {
    Database database = new Database();


    EntityManager entityManager;
    PasswordEncoder passwordEncoder;
    List<User> users = new ArrayList<>();
    List<Element> columns = new ArrayList<>();
    List<Element> values = new ArrayList<>();


    @Autowired
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Autowired
    public void setPasswordEncoder(@Lazy PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    public List<Object[]> getData(String done, String bio_code, Integer GRPPRM) throws SQLException {
        List<Object[]> objects = new ArrayList<>();
        try (Connection connection = database.getConnection()) {


            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("select  PATDIREC.PATIENTS_ID,\n" +
                    "dbo.fNNPlus_Patient (pat.PATIENTS_ID,1),\n" +
                    "--pat.nom+' '+substring(pat.PRENOM,1,1)+'. '+substring(pat.PATRONYME,1,1)+'.', \n" +
                    "\n" +
                    "(case \n" +
//                "when kontengent=0 then '108.б'\n" +
//                "when kontengent=1 then '109.а'\n" +
//                "when kontengent=2 then '109.б'\n" +
//                "when kontengent=3 then '116 б'\n" +
//                "when kontengent=4 then '110'\n" +
//                "when kontengent=5 then '132.б'\n" +
//                "when kontengent=6 then '132.в'\n" +
                    "when (select top 1 FM_CLINK_ID from FM_CLINK_PATIENTS where PATIENTS_ID=PAT.PATIENTS_ID order by DATE_FROM desc) in (1600, 1589, 1577, 1347,215, 214, 1590, 1578, 1601, 213, 1591, 1580,1602, 1348,\n" +
                    "1349" +
                    ") then '109.а'\n" +
                    "when (select top 1 FM_CLINK_ID from FM_CLINK_PATIENTS where PATIENTS_ID=PAT.PATIENTS_ID order by DATE_FROM desc) in (115) then '108.б'\n" +
                    "when (select top 1 FM_CLINK_ID from FM_CLINK_PATIENTS where PATIENTS_ID=PAT.PATIENTS_ID order by DATE_FROM desc) in (209,210,211,212,1350,1351,1352,1353,1581,1582,1583,1584,1592,1593,1594,1595,1603,1604,1605,1606," +
                    "208, 1596, 1585, 1607, 207,1597,1586,1608,206,1598,1587,1609,205,1599,1588,1610,1354,1355,1356,1357) then '109.б'\n" +
                    "when (select top 1 FM_CLINK_ID from FM_CLINK_PATIENTS where PATIENTS_ID=PAT.PATIENTS_ID order by DATE_FROM desc) in (217) then '110'\n" +
                    "            else '116 б'" +
                    "end),\n" +
                    "DIR_ANSW.MOTCONSU_RESP_ID\n" +
                    ",\n" +
                    "(case \n" +
                    "when LAB_METHODS.CODE='А/т к ВИЧ 1,2 +А/г' then \n" +
                    "(select DISTINCT AT_K_VICH_1_2 from DIR_ANSW DA inner join DATA_W693_VICH_SIFIL_GEPAT ON DA.MOTCONSU_RESP_ID=DATA_W693_VICH_SIFIL_GEPAT.MOTCONSU_ID where DA.MOTCONSU_RESP_ID= DIR_ANSW.MOTCONSU_RESP_ID ) \n" +
                    "when LAB_METHODS.CODE='HBsAg' then \n" +
                    " (select DISTINCT HBS_AG from DIR_ANSW DA inner join DATA_W693_VICH_SIFIL_GEPAT ON DA.MOTCONSU_RESP_ID=DATA_W693_VICH_SIFIL_GEPAT.MOTCONSU_ID where DA.MOTCONSU_RESP_ID= DIR_ANSW.MOTCONSU_RESP_ID ) \n" +
                    "when LAB_METHODS.CODE='Ат .к. HCV' then \n" +
                    " (select DISTINCT AT_K_HCV from DIR_ANSW DA inner join DATA_W693_VICH_SIFIL_GEPAT ON DA.MOTCONSU_RESP_ID=DATA_W693_VICH_SIFIL_GEPAT.MOTCONSU_ID where DA.MOTCONSU_RESP_ID= DIR_ANSW.MOTCONSU_RESP_ID ) \n" +
                    " when LAB_METHODS.CODE='Syphilis ИФА' then \n" +
                    " (select DISTINCT SIPHILIS_TPHA_TEST from DIR_ANSW DA inner join DATA_W693_VICH_SIFIL_GEPAT ON DA.MOTCONSU_RESP_ID=DATA_W693_VICH_SIFIL_GEPAT.MOTCONSU_ID where DA.MOTCONSU_RESP_ID= DIR_ANSW.MOTCONSU_RESP_ID )\n" +
                    " when LAB_METHODS.CODE='Сифилис МРП' then \n" +
                    " (select DISTINCT SIPHILIS_MR from DIR_ANSW DA inner join DATA_W693_VICH_SIFIL_GEPAT ON DA.MOTCONSU_RESP_ID=DATA_W693_VICH_SIFIL_GEPAT.MOTCONSU_ID where DA.MOTCONSU_RESP_ID= DIR_ANSW.MOTCONSU_RESP_ID )\n" +
                    "when LAB_METHODS.CODE='Анти-HBs' then \n" +
                    "(select DISTINCT ANTI_HBS from DIR_ANSW DA inner join DATA_W693_VICH_SIFIL_GEPAT ON DA.MOTCONSU_RESP_ID=DATA_W693_VICH_SIFIL_GEPAT.MOTCONSU_ID where DA.MOTCONSU_RESP_ID= DIR_ANSW.MOTCONSU_RESP_ID )\n" +
                    "when LAB_METHODS.CODE='Rub-G' then\n" +
                    "(select DISTINCT DATA_W693_VUI.DYN_72840_72913 from DIR_ANSW DA inner join DATA_W693_VUI ON DA.MOTCONSU_RESP_ID=DATA_W693_VUI.MOTCONSU_ID where DA.MOTCONSU_RESP_ID= DIR_ANSW.MOTCONSU_RESP_ID )\n" +
                    "when LAB_METHODS.CODE='Rub- M' then\n" +
                    "(select DISTINCT DATA_W693_VUI.DYN_72840_72914 from DIR_ANSW DA inner join DATA_W693_VUI ON DA.MOTCONSU_RESP_ID=DATA_W693_VUI.MOTCONSU_ID where DA.MOTCONSU_RESP_ID= DIR_ANSW.MOTCONSU_RESP_ID )\n" +
                    "when LAB_METHODS.CODE='cHSP60-Ig G(белок тепл.шока и' then\n" +
                    "(select DISTINCT DATA_W693_VUI.DYN_72840_72917 from DIR_ANSW DA inner join DATA_W693_VUI ON DA.MOTCONSU_RESP_ID=DATA_W693_VUI.MOTCONSU_ID where DA.MOTCONSU_RESP_ID= DIR_ANSW.MOTCONSU_RESP_ID )\n" +
                    "when LAB_METHODS.CODE='Clam-A' then\n" +
                    "(select DISTINCT DATA_W693_VUI.DYN_72840_72916 from DIR_ANSW DA inner join DATA_W693_VUI ON DA.MOTCONSU_RESP_ID=DATA_W693_VUI.MOTCONSU_ID where DA.MOTCONSU_RESP_ID= DIR_ANSW.MOTCONSU_RESP_ID )\n" +
                    "when LAB_METHODS.CODE='Chlam-G' then\n" +
                    "(select DISTINCT DATA_W693_VUI.DYN_72840_72915 from DIR_ANSW DA inner join DATA_W693_VUI ON DA.MOTCONSU_RESP_ID=DATA_W693_VUI.MOTCONSU_ID where DA.MOTCONSU_RESP_ID= DIR_ANSW.MOTCONSU_RESP_ID )" +
                    " end)\n" +
                    ",FM_DEP.MAIN_ORG_ID,\n" +
                    "FM_ORG.LABEL,\n" +
                    "PATDIREC.PATDIREC_ID,\n" +
                    "PATDIREC.DATE_BIO,\n" +
                    "--DS_PARAMS.DS_PARAMS_ID,\n" +
                    "--,PATDIREC.PL_EXAM_ID,\n" +
                    " --LAB_METHODS.LAB_METHODS_ID, \n" +
                    " LAB_METHODS.CODE, \n" +
                    "  PATDIREC.BIO_CODE,\n" +
                    "  PAT.POL,\n" +
                    "  PAT.ADRES_PO_PROPISKE\n" +
                    ",* from  PATDIREC PATDIREC WITH(NOLOCK)  JOIN PL_EXAM PL_EXAM WITH(NOLOCK)  ON PATDIREC.PL_EXAM_ID = PL_EXAM.PL_EXAM_ID \n" +
                    "INNER JOIN DIR_ANSW ON PATDIREC.PATDIREC_ID=DIR_ANSW.PATDIREC_ID\n" +
                    " inner join DIR_SERV ON PATDIREC.PATDIREC_ID =DIR_SERV.PATDIREC_ID\n" +
                    " inner JOIN FM_DEP ON PATDIREC.MEDECINS_BIO_DEP_ID=FM_DEP.FM_DEP_ID\n" +
                    "LEFT OUTER JOIN PATIENTS PAT WITH(NOLOCK)  ON PATDIREC.PATIENTS_ID = PAT.PATIENTS_ID \n" +
                    "INNER JOIN FM_ORG ON FM_DEP.MAIN_ORG_ID=FM_ORG.FM_ORG_ID\n" +
                    " inner join DS_SERVPARAMS ON DIR_SERV.FM_SERV_ID=DS_SERVPARAMS.FM_SERV_ID\n" +
                    " inner JOIN DS_PARAMS ON DS_SERVPARAMS.DS_PARAMS_ID=DS_PARAMS.DS_PARAMS_ID\n" +
                    " JOIN LAB_METHODBIO LAB_METHODBIO WITH(NOLOCK)  ON DS_PARAMS.DS_PARAMS_ID = LAB_METHODBIO.DS_PARAMS_ID \n" +
                    " JOIN LAB_METHODS LAB_METHODS WITH(NOLOCK)  ON LAB_METHODBIO.LAB_METHODS_ID = LAB_METHODS.LAB_METHODS_ID \n" +
                    " LEFT OUTER JOIN VIEW_GRPPRM VIEW_GRPPRM WITH(NOLOCK)  ON DS_PARAMS.DS_PARAMS_ID = VIEW_GRPPRM.DS_PARAMS_ID \n" +
                    "--DS_PARAMS \n" +
                    "where \n" +
                    "VIEW_GRPPRM.GRPPRM_ID =" + GRPPRM + " --рабочий журнал по вичам\n" +
                    done +
                    "and  PATDIREC.BIO_CODE= -- код забора\n" + bio_code +
                    "and PATDIREC.DATE_BIO >dateadd(day,-PL_EXAM.VAL_PERIOD,getdate()) --\n" +
                    " --and FM_DEP.MAIN_ORG_ID=20 --филиал выполнивший забор биоматериала");

            while (resultSet.next()) {
                objects.add(new Object[]{
                        resultSet.getObject(1),
                        resultSet.getObject(2),
                        resultSet.getObject(3),
                        resultSet.getObject(4),
                        resultSet.getObject(5),
                        resultSet.getObject(6),
                        resultSet.getObject(7),
                        resultSet.getObject(8),
                        resultSet.getObject(9),
                        resultSet.getObject(10),
                        resultSet.getObject(11),
                        resultSet.getObject(12),
                        resultSet.getObject(13)
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return objects;
    }

    public List<String[]> getDataGormonu(String bio_code, String done, String GPRM) {
        List<String[]> objects = new ArrayList<>();
        try (Connection connection = database.getConnection()) {
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("select PATDIREC.PATIENTS_ID,\n" +
                            "        dbo.fNNPlus_Patient (pat.PATIENTS_ID,1),\n" +
                            "\n" +
                            "        (case\n" +
                            "             --when LAB_METHODS.CODE='AMG'  then\n" +
                            "             when LAB_METHODS.LAB_METHODS_ID=1628  then\n" +
                            "                 (select DISTINCT DATA_W693_GORMONU.AMG from DIR_ANSW DA inner join DATA_W693_GORMONU ON DA.MOTCONSU_RESP_ID=DATA_W693_GORMONU.MOTCONSU_ID where DA.MOTCONSU_RESP_ID= DIR_ANSW.MOTCONSU_RESP_ID )\n" +
                            "             --when LAB_METHODS.CODE='AND' or LAB_METHODS.CODE='17-OH'  then\n" +
                            "             when LAB_METHODS.LAB_METHODS_ID=1711  then\n" +
                            "                 (select DISTINCT DATA_W693_GORMONU.ANDR_ZHEN_17_ON_PROG from DIR_ANSW DA inner join DATA_W693_GORMONU ON DA.MOTCONSU_RESP_ID=DATA_W693_GORMONU.MOTCONSU_ID where DA.MOTCONSU_RESP_ID= DIR_ANSW.MOTCONSU_RESP_ID )\n" +
                            "             --when LAB_METHODS.CODE='E2'   then\n" +
                            "             when LAB_METHODS.LAB_METHODS_ID=1512   then\n" +
                            "                 (select DISTINCT DATA_W693_GORMONU.ESTRADIOL from DIR_ANSW DA inner join DATA_W693_GORMONU ON DA.MOTCONSU_RESP_ID=DATA_W693_GORMONU.MOTCONSU_ID where DA.MOTCONSU_RESP_ID= DIR_ANSW.MOTCONSU_RESP_ID )\n" +
                            "             --when LAB_METHODS.CODE='CA-125'   then\n" +
                            "             when LAB_METHODS.LAB_METHODS_ID=1687   then\n" +
                            "                 (select DISTINCT DATA_W693_GORMONU.SA_125 from DIR_ANSW DA inner join DATA_W693_GORMONU ON DA.MOTCONSU_RESP_ID=DATA_W693_GORMONU.MOTCONSU_ID where DA.MOTCONSU_RESP_ID= DIR_ANSW.MOTCONSU_RESP_ID )\n" +
                            "     --when LAB_METHODS.CODE='RTH'   then \n" +
                            "     when LAB_METHODS.LAB_METHODS_ID=1504   then \n" +
                            "         (select DISTINCT DATA_W693_GORMONU.TTG from DIR_ANSW DA inner join DATA_W693_GORMONU ON DA.MOTCONSU_RESP_ID=DATA_W693_GORMONU.MOTCONSU_ID where DA.MOTCONSU_RESP_ID= DIR_ANSW.MOTCONSU_RESP_ID )\n" +
                            "     --when LAB_METHODS.CODE='FSH'   then\n" +
                            "     when LAB_METHODS.LAB_METHODS_ID=1511   then \n" +
                            "         (select DISTINCT DATA_W693_GORMONU.FSG from DIR_ANSW DA inner join DATA_W693_GORMONU ON DA.MOTCONSU_RESP_ID=DATA_W693_GORMONU.MOTCONSU_ID where DA.MOTCONSU_RESP_ID= DIR_ANSW.MOTCONSU_RESP_ID )" +
                            "    --when LAB_METHODS.CODE='ATA'   then \n" +
                            "    when LAB_METHODS.LAB_METHODS_ID=1510   then \n" +
                            "         (select DISTINCT DATA_W693_GORMONU.TPO from DIR_ANSW DA inner join DATA_W693_GORMONU ON DA.MOTCONSU_RESP_ID=DATA_W693_GORMONU.MOTCONSU_ID where DA.MOTCONSU_RESP_ID= DIR_ANSW.MOTCONSU_RESP_ID )" +
                            "--when LAB_METHODS.CODE='F4'   then \n" +
                            "when LAB_METHODS.LAB_METHODS_ID=1505   then \n" +
                            "(select DISTINCT DATA_W693_GORMONU.T4SV from DIR_ANSW DA inner join DATA_W693_GORMONU ON DA.MOTCONSU_RESP_ID=DATA_W693_GORMONU.MOTCONSU_ID where DA.MOTCONSU_RESP_ID= DIR_ANSW.MOTCONSU_RESP_ID )" +
                            "--when LAB_METHODS.CODE='PRL'   then \n" +
                            "when LAB_METHODS.LAB_METHODS_ID=1500   then \n" +
                            "(select DISTINCT DATA_W693_GORMONU.PROLAKTIN_MAKRO_PROLAKTIN from DIR_ANSW DA inner join DATA_W693_GORMONU ON DA.MOTCONSU_RESP_ID=DATA_W693_GORMONU.MOTCONSU_ID where DA.MOTCONSU_RESP_ID= DIR_ANSW.MOTCONSU_RESP_ID )" +
                            "--when LAB_METHODS.CODE='LH'   then \n" +
                            "when LAB_METHODS.LAB_METHODS_ID=1507   then \n" +
                            "(select DISTINCT DATA_W693_GORMONU.LG from DIR_ANSW DA inner join DATA_W693_GORMONU ON DA.MOTCONSU_RESP_ID=DATA_W693_GORMONU.MOTCONSU_ID where DA.MOTCONSU_RESP_ID= DIR_ANSW.MOTCONSU_RESP_ID )" +
                            "--when LAB_METHODS.CODE='PRG'   then \n" +
                            "when LAB_METHODS.LAB_METHODS_ID=430   then \n" +
                            "(select DISTINCT DATA_W693_GORMONU.PROGESTERON from DIR_ANSW DA inner join DATA_W693_GORMONU ON DA.MOTCONSU_RESP_ID=DATA_W693_GORMONU.MOTCONSU_ID where DA.MOTCONSU_RESP_ID= DIR_ANSW.MOTCONSU_RESP_ID )" +
                            "--when LAB_METHODS.CODE='TES'   then \n" +
                            "when LAB_METHODS.LAB_METHODS_ID=1506   then \n" +
                            "(select DISTINCT DATA_W693_GORMONU.ANDR_ZHEN_TOBSCH from DIR_ANSW DA inner join DATA_W693_GORMONU ON DA.MOTCONSU_RESP_ID=DATA_W693_GORMONU.MOTCONSU_ID where DA.MOTCONSU_RESP_ID= DIR_ANSW.MOTCONSU_RESP_ID )" +
                            "--when LAB_METHODS.CODE='SBG'   then \n" +
                            "when LAB_METHODS.LAB_METHODS_ID=1518   then \n" +
                            "(select DISTINCT DATA_W693_GORMONU.ANDR_ZHEN_SSSG from DIR_ANSW DA inner join DATA_W693_GORMONU ON DA.MOTCONSU_RESP_ID=DATA_W693_GORMONU.MOTCONSU_ID where DA.MOTCONSU_RESP_ID= DIR_ANSW.MOTCONSU_RESP_ID )" +
                            "--when LAB_METHODS.CODE='DHS'   then \n" +
                            "when LAB_METHODS.LAB_METHODS_ID=1516   then \n" +
                            "(select DISTINCT DATA_W693_GORMONU.ANDR_ZHEN_DGEA from DIR_ANSW DA inner join DATA_W693_GORMONU ON DA.MOTCONSU_RESP_ID=DATA_W693_GORMONU.MOTCONSU_ID where DA.MOTCONSU_RESP_ID= DIR_ANSW.MOTCONSU_RESP_ID )" +
                            " when LAB_METHODS.LAB_METHODS_ID=409  then\n" +
                            "(select DISTINCT cast (DATA_W693_GORMONU.PSA_OBSCH as varchar (25)) from DIR_ANSW DA inner join DATA_W693_GORMONU ON DA.MOTCONSU_RESP_ID=DATA_W693_GORMONU.MOTCONSU_ID where DA.MOTCONSU_RESP_ID= DIR_ANSW.MOTCONSU_RESP_ID )\n" +
                            "when LAB_METHODS.LAB_METHODS_ID=724  then\n" +
                            "(select DISTINCT DATA_W693_GORMONU.PSA_PSA_SV from DIR_ANSW DA inner join DATA_W693_GORMONU ON DA.MOTCONSU_RESP_ID=DATA_W693_GORMONU.MOTCONSU_ID where DA.MOTCONSU_RESP_ID= DIR_ANSW.MOTCONSU_RESP_ID )" +
                            " when LAB_METHODS.LAB_METHODS_ID=1502  then\n" +
                            "(select DISTINCT DATA_W693_GORMONU.ANDR_ZHEN_ANDROSTENDION from DIR_ANSW DA inner join DATA_W693_GORMONU ON DA.MOTCONSU_RESP_ID=DATA_W693_GORMONU.MOTCONSU_ID where DA.MOTCONSU_RESP_ID= DIR_ANSW.MOTCONSU_RESP_ID )" +
                            "end),\n" +

                            "        FM_ORG.LABEL,\n" +
                            "        PATDIREC.DATE_BIO,\n" +
                            "        --DS_PARAMS.DS_PARAMS_ID,\n" +
                            "        --,PATDIREC.PL_EXAM_ID,\n" +
                            "        LAB_METHODS.LAB_METHODS_ID,\n" +
                            "        --LAB_METHODS.CODE,\n" +
                            "        --VIEW_GRPPRM.LABEL,\n" +
                            "        PATDIREC.BIO_CODE,\n" +
                            "        PAT.POL,\n" +
                            "        PAT.ADRES_PO_PROPISKE,\n" +
                            "        PATDIREC.PATDIREC_ID\n" +
                            "         from  PATDIREC PATDIREC WITH(NOLOCK)  JOIN PL_EXAM PL_EXAM WITH(NOLOCK)  ON PATDIREC.PL_EXAM_ID = PL_EXAM.PL_EXAM_ID\n" +
                            "                                                 INNER JOIN DIR_ANSW ON PATDIREC.PATDIREC_ID=DIR_ANSW.PATDIREC_ID\n" +
                            "                                                 inner join DIR_SERV ON PATDIREC.PATDIREC_ID =DIR_SERV.PATDIREC_ID\n" +
                            "                                                 inner JOIN FM_DEP ON PATDIREC.MEDECINS_BIO_DEP_ID=FM_DEP.FM_DEP_ID\n" +
                            "                                                 LEFT OUTER JOIN PATIENTS PAT WITH(NOLOCK)  ON PATDIREC.PATIENTS_ID = PAT.PATIENTS_ID\n" +
                            "                                                 INNER JOIN FM_ORG ON FM_DEP.MAIN_ORG_ID=FM_ORG.FM_ORG_ID\n" +
                            "                                                 inner join DS_SERVPARAMS ON DIR_SERV.FM_SERV_ID=DS_SERVPARAMS.FM_SERV_ID\n" +
                            "                                                 inner JOIN DS_PARAMS ON DS_SERVPARAMS.DS_PARAMS_ID=DS_PARAMS.DS_PARAMS_ID\n" +
                            "                                                 JOIN LAB_METHODBIO LAB_METHODBIO WITH(NOLOCK)  ON DS_PARAMS.DS_PARAMS_ID = LAB_METHODBIO.DS_PARAMS_ID\n" +
                            "                                                 JOIN LAB_METHODS LAB_METHODS WITH(NOLOCK)  ON LAB_METHODBIO.LAB_METHODS_ID = LAB_METHODS.LAB_METHODS_ID\n" +
                            "                                                 LEFT OUTER JOIN VIEW_GRPPRM VIEW_GRPPRM WITH(NOLOCK)  ON DS_PARAMS.DS_PARAMS_ID = VIEW_GRPPRM.DS_PARAMS_ID\n" +
                            "    --DS_PARAMS\n" +
                            "where\n" + GPRM +
//                    "        VIEW_GRPPRM.GRPPRM_ID in (1836, 351) and " + " --рабочий журнал по вичам\n" +
//                    " PATDIREC.QUANTITY_DONE=" + done +
                            " LAB_METHODS.CODE not like '%Анализ не выполнен%'\n" +
                            "--                 LAB_METHODS.CODE='17-OH' order by PATDIREC.DATE_BIO desc\n" +
                            "and\n" +
                            "PATDIREC.BIO_CODE=" + bio_code +
                            " and PATDIREC.DATE_BIO >dateadd(day,-20,getdate())"
                            + "and GRPPRM_ID not in (25, 339, 1837, 1895, 1924)"
            );

            while (resultSet.next()) {
                objects.add(new String[]{
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7),
                        resultSet.getString(8),
                        resultSet.getString(9),
                        resultSet.getString(10)
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
//        objects
//                .stream()
//                        .map(Arrays::toString)
//                                .forEach(System.out::println);
        return objects;
    }

    public List<Object[]> getCommonData(int filial, String from, String to) {
        List<Object[]> objects = new ArrayList<>();
        try (Connection connection = database.getConnection()) {
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("select PATDIREC.PATIENTS_ID,\n" +
                            "        dbo.fNNPlus_Patient (pat.PATIENTS_ID,1),\n" +
                            "'mock'," +
//                            "\n" +
//                            "        (case\n" +
//                            "             when LAB_METHODS.CODE='AMG'  then\n" +
//                            "                 (select DISTINCT DATA_W693_GORMONU.AMG from DIR_ANSW DA inner join DATA_W693_GORMONU ON DA.MOTCONSU_RESP_ID=DATA_W693_GORMONU.MOTCONSU_ID where DA.MOTCONSU_RESP_ID= DIR_ANSW.MOTCONSU_RESP_ID )\n" +
//                            "             when LAB_METHODS.CODE='AND' or LAB_METHODS.CODE='17-OH'  then\n" +
//                            "                 (select DISTINCT DATA_W693_GORMONU.ANDR_ZHEN_17_ON_PROG from DIR_ANSW DA inner join DATA_W693_GORMONU ON DA.MOTCONSU_RESP_ID=DATA_W693_GORMONU.MOTCONSU_ID where DA.MOTCONSU_RESP_ID= DIR_ANSW.MOTCONSU_RESP_ID )\n" +
//                            "             when LAB_METHODS.CODE='E2'   then\n" +
//                            "                 (select DISTINCT DATA_W693_GORMONU.ESTRADIOL from DIR_ANSW DA inner join DATA_W693_GORMONU ON DA.MOTCONSU_RESP_ID=DATA_W693_GORMONU.MOTCONSU_ID where DA.MOTCONSU_RESP_ID= DIR_ANSW.MOTCONSU_RESP_ID )\n" +
//                            "             when LAB_METHODS.CODE='CA-125'   then\n" +
//                            "                 (select DISTINCT DATA_W693_GORMONU.SA_125 from DIR_ANSW DA inner join DATA_W693_GORMONU ON DA.MOTCONSU_RESP_ID=DATA_W693_GORMONU.MOTCONSU_ID where DA.MOTCONSU_RESP_ID= DIR_ANSW.MOTCONSU_RESP_ID )\n" +
//                            "     when LAB_METHODS.CODE='RTH'   then \n" +
//                            "         (select DISTINCT DATA_W693_GORMONU.TTG from DIR_ANSW DA inner join DATA_W693_GORMONU ON DA.MOTCONSU_RESP_ID=DATA_W693_GORMONU.MOTCONSU_ID where DA.MOTCONSU_RESP_ID= DIR_ANSW.MOTCONSU_RESP_ID )\n" +
//                            "     when LAB_METHODS.CODE='FSH'   then" +
//                            "         (select DISTINCT DATA_W693_GORMONU.FSG from DIR_ANSW DA inner join DATA_W693_GORMONU ON DA.MOTCONSU_RESP_ID=DATA_W693_GORMONU.MOTCONSU_ID where DA.MOTCONSU_RESP_ID= DIR_ANSW.MOTCONSU_RESP_ID )" +
//                            "    when LAB_METHODS.CODE='ATA'   then" +
//                            "         (select DISTINCT DATA_W693_GORMONU.TPO from DIR_ANSW DA inner join DATA_W693_GORMONU ON DA.MOTCONSU_RESP_ID=DATA_W693_GORMONU.MOTCONSU_ID where DA.MOTCONSU_RESP_ID= DIR_ANSW.MOTCONSU_RESP_ID )" +
//                            "when LAB_METHODS.CODE='F4'   then" +
//                            "(select DISTINCT DATA_W693_GORMONU.T4SV from DIR_ANSW DA inner join DATA_W693_GORMONU ON DA.MOTCONSU_RESP_ID=DATA_W693_GORMONU.MOTCONSU_ID where DA.MOTCONSU_RESP_ID= DIR_ANSW.MOTCONSU_RESP_ID )" +
//                            "when LAB_METHODS.CODE='PRL'   then" +
//                            "(select DISTINCT DATA_W693_GORMONU.PROLAKTIN_MAKRO_PROLAKTIN from DIR_ANSW DA inner join DATA_W693_GORMONU ON DA.MOTCONSU_RESP_ID=DATA_W693_GORMONU.MOTCONSU_ID where DA.MOTCONSU_RESP_ID= DIR_ANSW.MOTCONSU_RESP_ID )" +
//                            "when LAB_METHODS.CODE='LH'   then" +
//                            "(select DISTINCT DATA_W693_GORMONU.LG from DIR_ANSW DA inner join DATA_W693_GORMONU ON DA.MOTCONSU_RESP_ID=DATA_W693_GORMONU.MOTCONSU_ID where DA.MOTCONSU_RESP_ID= DIR_ANSW.MOTCONSU_RESP_ID )" +
//                            "when LAB_METHODS.CODE='PRG'   then" +
//                            "(select DISTINCT DATA_W693_GORMONU.PROGESTERON from DIR_ANSW DA inner join DATA_W693_GORMONU ON DA.MOTCONSU_RESP_ID=DATA_W693_GORMONU.MOTCONSU_ID where DA.MOTCONSU_RESP_ID= DIR_ANSW.MOTCONSU_RESP_ID )" +
//                            "when LAB_METHODS.CODE='TES'   then" +
//                            "(select DISTINCT DATA_W693_GORMONU.ANDR_ZHEN_TOBSCH from DIR_ANSW DA inner join DATA_W693_GORMONU ON DA.MOTCONSU_RESP_ID=DATA_W693_GORMONU.MOTCONSU_ID where DA.MOTCONSU_RESP_ID= DIR_ANSW.MOTCONSU_RESP_ID )" +
//                            "when LAB_METHODS.CODE='SBG'   then" +
//                            "(select DISTINCT DATA_W693_GORMONU.ANDR_ZHEN_SSSG from DIR_ANSW DA inner join DATA_W693_GORMONU ON DA.MOTCONSU_RESP_ID=DATA_W693_GORMONU.MOTCONSU_ID where DA.MOTCONSU_RESP_ID= DIR_ANSW.MOTCONSU_RESP_ID )" +
//                            "when LAB_METHODS.CODE='DHS'   then" +
//                            "(select DISTINCT DATA_W693_GORMONU.ANDR_ZHEN_DGEA from DIR_ANSW DA inner join DATA_W693_GORMONU ON DA.MOTCONSU_RESP_ID=DATA_W693_GORMONU.MOTCONSU_ID where DA.MOTCONSU_RESP_ID= DIR_ANSW.MOTCONSU_RESP_ID )" +
//                            "end),\n" +

                            "        FM_ORG.LABEL,\n" +
                            "        PATDIREC.DATE_BIO,\n" +
                            "        --DS_PARAMS.DS_PARAMS_ID,\n" +
                            "        --,PATDIREC.PL_EXAM_ID,\n" +
                            "        --LAB_METHODS.LAB_METHODS_ID,\n" +
//                    "        LAB_METHODS.CODE,\n" +
                            "        VIEW_GRPPRM.LABEL,\n" +
                            "        PATDIREC.BIO_CODE,\n" +
                            "        PAT.POL,\n" +
                            "        PAT.ADRES_PO_PROPISKE\n" +
                            "         from  PATDIREC PATDIREC WITH(NOLOCK)  JOIN PL_EXAM PL_EXAM WITH(NOLOCK)  ON PATDIREC.PL_EXAM_ID = PL_EXAM.PL_EXAM_ID\n" +
                            "                                                 INNER JOIN DIR_ANSW ON PATDIREC.PATDIREC_ID=DIR_ANSW.PATDIREC_ID\n" +
                            "                                                 inner join DIR_SERV ON PATDIREC.PATDIREC_ID =DIR_SERV.PATDIREC_ID\n" +
                            "                                                 inner JOIN FM_DEP ON PATDIREC.MEDECINS_BIO_DEP_ID=FM_DEP.FM_DEP_ID\n" +
                            "                                                 LEFT OUTER JOIN PATIENTS PAT WITH(NOLOCK)  ON PATDIREC.PATIENTS_ID = PAT.PATIENTS_ID\n" +
                            "                                                 INNER JOIN FM_ORG ON FM_DEP.MAIN_ORG_ID=FM_ORG.FM_ORG_ID\n" +
                            "                                                 inner join DS_SERVPARAMS ON DIR_SERV.FM_SERV_ID=DS_SERVPARAMS.FM_SERV_ID\n" +
                            "                                                 inner JOIN DS_PARAMS ON DS_SERVPARAMS.DS_PARAMS_ID=DS_PARAMS.DS_PARAMS_ID\n" +
                            "                                                 JOIN LAB_METHODBIO LAB_METHODBIO WITH(NOLOCK)  ON DS_PARAMS.DS_PARAMS_ID = LAB_METHODBIO.DS_PARAMS_ID\n" +
                            "                                                 JOIN LAB_METHODS LAB_METHODS WITH(NOLOCK)  ON LAB_METHODBIO.LAB_METHODS_ID = LAB_METHODS.LAB_METHODS_ID\n" +
                            "                                                 LEFT OUTER JOIN VIEW_GRPPRM VIEW_GRPPRM WITH(NOLOCK)  ON DS_PARAMS.DS_PARAMS_ID = VIEW_GRPPRM.DS_PARAMS_ID\n" +
                            "    --DS_PARAMS\n" +
                            "where\n" +
                            "        VIEW_GRPPRM.GRPPRM_ID in (1836, 351, 350, 1, 338, 337, 1793, 1258, 340) and " + " --рабочий журнал по вичам\n" +
                            " PATDIREC.QUANTITY_DONE=0 " +
//                            "\n" +
//                            "and LAB_METHODS.CODE not like 'Анализ не выполнен'\n" +
                            "and FM_ORG.FM_ORG_ID= " + filial +
//                            "  and " +
//                            "PATDIREC.BIO_CODE=" + bio_code +
//                            "and PATDIREC.DATE_BIO >dateadd(day,-"+from+",getdate()) and PATDIREC.DATE_BIO <dateadd(day,-"+to+",getdate())"
                            "and PATDIREC.DATE_BIO > '" + from + "' and PATDIREC.DATE_BIO < '" + to + "'"
                            + "and GRPPRM_ID not in (25, 339, 1837, 1895, 1924)"
            );

            while (resultSet.next()) {
                objects.add(new Object[]{
                        resultSet.getObject(1),
                        resultSet.getObject(2),
                        resultSet.getObject(3),
                        resultSet.getObject(4),
                        resultSet.getObject(5),
                        resultSet.getObject(6),
                        resultSet.getObject(7),
                        resultSet.getObject(8),
                        resultSet.getObject(9),
                });

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return objects;
    }

    public List<String> getOncoCytologyCodes(int filial, String from, String to) {
        List<String> objects = new ArrayList<>();
        try (Connection connection = database.getConnection()) {
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("select  " +
//                    "PATDIREC.PATIENTS_ID,\n" +
//                    "                dbo.fNNPlus_Patient (pat.PATIENTS_ID,1),\n" +
                            "                PATDIREC.BIO_CODE\n" +
//                    "                PATDIREC.DATE_BIO,\n" +
//                    "                FM_ORG.LABEL,\n" +
//                    "                'онкоцитология'\n" +
                            "from  PATDIREC PATDIREC WITH(NOLOCK)  JOIN PL_EXAM PL_EXAM WITH(NOLOCK)  ON PATDIREC.PL_EXAM_ID = PL_EXAM.PL_EXAM_ID\n" +
                            "                                      INNER JOIN DIR_ANSW ON PATDIREC.PATDIREC_ID=DIR_ANSW.PATDIREC_ID\n" +
                            "                                      inner join DIR_SERV ON PATDIREC.PATDIREC_ID =DIR_SERV.PATDIREC_ID\n" +
                            "                                      inner JOIN FM_DEP ON PATDIREC.MEDECINS_BIO_DEP_ID=FM_DEP.FM_DEP_ID\n" +
                            "                                      LEFT OUTER JOIN PATIENTS PAT WITH(NOLOCK)  ON PATDIREC.PATIENTS_ID = PAT.PATIENTS_ID\n" +
                            "                                      INNER JOIN FM_ORG ON FM_DEP.MAIN_ORG_ID=FM_ORG.FM_ORG_ID\n" +
                            "where\n" +
                            "                  PATDIREC.PL_EXAM_ID IN (7437, 11891, 9503 )\n" +
                            "                and FM_ORG.FM_ORG_ID=" + filial + "\n" +
                            "\n" +
//                    "  and PATDIREC.DATE_BIO >dateadd(day,-"+from+", getdate()) and PATDIREC.DATE_BIO <dateadd(day,-"+to+", getdate())"
                            "and PATDIREC.DATE_BIO > '" + from + "' and PATDIREC.DATE_BIO < '" + to + "'"
            );

            while (resultSet.next()) {
                objects.add(resultSet.getString(1));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return objects;
    }

    public List<Object[]> getOncoCytology(String code, String from, String to) {
        List<Object[]> objects = new ArrayList<>();
        try (Connection connection = database.getConnection()) {
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("select  PATDIREC.PATIENTS_ID,\n" +
                            "                dbo.fNNPlus_Patient (pat.PATIENTS_ID,1),\n" +
                            "                PATDIREC.BIO_CODE,\n" +
                            "                PATDIREC.DATE_BIO,\n" +
                            "                FM_ORG.LABEL,\n" +
                            "                case when PATDIREC.PL_EXAM_ID in (11891,9503) then 'кариотип'\n" +
                            "                    when PATDIREC.PL_EXAM_ID in (7437) then 'онкоцитология'\n" +
                            "                        end\n" +
                            "from  PATDIREC PATDIREC WITH(NOLOCK)  JOIN PL_EXAM PL_EXAM WITH(NOLOCK)  ON PATDIREC.PL_EXAM_ID = PL_EXAM.PL_EXAM_ID\n" +
                            "                                      INNER JOIN DIR_ANSW WITH(NOLOCK) ON PATDIREC.PATDIREC_ID=DIR_ANSW.PATDIREC_ID\n" +
                            "                                      inner join DIR_SERV WITH(NOLOCK) ON PATDIREC.PATDIREC_ID =DIR_SERV.PATDIREC_ID\n" +
                            "                                      inner JOIN FM_DEP WITH(NOLOCK) ON PATDIREC.MEDECINS_BIO_DEP_ID=FM_DEP.FM_DEP_ID\n" +
                            "                                      LEFT OUTER JOIN PATIENTS PAT WITH(NOLOCK)  ON PATDIREC.PATIENTS_ID = PAT.PATIENTS_ID\n" +
                            "                                      INNER JOIN FM_ORG WITH(NOLOCK) ON FM_DEP.MAIN_ORG_ID=FM_ORG.FM_ORG_ID\n" +
                            "where\n" +
                            "                  PATDIREC.PL_EXAM_ID IN (7437, 11891, 9503 )\n" +
                            "and PATDIREC.QUANTITY_DONE=0" +
//                    "                and FM_ORG.FM_ORG_ID="+
                            "and PATDIREC.BIO_CODE=" + code + " \n" +
//                    " and PATDIREC.DATE_BIO >dateadd(day,- "+from+", getdate()) and PATDIREC.DATE_BIO <dateadd(day,- "+to+", getdate())"
                            "and PATDIREC.DATE_BIO > '" + from + "' and PATDIREC.DATE_BIO < '" + to + "'"
            );

            while (resultSet.next()) {
                objects.add(new Object[]{
                        resultSet.getObject(1),
                        resultSet.getObject(2),
                        resultSet.getObject(3),
                        resultSet.getObject(4),
                        resultSet.getObject(5),
                        resultSet.getObject(6)
                });

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return objects;
    }

    public List<Object[]> chek(String done, String bio_code, Integer GRPPRM) throws SQLException {
        List<Object[]> objects = new ArrayList<>();
        try (Connection connection = database.getConnection()) {


            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("select  --PATDIREC.PATIENTS_ID,\n" +
                    "       -- dbo.fNNPlus_Patient (pat.PATIENTS_ID,1),\n" +
                    "--pat.nom+' '+substring(pat.PRENOM,1,1)+'. '+substring(pat.PATRONYME,1,1)+'.',\n" +
                    "\n" +
                    "--         (case\n" +
                    "--              when kontengent=0 then '108.б - доноры биологических жидкостей'\n" +
                    "--              when kontengent=1 then '109.а - беременные при взятии на учет'\n" +
                    "--              when kontengent=2 then '109.б - беременные на сроке 25-26 и 38-39 недель'\n" +
                    "--              when kontengent=3 then '116 б - в соответствии со стандартами мед. помощи (ВРТ, перед манипуляциями,добровольное обследование)'\n" +
                    "--              when kontengent=4 then '110 - половые партнеры беременных'\n" +
                    "--              when kontengent=5 then '132.б - профилактический медосмотр'\n" +
                    "--              when kontengent=6 then '132.в - медицинские аварийные ситуации'\n" +
                    "--             end),\n" +
                    "--         DIR_ANSW.MOTCONSU_RESP_ID\n" +
                    "--         ,\n" +
                    "        (case\n" +
                    "             when LAB_METHODS.CODE='А/т к ВИЧ 1,2 +А/г' then\n" +
                    "                 (select DISTINCT AT_K_VICH_1_2 from DIR_ANSW DA inner join DATA_W693_VICH_SIFIL_GEPAT ON DA.MOTCONSU_RESP_ID=DATA_W693_VICH_SIFIL_GEPAT.MOTCONSU_ID where DA.MOTCONSU_RESP_ID= DIR_ANSW.MOTCONSU_RESP_ID )\n" +
                    "             when LAB_METHODS.CODE='HBsAg' then\n" +
                    "                 (select DISTINCT HBS_AG from DIR_ANSW DA inner join DATA_W693_VICH_SIFIL_GEPAT ON DA.MOTCONSU_RESP_ID=DATA_W693_VICH_SIFIL_GEPAT.MOTCONSU_ID where DA.MOTCONSU_RESP_ID= DIR_ANSW.MOTCONSU_RESP_ID )\n" +
                    "             when LAB_METHODS.CODE='Ат .к. HCV' then\n" +
                    "                 (select DISTINCT AT_K_HCV from DIR_ANSW DA inner join DATA_W693_VICH_SIFIL_GEPAT ON DA.MOTCONSU_RESP_ID=DATA_W693_VICH_SIFIL_GEPAT.MOTCONSU_ID where DA.MOTCONSU_RESP_ID= DIR_ANSW.MOTCONSU_RESP_ID )\n" +
                    "             when LAB_METHODS.CODE='Syphilis ИФА' then\n" +
                    "                 (select DISTINCT SIPHILIS_TPHA_TEST from DIR_ANSW DA inner join DATA_W693_VICH_SIFIL_GEPAT ON DA.MOTCONSU_RESP_ID=DATA_W693_VICH_SIFIL_GEPAT.MOTCONSU_ID where DA.MOTCONSU_RESP_ID= DIR_ANSW.MOTCONSU_RESP_ID )\n" +
                    "             when LAB_METHODS.CODE='Сифилис МРП' then\n" +
                    "                 (select DISTINCT SIPHILIS_MR from DIR_ANSW DA inner join DATA_W693_VICH_SIFIL_GEPAT ON DA.MOTCONSU_RESP_ID=DATA_W693_VICH_SIFIL_GEPAT.MOTCONSU_ID where DA.MOTCONSU_RESP_ID= DIR_ANSW.MOTCONSU_RESP_ID )\n" +
                    "when LAB_METHODS.CODE='Rub-G' then\n" +
                    "(select DISTINCT DATA_W693_VUI.DYN_72840_72913 from DIR_ANSW DA inner join DATA_W693_VUI ON DA.MOTCONSU_RESP_ID=DATA_W693_VUI.MOTCONSU_ID where DA.MOTCONSU_RESP_ID= DIR_ANSW.MOTCONSU_RESP_ID )\n" +
                    "when LAB_METHODS.CODE='Rub- M' then\n" +
                    "(select DISTINCT DATA_W693_VUI.DYN_72840_72914 from DIR_ANSW DA inner join DATA_W693_VUI ON DA.MOTCONSU_RESP_ID=DATA_W693_VUI.MOTCONSU_ID where DA.MOTCONSU_RESP_ID= DIR_ANSW.MOTCONSU_RESP_ID )\n" +
                    "when LAB_METHODS.CODE='cHSP60-Ig G(белок тепл.шока и' then\n" +
                    "(select DISTINCT DATA_W693_VUI.DYN_72840_72917 from DIR_ANSW DA inner join DATA_W693_VUI ON DA.MOTCONSU_RESP_ID=DATA_W693_VUI.MOTCONSU_ID where DA.MOTCONSU_RESP_ID= DIR_ANSW.MOTCONSU_RESP_ID )\n" +
                    "when LAB_METHODS.CODE='Clam-A' then\n" +
                    "(select DISTINCT DATA_W693_VUI.DYN_72840_72916 from DIR_ANSW DA inner join DATA_W693_VUI ON DA.MOTCONSU_RESP_ID=DATA_W693_VUI.MOTCONSU_ID where DA.MOTCONSU_RESP_ID= DIR_ANSW.MOTCONSU_RESP_ID )\n" +
                    "when LAB_METHODS.CODE='Chlam-G' then\n" +
                    "(select DISTINCT DATA_W693_VUI.DYN_72840_72915 from DIR_ANSW DA inner join DATA_W693_VUI ON DA.MOTCONSU_RESP_ID=DATA_W693_VUI.MOTCONSU_ID where DA.MOTCONSU_RESP_ID= DIR_ANSW.MOTCONSU_RESP_ID )" +

                    "            end),\n" +
                    "--         ,FM_DEP.MAIN_ORG_ID,\n" +
                    "--         FM_ORG.LABEL,\n" +
                    "--         PATDIREC.PATDIREC_ID,\n" +
                    "--         PATDIREC.DATE_BIO,\n" +
                    "--DS_PARAMS.DS_PARAMS_ID,\n" +
                    "--,PATDIREC.PL_EXAM_ID,\n" +
                    "        --LAB_METHODS.LAB_METHODS_ID,\n" +
                    "        LAB_METHODS.CODE\n" +
                    "--         PATDIREC.BIO_CODE,\n" +
                    "--         PAT.POL\n" +
                    "\n" +
                    "        ,* from  PATDIREC PATDIREC WITH(NOLOCK)  JOIN PL_EXAM PL_EXAM WITH(NOLOCK)  ON PATDIREC.PL_EXAM_ID = PL_EXAM.PL_EXAM_ID\n" +
                    "                                                 INNER JOIN DIR_ANSW WITH(NOLOCK) ON PATDIREC.PATDIREC_ID=DIR_ANSW.PATDIREC_ID\n" +
                    "                                                 inner join DIR_SERV WITH(NOLOCK) ON PATDIREC.PATDIREC_ID =DIR_SERV.PATDIREC_ID\n" +
                    "                                                 inner JOIN FM_DEP WITH(NOLOCK) ON PATDIREC.MEDECINS_BIO_DEP_ID=FM_DEP.FM_DEP_ID\n" +
                    "                                                 LEFT OUTER JOIN PATIENTS PAT WITH(NOLOCK)  ON PATDIREC.PATIENTS_ID = PAT.PATIENTS_ID\n" +
                    "                                                 INNER JOIN FM_ORG WITH(NOLOCK) ON FM_DEP.MAIN_ORG_ID=FM_ORG.FM_ORG_ID\n" +
                    "                                                 inner join DS_SERVPARAMS WITH(NOLOCK) ON DIR_SERV.FM_SERV_ID=DS_SERVPARAMS.FM_SERV_ID\n" +
                    "                                                 inner JOIN DS_PARAMS WITH(NOLOCK) ON DS_SERVPARAMS.DS_PARAMS_ID=DS_PARAMS.DS_PARAMS_ID\n" +
                    "                                                 JOIN LAB_METHODBIO LAB_METHODBIO WITH(NOLOCK)  ON DS_PARAMS.DS_PARAMS_ID = LAB_METHODBIO.DS_PARAMS_ID\n" +
                    "                                                 JOIN LAB_METHODS LAB_METHODS WITH(NOLOCK)  ON LAB_METHODBIO.LAB_METHODS_ID = LAB_METHODS.LAB_METHODS_ID\n" +
                    "                                                 LEFT OUTER JOIN VIEW_GRPPRM VIEW_GRPPRM WITH(NOLOCK)  ON DS_PARAMS.DS_PARAMS_ID = VIEW_GRPPRM.DS_PARAMS_ID\n" +
                    "--DS_PARAMS\n" +
                    "where\n" +
                    "        VIEW_GRPPRM.GRPPRM_ID =" + GRPPRM + " --рабочий журнал по вичам\n" +
                    "  --and PATDIREC.QUANTITY_DONE=0 --анализ не выполнен\n" +
                    "  and  PATDIREC.BIO_CODE= -- код забора\n" + bio_code +
                    "  and PATDIREC.DATE_BIO >dateadd(day,-PL_EXAM.VAL_PERIOD,getdate()) --\n" +
                    "--and FM_DEP.MAIN_ORG_ID=20 --филиал выполнивший забор биоматериала");

            while (resultSet.next()) {
                objects.add(new Object[]{
                        resultSet.getObject(1),
                        resultSet.getObject(2),
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return objects;
    }

    //    public Object[] getCredentials(String s) throws SQLException {
//
//        Connection connection = database.getConnection();
//        Statement statement = connection.createStatement();
//
//        ResultSet resultSet = statement.executeQuery("select name, password_hash\n" +
//                "from sys.sql_logins\n" +
//                "where name =" +s);
//
//         return new Object[] {
//                 resultSet.getObject(1),
//                 resultSet.getObject(2)
//         };
//    }
    public void registerUser(User user) {

        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);

    }

    @Override
//    public User loadUserByUsername(String s) {
//    Set<Role> roles =  new HashSet<>();
//        roles.add(new Role("USER"));
//        roles.add(new Role("ADMIN"));
//        User user = new User("grebnev_a", "1072005", roles);
//        registerUser(user);
//
//        users.add(user);
//        return users
//                .stream()
//                .filter(n -> n.getName().equals(s)).findFirst().get();
//    }

    public User loadUserByUsername(String s) {

        User user = new User();
        Set<Role> roles = new HashSet<>();
        try (Connection connection = database.getConnection()) {

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM LABOR_USERS where nameUser=" + "'" + s + "'");
            while (resultSet.next()) {
                roles.add(new Role(resultSet.getString(4)));
                user.setName(resultSet.getString(2));
                user.setPassword(resultSet.getString(3));
                user.setRoles(roles);
            }


        } catch (Exception ex) {
            System.out.println(ex);
        }
        return user;
    }

    @Override
    public void saveUser(String name, String password, String role) {
        User user = new User(name, password, null);
        registerUser(user);
        try (Connection connection = database.getConnection()) {

            Statement statement = connection.createStatement();
            statement.executeUpdate("insert LABOR_USERS (nameUser, passwordUser, roleUser)" +
                    "VALUES (" + "'" + user.getName() + "'" + ", " + "'" + user.getPassword() + "'" + "," + "'" + role + "'" + ")");
            System.out.println("User с именем –" + name + " добавлен в базу данных");

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public List<User> getAllUsers() {
        List<User> allUsers = new ArrayList<>();
        Set<Role> roles = new HashSet<>();

        try (Connection connection = database.getConnection()) {

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM LABOR_USERS");
            while (resultSet.next()) {
                roles.add(new Role(resultSet.getString(4)));
                allUsers.add(new User(resultSet.getLong(1), resultSet.getString(2),
                        resultSet.getString(3), roles));
                roles.clear();
            }


        } catch (Exception ex) {
            System.out.println(ex);
        }
        return allUsers;
    }

    public void removeUserById(long id) {
        try (Connection connection = database.getConnection()) {

            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM LABOR_USERS WHERE Id =" + id);

        } catch (Exception ex) {
            System.out.println(ex);
        }

    }

    @Transactional
    public void saveCommon(CommonAnalysis ca) {
        entityManager.persist(ca);

    }

    @Transactional
    public void saveAssigment(Assignment a) {
        entityManager.persist(a);

    }

    public List<CommonAnalysis> getCommon() {

        return entityManager.createQuery("select ca from CommonAnalysis ca").getResultList();
    }

    public List<CommonAnalysis> getCommonByStatus() {

        return entityManager.createQuery("select ca from CommonAnalysis ca where ca.status=true ").getResultList();
    }

    @Transactional
    public void deleteCommon(long id) {
        CommonAnalysis ca = entityManager.find(CommonAnalysis.class, id);
        entityManager.remove(ca);
    }

    @Transactional
    public void updateByLabel(String label) {
        for (CommonAnalysis ca : getCommon()
                .stream().filter(a -> (a.getLabel().equals(label) && !a.isStatus()))
//                .filter(a->!a.isStatus())
                .collect(Collectors.toList())) {
            ca.setStatus(true);
            entityManager.merge(ca);
        }
    }

    @GetMapping
    public List<CommonAnalysis> getByLabel(String label, boolean status) {
        return getCommon().stream()
                .filter(a -> (a.getLabel().equals(label) && a.isStatus() == status))
                .collect(Collectors.toList());
    }

//    public void deleteCommon() {
//        entityManager.createNativeQuery("delete from common_analysis ").executeUpdate();
//    }

    public Set<String> getColumnIndexes(List<CommonAnalysis> analyses, Set<String> indets) {
//        Map<String, Integer> columnIndexes = new HashMap<>();
        Set<String> commonSet = new HashSet<>();
        int index = 0;
        for (CommonAnalysis ca : analyses) {
            commonSet.addAll(
                    ca.getAssignments()
                            .stream()
                            .map(Assignment::getName)
                            .collect(Collectors.toSet()));
        }
        indets.retainAll(commonSet);
//        for (String s : indets) {
//            columnIndexes.put(s, index++);
//        }
        return indets;
    }

    public Document getDoc(List<CommonAnalysis> analyses, Set<String> indets, Document doc) {

        Set<String> map = getColumnIndexes(analyses, indets);
//        List<Integer> integers = new ArrayList<>();
//        for (int i = 0; i < map.size(); i++) {
//            integers.add(0);
//        }

        List<String> columnStrings = new ArrayList<>(map).stream().sorted().collect(Collectors.toList());

        int count = 1;
        Element root = doc.createElement("report");
        Element element = doc.createElement("column");
        element.setAttribute("data", "ЭМК");
        Element elementFio = doc.createElement("column");
        elementFio.setAttribute("data", "ФИО");
        Element elementDate = doc.createElement("column");
        elementDate.setAttribute("data", "Дата забора");
        Element elementCode = doc.createElement("column");
        elementCode.setAttribute("data", "Кбм");
        Element elementCounter = doc.createElement("column");
        elementCounter.setAttribute("data", "№");
        root.appendChild(element);
        root.appendChild(elementFio);
        root.appendChild(elementDate);
        root.appendChild(elementCode);
        root.appendChild(elementCounter);

        for (String s : columnStrings) {
            Element e = doc.createElement("column");
            switch (s) {
                case ("***Кардиолипиновые антитела к бледной трепонеме (сифилис)"):
                    s = "МРП";
                    break;
                case ("**Антиген \"S\" вируса гепатита B          (HBs Ag)"):
                    s = "HBs Ag";
                    break;
                case ("**Антитела к бледной трепонеме (сифилис) (IgM и IgG)"):
                    s = "Syph ИФА";
                    break;
                case ("**Антитела к вирусу гепатита С (анти-HCV)   (суммарные)"):
                    s = "anti-HCV";
                    break;
                case ("**Антитела к вирусу иммунодефицита человека 1,2 (ВИЧ1,2)+антиген р24"):
                    s = "А/т к ВИЧ 1,2 +А/г";
                    break;
                case ("Антитела к вирусу краснухи Ig G"):
                    s = "Rub-G";
                    break;
                case ("Антитела к вирусу краснухи Ig M"):
                    s = "Rub- M";
                    break;
                case ("Антитела к хламидии трахоматис IgA"):
                    s = "Clam-A";
                    break;
                case ("Антитела к хламидии трахоматис IgG"):
                    s = "Chlam-G";
                    break;
                case ("Антитела к БТШ хламидии трахоматис IgG"):
                    s = "cHSP60-Ig G(белок тепл.шока и";
                    break;
                default:
                    s = s;
                    break;
            }
            e.setAttribute("data", s);
            root.appendChild(e);
        }
        Element filial = doc.createElement("column");
        filial.setAttribute("data", "филиал");
        root.appendChild(filial);

        for (CommonAnalysis ca : analyses) {
            int k = 0;
            Set<String> nameSet =
                    ca.getAssignments().stream().map(Assignment::getName).collect(Collectors.toSet());
            nameSet.retainAll(map);

            if (nameSet.size() > 0) {
                Element item = doc.createElement("item");
                Element emc = doc.createElement("data");
                emc.setAttribute("value", ca.getEmc());
                Element fio = doc.createElement("data");
                fio.setAttribute("value", ca.getFio());
                Element date = doc.createElement("data");
                date.setAttribute("value", ca.getDate_bio());
                Element code = doc.createElement("data");
                code.setAttribute("value", ca.getCode());
                Element counter = doc.createElement("data");
                counter.setAttribute("value", String.valueOf(count));
                item.appendChild(emc);
                item.appendChild(fio);
                item.appendChild(date);
                item.appendChild(code);
                item.appendChild(counter);
                count++;

//                System.out.println(ca.getFio());
//                for (String a : nameSet) {
//                    Integer asi=0;
//                    try {
//                        asi = map.get(a);
//                        integers.set(asi, 1);
//
//
//                    } catch (NullPointerException ignored) {
//
//                    }
//                    System.out.println(a +" "+asi);
//                }

                for (int i = 0; i < columnStrings.size(); i++) {
                    if (nameSet.contains(columnStrings.get(i))) {
//                        integers.set(i,1);
                        Element e = doc.createElement("data");
                        e.setAttribute("value", "1");
                        item.appendChild(e);
                    } else {
                        Element e = doc.createElement("data");
                        e.setAttribute("value", "");
                        item.appendChild(e);
                    }
                }

//                for (int i = 0; i < integers.size(); i++) {
//                    Element e = doc.createElement("data");
//                    e.setAttribute("value", String.valueOf(integers.get(i)).equals("0") ? "" : "1");
//                    integers.set(i, 0);
//                    item.appendChild(e);
//
//                }

                root.appendChild(item);
                Element filialValue = doc.createElement("data");
                filialValue.setAttribute("value", ca.getLabel());
                item.appendChild(filialValue);
            }


        }

        doc.appendChild(root);
        return doc;

    }

    @Override
    public Document getGormDoc(List<Analysis> analyses, Set<String> indets, Document doc) {

//        Element root = doc.createElement("report");
//        Element element = doc.createElement("column");
//        element.setAttribute("data", "ЭМК");
//        Element elementFio = doc.createElement("column");
//        elementFio.setAttribute("data", "ФИО");
//        Element elementDate = doc.createElement("column");
//        elementDate.setAttribute("data", "Дата забора");
//        Element elementCode = doc.createElement("column");
//        elementCode.setAttribute("data", "Кбм");
//        Element elementCounter = doc.createElement("column");
//        elementCounter.setAttribute("data", "№");
//
//        root.appendChild(element);
//        root.appendChild(elementFio);
//        root.appendChild(elementDate);
//        root.appendChild(elementCode);
//        root.appendChild(elementCounter);
//
//        for (Analysis a: analyses) {
//            Element item = doc.createElement("item");
//            if (indets.contains(a.getHiv())) {Element element1 = doc.createElement("data"); element1.setAttribute("value", "1"); item.appendChild(element1)}
//        }

        return null;
    }

    public List<String> getBCAsignments(String in) {
        List<String> objects = new ArrayList<>();

        try (Connection connection = database.getConnection()) {
            ResultSet rs = connection.createStatement()
                    .executeQuery("select  distinct  LABEL from VIEW_GRPPRM\n" +
                            "where GRPPRM_ID in (" + in + ") ");
            while (rs.next()) {
                objects.add(rs.getString("LABEL"));
            }

        } catch (Exception ignored) {
        }

        return objects;

    }

    public Long getMaxId() {
        return (Long) entityManager.createQuery("select MAX (ca.id) from CommonAnalysis ca").getSingleResult();
    }

    public Integer createMotconsu(String PatientsId, String medecinsId) {
        try (Connection connection = database.getConnection()) {
            Statement st = connection.createStatement();
            int i = 0;

            String query = "declare " +
                    "  @pacient_ int=" + PatientsId + ", " +
                    "  @medecins_ID int=" + medecinsId + ", " +
                    "  @fm_dep_id int=189 /*лаборатория Серова*/" +
//                    "  @motconsu_id int="+MOTCONSU_ID+", /*текущая запись*/ " +
                    "/*Переменные для скрипта*/ " +


                    "declare @MOTCONSU_ int, " +
                    "  @meddep_id int " +
                    "set @meddep_id =(select meddep_id FROM meddep where MEDECINS_id =@MEDECINS_id and FM_DEP_ID=@fm_dep_id) " +
                    "/*Шаг 1 Создаем запись в ЭМК Направления */" +
                    "exec up_get_nn_id  'MOTCONSU', 1, @MOTCONSU_ output " +
                    "insert into MOTCONSU " +
                    "(MOTCONSU_ID, " +
                    "DATE_CONSULTATION,  " +
                    "MODIFY_DATE_TIME,  " +
                    "MODELS_ID,  " +
                    "MEDECINS_MODIFY_ID,  " +
                    "PATIENTS_ID,  " +
                    "KRN_CREATE_USER_ID,  " +
                    "REC_STATUS,  " +
                    "FM_DEP_ID,  " +
                    "CREATE_DATE_TIME,  " +
                    "MEDECINS_CREATE_ID,  " +
                    "MEDECINS_ID,  " +
                    "MEDDEP_ID,  " +
                    "PUBLISHED,  " +
                    "CHANGED,  " +
                    "CONS_STATUS,  " +
                    "KRN_MODIFY_USER_ID,  " +
                    "KRN_MODIFY_DATE,  " +
                    "KRN_CREATE_DATE)  " +
                    "values(@MOTCONSU_, " +
                    "getdate(), " +
                    "getdate(), " +
                    "453, " +
                    "@medecins_ID, " +
                    "@pacient_, " +
                    "@medecins_ID, " +
                    "'W', " +
                    "@fm_dep_id, " +
                    "getdate(), " +
                    "@medecins_ID, " +
                    "@medecins_ID, " +
                    "@meddep_id, " +
                    "0, " +
                    "0, " +
                    "'N', " +
                    "@medecins_ID, " +
                    "getdate(), " +
                    "getdate()) " +
                    "return @MOTCONSU_";
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                i = rs.getInt(1);
            }
            return i;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void createPatdirec(String PatientsId, String medecinsId, String MOTCONSU_ID, int exam, int serv, int patdirec) {
        String DIR_ANSW_ID = null;
        String BIO_CODE = null;
        try (Connection connection = database.getConnection()) {
            Statement st = connection.createStatement();

            String query = "declare " +
                    "  @pacient_ int=" + PatientsId + ", " +
                    "  @medecins_ID int=" + medecinsId + ", " +
                    "  @fm_dep_id int=189, /*лаборатория Серова*/" +
                    "  @exam_ int = " + exam + ", " +
                    "  @serv_ int = " + serv + ", " +
                    "  @filial_ int=56, " +
//                    "  @motconsu_id int="+MOTCONSU_ID+", /*текущая запись*/ " +
                    "/*Переменные для скрипта*/ " +


                    "declare @MOTCONSU_ int = " + MOTCONSU_ID + ", " +
                    "  @PATDIR_ int, " +
                    "  @DirServ_ int, " +
                    "  @bill_ int, " +
                    "  @billdet_ int, " +
                    "  @Cena_ int, " +
                    "  @SUMM_ int, " +
                    "  @FM_BP_ int, " +
                    "  @category_ int, " +
                    "  @DIRANSW_ int, " +
                    "  @inv_id_ int , " +
                    "  @meddep_id int " +
                    "set @meddep_id =(select meddep_id FROM meddep where MEDECINS_id =@MEDECINS_id and FM_DEP_ID=@fm_dep_id) " +
                    "/*Шаг 1 Создаем запись в ЭМК Направления */" +

                    "/*--Шаг 2: Создаем направление на услугу*/ " +
                    "exec up_get_nn_id  'PATDIREC', 1, @PATDIR_ output " +
                    "insert into PATDIREC " +
                    "(PATDIREC_ID, " +
                    "DIR_STATE,  " +
                    "PATIENTS_ID, " +
                    "MEDECINS_CREATOR_ID, " +
                    "MOTCONSU_ID, " +
                    "PL_EXAM_ID, " +
                    "QUANTITY,  " +
                    "BIO_TYPE, " +
                    "CITO, " +
                    "FM_INTORG_ID, " +
                    "STANDARTED, " +
                    "CREATE_DATE_TIME, " +
                    "CANCELLED, " +
                    "BEGIN_DATE_TIME, " +
                    "KRN_CREATE_USER_ID, " +
                    "MANIPULATIVE, " +
                    "KEEP_INTAKE_TIME, " +
                    "PATDIREC_KIND, " +
                    "NEED_OPEN_EDITOR, " +
                    "KRN_MODIFY_USER_ID, " +
                    "KRN_MODIFY_DATE, " +
                    "KRN_CREATE_DATE) " +
                    "values( " +
                    "@PATDIR_, " +
                    "1, " +
                    "@pacient_, /*-- ЭМК пациента подставите из ЛК*/ " +
                    "@medecins_ID, " +
                    "@MOTCONSU_, " +
                    "@exam_, /*-- зависит от того, что мы хотим продать из таблицы PL_EXAM*/ " +
                    "1, " +
                    "'', " +
                    "0, " +
                    "@filial_, " +
                    "0, " +
                    "getdate(), " +
                    "0, " +
                    "getdate(), " +
                    "@medecins_ID, " +
                    "0, " +
                    "0, " +
                    "0, " +
                    "0, " +
                    "@medecins_ID, " +
                    "getdate(), " +
                    "getdate()) " +

                    "/*--Шаг 4: Создаем талон, в таблице FM_BILL связывают запись в ЭМК созданную на 1 шаге с талоном.*/ " +
                    "exec up_get_nn_id  'FM_BILL', 1,@bill_ output " +
                    "insert into FM_BILL " +
                    "(FM_BILL_ID, " +
                    "BILL_DATE, " +
                    "PATIENTS_ID, " +
                    "MOTCONSU_DIR_ID, " +
                    "MOTCONSU_MAIN_ID, " +
                    "MEDECINS_CREATE_ID, " +
                    "MEDECINS_MODIFY_ID, " +
                    "DATE_MODIFY, " +
                    "DATE_CREATE, " +
                    "FM_ORG_ID, " +
                    "INSURANCE_TYPE, " +
                    "KRN_CREATE_USER_ID, " +
                    "IS_PREPAID, " +
                    "BILL_TYPE, " +
                    "ESTIMATE_CHILD, " +
                    "ERR_SAVED, " +
                    "KRN_MODIFY_USER_ID, " +
                    "KRN_MODIFY_DATE, " +
                    "KRN_CREATE_DATE) " +
                    "values( " +
                    "@bill_, " +
                    "dbo.date(getdate()), " +
                    "@pacient_, " +
                    "@MOTCONSU_, " +
                    "@MOTCONSU_, " +
                    "@medecins_ID, " +
                    "@medecins_ID, " +
                    "getdate(), " +
                    "getdate(), " +
                    "@filial_, " +
                    "'D', " +
                    "@medecins_ID, " +
                    "0, " +
                    "5, " +
                    "0, " +
                    "1, " +
                    "@medecins_ID, " +
                    "getdate(), " +
                    "getdate()) " +
                    " " +
                    " " +
                    "/*17218 геп в 17219 геп с 17901 вич*/ " +

                    "if @serv_  is not null begin  exec dbo.plOplataUslugiBilldetDiscount100 @pacient_,@PATDIR_,@filial_,1,@bill_, @serv_ end " +

                    "update FM_BILL set ERR_SAVED = 0 where ERR_SAVED = 1 and FM_BILL_ID = @bill_ " +

                    "/*--Шаг 7 */ " +
                    "exec up_get_nn_id  'DIR_ANSW', 1, @DIRANSW_ output " +
                    "insert DIR_ANSW " +
                    "(DIR_ANSW_ID, " +
                    " PATDIREC_ID,    " +
                    " FM_BILL_ID, " +
                    " ANSW_STATE, " +
                    " CANCEL_PAY) " +
                    "values(  " +
                    "@DIRANSW_, " +
                    "@PATDIR_, " +
                    "@bill_, " +
                    "0, " +
                    "0 " +
                    ") " +
                    "declare @code varchar(10), " +
                    "  @DATE_BIO datetime, " +
                    "  @MEDECINS_BIO_ID int, " +
                    "  @MEDECINS_BIO_DEP_ID int " +
                    "SELECT @code=P.BIO_CODE , @DATE_BIO= P.DATE_BIO, @MEDECINS_BIO_ID = P.MEDECINS_BIO_ID,@MEDECINS_BIO_DEP_ID=P.MEDECINS_BIO_DEP_ID " +
                    "from PATDIREC P " +
//                    "inner join DIR_ANSW D ON P.PATDIREC_ID=D.PATDIREC_ID  " +
//                    "INNER JOIN MOTCONSU M ON M.MOTCONSU_ID=D.MOTCONSU_RESP_ID " +
                    "where P.PATDIREC_ID= " + patdirec +

                    "/*Новый контейнер для забора*/ " +
                    " declare  " +
                    "@LAB_CONTS_ID int, " +
                    "@LAB_PATDIRECCONTS_ID int " +

                    "exec up_get_nn_id  'LAB_CONTS', 1, @LAB_CONTS_ID output " +
                    "insert into LAB_CONTS " +
                    "(LAB_CONTS_ID, PATIENTS_ID, CODE, BIO_DATE, STATE, DATE_EXP, MEDECINS_BIO_ID, MEDECINS_BIO_DEP_ID, KRN_CREATE_USER_ID, PATDIR_STATE, MEDDEP_BIO_ID) " +
                    "values(@LAB_CONTS_ID, @pacient_, @code, dateadd(MINUTE,1,@DATE_BIO), 0, dateadd(MINUTE,1,@DATE_BIO), @MEDECINS_BIO_ID, @MEDECINS_BIO_DEP_ID, @medecins_ID, 'B', @meddep_id) " +

                    "exec up_get_nn_id  'LAB_PATDIRECCONTS', 1, @LAB_PATDIRECCONTS_ID output " +
                    "insert into LAB_PATDIRECCONTS " +
                    "(LAB_PATDIRECCONTS_ID, LAB_CONTS_ID, PATDIREC_ID, KRN_CREATE_USER_ID) " +
                    "values(@LAB_PATDIRECCONTS_ID, @LAB_CONTS_ID, @PATDIR_, @medecins_ID) " +

                    "update PATDIREC set  " +
                    "BIO_CODE = @code " +
                    ",DATE_BIO = dateadd(MINUTE,1,@DATE_BIO) " +
                    ",MEDECINS_BIO_ID = @MEDECINS_BIO_ID " +
                    ",MEDECINS_BIO_DEP_ID = @MEDECINS_BIO_DEP_ID " +
                    ",STATE = 'B', " +
//                    "KRN_MODIFY_DATE = convert(varchar(30),dbo.fnGetDateLocal(),20), " +
                    "KRN_MODIFY_USER_ID = @medecins_ID " +
                    "where PATDIREC_ID = @PATDIR_ " +

                    "/*--Шаг 7 убираем флаг ошибки в талоне */ " +
                    "update FM_BILL set ERR_SAVED = 0 where ERR_SAVED = 1 and FM_BILL_ID =@bill_ ";

//            String query = "exec dbo.plOplataUslugiPovtorVICH "+PatientsId+", "+medecinsId+", 148, 12373,56, "+MOTCONSU_ID+", "+ vich +", "+ gepC+", "+ gepB;
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.executeUpdate();

        } catch (Exception e) {
            System.err.println("Ошибка! ");
            System.err.println(e.getMessage());
        }

    }

    public List<AchtvCounter> getACHTVData(String end) throws SQLException {
        List<AchtvCounter> achtvCounters = new ArrayList<>();

        try (Connection connection = database.getConnection()) {
            Statement st = connection.createStatement();

            ResultSet rst = st.executeQuery("declare @table table (datecons date, total int, higher int,  pravda varchar(25))\n" +
                    "declare\n" +
                    "    @curr_date date='01.09.2022',\n" +
                    "    @data_cons datetime,\n" +
                    "    @total int=0,\n" +
                    "    @higher int=0,\n" +
                    "    @fm_intorg int,\n" +
                    "    @tru varchar(25)\n" +
                    " declare DATA cursor local for\n" +
                    " select count(cast(DBA.DATE_CONSULTATION as date)), cast(DBA.DATE_CONSULTATION as date),  P.FM_INTORG_ID,\n" +
                    "       (select count(ACHTV) from DATA_BLOOD_ANALYS_COAGUL DBA1 WITH(NOLOCK)\n" +
                    "                     join MOTCONSU M1 WITH(NOLOCK) on M1.MOTCONSU_ID=DBA1.MOTCONSU_ID\n" +
                    "                     join DIR_ANSW DA1 WITH(NOLOCK) on MOTCONSU_RESP_ID= M1.MOTCONSU_ID\n" +
                    "                     join PATDIREC P1 WITH(NOLOCK) on P1.PATDIREC_ID=DA1.PATDIREC_ID where cast(DBA1.DATE_CONSULTATION as date)=cast(DBA.DATE_CONSULTATION as date)\n" +
                    "                                                                       and cast (replace(DBA1.ACHTV, ',', '.') as float)>37.2\n" +
                    "           and P1.FM_INTORG_ID=P.FM_INTORG_ID)\n" +
                    "\n" +
                    " from DATA_BLOOD_ANALYS_COAGUL DBA WITH(NOLOCK)\n" +
                    "                                                              join MOTCONSU M WITH(NOLOCK) on M.MOTCONSU_ID=DBA.MOTCONSU_ID\n" +
                    "                                                              join DIR_ANSW DA WITH(NOLOCK) on MOTCONSU_RESP_ID= M.MOTCONSU_ID\n" +
                    "                                                              join PATDIREC P WITH(NOLOCK) on P.PATDIREC_ID=DA.PATDIREC_ID\n" +
                    "--                                         join FM_DEP FD on P.MEDECINS_BIO_ID = FD.MEDECINS_ID\n" +
                    "\n" +
                    "\n" +
                    " where cast(DBA.DATE_CONSULTATION as date) >= '01.09.2022'\n" +
                    " group by  cast(DBA.DATE_CONSULTATION as date), P.FM_INTORG_ID\n" +
                    "\n" +
                    " open DATA\n" +
                    " fetch next from DATA into\n" +
                    "    @total, @data_cons, @fm_intorg, @higher\n" +
                    " while @@fetch_status=0\n" +
                    "    begin\n" +
                    "        insert into @table values (@data_cons, @total, @higher,  (case\n" +
                    "                                                                    when @fm_intorg=53 or @fm_intorg=56 then 'Казань'\n" +
                    "                                                                    when @fm_intorg=20 then 'Ижевск'\n" +
                    "                                                                    when @fm_intorg=43 then 'Челны'\n" +
                    "                                                                    when @fm_intorg=42 then 'Киров'\n" +
                    "                                                                        end   ))\n" +
                    "        fetch next from DATA into\n" +
                    "            @total, @data_cons, @fm_intorg, @higher\n" +
                    "    end\n" +
                    " close DATA\n" +
                    " deallocate DATA \n" +
                    end);
            while (rst.next()) {
                achtvCounters.add(new AchtvCounter(rst.getDate(1).toLocalDate(), rst.getInt(2), rst.getInt(3), rst.getString(4)));
            }
            return achtvCounters;
        }
    }
}
