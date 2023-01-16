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

        ResultSet resultSet = statement.executeQuery("");

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
//       return entityManager.createNativeQuery("" +
//               " PATDIREC.DATE_BIO")
//               .setParameter("bio_code", bio_code)
//               .getResultList();
        return objects;
    }
    public  List<Object[]> chek (String done, String bio_code) throws SQLException {
        List<Object[]> objects = new ArrayList<>();
        Connection connection = database.getConnection();
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("");

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
//        ResultSet resultSet = statement.executeQuery("");
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
//        User user = new User("", "", roles);
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

            ResultSet resultSet = statement.executeQuery("");
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
            statement.executeUpdate("");
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

            ResultSet resultSet = statement.executeQuery("");
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
            statement.executeUpdate("" + id);

        } catch (Exception ex) {
            System.out.println(ex);
        }

    }


}
