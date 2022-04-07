package com.example.demo.dao;

import com.example.demo.model.User;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
public interface UptakeDao {
    List<Object[]> getData(String done,String bio_code, Integer GRPPRM) throws SQLException;
    List<Object[]> chek (String done,String bio_code) throws SQLException;
    User loadUserByUsername(String s);
    void saveUser(String name, String password, String role);
    public List<User> getAllUsers();
    public void removeUserById(long id);

}
