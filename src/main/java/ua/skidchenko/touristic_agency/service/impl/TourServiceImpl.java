package ua.skidchenko.touristic_agency.service.impl;

import ua.skidchenko.touristic_agency.dto.Page;
import ua.skidchenko.touristic_agency.dao.DaoFactory;
import ua.skidchenko.touristic_agency.controller.util.OrderOfTours;
import ua.skidchenko.touristic_agency.dao.TourDao;
import ua.skidchenko.touristic_agency.dto.TourDTO;
import ua.skidchenko.touristic_agency.entity.Tour;
import ua.skidchenko.touristic_agency.entity.enums.TourStatus;
import ua.skidchenko.touristic_agency.entity.enums.TourType;
import ua.skidchenko.touristic_agency.exceptions.NotPresentInDatabaseException;

import ua.skidchenko.touristic_agency.service.TourService;

import java.util.stream.Collectors;

//@Service
//@Log4j2
public class TourServiceImpl implements TourService {

    private final TourDao tourDao = DaoFactory.getInstance().createTourDao();
    //TODO вынести в проперти
    private int pageSize = 5;

    @Override
    public Tour saveTourToDB(Tour tour) {
//        log.info("Saving tour to DB: " + tour.toString());
        return tourDao.create(tour);
    }

    @Override
    public Page<Tour> getPagedWaitingToursOrderedByArgs(OrderOfTours orderOfTours,
                                                        String direction,
                                                        int currentPage) {
        return tourDao.findAllSortedPageableByTourStatus
                (orderOfTours, TourStatus.WAITING, pageSize, currentPage, direction);
    }

    @Override
    public Tour saveNewTour(TourDTO tourDTO) {
//        log.info("Saving new into DB tour built from DTO: " + tourDTO.toString());
        Tour newTour = Tour.buildTourFromTourDTO(tourDTO);
        return tourDao.create(newTour);
    }

    @Override
    public TourDTO getWaitingTourDTOById(Long tourId) {
//        log.info("Retrieving new tourDTO from DB by tour ID. Tour ID: " + tourId);
        Tour tour = tourDao.findByIdAndTourStatus(tourId, TourStatus.WAITING)
                .<NotPresentInDatabaseException>orElseThrow(() -> {
//                    log.warn("Tour not present in DB. Tour ID:" + tourId);
                    throw new NotPresentInDatabaseException("Tour not present in DB. Tour ID:" + tourId);
                });
        return TourDTO.buildTourDTOFromTour(tour);
    }

    @Override
    public Tour updateTourAfterChanges(TourDTO tourDTO) {
//        log.info("Updating tour with data from tourDTO: " + tourDTO.toString());
        Tour tourToSave = Tour.buildTourFromTourDTO(tourDTO);
        tourDao.update(tourToSave);
        return tourToSave;
    }

    @Override
    public void markTourAsDeleted(Long tourId) {
        tourDao.setTourAsDeleted(tourId);
    }



}
