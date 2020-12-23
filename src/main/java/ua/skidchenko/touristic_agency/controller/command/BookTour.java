package ua.skidchenko.touristic_agency.controller.command;

import ua.skidchenko.touristic_agency.service.TourService;
import ua.skidchenko.touristic_agency.service.client_services.UserBookingService;

import javax.servlet.http.HttpServletRequest;

public class BookTour implements Command {
    private final UserBookingService userBookingService;

    public BookTour(UserBookingService userBookingService) {
        this.userBookingService = userBookingService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String tourId = request.getParameter("tour_id");
        if (tourId != null && !tourId.isEmpty()) {
            userBookingService.bookTourByIdForUsername(Long.valueOf(tourId),
                    (String) request.getSession().getAttribute("username"));
        } else {
            request.setAttribute("isTourIdNotCorrect", true);
        }
        return "redirect:/app/display-tours";
    }
}
