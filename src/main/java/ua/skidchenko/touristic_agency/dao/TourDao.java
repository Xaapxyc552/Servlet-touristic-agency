package ua.skidchenko.touristic_agency.dao;

import ua.skidchenko.touristic_agency.entity.Tour;
import ua.skidchenko.touristic_agency.entity.enums.TourStatus;

import java.util.List;
import java.util.Optional;

public interface TourDao extends GenericDao<Tour>{

    List<Tour> findAllSortedPageableByTourStatus(OrderOfTours orderOfTours,
                                           TourStatus tourStatus,
                                           int pageSize,
                                           int pageNum,
                                           String sortingDirection);

    Optional<Tour> findByIdAndTourStatus(Long id, TourStatus status);

    Optional<Tour> findById(Long id);
}
