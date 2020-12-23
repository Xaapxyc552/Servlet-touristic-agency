package ua.skidchenko.touristic_agency.service.client_services;

import ua.skidchenko.touristic_agency.entity.Check;

import java.sql.SQLException;
import java.util.List;

public interface UserBookingService {
    void bookTourByIdForUsername(Long tourId, String username);

    List<Check> findAllChecksByUsernameOrderByStatus(String username, int page) throws SQLException;

    void cancelBookingByCheckId(Long checkId) throws SQLException;
}
