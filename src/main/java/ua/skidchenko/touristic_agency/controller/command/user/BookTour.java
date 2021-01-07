package ua.skidchenko.touristic_agency.controller.command.user;

import ua.skidchenko.touristic_agency.controller.command.Command;
import ua.skidchenko.touristic_agency.exceptions.UserHasNoMoneyException;
import ua.skidchenko.touristic_agency.service.client_services.UserBookingService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;

public class BookTour implements Command {
    private final UserBookingService userBookingService;

    public BookTour(UserBookingService userBookingService) {
        this.userBookingService = userBookingService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String tourId = request.getParameter("tour_id");
        if (tourId != null && !tourId.isEmpty()) {
            try {
                userBookingService.bookTourByIdForUsername(Long.valueOf(tourId),
                        (String) request.getSession().getAttribute("username"));
            } catch (UserHasNoMoneyException e) {
                Locale locale;
                Optional<String> language = Arrays.stream(request.getCookies())
                        .filter(n -> n.getName().equals("language"))
                        .map(Cookie::getValue)
                        .findFirst();
                locale =Locale.forLanguageTag(language.orElse("en-gb"));
                String localizedMessage = e.getLocalizedMessage(locale);
                request.setAttribute("localizedMessage", localizedMessage);
                return "/view/errorPage.jsp";
            }
        } else {
            request.setAttribute("isTourIdNotCorrect", true);
        }
        return "redirect:/app/display-tours";
    }
}
