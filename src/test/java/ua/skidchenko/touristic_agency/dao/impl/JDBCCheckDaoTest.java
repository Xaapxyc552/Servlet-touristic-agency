package ua.skidchenko.touristic_agency.dao.impl;

import junit.framework.TestCase;
import ua.skidchenko.touristic_agency.dao.CheckDao;
import ua.skidchenko.touristic_agency.dao.DaoFactory;
import ua.skidchenko.touristic_agency.entity.Check;

import java.sql.SQLException;
import java.util.List;

public class JDBCCheckDaoTest extends TestCase {

    public void testFindAllByUserOrderByStatus() {
        CheckDao checkDao = DaoFactory.getInstance().createCheckDao();
        List<Check> user;
        try {
            user = checkDao.findAllByUserOrderByStatus("user", 5, 0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("123");

    }
}