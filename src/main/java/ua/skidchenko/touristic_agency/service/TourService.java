package ua.skidchenko.touristic_agency.service;

import ua.skidchenko.touristic_agency.dao.OrderOfTours;
import ua.skidchenko.touristic_agency.entity.Tour;
import ua.skidchenko.touristic_agency.service.client_services.AdminTourService;

import java.util.List;

public interface TourService extends AdminTourService {

    List<Tour> getPagedWaitingToursOrderedByArgs(OrderOfTours orderOfTours, String direction, int currentPage);

}
