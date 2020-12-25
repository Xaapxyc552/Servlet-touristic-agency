package ua.skidchenko.touristic_agency.dao;

import ua.skidchenko.touristic_agency.controller.util.OrderOfTours;
import ua.skidchenko.touristic_agency.dto.Page;
import ua.skidchenko.touristic_agency.entity.Tour;
import ua.skidchenko.touristic_agency.entity.enums.TourStatus;

import java.util.Optional;

public interface TourDao extends GenericDao<Tour>{

    Page<Tour> findAllSortedPageableByTourStatus(OrderOfTours orderOfTours,
                                                 TourStatus tourStatus,
                                                 int pageSize,
                                                 int pageNum,
                                                 String sortingDirection);

    Optional<Tour> findByIdAndTourStatus(Long id, TourStatus status);

    Optional<Tour> findById(Long id);

    void setTourAsDeleted(Long tourId);
}
