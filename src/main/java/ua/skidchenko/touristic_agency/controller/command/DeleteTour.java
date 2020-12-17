package ua.skidchenko.touristic_agency.controller.command;

import ua.skidchenko.touristic_agency.service.TourService;

import javax.servlet.http.HttpServletRequest;

public class DeleteTour implements Command {
    private final TourService tourService;

    public DeleteTour(TourService tourService) {
        this.tourService = tourService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String tourId = request.getParameter("tour_id");
        if (tourId != null && !tourId.isEmpty()) {
            tourService.markTourAsDeleted(Long.valueOf(tourId));
        } else {
            request.setAttribute("isTourIdNotCorrect", true);
        }
        return "redirect:/app/display-tours";
    }
}
