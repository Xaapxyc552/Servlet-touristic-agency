package ua.skidchenko.touristic_agency.controller.command.manager;

import ua.skidchenko.touristic_agency.controller.command.Command;
import ua.skidchenko.touristic_agency.controller.util.Page;
import ua.skidchenko.touristic_agency.entity.Check;
import ua.skidchenko.touristic_agency.service.client_services.ManagerBookingService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ManagerTourOperations implements Command {
    private final ManagerBookingService managerBookingService;

    public ManagerTourOperations(ManagerBookingService managerBookingService) {
        this.managerBookingService = managerBookingService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        int currentPage = request.getParameter("currentPage") == null
                ? 0 : Integer.parseInt(request.getParameter("currentPage"));
        Page<Check> pagedWaitingChecks = managerBookingService.getPagedWaitingChecks(currentPage);
        request.setAttribute("waitingChecks",pagedWaitingChecks.getContent());
        request.setAttribute("currentPage",currentPage);
        request.setAttribute("pagesSequence",getPagesSequence(pagedWaitingChecks.getAmountOfPages()));
        return "/view/manager/managerTourPage.jsp";
    }

    private List<String> getPagesSequence(int amountOfPages) {
        return IntStream.range(0, amountOfPages)
                .boxed()
                .map(Objects::toString)
                .collect(Collectors.toList());
    }
}
