package com.example.demo.dao;

import com.example.demo.config.Database;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository("UptakeDao")
public class UptakeDaoImpl implements UptakeDao {
    Database database = new Database();


    EntityManager entityManager;
    PasswordEncoder passwordEncoder;
    List<User> users = new ArrayList<>();


    @Autowired
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    @Autowired
    public void setPasswordEncoder(@Lazy PasswordEncoder passwordEncoder) {this.passwordEncoder = passwordEncoder;}


    public  List<Object[]> getData(String done, String bio_code, Integer GRPPRM) throws SQLException {
        List<Object[]> objects = new ArrayList<>();
        Connection connection = database.getConnection();
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
                "VIEW_GRPPRM.GRPPRM_ID ="+ GRPPRM+" --рабочий журнал по вичам\n" +
                done +
                "and  PATDIREC.BIO_CODE= -- код забора\n" + bio_code+
                "and PATDIREC.DATE_BIO >dateadd(day,-PL_EXAM.VAL_PERIOD,getdate()) --\n" +
                " --and FM_DEP.MAIN_ORG_ID=20 --филиал выполнивший забор биоматериала");

        while (resultSet.next()) {
            objects.add(new Object[] {
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
//
//       return entityManager.createNativeQuery("SELECT DISTINCT patdirec.PATIENTS_ID,\n" +
//               " dbo.fNNPlus_Patient (pat.PATIENTS_ID,1),\n" +
//               "--pat.nom+' '+substring(pat.PRENOM,1,1)+'. '+substring(pat.PATRONYME,1,1)+'.', \n" +
//               "(case \n" +
//               "when patdirec.kontengent=0 then '108.б - доноры биологических жидкостей'\n" +
//               "when patdirec.kontengent=1 then '109.а - беременные при взятии на учет'\n" +
//               "when patdirec.kontengent=2 then '109.б - беременные на сроке 25-26 и 38-39 недель'\n" +
//               "when patdirec.kontengent=3 then '116 б - в соответствии со стандартами мед. помощи (ВРТ, перед манипуляциями,добровольное обследование)'\n" +
//               "when patdirec.kontengent=4 then '110 - половые партнеры беременных'\n" +
//               "when patdirec.kontengent=5 then '132.б - профилактический медосмотр'\n" +
//               "when patdirec.kontengent=6 then '132.в - медицинские аварийные ситуации'\n" +
//               "end),\n" +
//               "DIR_ANSW.MOTCONSU_RESP_ID\n" +
//               ",\n" +
//               "(case \n" +
//               "when LAB_METHODS.CODE='А/т к ВИЧ 1,2 +А/г' then \n" +
//               "(select DISTINCT AT_K_VIH_1_2 from DIR_ANSW DA inner join DATA141 ON DA.MOTCONSU_RESP_ID=DATA141.MOTCONSU_ID where DA.MOTCONSU_RESP_ID= DIR_ANSW.MOTCONSU_RESP_ID ) \n" +
//               "when LAB_METHODS.CODE='HBsAg' then \n" +
//               " (select DISTINCT HBS_AG from DIR_ANSW DA inner join DATA141 ON DA.MOTCONSU_RESP_ID=DATA141.MOTCONSU_ID where DA.MOTCONSU_RESP_ID= DIR_ANSW.MOTCONSU_RESP_ID ) \n" +
//               "when LAB_METHODS.CODE='Ат .к. HCV' then \n" +
//               " (select DISTINCT HCV from DIR_ANSW DA inner join DATA141 ON DA.MOTCONSU_RESP_ID=DATA141.MOTCONSU_ID where DA.MOTCONSU_RESP_ID= DIR_ANSW.MOTCONSU_RESP_ID ) \n" +
//               "when LAB_METHODS.CODE='Syphilis ИФА' then \n" +
//               " (select DISTINCT SIPHILIS_TPHA_TEST from DIR_ANSW DA inner join DATA141 ON DA.MOTCONSU_RESP_ID=DATA141.MOTCONSU_ID where DA.MOTCONSU_RESP_ID= DIR_ANSW.MOTCONSU_RESP_ID )\n" +
//               " when LAB_METHODS.CODE='Сифилис МРП' then \n" +
//               " (select DISTINCT SIPHILIS_MR from DIR_ANSW DA inner join DATA141 ON DA.MOTCONSU_RESP_ID=DATA141.MOTCONSU_ID where DA.MOTCONSU_RESP_ID= DIR_ANSW.MOTCONSU_RESP_ID )\n" +
//               " end)\n" +
//               ",FM_DEP.MAIN_ORG_ID,\n" +
//               "FM_ORG.LABEL,\n" +
//               "PATDIREC.PATDIREC_ID,\n" +
//               "PATDIREC.DATE_BIO,\n" +
//               "--DS_PARAMS.DS_PARAMS_ID,\n" +
//               "--,PATDIREC.PL_EXAM_ID,\n" +
//               " --LAB_METHODS.LAB_METHODS_ID, \n" +
//               " LAB_METHODS.CODE, \n" +
//               "  PATDIREC.BIO_CODE\n" +
//               "  --,LAB_METHODBIO.LAB_METHODBIO_ID\n" +
//               "  FROM\n" +
//               " PATDIREC PATDIREC WITH(NOLOCK)  JOIN PL_EXAM PL_EXAM WITH(NOLOCK)  ON PATDIREC.PL_EXAM_ID = PL_EXAM.PL_EXAM_ID \n" +
//               " INNER JOIN DIR_ANSW ON PATDIREC.PATDIREC_ID=DIR_ANSW.PATDIREC_ID\n" +
//               " inner join DIR_SERV ON PATDIREC.PATDIREC_ID =DIR_SERV.PATDIREC_ID\n" +
//               " inner JOIN FM_DEP ON PATDIREC.MEDECINS_BIO_DEP_ID=FM_DEP.FM_DEP_ID\n" +
//               " INNER JOIN FM_ORG ON FM_DEP.MAIN_ORG_ID=FM_ORG.FM_ORG_ID\n" +
//               " inner join DS_SERVPARAMS ON DIR_SERV.FM_SERV_ID=DS_SERVPARAMS.FM_SERV_ID\n" +
//               " inner JOIN DS_PARAMS ON DS_SERVPARAMS.DS_PARAMS_ID=DS_PARAMS.DS_PARAMS_ID\n" +
//               " JOIN LAB_METHODBIO LAB_METHODBIO WITH(NOLOCK)  ON DS_PARAMS.DS_PARAMS_ID = LAB_METHODBIO.DS_PARAMS_ID \n" +
//               " JOIN LAB_METHODS LAB_METHODS WITH(NOLOCK)  ON LAB_METHODBIO.LAB_METHODS_ID = LAB_METHODS.LAB_METHODS_ID \n" +
//               " LEFT OUTER JOIN VIEW_GRPPRM VIEW_GRPPRM WITH(NOLOCK)  ON DS_PARAMS.DS_PARAMS_ID = VIEW_GRPPRM.DS_PARAMS_ID \n" +
//               " LEFT OUTER JOIN PATIENTS PAT WITH(NOLOCK)  ON PATDIREC.PATIENTS_ID = PAT.PATIENTS_ID \n" +
//               "WHERE\n" +
//               "VIEW_GRPPRM.GRPPRM_ID = 227 --рабочий журнал по вичам\n" +
//               "and PATDIREC.QUANTITY_DONE=0 --анализ не выполнен\n" +
//               "and  PATDIREC.BIO_CODE=: bio_code -- код забора\n" +
//               "and PATDIREC.DATE_BIO >dateadd(day,-30,'20200209') --\n" +
//               " --and FM_DEP.MAIN_ORG_ID=20 --филиал выполнивший забор биоматериала\n" +
//               "ORDER BY\n" +
//               " PATDIREC.DATE_BIO")
//               .setParameter("bio_code", bio_code)
//               .getResultList();
        return objects;
    }
    public  List<Object[]> chek (String done, String bio_code) throws SQLException {
        List<Object[]> objects = new ArrayList<>();
        Connection connection = database.getConnection();
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
                "--DS_PARAMS\n" +
                "where\n" +
                "        VIEW_GRPPRM.GRPPRM_ID = 350 --рабочий журнал по вичам\n" +
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
    public void registerUser(User user){

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
        Set<Role> roles =  new HashSet<>();
        try {
            Connection connection = database.getConnection();
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM LABOR_USERS where nameUser="+"'"+s+"'");
            while (resultSet.next()) {
                roles.add(new Role(resultSet.getString(4)));
                user.setName(resultSet.getString(2));
                user.setPassword(resultSet.getString(3));
                user.setRoles(roles);
            }



        } catch (Exception ex) {
            System.out.println(ex);}
        return user;
    }

    @Override
    public void saveUser(String name, String password, String role) {
        User user = new User(name, password, null);
        registerUser(user);
        try {
            Connection connection = database.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate("insert LABOR_USERS (nameUser, passwordUser, roleUser)" +
                    "VALUES (" + "'" + user.getName() + "'" + ", " + "'" + user.getPassword() + "'" + "," + "'"+ role+ "'" + ")");
            System.out.println("User с именем –" + name + " добавлен в базу данных");

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public List<User> getAllUsers() {
        List<User> allUsers = new ArrayList<>();
        Set<Role> roles =  new HashSet<>();

        try {
            Connection connection = database.getConnection();
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM LABOR_USERS");
            while (resultSet.next()) {
                roles.add(new Role(resultSet.getString(4)));
                allUsers.add(new User(resultSet.getLong(1), resultSet.getString(2),
                        resultSet.getString(3),roles));
                roles.clear();
            }



        } catch (Exception ex) {
            System.out.println(ex);
        }
        return allUsers;
    }
    public void removeUserById(long id) {
        try {
            Connection connection = database.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM LABOR_USERS WHERE Id =" + id);

        } catch (Exception ex) {
            System.out.println(ex);
        }

    }


}
