package ua.skidchenko.touristic_agency.controller.command.user;

import ua.skidchenko.touristic_agency.controller.command.Command;
import ua.skidchenko.touristic_agency.service.client_services.UserBookingService;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

public class RemoveBookingFromCheck implements Command {
    UserBookingService userBookingService;

    public RemoveBookingFromCheck(UserBookingService userBookingService) {
        this.userBookingService = userBookingService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        if (request.getParameter("check_id") == null) {
            throw new IllegalArgumentException("Check id to cancel cannot be null!");
        }
        try {
            userBookingService.cancelBookingByCheckId(Long.parseLong(request.getParameter("check_id")));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "redirect:/app/user/personal-account";
    }
}
