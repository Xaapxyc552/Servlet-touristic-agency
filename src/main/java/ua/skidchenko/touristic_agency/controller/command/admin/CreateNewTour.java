package ua.skidchenko.touristic_agency.controller.command.admin;

import ua.skidchenko.touristic_agency.controller.command.Command;
import ua.skidchenko.touristic_agency.controller.util.MoneyTransformer;
import ua.skidchenko.touristic_agency.dto.TourDTO;
import ua.skidchenko.touristic_agency.service.TourService;

import javax.servlet.http.HttpServletRequest;

public class CreateNewTour implements Command {
   private final TourService tourService;

    public CreateNewTour(TourService tourService) {
        this.tourService = tourService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        TourDTO tourDTOToCreate = TourDTO.buildTourDTOFromRequest(request);
        tourDTOToCreate.setPrice(MoneyTransformer.getInstance().transformToKopecks(
                Integer.parseInt(tourDTOToCreate.getPrice())
                ,request));
        tourService.saveNewTour(tourDTOToCreate);
        request.setAttribute("isTourCreated",true);
        return "/view/admin/newTour.jsp";
    }



}
