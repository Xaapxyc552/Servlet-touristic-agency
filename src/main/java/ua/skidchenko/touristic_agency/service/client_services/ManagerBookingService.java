package ua.skidchenko.touristic_agency.service.client_services;


import ua.skidchenko.touristic_agency.dto.Page;
import ua.skidchenko.touristic_agency.entity.Check;

public interface ManagerBookingService {
//    @Transactional
    void declineBooking(Long checkId);
//
    void confirmBooking(Long checkId);

    Page<Check> getPagedWaitingChecks(int currentPage);
}
