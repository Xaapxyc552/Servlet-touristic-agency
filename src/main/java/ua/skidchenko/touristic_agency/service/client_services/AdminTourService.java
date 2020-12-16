package ua.skidchenko.touristic_agency.service.client_services;

import ua.skidchenko.touristic_agency.dto.TourDTO;
import ua.skidchenko.touristic_agency.entity.Tour;

public interface AdminTourService {
    Tour saveTourToDB(Tour tour);

    Tour saveNewTour(TourDTO tourDTO);

    TourDTO getWaitingTourDTOById(Long tourId);
//TODO
//    Tour updateTourAfterChanges(TourDTO tourDTO);
//TODO
//    Tour markTourAsDeleted(Long tourId);
}
