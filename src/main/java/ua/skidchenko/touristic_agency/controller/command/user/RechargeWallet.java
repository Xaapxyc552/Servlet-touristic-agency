package ua.skidchenko.touristic_agency.controller.command.user;

import ua.skidchenko.touristic_agency.controller.command.Command;
import ua.skidchenko.touristic_agency.controller.util.MoneyTransformer;
import ua.skidchenko.touristic_agency.service.UserService;

import javax.servlet.http.HttpServletRequest;

public class RechargeWallet implements Command {
    private final UserService userService;

    public RechargeWallet(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String amountOfCharge = request.getParameter("amountOfCharge");
        if (amountOfCharge != null && !amountOfCharge.isEmpty()) {
            Long amountOfChargeInt = Long.valueOf(
                    MoneyTransformer.getInstance().transformToKopecks(Double.parseDouble(amountOfCharge), request)
            );
            userService.chargeUserWallet(amountOfChargeInt,
                    (String) request.getSession().getAttribute("username"));
        } else {
            request.setAttribute("isAmountOfChargeNotCorrect", true);
        }
        return "redirect:/app/user/personal-account";
    }
}
