package ua.skidchenko.touristic_agency.dao.impl;

import ua.skidchenko.touristic_agency.dao.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCDaoFactory extends DaoFactory {
    @Override
    public CheckDao createCheckDao() {
        return new JDBCCheckDao();
    }

    @Override
    public UserDao createUserDao() {
        return new JDBCUserDao();
    }

    @Override
    public TourDao createTourDao() {
        return new JDBCTourDao();
    }
}
