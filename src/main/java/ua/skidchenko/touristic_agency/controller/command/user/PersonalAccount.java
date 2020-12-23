package ua.skidchenko.touristic_agency.controller.command.user;

import ua.skidchenko.touristic_agency.controller.command.Command;
import ua.skidchenko.touristic_agency.dao.OrderOfTours;
import ua.skidchenko.touristic_agency.entity.Check;
import ua.skidchenko.touristic_agency.service.TourService;
import ua.skidchenko.touristic_agency.service.client_services.UserBookingService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

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
            List<Check> checksByUsername = userBookingService.findAllChecksByUsernameOrderByStatus(
                    (String) request.getSession().getAttribute("username"),
                    currentPage);
            request.setAttribute("checkToDisplay",checksByUsername);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "/view/user/personalAccount.jsp";
    }
}
