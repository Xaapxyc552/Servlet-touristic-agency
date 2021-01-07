package ua.skidchenko.touristic_agency.service.impl;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.skidchenko.touristic_agency.dto.CheckDTO;
import ua.skidchenko.touristic_agency.dto.Page;
import ua.skidchenko.touristic_agency.dao.CheckDao;
import ua.skidchenko.touristic_agency.dao.DaoFactory;
import ua.skidchenko.touristic_agency.entity.enums.CheckStatus;
import ua.skidchenko.touristic_agency.service.client_services.ManagerBookingService;

import java.util.ResourceBundle;


public class ManagerBookingServiceImpl implements ManagerBookingService {
    private static final Logger log = LogManager.getLogger(ManagerBookingServiceImpl.class.getName());

    private static final CheckDao checkDao = DaoFactory.getInstance().createCheckDao();

    private static final int PAGE_SIZE =
            Integer.parseInt(ResourceBundle.getBundle("common").getString("page.size"));

    @Override
    public Page<CheckDTO> getPagedWaitingChecks(int currentPage) {
        log.info("Starting retrieving waiting checks from DB.");
        return checkDao.findAllByStatus(
                CheckStatus.getInstanceByEnum(CheckStatus.Status.WAITING_FOR_CONFIRM), PAGE_SIZE, currentPage
        );

    }

    @Override
    public void declineBooking(Long checkId) {
        log.info("Declining booking by checkId. Check ID: {}",checkId);
        checkDao.declineBookingById(checkId);
    }

    @Override
    public void confirmBooking(Long checkId) {
        log.info("Confirming booking by checkId. Check ID: {}",checkId);
        checkDao.confirmBookingByCheckId(checkId);
    }


}
