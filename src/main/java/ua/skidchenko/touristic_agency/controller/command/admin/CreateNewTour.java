package ua.skidchenko.touristic_agency.controller.command.admin;

import ua.skidchenko.touristic_agency.controller.command.Command;
import ua.skidchenko.touristic_agency.dto.TourDTO;
import ua.skidchenko.touristic_agency.entity.Tour;
import ua.skidchenko.touristic_agency.entity.enums.HotelType;
import ua.skidchenko.touristic_agency.entity.enums.TourStatus;
import ua.skidchenko.touristic_agency.entity.enums.TourType;
import ua.skidchenko.touristic_agency.service.TourService;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class CreateNewTour implements Command {
   private final TourService tourService;

    public CreateNewTour(TourService tourService) {
        this.tourService = tourService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        TourDTO tourDTO = TourDTO.buildTourDTOFromRequest(request);
        tourService.saveNewTour(tourDTO);
        request.setAttribute("isTourCreated",true);
        return "/view/admin/newTour.jsp";
    }



}
