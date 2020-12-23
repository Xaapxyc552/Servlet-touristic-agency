package ua.skidchenko.touristic_agency.controller.command.manager;

import ua.skidchenko.touristic_agency.controller.command.Command;
import ua.skidchenko.touristic_agency.service.client_services.ManagerBookingService;

import javax.servlet.http.HttpServletRequest;

public class ConfirmBooking implements Command {
    private final ManagerBookingService managerBookingService;

    public ConfirmBooking(ManagerBookingService managerBookingService) {
        this.managerBookingService = managerBookingService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String checkId = request.getParameter("check_id");
        if (checkId != null && !checkId.isEmpty()) {
            managerBookingService.confirmBooking(Long.valueOf(checkId));
        } else {
            request.setAttribute("isTourIdNotCorrect", true);
        }
        return "redirect:/app/display-tours";
    }
}
