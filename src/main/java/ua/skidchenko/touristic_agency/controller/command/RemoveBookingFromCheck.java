package ua.skidchenko.touristic_agency.controller.command;

import ua.skidchenko.touristic_agency.entity.Check;
import ua.skidchenko.touristic_agency.service.client_services.UserBookingService;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;

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
