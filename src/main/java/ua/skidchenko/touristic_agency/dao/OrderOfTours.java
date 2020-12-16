package ua.skidchenko.touristic_agency.dao;

public enum OrderOfTours {
    AMOUNT_OF_PERSONS("amount_of_persons")
    ,PRICE("price")
    ,HOTEL_TYPE("hotel_type"),
    TOUR_TYPE("tour_types");

    OrderOfTours(String propertyToSort) {
        this.propertyToSort = propertyToSort;
    }

    private final String propertyToSort;

    public String getPropertyToSort() {
        return propertyToSort;
    }

}
