package ua.skidchenko.touristic_agency.controller.command.manager;

import ua.skidchenko.touristic_agency.controller.command.Command;
import ua.skidchenko.touristic_agency.service.client_services.ManagerBookingService;

import javax.servlet.http.HttpServletRequest;

public class DeclineBooking implements Command {
    private final ManagerBookingService managerBookingService;

    public DeclineBooking(ManagerBookingService managerBookingService) {
        this.managerBookingService = managerBookingService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String checkId = request.getParameter("check_id");
        if (checkId != null && !checkId.isEmpty()) {
            managerBookingService.declineBooking(Long.valueOf(checkId));
        } else {
            request.setAttribute("isTourIdNotCorrect", true);
        }
        return "redirect:/app/display-tours";
    }
}
