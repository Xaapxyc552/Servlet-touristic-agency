package ua.skidchenko.touristic_agency.controller;

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
import ua.skidchenko.touristic_agency.service.service_factory.ServiceFactory;

public class CommandFactory {

    private static CommandFactory commandFactory;
    private static ServiceFactory serviceFactory;

    public static CommandFactory getInstance() {
        if (commandFactory == null) {
            synchronized (CommandFactory.class) {
                if (commandFactory == null) {
                    commandFactory = new CommandFactory();
                }
            }
        }
        return commandFactory;
    }

    protected CommandFactory() {
        serviceFactory = ServiceFactory.getInstance();
    }

    public DeleteTour deleteTour() {
        return new DeleteTour(serviceFactory.tourService());
    }

    public LogOut logOut() {
        return new LogOut();
    }

    public Login login() {
        return new Login(serviceFactory.userService());
    }

    public RegistrationPage registrationPage() {
        return new RegistrationPage();
    }

    public RegisterUser registerUser() {
        return new RegisterUser(serviceFactory.userService());
    }

    public LocaleChange localeChange() {
        return new LocaleChange();
    }

    public DisplayTours displayTours() {
        return new DisplayTours(serviceFactory.tourService());
    }

    public PersonalAccount personalAccount() {
        return new PersonalAccount(serviceFactory.userBookingService(), serviceFactory.userService());
    }

    public RemoveBookingFromCheck removeBookingFromCheck() {
        return new RemoveBookingFromCheck(serviceFactory.userBookingService());
    }

    public BookTour bookTour() {
        return new BookTour(serviceFactory.userBookingService());
    }

    public RechargeWallet rechargeWallet() {
        return new RechargeWallet(serviceFactory.userService());
    }

    public ManagerTourOperations managerTourOperations() {
        return new ManagerTourOperations(serviceFactory.managerBookingService());
    }

    public DeclineBooking declineBooking() {
        return new DeclineBooking(serviceFactory.managerBookingService());
    }

    public ConfirmBooking confirmBooking() {
        return new ConfirmBooking(serviceFactory.managerBookingService());
    }

    public NewTour newTour() {
        return new NewTour();
    }

    public CreateNewTour createNewTour() {
        return new CreateNewTour(serviceFactory.tourService());
    }

    public EditTour editTour() {
        return new EditTour(serviceFactory.tourService());
    }

    public UpdateEditedTour updateEditedTour() {
        return new UpdateEditedTour(serviceFactory.tourService());
    }


}
