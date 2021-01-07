package ua.skidchenko.touristic_agency.service.impl;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.skidchenko.touristic_agency.dto.CheckDTO;
import ua.skidchenko.touristic_agency.dto.Page;
import ua.skidchenko.touristic_agency.dao.CheckDao;
import ua.skidchenko.touristic_agency.dao.DaoFactory;
import ua.skidchenko.touristic_agency.service.client_services.UserBookingService;

import java.sql.SQLException;
import java.util.ResourceBundle;


public class UserBookingServiceImpl implements UserBookingService {

    private static final Logger log = LogManager.getLogger(UserBookingServiceImpl.class.getName());

    private static final int PAGE_SIZE =
            Integer.parseInt(ResourceBundle.getBundle("common").getString("page.size"));
    private final CheckDao checkDao = DaoFactory.getInstance().createCheckDao();

    @Override
    public void bookTourByIdForUsername(Long tourId, String username) {
        log.info("Booking tour for user by username and tourId. " +
                "Username: {}. Tour ID: {}.",username,tourId);
        checkDao.createNewCheck(username, tourId);
    }

    @Override
    public Page<CheckDTO> findAllChecksByUsernameOrderByStatus(String username, int page)  {
        log.info("Retrieving paged user's checks ordered by status. Username: " +
                "{}. Page: {}",username,page);
            return checkDao.findAllCheckDTOByUserOrderByStatus(username, PAGE_SIZE, page);
    }

    @Override
    public void cancelBookingByCheckId(Long checkId) {
        log.info("Canceling booking by checkId. Check ID: {}",checkId);
        checkDao.cancelBookingByCheckId(checkId);
    }
}
