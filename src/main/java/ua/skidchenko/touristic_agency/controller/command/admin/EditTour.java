package ua.skidchenko.touristic_agency.controller.command.admin;

import ua.skidchenko.touristic_agency.controller.command.Command;
import ua.skidchenko.touristic_agency.controller.util.MoneyTransformer;
import ua.skidchenko.touristic_agency.dto.TourDTO;
import ua.skidchenko.touristic_agency.entity.enums.HotelType;
import ua.skidchenko.touristic_agency.service.TourService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class EditTour implements Command {
    private final TourService tourService;

    public EditTour(TourService tourService) {
        this.tourService = tourService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        TourDTO tourToEdit = tourService.getWaitingTourDTOById(Long.valueOf(request.getParameter("tour_id")));
        tourToEdit.setPrice(MoneyTransformer.getInstance().transformToCurrency(
                Integer.parseInt(tourToEdit.getPrice())
                ,request));
        request.setAttribute("tourToEdit", tourToEdit);
        return "/view/admin/editTour.jsp";
    }


}
