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
import ua.skidchenko.touristic_agency.exceptions.ForbiddenOperationExceprtion;
import ua.skidchenko.touristic_agency.exceptions.NotPresentInDatabaseException;
import ua.skidchenko.touristic_agency.service.client_services.UserBookingService;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;


//@Log4j2
public class UserBookingServiceImpl implements UserBookingService {

    private final int pageSize=5;

    private final CheckDao checkDao = DaoFactory.getInstance().createCheckDao();
    private final UserDao userDao = DaoFactory.getInstance().createUserDao();
    private final TourDao tourDao = DaoFactory.getInstance().createTourDao();

    @Override
//    @Transactional
    //TODO реализовать create в checkDao,Transactional
    public Check bookTourByIdForUsername(Long tourId, String username) {
//        log.info("Booking tour for user by username and tourId. " +
//                "Username: " + username + ". Tour ID:" + tourId + ".");
        Tour tour = getTourFromRepositoryByIdAndStatus(tourId, TourStatus.WAITING);
        User user = getUserFromRepository(username);

        if (user.getMoney().compareTo(tour.getPrice()) < 0) {
//            log.warn("User has not enough money");
            throw new IllegalArgumentException("User has not enough money");
        }
        user.setMoney(user.getMoney() - tour.getPrice());
        tour.setTourStatus(TourStatus.REGISTERED);
        Check bookingCheck = Check.builder()
                .status(
                        CheckStatus.getInstanceByEnum(CheckStatus.Status.WAITING_FOR_CONFIRM)
                )
                .tour(tour)
                .totalPrice(tour.getPrice())
                .user(user)
                .build();
//        log.info("Finished creation check:" + bookingCheck.toString());
        return checkDao.create(bookingCheck);
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
    //TODO реализовать update в checkDao,Transactional
    public Boolean cancelBookingByCheckId(Long checkId) throws SQLException {
//        log.info("Canceling booking by checkId. Check ID: " + checkId.toString());
        Check checkFromDB = getCheckFromRepositoryByIdAndStatus(checkId,
                CheckStatus.getInstanceByEnum(CheckStatus.Status.WAITING_FOR_CONFIRM));
        User userFromDB = checkFromDB.getUser();
        checkFromDB.getTour().setTourStatus(TourStatus.WAITING);
        userFromDB.setMoney(
                userFromDB.getMoney() + checkFromDB.getTotalPrice()
        );
        checkFromDB.setStatus(CheckStatus.getInstanceByEnum(
                CheckStatus.Status.CANCELED)
        );
        tourDao.update(checkFromDB.getTour());
        userDao.update(checkFromDB.getUser());
        checkDao.update(checkFromDB);
        return Boolean.TRUE;
    }

    private Tour getTourFromRepositoryByIdAndStatus(Long tourId, TourStatus status) {
        return tourDao.findByIdAndTourStatus(tourId, status)
                .orElseThrow(() -> {
//                            log.warn("Tour not presented in Database. Tour id: " + tourId);
                            return new NotPresentInDatabaseException(
                                    "Tour not presented in Database. Tour id: " + tourId);
                        }
                );
    }

    private User getUserFromRepository(String username) {
        return userDao.findByUsernameAndRole(username, Role.ROLE_USER)
                .orElseThrow(() -> {
//                            log.warn("User not presented in Database. Username: " + username);
                            return new NotPresentInDatabaseException(
                                    "User not presented in Database. Username: " + username);
                        }
                );
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
