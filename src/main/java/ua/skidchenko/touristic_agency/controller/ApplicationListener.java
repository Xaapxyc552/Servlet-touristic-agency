package ua.skidchenko.touristic_agency.controller;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.Locale;

@WebListener
public class ApplicationListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        Locale.setDefault(new Locale("en"));
    }


    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        //empty
    }
}
