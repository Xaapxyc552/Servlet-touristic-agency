package ua.skidchenko.touristic_agency.dao.impl;

import ua.skidchenko.touristic_agency.dao.DaoFactory;
import ua.skidchenko.touristic_agency.dao.UserDao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCDaoFactory extends DaoFactory {
    @Override
    public UserDao createUserDao() {
        return new JDBCUserDao(getConnection());
    }
//    @Override
//    public DriverDao createDriverDao() {
//        return new JDBCDriverDao(getConnection());
//    }

    private Connection getConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(
                    "jdbc:postgresql://localhost:4000/touristic_agency",
                    "postgres",
                    "4461");
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}