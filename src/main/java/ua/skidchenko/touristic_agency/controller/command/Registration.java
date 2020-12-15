package ua.skidchenko.touristic_agency.controller.command;

import ua.skidchenko.touristic_agency.service.UserService;
import ua.skidchenko.touristic_agency.service.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;

public class Registration implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        return "/view/registration.jsp";
    }
}