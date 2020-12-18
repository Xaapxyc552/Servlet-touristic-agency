package ua.skidchenko.touristic_agency.dao.impl;

import junit.framework.TestCase;
import ua.skidchenko.touristic_agency.dao.DaoFactory;
import ua.skidchenko.touristic_agency.dao.UserDao;
import ua.skidchenko.touristic_agency.entity.User;
import ua.skidchenko.touristic_agency.entity.enums.Role;

import java.sql.SQLException;

public class JDBCUserDaoTest extends TestCase {

    public void testFindByUsernameAndRole() throws SQLException {
//        DriverManager.getConnection(
//                "jdbc:postgresql://localhost:4000/touristic_agency",
//                "postgres",
//                "4461");
//
//        UserDao userDao = DaoFactory.getInstance().createUserDao();
//        Optional<User> admin = userDao.findByUsernameAndRole("admin", Role.ROLE_ADMIN);
//        System.out.println("asd");
    }

    public void testFindByUsername() {

    }

    public void testUpdate() {
//        UserDao userDao = DaoFactory.getInstance().createUserDao();
//        User changedusername = userDao.findByUsername("changedusername").get();
//        changedusername.setMoney(100000L);
//        changedusername.setEmail("email@com");
//        changedusername.setRole(Role.ROLE_USER);
//        changedusername.setPassword("password222");
//        changedusername.setEnabled(true);
//        changedusername.setUsername("usernam14");
//        try {
//            userDao.update(changedusername);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }
}