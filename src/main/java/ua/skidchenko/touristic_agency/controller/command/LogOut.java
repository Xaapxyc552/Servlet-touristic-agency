package ua.skidchenko.touristic_agency.controller.command;

import javax.servlet.http.HttpServletRequest;

public class LogOut implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        return "redirect:/common.jsp";
    }
}
