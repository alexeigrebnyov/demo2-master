package com.example.demo.dao;

import com.example.demo.model.*;
import com.example.demo.model.qc.CounterByValue;
import com.example.demo.model.qc.GlukozaExpress;
import com.example.demo.model.qc.achtv.AchtvCounter;
import org.springframework.stereotype.Repository;
import org.w3c.dom.Document;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Repository
public interface UptakeDao {
    List<Object[]> getData(String done,String bio_code, Integer GRPPRM) throws SQLException;
    List<String[]> getDataGormonu(String bio_code, String done, String GPRM) throws SQLException;
    List<Object[]> chek (String done,String bio_code, Integer GRPPRM) throws SQLException;
    User loadUserByUsername(String s);
    void saveUser(String name, String password, String role);
    public List<User> getAllUsers();
    public void removeUserById(long id);
    void saveCommon(CommonAnalysis ca);
    public List<CommonAnalysis> getCommon();
    public void deleteCommon(long id);
    public Document getDoc(List<CommonAnalysis> analyses, Set<String> indets, Document doc);
    public Document getGormDoc(List<Analysis> analyses, Set<String> indets, Document doc);
    List<String> getBCAsignments(String in);
    void updateByLabel(String label);
    List<CommonAnalysis> getByLabel(String label, boolean status);
    Long getMaxId();
    public List<Object[]> getCommonData(int filial, String from, String to);
    List<String> getOncoCytologyCodes(int filial, String from, String to);
    List<Object[]> getOncoCytology(String code, String from, String to);
    public void saveAssigment(Assignment a);
    Integer createMotconsu(String PatientsId, String medecinsId);
    void createPatdirec (String PatientsId,String medecinsId, String MOTCONSU_ID, int exam, int serv, int patdirec );
    List<CommonAnalysis> getCommonByStatus();
    List<AchtvCounter> getACHTVData(String end) throws SQLException;

    List<CounterByValue> getACHTVDataByValue(String end, String dataFrom, String dataTo) throws SQLException;
    List<CounterByValue> getPtimeDataByValue(String end, String dataFrom, String dataTo) throws SQLException;
    List<CounterByValue> getFibrDataByValue(String end, String dataFrom, String dataTo) throws SQLException;
    List<GlukozaExpress> getGlukozaExpressValue(String end, String dataFrom, String dataTo) throws SQLException;
    List<GlukozaExpress> getTestFragmentaciiValue(String end, String dataFrom, String dataTo) throws SQLException;
}
