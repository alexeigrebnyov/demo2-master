package com.example.demo.service;

import com.example.demo.dao.UptakeDao;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

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
    public List<Object[]> chek (String done,String bio_code) throws SQLException {
        return uptakeDao.chek(done, bio_code);
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


}
