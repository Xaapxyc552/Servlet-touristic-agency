package ua.skidchenko.touristic_agency.dao.rowmapper.impl;

import ua.skidchenko.touristic_agency.dao.rowmapper.GenericRowMapper;
import ua.skidchenko.touristic_agency.dto.CheckDTO;
import ua.skidchenko.touristic_agency.entity.Check;
import ua.skidchenko.touristic_agency.entity.Tour;
import ua.skidchenko.touristic_agency.entity.enums.CheckStatus;
import ua.skidchenko.touristic_agency.entity.enums.HotelType;
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

public class CheckRowMapper implements GenericRowMapper<Check> {
    @Override
    public Check mapRow(ResultSet rs) {
        try {
            return Check.builder()
                    .id(rs.getLong("check_id"))
                    .status(CheckStatus.getInstanceByEnum(
                            CheckStatus.Status.valueOf(rs.getString("check_status")))
                    )
                    .totalPrice(rs.getLong("total_price"))
                    .build();
        } catch (SQLException throwables) {
            throw new RowMappingException("Exception during mapping check",throwables);
        }
    }

    public CheckDTO mapRowToCheckDTO(ResultSet rs) {
        try {
            String engLangCode = "en_GB";
            String ukrLangCode = "uk_UA";
            Map<String, String> names = new HashMap<>();
            names.put(ukrLangCode, rs.getString("nameukr"));
            names.put(engLangCode, rs.getString("nameeng"));

            return CheckDTO.builder()
                    .userName(rs.getString("username"))
                    .userEmail(rs.getString("email"))
                    .tourName(names)
                    .id(String.valueOf(rs.getLong("check_id")))
                    .status(CheckStatus.getInstanceByEnum(
                            CheckStatus.Status.valueOf(rs.getString("check_status")))
                    )
                    .totalPrice(String.valueOf(rs.getLong("total_price")))
                    .build();
        } catch (SQLException throwables) {
            throw new RowMappingException("Exception during mapping check to checkDTO",throwables);
        }
    }
}

