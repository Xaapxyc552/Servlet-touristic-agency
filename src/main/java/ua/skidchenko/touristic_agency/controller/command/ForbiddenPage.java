package ua.skidchenko.touristic_agency.controller.command;

import ua.skidchenko.touristic_agency.service.TourService;

import javax.servlet.http.HttpServletRequest;

public class ForbiddenPage implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        return "/view/forbidden.jsp";
    }
}
