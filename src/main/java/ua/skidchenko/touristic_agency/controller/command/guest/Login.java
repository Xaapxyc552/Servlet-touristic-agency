package ua.skidchenko.touristic_agency.controller.command.guest;

import ua.skidchenko.touristic_agency.controller.command.Command;
import ua.skidchenko.touristic_agency.entity.User;
import ua.skidchenko.touristic_agency.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class Login implements Command {
    private final UserService userService;

    public Login(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        request.getSession().invalidate();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        System.out.println(username + " " + password);
        if (username == null || username.equals("") || password == null || password.equals("")) {
            return "/view/login.jsp";
        }
        Optional<User> userByUsername = userService.getUserByUsername(username);
        if (userByUsername.isPresent() && userByUsername.get().getPassword().   equals(password)) {
            User user = userByUsername.get();
            request.getSession().setAttribute("username", user.getUsername());
            request.getSession().setAttribute("firstname", user.getFirstname());
            request.getSession().setAttribute("role", user.getRole().getAuthority());
            request.getSession().setAttribute("money", user.getMoney());
            request.getSession().setAttribute("email", user.getEmail());

            switch (user.getRole()) {
                case ROLE_USER:
                    return "redirect:/app/user/personal-account";
                case ROLE_ADMIN:
                case ROLE_MANAGER:
                    return "redirect:/app/manager/tours-operations";
            }
        }
        request.setAttribute("loginUnsuccessful", Boolean.TRUE);
        return "/view/login.jsp";
    }
}
