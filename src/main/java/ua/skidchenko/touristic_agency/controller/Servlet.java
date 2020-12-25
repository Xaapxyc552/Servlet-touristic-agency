package ua.skidchenko.touristic_agency.controller;

import ua.skidchenko.touristic_agency.controller.command.*;
import ua.skidchenko.touristic_agency.controller.command.admin.CreateNewTour;
import ua.skidchenko.touristic_agency.controller.command.admin.EditTour;
import ua.skidchenko.touristic_agency.controller.command.admin.NewTour;
import ua.skidchenko.touristic_agency.controller.command.admin.UpdateEditedTour;
import ua.skidchenko.touristic_agency.controller.command.guest.*;
import ua.skidchenko.touristic_agency.controller.command.manager.ConfirmBooking;
import ua.skidchenko.touristic_agency.controller.command.manager.DeclineBooking;
import ua.skidchenko.touristic_agency.controller.command.manager.DeleteTour;
import ua.skidchenko.touristic_agency.controller.command.manager.ManagerTourOperations;
import ua.skidchenko.touristic_agency.controller.command.user.BookTour;
import ua.skidchenko.touristic_agency.controller.command.user.PersonalAccount;
import ua.skidchenko.touristic_agency.controller.command.user.RechargeWallet;
import ua.skidchenko.touristic_agency.controller.command.user.RemoveBookingFromCheck;
import ua.skidchenko.touristic_agency.service.impl.ManagerBookingServiceImpl;
import ua.skidchenko.touristic_agency.service.impl.TourServiceImpl;
import ua.skidchenko.touristic_agency.service.impl.UserBookingServiceImpl;
import ua.skidchenko.touristic_agency.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Servlet extends HttpServlet {
    private final Map<String, Command> commands = new HashMap<>();
    private final CommandFactory commandFactory = CommandFactory.getInstance();
    @Override
    public void init(){
        commands.put("logout", commandFactory.logOut());
        commands.put("login", commandFactory.login());
        commands.put("registration", commandFactory.registrationPage());
        commands.put("register-user", commandFactory.registerUser());
        commands.put("locale" , commandFactory.localeChange());
        commands.put("display-tours" , commandFactory.displayTours());
        commands.put("user/personal-account" , commandFactory.personalAccount());
        commands.put("user/remove-booking" , commandFactory.removeBookingFromCheck());
        commands.put("user/book-tour" , commandFactory.bookTour());
        commands.put("user/recharge-account" , commandFactory.rechargeWallet());
        commands.put("manager/tours-operations" , commandFactory.managerTourOperations());
        commands.put("manager/check-decline" , commandFactory.declineBooking());
        commands.put("manager/check-confirm" , commandFactory.confirmBooking());
        commands.put("admin/tour/delete" , commandFactory.deleteTour());
        commands.put("admin/new-tour" , commandFactory.newTour());
        commands.put("admin/new-tour/create" , commandFactory.createNewTour());
        commands.put("admin/edit-tour" , commandFactory.editTour());
        commands.put("admin/edit-tour/update" ,commandFactory.updateEditedTour());
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