package ua.skidchenko.touristic_agency.service;

import ua.skidchenko.touristic_agency.dto.Page;
import ua.skidchenko.touristic_agency.controller.util.OrderOfTours;
import ua.skidchenko.touristic_agency.dto.TourDTO;
import ua.skidchenko.touristic_agency.entity.Tour;

public interface TourService {
    Tour saveTourToDB(Tour tour);

    void markTourAsDeleted(Long tourId);

    Tour saveNewTour(TourDTO tourDTO);

    TourDTO getWaitingTourDTOById(Long tourId);
    //TODO
    Tour updateTourAfterChanges(TourDTO tourDTO);
//TODO
//    Tour markTourAsDeleted(Long tourId);

    Page<Tour> getPagedWaitingToursOrderedByArgs(OrderOfTours orderOfTours, String direction, int currentPage);


}
