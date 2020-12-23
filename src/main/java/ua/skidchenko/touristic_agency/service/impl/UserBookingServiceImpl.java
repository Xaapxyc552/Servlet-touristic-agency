package ua.skidchenko.touristic_agency.service.impl;


import ua.skidchenko.touristic_agency.dao.CheckDao;
import ua.skidchenko.touristic_agency.dao.DaoFactory;
import ua.skidchenko.touristic_agency.dao.TourDao;
import ua.skidchenko.touristic_agency.dao.UserDao;
import ua.skidchenko.touristic_agency.entity.Check;
import ua.skidchenko.touristic_agency.entity.Tour;
import ua.skidchenko.touristic_agency.entity.User;
import ua.skidchenko.touristic_agency.entity.enums.CheckStatus;
import ua.skidchenko.touristic_agency.entity.enums.Role;
import ua.skidchenko.touristic_agency.entity.enums.TourStatus;
import ua.skidchenko.touristic_agency.exceptions.NotPresentInDatabaseException;
import ua.skidchenko.touristic_agency.service.client_services.UserBookingService;

import java.sql.SQLException;
import java.util.List;


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
    public List<Check> findAllChecksByUsernameOrderByStatus(String username, int page) throws SQLException {
//        log.info("Retrieving paged user's checks ordered by status. Username: " +
//                "" + username + ". Page: " + page);
        try {
            return checkDao.findAllByUserOrderByStatus(username, pageSize,page);
        } catch (SQLException e) {
            throw new SQLException("Exception during retrieving checks from DB.",e);
        }
    }

    @Override
//    @Transactional
    //TODO реализовать update в checkDao,Transactional (можно создавать новое ДАО на каждый вызов транзакционного метода)
    public void cancelBookingByCheckId(Long checkId) throws SQLException {
//        log.info("Canceling booking by checkId. Check ID: " + checkId.toString());
        checkDao.cancelBookingByCheckId(checkId);
    }

    private Check getCheckFromRepositoryByIdAndStatus(Long checkId, CheckStatus tourStatus) {
        return checkDao.findByIdAndStatus(checkId, tourStatus)
                .orElseThrow(() -> {
//                            log.warn("Check not presented in Database. Check ID: " + checkId);
                            return new NotPresentInDatabaseException(
                                    "Check not presented in Database. Check ID: " + checkId);
                        }
                );
    }
}
