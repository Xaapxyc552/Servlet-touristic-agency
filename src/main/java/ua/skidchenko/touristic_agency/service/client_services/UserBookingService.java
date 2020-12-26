package ua.skidchenko.touristic_agency.service.client_services;

import ua.skidchenko.touristic_agency.dto.CheckDTO;
import ua.skidchenko.touristic_agency.dto.Page;

import java.sql.SQLException;

public interface UserBookingService {
    void bookTourByIdForUsername(Long tourId, String username);

    Page<CheckDTO> findAllChecksByUsernameOrderByStatus(String username, int page);

    void cancelBookingByCheckId(Long checkId) throws SQLException;
}
