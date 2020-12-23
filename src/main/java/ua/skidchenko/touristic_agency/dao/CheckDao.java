package ua.skidchenko.touristic_agency.dao;

import ua.skidchenko.touristic_agency.entity.Check;
import ua.skidchenko.touristic_agency.entity.Tour;
import ua.skidchenko.touristic_agency.entity.User;
import ua.skidchenko.touristic_agency.entity.enums.CheckStatus;
import ua.skidchenko.touristic_agency.entity.enums.TourStatus;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface CheckDao extends GenericDao<Check> {

    List<Check> findAllByUserOrderByStatus(String username, int pageSize, int pageNum) throws SQLException;

    List<Check> findAllByStatus(CheckStatus status, int pageSize, int pageNum);

    Optional<Check> findByIdAndStatus(Long id, CheckStatus statuse);

    void createNewCheck(String username, Long tourId);

    void cancelBookingByCheckId(Long checkId);

    void declineBookingById(Long checkId);

    void confirmBookingByCheckId(Long checkId);
}
