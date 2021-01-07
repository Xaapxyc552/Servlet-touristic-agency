package ua.skidchenko.touristic_agency.controller.command.guest;

import ua.skidchenko.touristic_agency.controller.command.Command;
import ua.skidchenko.touristic_agency.dto.UserDTO;
import ua.skidchenko.touristic_agency.exceptions.UsernameExistsException;
import ua.skidchenko.touristic_agency.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;
import java.util.stream.Collectors;

public class RegisterUser implements Command {
    private final UserService userService;

    private static final String REGISTRATION_PAGE_PATH = "/view/registration.jsp";
    public RegisterUser(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        UserDTO userDTO = UserDTO.builder().email(request.getParameter("email"))
                .firstname(request.getParameter("firstname"))
                .password(request.getParameter("password"))
                .username(request.getParameter("username"))
                .build();
        Validator validator = Validation.
                buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<UserDTO>> constraintViolations = validator.validate(userDTO);
        if (!constraintViolations.isEmpty()) {
            request.setAttribute("constraintViolations", constraintViolations.stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.toList()));
            return REGISTRATION_PAGE_PATH;
        }
        try {
            userService.saveUser(userDTO);
            request.setAttribute("userRegistrationMessage", true);
        } catch (UsernameExistsException existsException) {
            request.setAttribute("userRegistrationMessage", false);
            return REGISTRATION_PAGE_PATH;
        }
        return REGISTRATION_PAGE_PATH;
    }
}
