package com.example.demo.service;

import com.example.demo.dao.UptakeDao;
import com.example.demo.model.CommonAnalysis;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class UptakeSeviceImpl implements UptakeService {

    UptakeDao uptakeDao;

    @Autowired
    public void setUptakeDao(UptakeDao uptakeDao) {
        this.uptakeDao = uptakeDao;
    }

    @Override
    public List<Object[]> getData(String done,String bio_code, Integer GRPPRM) throws SQLException {
        return uptakeDao.getData(done, bio_code, GRPPRM);
    }

    @Override
    public List<Object[]> getDataGormonu(String bio_code,  String done, String GPRM) throws SQLException {
        return uptakeDao.getDataGormonu(bio_code,  done, GPRM);
    }

    @Override
    public List<Object[]> chek (String done,String bio_code, Integer GRPPRM) throws SQLException {
        return uptakeDao.chek(done, bio_code, GRPPRM);
    }


    @Override
    public void saveUser(String name, String password, String role) {
        uptakeDao.saveUser(name, password, role);
    }

    @Override
    public List<User> getAllUsers() {
        return uptakeDao.getAllUsers();
    }

    @Override
    public void removeUserById(long id) {
        uptakeDao.removeUserById(id);
    }

    @Override
    public void saveCommon(CommonAnalysis ca) {
        uptakeDao.saveCommon(ca);

    }

    @Override
    public List<CommonAnalysis> getCommon() {
        return uptakeDao.getCommon();
    }

    @Override
    public void deleteCommon(long id) {
        uptakeDao.deleteCommon(id);

    }

    @Override
    public Document getDoc(List<CommonAnalysis> analyses, Set<String> indets, Document doc) {
        return uptakeDao.getDoc(analyses, indets, doc);
    }

    @Override
    public List<String> getBCAsignments(String in) {
        return uptakeDao.getBCAsignments(in);
    }


}
