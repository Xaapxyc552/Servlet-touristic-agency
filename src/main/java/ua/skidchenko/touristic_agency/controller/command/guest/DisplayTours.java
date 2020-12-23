package ua.skidchenko.touristic_agency.controller.command.guest;

import ua.skidchenko.touristic_agency.controller.command.Command;
import ua.skidchenko.touristic_agency.controller.util.Page;
import ua.skidchenko.touristic_agency.dao.OrderOfTours;
import ua.skidchenko.touristic_agency.entity.Tour;
import ua.skidchenko.touristic_agency.service.TourService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DisplayTours implements Command {
    private final TourService tourService;

    public static final String ORDER_OF_TOURS = "orderOfTours";
    public static final String CURRENT_PAGE = "currentPage";
    public static final String CURRENT_DIRECTION = "currentDirection";
    public static final String DIRECTION = "direction";
    private static final String CURRENT_ORDER_OF_TOURS = "currentOrderOfTours";


    public DisplayTours(TourService tourService) {
        this.tourService = tourService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();

        OrderOfTours orderOfTours = (OrderOfTours) session.getAttribute(CURRENT_ORDER_OF_TOURS);
        String direction = (String) session.getAttribute(CURRENT_DIRECTION);
        Integer currentPage = (Integer) session.getAttribute(CURRENT_PAGE);

        if (request.getParameter(ORDER_OF_TOURS) != null && request.getParameter(DIRECTION) != null) {
            orderOfTours = OrderOfTours.valueOf(request.getParameter(ORDER_OF_TOURS));
            direction = request.getParameter("direction");
            currentPage = 0;
            session.setAttribute(CURRENT_ORDER_OF_TOURS, orderOfTours);
            session.setAttribute(CURRENT_DIRECTION, direction);
            session.setAttribute(CURRENT_PAGE, currentPage);
        } else if (request.getParameter(CURRENT_PAGE) != null) {
            currentPage = Integer.valueOf(request.getParameter(CURRENT_PAGE));
            session.setAttribute(CURRENT_PAGE, currentPage);

        }
        Page<Tour> pageOfSortedTours = tourService.getPagedWaitingToursOrderedByArgs(
                orderOfTours, direction, currentPage
        );
        request.setAttribute("toursFromDb", pageOfSortedTours.getContent());
        request.setAttribute("pagesSequence",getPagesSequence(pageOfSortedTours.getAmountOfPages()));
        return "/view/tours.jsp";
    }


    private List<String> getPagesSequence(int amountOfPages) {
        return IntStream.range(0, amountOfPages)
                .boxed()
                .map(Objects::toString)
                .collect(Collectors.toList());
    }
}
