package com.example.demo.dao;

import com.example.demo.model.CommonAnalysis;
import com.example.demo.model.User;
import org.springframework.stereotype.Repository;
import org.w3c.dom.Document;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Repository
public interface UptakeDao {
    List<Object[]> getData(String done,String bio_code, Integer GRPPRM) throws SQLException;
    List<Object[]> getDataGormonu(String bio_code,  String done, String GPRM) throws SQLException;
    List<Object[]> chek (String done,String bio_code, Integer GRPPRM) throws SQLException;
    User loadUserByUsername(String s);
    void saveUser(String name, String password, String role);
    public List<User> getAllUsers();
    public void removeUserById(long id);
    void saveCommon(CommonAnalysis ca);
    public List<CommonAnalysis> getCommon();
    public void deleteCommon(long id);
    public Document getDoc(List<CommonAnalysis> analyses, Set<String> indets, Document doc);
    List<String> getBCAsignments(String in);

}
