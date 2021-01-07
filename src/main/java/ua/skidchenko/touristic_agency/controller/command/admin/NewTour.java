package ua.skidchenko.touristic_agency.controller.command.admin;

import ua.skidchenko.touristic_agency.controller.command.Command;

import javax.servlet.http.HttpServletRequest;

public class NewTour implements Command {

    @Override
    public String execute(HttpServletRequest request) {

        return "/view/admin/newTour.jsp";
    }

}
