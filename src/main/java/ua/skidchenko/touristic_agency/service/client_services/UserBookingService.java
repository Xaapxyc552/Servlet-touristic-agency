package ua.skidchenko.touristic_agency.service.client_services;

import ua.skidchenko.touristic_agency.dto.Page;
import ua.skidchenko.touristic_agency.entity.Check;

import java.sql.SQLException;

public interface UserBookingService {
    void bookTourByIdForUsername(Long tourId, String username);

    Page<Check> findAllChecksByUsernameOrderByStatus(String username, int page) throws SQLException;

    void cancelBookingByCheckId(Long checkId) throws SQLException;
}
