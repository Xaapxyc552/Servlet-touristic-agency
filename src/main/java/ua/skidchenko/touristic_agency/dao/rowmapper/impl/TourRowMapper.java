package ua.skidchenko.touristic_agency.dao.rowmapper.impl;

import ua.skidchenko.touristic_agency.dao.rowmapper.GenericRowMapper;
import ua.skidchenko.touristic_agency.entity.Tour;
import ua.skidchenko.touristic_agency.entity.User;
import ua.skidchenko.touristic_agency.entity.enums.HotelType;
import ua.skidchenko.touristic_agency.entity.enums.Role;
import ua.skidchenko.touristic_agency.entity.enums.TourStatus;
import ua.skidchenko.touristic_agency.entity.enums.TourType;
import ua.skidchenko.touristic_agency.exceptions.NotPresentInDatabaseException;
import ua.skidchenko.touristic_agency.exceptions.RowMappingException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class TourRowMapper implements GenericRowMapper<Tour> {
    @Override
    public Tour mapRow(ResultSet rs) {
        String engLangCode = "en_GB";
        String ukrLangCode = "uk_UA";
        Map<String, String> names = new HashMap<>();
        Map<String, String> descriptions = new HashMap<>();
        try {
            names.put(ukrLangCode, rs.getString("nameukr"));
            names.put(engLangCode, rs.getString("nameeng"));
            descriptions.put(ukrLangCode, rs.getString("descukr"));
            descriptions.put(engLangCode, rs.getString("desceng"));
            return Tour.builder()
                    .name(names)
                    .description(descriptions)
                    .burning(rs.getBoolean("burning"))
                    .amountOfPersons(rs.getInt("amount_of_persons"))
                    .price(rs.getLong("price"))
                    .hotelType(HotelType.values()[rs.getInt("hotel_type")])
                    .tourStatus(TourStatus.valueOf(rs.getString("tour_status")))
                    .id(rs.getLong("id"))
                    .tourTypes(
                            Arrays.stream(rs.getString("tour_types")
                                    .split(","))
                                    .map(n -> TourType.getInstanceByType(TourType.Type.valueOf(n)))
                                    .collect(Collectors.toList())
                    )
                    .build();
        } catch (SQLException e) {
            throw new RowMappingException("Exception during mapping tour",e);
        }
    }
}
