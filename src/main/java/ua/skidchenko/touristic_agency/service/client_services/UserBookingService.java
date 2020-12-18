package ua.skidchenko.touristic_agency.service.client_services;

import ua.skidchenko.touristic_agency.entity.Check;

import java.sql.SQLException;
import java.util.List;

public interface UserBookingService {
    Check bookTourByIdForUsername(Long tourId, String username);

    List<Check> findAllChecksByUsernameOrderByStatus(String username, int page) throws SQLException;

    Boolean cancelBookingByCheckId(Long checkId) throws SQLException;
}
