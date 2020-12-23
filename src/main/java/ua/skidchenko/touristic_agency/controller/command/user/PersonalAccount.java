package ua.skidchenko.touristic_agency.controller.command.user;

import ua.skidchenko.touristic_agency.controller.command.Command;
import ua.skidchenko.touristic_agency.controller.util.Page;
import ua.skidchenko.touristic_agency.dao.OrderOfTours;
import ua.skidchenko.touristic_agency.entity.Check;
import ua.skidchenko.touristic_agency.service.TourService;
import ua.skidchenko.touristic_agency.service.client_services.UserBookingService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PersonalAccount implements Command {
    UserBookingService userBookingService;

    public PersonalAccount(UserBookingService userBookingService) {
        this.userBookingService = userBookingService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        int currentPage = request.getParameter("currentPage")==null
                ? 0 : Integer.parseInt(request.getParameter("currentPage"));
        try {
            Page<Check> checksByUsername = userBookingService.findAllChecksByUsernameOrderByStatus(
                    (String) request.getSession().getAttribute("username"),
                    currentPage);
            request.setAttribute("checkToDisplay",checksByUsername.getContent());
            request.setAttribute("currentPage",currentPage);
            request.setAttribute("pagesSequence",getPagesSequence(checksByUsername.getAmountOfPages()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "/view/user/personalAccount.jsp";
    }
    private List<String> getPagesSequence(int amountOfPages) {
        return IntStream.range(0, amountOfPages)
                .boxed()
                .map(Objects::toString)
                .collect(Collectors.toList());
    }
}
