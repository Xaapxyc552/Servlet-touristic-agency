package ua.skidchenko.touristic_agency.controller.command.guest;

import ua.skidchenko.touristic_agency.controller.command.Command;

import javax.servlet.http.HttpServletRequest;

public class ForbiddenPage implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        return "/view/errorPage.jsp";
    }
}
