package ua.skidchenko.touristic_agency.controller;

import ua.skidchenko.touristic_agency.dao.OrderOfTours;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class SessionListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        se.getSession().setAttribute("currentOrderOfTours", OrderOfTours.PRICE);
        se.getSession().setAttribute("currentPage", 0);
        se.getSession().setAttribute("currentDirection", "desc");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {

    }
}
