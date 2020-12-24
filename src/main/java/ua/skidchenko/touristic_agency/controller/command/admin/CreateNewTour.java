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
        TourDTO tourDTO = buildTourDTOFromRequest(request);
        tourService.saveNewTour(tourDTO);
        request.setAttribute("isTourCreated",true);
        return "/view/admin/newTour.jsp";
    }

    private TourDTO buildTourDTOFromRequest (HttpServletRequest request) {
        String engLangCode = "en_GB";
        String ukrLangCode = "uk_UA";
        Map parameterMap = request.getParameterMap();
        Map<String, String> names = new HashMap<>();
        Map<String, String> descriptions = new HashMap<>();
            names.put(ukrLangCode, request.getParameter("name_uk_UA"));
            names.put(engLangCode, request.getParameter("name_en_GB"));
            descriptions.put(ukrLangCode, request.getParameter("description_uk_UA"));
            descriptions.put(engLangCode, request.getParameter("description_en_GB"));
        return TourDTO.builder()
                .name(names)
                .description(descriptions)
                .burning(request.getParameter("burning"))
                .amountOfPersons(request.getParameter("amountOfPersons"))
                .price(request.getParameter("price"))
                .hotelType(HotelType.valueOf(request.getParameter("hotelType")))
                .tourTypes(
                        Arrays.stream(request.getParameter("tourTypes")
                                .split(","))
                                .collect(Collectors.toList())
                )
                .build();
    }

}
