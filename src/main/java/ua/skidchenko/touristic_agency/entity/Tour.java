package ua.skidchenko.touristic_agency.entity;

import ua.skidchenko.touristic_agency.dto.TourDTO;
import ua.skidchenko.touristic_agency.entity.enums.HotelType;
import ua.skidchenko.touristic_agency.entity.enums.TourStatus;
import ua.skidchenko.touristic_agency.entity.enums.TourType;

import java.util.List;
import java.util.Map;
import java.util.Objects;


public class Tour {
    private Long id;
    private Map<String, String> name;
    private Map<String, String> description;
    private boolean burning;
    private int amountOfPersons;
    private Long price;
    private TourStatus tourStatus;
    private List<TourType> tourTypes;
    private HotelType hotelType;

    public Tour(Long id,
                Map<String, String> name,
                Map<String, String> description,
                boolean burning, int amountOfPersons,
                Long price, TourStatus tourStatus,
                List<TourType> tourTypes,
                HotelType hotelType) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.burning = burning;
        this.amountOfPersons = amountOfPersons;
        this.price = price;
        this.tourStatus = tourStatus;
        this.tourTypes = tourTypes;
        this.hotelType = hotelType;
    }

    public Tour() {
    }

    public static Tour buildTourFromTourDTO(TourDTO tourDTO) {
        return Tour.builder()
                .tourStatus(TourStatus.WAITING)
                .hotelType(tourDTO.getHotelType())
                .description(tourDTO.getDescription())
                .price(Long.valueOf(tourDTO.getPrice()))
                .name(tourDTO.getName())
                .amountOfPersons(
                        Integer.parseInt(tourDTO.getAmountOfPersons())
                )
                .id(Long.valueOf(tourDTO.getId()))
                .burning(Boolean.parseBoolean(tourDTO.getBurning()))
                .tourTypes(
                        TourType.getTourTypesFromStringList(tourDTO.getTourTypes()
                        )
                ).build();
    }

    public static TourBuilder builder() {
        return new TourBuilder();
    }

    public Long getId() {
        return this.id;
    }

    public Map<String, String> getName() {
        return this.name;
    }

    public Map<String, String> getDescription() {
        return this.description;
    }

    public boolean isBurning() {
        return this.burning;
    }

    public int getAmountOfPersons() {
        return this.amountOfPersons;
    }

    public Long getPrice() {
        return this.price;
    }

    public TourStatus getTourStatus() {
        return this.tourStatus;
    }

    public List<TourType> getTourTypes() {
        return this.tourTypes;
    }

    public HotelType getHotelType() {
        return this.hotelType;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(Map<String, String> name) {
        this.name = name;
    }

    public void setDescription(Map<String, String> description) {
        this.description = description;
    }

    public void setBurning(boolean burning) {
        this.burning = burning;
    }

    public void setAmountOfPersons(int amountOfPersons) {
        this.amountOfPersons = amountOfPersons;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public void setTourStatus(TourStatus tourStatus) {
        this.tourStatus = tourStatus;
    }

    public void setTourTypes(List<TourType> tourTypes) {
        this.tourTypes = tourTypes;
    }

    public void setHotelType(HotelType hotelType) {
        this.hotelType = hotelType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tour tour = (Tour) o;
        return burning == tour.burning &&
                amountOfPersons == tour.amountOfPersons &&
                Objects.equals(id, tour.id) &&
                Objects.equals(name, tour.name) &&
                Objects.equals(description, tour.description) &&
                Objects.equals(price, tour.price) &&
                tourStatus == tour.tourStatus &&
                Objects.equals(tourTypes, tour.tourTypes) &&
                hotelType == tour.hotelType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, burning, amountOfPersons, price, tourStatus, tourTypes, hotelType);
    }

    public String toString() {
        return "Tour(id=" + this.getId() + ", name=" + this.getName() +
                ", burning=" + this.isBurning() + ", amountOfPersons=" +
                this.getAmountOfPersons() + ", price=" + this.getPrice() +
                ", tourStatus=" + this.getTourStatus() + ", hotelType=" +
                this.getHotelType() + ")";
    }

    public static class TourBuilder {
        private Long id;
        private Map<String, String> name;
        private Map<String, String> description;
        private boolean burning;
        private int amountOfPersons;
        private Long price;
        private TourStatus tourStatus;
        private List<TourType> tourTypes;
        private HotelType hotelType;

        TourBuilder() {
        }

        public TourBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public TourBuilder name(Map<String, String> name) {
            this.name = name;
            return this;
        }

        public TourBuilder description(Map<String, String> description) {
            this.description = description;
            return this;
        }

        public TourBuilder burning(boolean burning) {
            this.burning = burning;
            return this;
        }

        public TourBuilder amountOfPersons(int amountOfPersons) {
            this.amountOfPersons = amountOfPersons;
            return this;
        }

        public TourBuilder price(Long price) {
            this.price = price;
            return this;
        }

        public TourBuilder tourStatus(TourStatus tourStatus) {
            this.tourStatus = tourStatus;
            return this;
        }

        public TourBuilder tourTypes(List<TourType> tourTypes) {
            this.tourTypes = tourTypes;
            return this;
        }

        public TourBuilder hotelType(HotelType hotelType) {
            this.hotelType = hotelType;
            return this;
        }

        public Tour build() {
            return new Tour(id, name, description, burning, amountOfPersons, price, tourStatus, tourTypes, hotelType);
        }

        public String toString() {
            return "Tour.TourBuilder(id=" + this.id + ", name=" + this.name + ", description=" + this.description + ", burning=" + this.burning + ", amountOfPersons=" + this.amountOfPersons + ", price=" + this.price + ", tourStatus=" + this.tourStatus + ", tourTypes=" + this.tourTypes + ", hotelType=" + this.hotelType + ")";
        }
    }
}
