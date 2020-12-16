package ua.skidchenko.touristic_agency.controller.command;

import ua.skidchenko.touristic_agency.dao.OrderOfTours;
import ua.skidchenko.touristic_agency.service.TourService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class DisplayTours implements Command {

    private TourService tourService;

    public static final String ORDER_OF_TOURS = "orderOfTours";
    public static final String CURRENT_PAGE = "currentPage";
    public static final String CURRENT_DIRECTION = "currentDirection";
    private static final String CURRENT_ORDER_OF_TOURS = "currentOrderOfTours";
    private static final OrderOfTours DEFAULT_SORTING = OrderOfTours.PRICE;
    private static final String DEFAULT_DIRECTION = "desc";
    private static final Integer DEFAULT_CURRENT_PAGE = 0;

    public DisplayTours(TourService tourService) {
        this.tourService = tourService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();

        if (request.getParameter(ORDER_OF_TOURS) != null && request.getParameter("direction") != null) {
            getSortingFromRequest(request);
        } else if (session.getAttribute(CURRENT_ORDER_OF_TOURS) != null &&
                session.getAttribute(CURRENT_DIRECTION) != null &&
                request.getParameter(CURRENT_PAGE) != null) {
            getCurrentSortingFromSession(request);
        } else {
            getWaitingToursByDefaultWorting(request);
        }
        return "/view/tours.jsp";
    }

    private void getSortingFromRequest(HttpServletRequest request) {
        HttpSession session = request.getSession();

        OrderOfTours orderOfTours = OrderOfTours.valueOf(request.getParameter(ORDER_OF_TOURS));
        String direction = request.getParameter("direction");
        session.setAttribute(CURRENT_ORDER_OF_TOURS, orderOfTours.getPropertyToSort());
        session.setAttribute(CURRENT_DIRECTION, direction);
        request.setAttribute("toursFromDb",
                tourService.getPagedWaitingToursOrderedByArgs(orderOfTours, direction, DEFAULT_CURRENT_PAGE));
        request.setAttribute(CURRENT_ORDER_OF_TOURS, orderOfTours.getPropertyToSort());
        request.setAttribute(CURRENT_DIRECTION, direction);
        request.setAttribute(CURRENT_PAGE, DEFAULT_CURRENT_PAGE);
    }

    private void getCurrentSortingFromSession(HttpServletRequest request) {
        HttpSession session = request.getSession();

        OrderOfTours orderOfTours = (OrderOfTours) session.getAttribute(CURRENT_ORDER_OF_TOURS);
        String direction = (String) session.getAttribute("direction");
        int currentPage = Integer.parseInt(request.getParameter(CURRENT_PAGE));

        request.setAttribute("toursFromDb",
                tourService.getPagedWaitingToursOrderedByArgs(
                        orderOfTours,
                        direction,
                        currentPage));
        request.setAttribute(CURRENT_ORDER_OF_TOURS, orderOfTours.getPropertyToSort());
        request.setAttribute(CURRENT_DIRECTION, direction);
        request.setAttribute(CURRENT_PAGE, currentPage);
    }

    private void getWaitingToursByDefaultWorting(HttpServletRequest request) {
        request.setAttribute("toursFromDb",
                tourService.getPagedWaitingToursOrderedByArgs(DEFAULT_SORTING, DEFAULT_DIRECTION, DEFAULT_CURRENT_PAGE));
        request.setAttribute(CURRENT_ORDER_OF_TOURS, DEFAULT_SORTING.getPropertyToSort().toLowerCase());
        request.setAttribute(CURRENT_DIRECTION, DEFAULT_DIRECTION);
        request.setAttribute(CURRENT_PAGE, DEFAULT_CURRENT_PAGE);
    }
}
