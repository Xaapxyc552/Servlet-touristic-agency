package ua.skidchenko.touristic_agency.controller;

import ua.skidchenko.touristic_agency.controller.command.*;
import ua.skidchenko.touristic_agency.service.impl.TourServiceImpl;
import ua.skidchenko.touristic_agency.service.impl.UserBookingServiceImpl;
import ua.skidchenko.touristic_agency.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.awt.print.Book;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Servlet extends HttpServlet {
    private final Map<String, Command> commands = new HashMap<>();

    @Override
    public void init(){
        commands.put("logout", new LogOut());
        commands.put("login", new Login(new UserServiceImpl()));
        commands.put("forbidden", new ForbiddenPage());
        commands.put("registration", new RegistrationPage());
        commands.put("register-user", new RegisterUser(new UserServiceImpl()));
        commands.put("locale" , new LocaleChange());
        commands.put("display-tours" , new DisplayTours(new TourServiceImpl()));
        commands.put("user/personal-account" , new PersonalAccount(new UserBookingServiceImpl()));
        commands.put("admin/tour/delete" , new DeleteTour(new TourServiceImpl()));
        commands.put("user/remove-booking" , new RemoveBookingFromCheck(new UserBookingServiceImpl()));
        commands.put("user/book-tour" , new BookTour(new UserBookingServiceImpl()));
    }

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws IOException, ServletException {
        processRequest(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
           processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getRequestURI();
        path = path.replaceAll(".*/app/" , "");
        Command command = commands.getOrDefault(path ,
                (r)->"/view/unsupportedCommand)");
        String page = command.execute(request);
        if(page.startsWith("redirect:")){
            response.sendRedirect(page.replace("redirect:", ""));
        }else {
            request.getRequestDispatcher(page).forward(request, response);
        }
    }
}