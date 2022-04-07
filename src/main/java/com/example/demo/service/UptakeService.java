package com.example.demo.service;

import com.example.demo.model.User;

import java.sql.SQLException;
import java.util.List;

public interface UptakeService {
    List<Object[]> getData(String done, String bio_code) throws SQLException;
    List<Object[]> chek (String done, String bio_code) throws SQLException;
    void saveUser(String name, String password, String role);
    public List<User> getAllUsers();
    public void removeUserById(long id);


}
