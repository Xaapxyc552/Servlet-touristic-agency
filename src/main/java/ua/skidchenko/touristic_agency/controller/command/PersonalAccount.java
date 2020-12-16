package ua.skidchenko.touristic_agency.controller.command;

import ua.skidchenko.touristic_agency.dao.OrderOfTours;
import ua.skidchenko.touristic_agency.service.TourService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class PersonalAccount implements Command {


    @Override
    public String execute(HttpServletRequest request) {
        return "/view/user/personalAccount.jsp";
    }
}
