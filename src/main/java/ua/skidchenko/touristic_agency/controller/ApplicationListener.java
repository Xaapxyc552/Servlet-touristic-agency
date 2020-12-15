package ua.skidchenko.touristic_agency.controller;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.Locale;
import java.util.ResourceBundle;

@WebListener
public class ApplicationListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        Locale.setDefault(new Locale("en"));
//        Locale.setDefault(new Locale("uk","UA"));
    }


    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
