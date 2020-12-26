package ua.skidchenko.touristic_agency.service.impl;


import ua.skidchenko.touristic_agency.dto.CheckDTO;
import ua.skidchenko.touristic_agency.dto.Page;
import ua.skidchenko.touristic_agency.dao.CheckDao;
import ua.skidchenko.touristic_agency.dao.DaoFactory;
import ua.skidchenko.touristic_agency.entity.Check;
import ua.skidchenko.touristic_agency.entity.enums.CheckStatus;
import ua.skidchenko.touristic_agency.service.client_services.ManagerBookingService;

//@Service
//@Log4j2
public class ManagerBookingServiceImpl implements ManagerBookingService {

    private static final CheckDao checkDao = DaoFactory.getInstance().createCheckDao();

    private int pageSize = 5;

    @Override
    public Page<CheckDTO> getPagedWaitingChecks(int currentPage) {
//        log.info("Starting retrieving waiting checks from DB.");
        return checkDao.findAllByStatus(
                CheckStatus.getInstanceByEnum(CheckStatus.Status.WAITING_FOR_CONFIRM), pageSize, currentPage
        );

    }

    @Override
    public void declineBooking(Long checkId) {
//        log.info("Declining booking by checkId. Check ID: " + checkId.toString());
        checkDao.declineBookingById(checkId);
    }

    @Override
    public void confirmBooking(Long checkId) {
        //        log.info("Confirming booking by checkId. Check ID: " + checkId.toString());
        checkDao.confirmBookingByCheckId(checkId);
    }


}
