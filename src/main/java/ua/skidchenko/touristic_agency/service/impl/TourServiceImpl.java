package ua.skidchenko.touristic_agency.service.impl;

import ua.skidchenko.touristic_agency.dao.DaoFactory;
import ua.skidchenko.touristic_agency.dao.OrderOfTours;
import ua.skidchenko.touristic_agency.dao.TourDao;
import ua.skidchenko.touristic_agency.dto.TourDTO;
import ua.skidchenko.touristic_agency.entity.Tour;
import ua.skidchenko.touristic_agency.entity.enums.TourStatus;
import ua.skidchenko.touristic_agency.entity.enums.TourType;
import ua.skidchenko.touristic_agency.exceptions.NotPresentInDatabaseException;

import ua.skidchenko.touristic_agency.exceptions.TourNotPresentInDBException;
import ua.skidchenko.touristic_agency.service.TourService;

import java.sql.SQLException;
import java.util.*;
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
    public List<Tour> getPagedWaitingToursOrderedByArgs(OrderOfTours orderOfTours,
                                                        String direction,
                                                        int currentPage) {
        return tourDao.findAllSortedPageableByTourStatus
                (orderOfTours, TourStatus.WAITING, pageSize, currentPage, direction);
    }

    @Override
    public Tour saveNewTour(TourDTO tourDTO) {
//        log.info("Saving new into DB tour built from DTO: " + tourDTO.toString());
        Tour newTour = buildNewTourFromTourDTO(tourDTO);
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
        return TourDTO.builder()
                .id(String.valueOf(tour.getId()))
                .amountOfPersons(String.valueOf(tour.getAmountOfPersons()))
                .description(tour.getDescription())
                .name(tour.getName())
                .price(String.valueOf(tour.getPrice()))
                .hotelType(tour.getHotelType())
                .tourTypes(
                        tour.getTourTypes().stream()
                                .map(String::valueOf)
                                .collect(Collectors.toList())
                ).build();
    }

//    @Override
//    @Transactional
//    public Tour updateTourAfterChanges(TourDTO tourDTO) {
//        log.info("Updating tour with data from tourDTO: " + tourDTO.toString());
//        Optional<Tour> byId = tourRepository.findByIdAndTourStatusIn(Long.valueOf(tourDTO.getId()),
//                Collections.singletonList(TourStatus.WAITING));
//        if (!byId.isPresent()) {
//            throw new TourNotPresentInDBException("Tour was deleted from DB during editing.");
//        }
//        Tour tourToSave = buildNewTourFromTourDTO(tourDTO);
//        tourRepository.save(tourToSave);
//        return tourToSave;
//    }

    @Override
    //TODO transactional
    public Tour markTourAsDeleted(Long tourId) {
        //TODO можно ли таким образом обеспечивать танзакционность?
//        log.info("Marking tour as deleted. TourID: " + tourId);
        try {
//            tourDao.getConnection().setAutoCommit(false);
            Tour tour = tourDao.findByIdAndTourStatus(tourId, TourStatus.WAITING)
                    .<TourNotPresentInDBException>orElseThrow(() -> {
//                    log.warn("Waiting tour is not present id DB. Tour id: " + tourId);
                                throw new TourNotPresentInDBException(
                                        "Waiting tour is not present id DB. Tour id:" + tourId);
                            }
                    );
            tour.setTourStatus(TourStatus.DELETED);
            Tour save = tourDao.update(tour);
//            tourDao.getConnection().commit();
//            tourDao.getConnection().setAutoCommit(true);
//        log.info("Tour marked as deleted. Tour id: " + tourId);
            return save;
        } catch (SQLException e) {
            e.printStackTrace();
            //                tourDao.getConnection().rollback();
//                tourDao.getConnection().setAutoCommit(true);
            throw new NotPresentInDatabaseException("Exception during deleting tour.");
        }
    }

    private Tour buildNewTourFromTourDTO(TourDTO tourDTO) {
//        log.info("Building new tour from DTO to save or to update edited tour. TourDTO: " + tourDTO.toString());
        Tour build = Tour.builder()
                .tourStatus(TourStatus.WAITING)
                .hotelType(tourDTO.getHotelType())
                .description(tourDTO.getDescription())
                .price(Long.valueOf(tourDTO.getPrice()))
                .name(tourDTO.getName())
                .amountOfPersons(
                        Integer.parseInt(tourDTO.getAmountOfPersons())
                )
                .tourTypes(
                        TourType.getTourTypesFromStringList(tourDTO.getTourTypes()
                        )
                ).build();
        if (tourDTO.getBurning() != null) {
            build.setBurning(Boolean.parseBoolean(tourDTO.getBurning()));
        }
        if (tourDTO.getId() != null) {
            build.setId(Long.valueOf(tourDTO.getId()));
        }
        return build;
    }


}
