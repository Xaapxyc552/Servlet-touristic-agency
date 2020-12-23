package ua.skidchenko.touristic_agency.service.client_services;


import ua.skidchenko.touristic_agency.entity.Check;

import java.util.List;

public interface ManagerBookingService {
//    @Transactional
    void declineBooking(Long checkId);
//
    void confirmBooking(Long checkId);

    List<Check> getPagedWaitingChecks(int currentPage);
}
