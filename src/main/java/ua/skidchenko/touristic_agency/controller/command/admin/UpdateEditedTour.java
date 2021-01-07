package ua.skidchenko.touristic_agency.controller.command.admin;

import ua.skidchenko.touristic_agency.controller.command.Command;
import ua.skidchenko.touristic_agency.controller.util.MoneyTransformer;
import ua.skidchenko.touristic_agency.dto.TourDTO;
import ua.skidchenko.touristic_agency.service.TourService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

public class UpdateEditedTour implements Command {
    private final TourService tourService;

    public UpdateEditedTour(TourService tourService) {
        this.tourService = tourService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        TourDTO tourToEdit = TourDTO.buildTourDTOFromRequest(request);
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<TourDTO>> validationErrors = validator.validate(tourToEdit);
        if (!validationErrors.isEmpty()) {
            request.setAttribute("tourToEdit",tourToEdit);
            request.setAttribute("validationErrors",validationErrors);
            return "/view/admin/editTour.jsp";
        }
        tourToEdit.setPrice(MoneyTransformer.getInstance().transformToKopecks(
                Double.parseDouble(tourToEdit.getPrice())
                ,request));
        tourService.updateTourAfterChanges(tourToEdit);
        request.setAttribute("tourToEdit", tourToEdit);
        return "redirect:/app/display-tours";
    }
}
