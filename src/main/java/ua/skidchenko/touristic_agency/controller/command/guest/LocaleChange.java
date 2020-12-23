package ua.skidchenko.touristic_agency.controller.command.guest;

import ua.skidchenko.touristic_agency.controller.command.Command;

import javax.servlet.http.HttpServletRequest;

public class LocaleChange implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        return "redirect:" + request.getHeader("referer");
    }
}
