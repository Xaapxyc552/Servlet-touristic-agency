package ua.skidchenko.touristic_agency.service.service_factory;

import ua.skidchenko.touristic_agency.service.TourService;
import ua.skidchenko.touristic_agency.service.UserService;
import ua.skidchenko.touristic_agency.service.client_services.ManagerBookingService;
import ua.skidchenko.touristic_agency.service.client_services.UserBookingService;
import ua.skidchenko.touristic_agency.service.impl.ManagerBookingServiceImpl;
import ua.skidchenko.touristic_agency.service.impl.TourServiceImpl;
import ua.skidchenko.touristic_agency.service.impl.UserBookingServiceImpl;
import ua.skidchenko.touristic_agency.service.impl.UserServiceImpl;

import java.util.ResourceBundle;

public class ServiceFactory {
    private static ServiceFactory serviceFactory;

    public static ServiceFactory getInstance() {
        if (serviceFactory == null) {
            synchronized (ServiceFactory.class) {
                if (serviceFactory == null) {
                    serviceFactory = new ServiceFactory();
                }
            }
        }
        return serviceFactory;
    }

    protected ServiceFactory() {
    }

    public UserService userService() {
        return new UserServiceImpl();
    }

    public UserBookingService userBookingService() {
        return new UserBookingServiceImpl();
    }

    public TourService tourService() {
        return new TourServiceImpl();
    }

    public ManagerBookingService managerBookingService() {
        return new ManagerBookingServiceImpl();
    }

}
