package ua.skidchenko.touristic_agency.controller.command.user;

import ua.skidchenko.touristic_agency.controller.command.Command;
import ua.skidchenko.touristic_agency.controller.util.MoneyTransformer;
import ua.skidchenko.touristic_agency.dto.CheckDTO;
import ua.skidchenko.touristic_agency.dto.Page;
import ua.skidchenko.touristic_agency.entity.Check;
import ua.skidchenko.touristic_agency.entity.User;
import ua.skidchenko.touristic_agency.service.UserService;
import ua.skidchenko.touristic_agency.service.client_services.UserBookingService;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PersonalAccount implements Command {
    UserBookingService userBookingService;
    UserService userService;

    public PersonalAccount(UserBookingService userBookingService, UserService userService) {
        this.userBookingService = userBookingService;
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        int currentPage = request.getParameter("currentPage") == null
                ? 0 : Integer.parseInt(request.getParameter("currentPage"));
        Page<CheckDTO> checksByUsername = userBookingService.findAllChecksByUsernameOrderByStatus(
                (String) request.getSession().getAttribute("username"),
                currentPage);
        List<CheckDTO> checksToDisplay = checksByUsername
                .getContent()
                .stream()
                .peek(n -> n.setTotalPrice(
                        MoneyTransformer.getInstance().transformToCurrency(
                                Math.toIntExact(Long.parseLong(n.getTotalPrice())), request)))
                .collect(Collectors.toList());
        request.setAttribute("checkToDisplay", checksToDisplay);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("pagesSequence", getPagesSequence(checksByUsername.getAmountOfPages()));
        User user = userService.
                getUserByUsername((String) (request.getSession().getAttribute("username"))).get();
        int money = Math.toIntExact(user.getMoney());
        request.setAttribute("money", MoneyTransformer.getInstance().transformToCurrency(money, request));

        return "/view/user/personalAccount.jsp";
    }

    private List<String> getPagesSequence(int amountOfPages) {
        return IntStream.range(0, amountOfPages)
                .boxed()
                .map(Objects::toString)
                .collect(Collectors.toList());
    }
}
