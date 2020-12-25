package ua.skidchenko.touristic_agency.service.impl;


import ua.skidchenko.touristic_agency.dto.Page;
import ua.skidchenko.touristic_agency.dao.CheckDao;
import ua.skidchenko.touristic_agency.dao.DaoFactory;
import ua.skidchenko.touristic_agency.dao.TourDao;
import ua.skidchenko.touristic_agency.dao.UserDao;
import ua.skidchenko.touristic_agency.entity.Check;
import ua.skidchenko.touristic_agency.service.client_services.UserBookingService;

import java.sql.SQLException;


//@Log4j2
public class UserBookingServiceImpl implements UserBookingService {

    private static final int pageSize=5;

    private final CheckDao checkDao = DaoFactory.getInstance().createCheckDao();
    private final UserDao userDao = DaoFactory.getInstance().createUserDao();
    private final TourDao tourDao = DaoFactory.getInstance().createTourDao();

    @Override
    public void bookTourByIdForUsername(Long tourId, String username) {
//        log.info("Booking tour for user by username and tourId. " +
//                "Username: " + username + ". Tour ID:" + tourId + ".");
        checkDao.createNewCheck(username,tourId);
//        log.info("Finished creation check:" + bookingCheck.toString());
    }

    @Override
    public Page<Check> findAllChecksByUsernameOrderByStatus(String username, int page) throws SQLException {
//        log.info("Retrieving paged user's checks ordered by status. Username: " +
//                "" + username + ". Page: " + page);
        try {
            return checkDao.findAllByUserOrderByStatus(username, pageSize,page);
        } catch (SQLException e) {
            throw new SQLException("Exception during retrieving checks from DB.",e);
        }
    }

    @Override
    public void cancelBookingByCheckId(Long checkId) throws SQLException {
//        log.info("Canceling booking by checkId. Check ID: " + checkId.toString());
        checkDao.cancelBookingByCheckId(checkId);
    }
}
