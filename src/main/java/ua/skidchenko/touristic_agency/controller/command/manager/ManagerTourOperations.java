package ua.skidchenko.touristic_agency.controller.command.manager;

import ua.skidchenko.touristic_agency.controller.command.Command;
import ua.skidchenko.touristic_agency.service.client_services.ManagerBookingService;

import javax.servlet.http.HttpServletRequest;

public class ManagerTourOperations implements Command {
    private final ManagerBookingService managerBookingService;

    public ManagerTourOperations(ManagerBookingService managerBookingService) {
        this.managerBookingService = managerBookingService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        int currentPage = request.getParameter("currentPage") == null
                ? 0 : Integer.parseInt(request.getParameter("currentPage"));
        request.setAttribute("waitingChecks",managerBookingService.getPagedWaitingChecks(currentPage));
        return "/view/manager/managerTourPage.jsp";
    }
}
