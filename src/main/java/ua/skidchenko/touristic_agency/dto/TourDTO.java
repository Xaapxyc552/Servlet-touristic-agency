package ua.skidchenko.touristic_agency.dto;

import ua.skidchenko.touristic_agency.entity.Tour;
import ua.skidchenko.touristic_agency.entity.enums.HotelType;
import ua.skidchenko.touristic_agency.entity.enums.TourType;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.*;
import java.util.stream.Collectors;

public class TourDTO {

    private String id;

    @NotNull(message = "Name field null!")
    private Map<String,String> name;

    @NotNull(message = "Description field null!")
    private Map<String,String> description;

    @NotNull(message = "Amount of persons field null!")
    @NotBlank(message = "Amount of persons field blank!")
    @Pattern(regexp = "^[0-9]*$", message = "Amount of persons should be integer positive!")
    private String amountOfPersons;

    @NotNull(message = "Price field null!")
    @NotBlank(message = "Price field blank!")
    @Pattern(regexp = "^[0-9]+(\\.[0-9]{1,2})?$", message = "Price should be numerical!")
    private String price;

    @NotNull(message = "Tour types field null!")
    private List<String> tourTypes;

    @NotNull(message = "Hotel type of persons field null!")
    private HotelType hotelType;

    private String burning;

    public TourDTO(String id,
                   Map<String, String> name,
                   Map<String, String> description,
                   String amountOfPersons,
                   String price,
                   List<String> tourTypes,
                   HotelType hotelType, String burning) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.amountOfPersons = amountOfPersons;
        this.price = price;
        this.tourTypes = tourTypes;
        this.hotelType = hotelType;
        this.burning = burning;
    }

    public TourDTO() {
    }

    public static TourDTO buildTourDTOFromRequest(HttpServletRequest request) {
        String engLangCode = "en_GB";
        String ukrLangCode = "uk_UA";
        Map<String, String> names = new HashMap<>();
        Map<String, String> descriptions = new HashMap<>();
        names.put(ukrLangCode, request.getParameter("name_uk_UA"));
        names.put(engLangCode, request.getParameter("name_en_GB"));
        descriptions.put(ukrLangCode, request.getParameter("description_uk_UA"));
        descriptions.put(engLangCode, request.getParameter("description_en_GB"));
        return TourDTO.builder()
                .name(names)
                .description(descriptions)
                .id(request.getParameter("tour_id"))
                .burning(request.getParameter("burning"))
                .amountOfPersons(request.getParameter("amountOfPersons"))
                .price(request.getParameter("price"))
                .hotelType(HotelType.valueOf(request.getParameter("hotelType")))
                .tourTypes(
                        Arrays.asList(request.getParameterValues("tourTypes"))
                )
                .build();
    }

    public static TourDTO buildTourDTOFromTour(Tour tour) {
        return TourDTO.builder()
                .id(String.valueOf(tour.getId()))
                .burning(String.valueOf(tour.isBurning()))
                .amountOfPersons(String.valueOf(tour.getAmountOfPersons()))
                .description(tour.getDescription())
                .name(tour.getName())
                .price(String.valueOf(tour.getPrice()))
                .hotelType(tour.getHotelType())
                .tourTypes(
                        tour.getTourTypes().stream()
                                .map(TourType::getType)
                                .map(String::valueOf)
                                .collect(Collectors.toList())
                ).build();
    }

    public static TourDTOBuilder builder() {
        return new TourDTOBuilder();
    }

    public String getId() {
        return this.id;
    }

    public  Map<String, String> getName() {
        return this.name;
    }

    public  Map<String, String> getDescription() {
        return this.description;
    }

    public  String getAmountOfPersons() {
        return this.amountOfPersons;
    }

    public String getPrice() {
        return this.price;
    }

    public  List<String> getTourTypes() {
        return this.tourTypes;
    }

    public  HotelType getHotelType() {
        return this.hotelType;
    }

    public String getBurning() {
        return this.burning;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(Map<String, String> name) {
        this.name = name;
    }

    public void setDescription(Map<String, String> description) {
        this.description = description;
    }

    public void setAmountOfPersons(String amountOfPersons) {
        this.amountOfPersons = amountOfPersons;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setTourTypes(List<String> tourTypes) {
        this.tourTypes = tourTypes;
    }

    public void setHotelType(HotelType hotelType) {
        this.hotelType = hotelType;
    }

    public void setBurning(String burning) {
        this.burning = burning;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TourDTO tourDTO = (TourDTO) o;
        return Objects.equals(id, tourDTO.id) &&
                Objects.equals(name, tourDTO.name) &&
                Objects.equals(description, tourDTO.description) &&
                Objects.equals(amountOfPersons, tourDTO.amountOfPersons) &&
                Objects.equals(price, tourDTO.price) &&
                Objects.equals(tourTypes, tourDTO.tourTypes) &&
                hotelType == tourDTO.hotelType &&
                Objects.equals(burning, tourDTO.burning);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, amountOfPersons, price, tourTypes, hotelType, burning);
    }

    public String toString() {
        return "TourDTO(id=" + this.getId() + ", name=" + this.getName() +
                ", description=" + this.getDescription() + ", amountOfPersons=" +
                this.getAmountOfPersons() + ", price=" + this.getPrice() +
                ", tourTypes=" + this.getTourTypes() + ", hotelType=" + this.getHotelType() +
                ", burning=" + this.getBurning() + ")";
    }

    public static class TourDTOBuilder {
        private String id;
        private Map<String, String> name;
        private Map<String, String> description;
        private String amountOfPersons;
        private String price;
        private List<String> tourTypes;
        private HotelType hotelType;
        private String burning;

        TourDTOBuilder() {
        }

        public TourDTOBuilder id(String id) {
            this.id = id;
            return this;
        }

        public TourDTOBuilder name(Map<String, String> name) {
            this.name = name;
            return this;
        }

        public TourDTOBuilder description(Map<String, String> description) {
            this.description = description;
            return this;
        }

        public TourDTOBuilder amountOfPersons(String amountOfPersons) {
            this.amountOfPersons = amountOfPersons;
            return this;
        }

        public TourDTOBuilder price(String price) {
            this.price = price;
            return this;
        }

        public TourDTOBuilder tourTypes(List<String> tourTypes) {
            this.tourTypes = tourTypes;
            return this;
        }

        public TourDTOBuilder hotelType(HotelType hotelType) {
            this.hotelType = hotelType;
            return this;
        }

        public TourDTOBuilder burning(String burning) {
            this.burning = burning;
            return this;
        }

        public TourDTO build() {
            return new TourDTO(id, name, description, amountOfPersons, price, tourTypes, hotelType, burning);
        }

        public String toString() {
            return "TourDTO.TourDTOBuilder(id=" + this.id + ", name=" + this.name +
                    ", description=" + this.description + ", amountOfPersons=" +
                    this.amountOfPersons + ", price=" + this.price + ", tourTypes=" +
                    this.tourTypes + ", hotelType=" + this.hotelType + ", burning=" + this.burning + ")";
        }
    }
}
