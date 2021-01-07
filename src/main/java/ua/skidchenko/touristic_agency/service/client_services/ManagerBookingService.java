package ua.skidchenko.touristic_agency.service.client_services;


import ua.skidchenko.touristic_agency.dto.CheckDTO;
import ua.skidchenko.touristic_agency.dto.Page;

public interface ManagerBookingService {
//    @Transactional
    void declineBooking(Long checkId);
//
    void confirmBooking(Long checkId);

    Page<CheckDTO> getPagedWaitingChecks(int currentPage);
}
