package ua.skidchenko.touristic_agency.dao.rowmapper.impl;

import ua.skidchenko.touristic_agency.dao.rowmapper.GenericRowMapper;
import ua.skidchenko.touristic_agency.dto.CheckDTO;
import ua.skidchenko.touristic_agency.entity.Check;
import ua.skidchenko.touristic_agency.entity.enums.CheckStatus;
import ua.skidchenko.touristic_agency.exceptions.RowMappingException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class CheckRowMapper implements GenericRowMapper<Check> {
    @Override
    public Check mapRow(ResultSet rs) {
        try {
            LocalDateTime modificationDate = rs.getTimestamp("modified_time") == null ?
                    null : rs.getTimestamp("modified_time").toLocalDateTime();
            LocalDateTime creationDate = rs.getTimestamp("creation_time") == null ?
                    null : rs.getTimestamp("creation_time").toLocalDateTime();
            return Check.builder()
                    .id(rs.getLong("check_id"))
                    .status(CheckStatus.getInstanceByEnum(
                            CheckStatus.Status.valueOf(rs.getString("check_status")))
                    )
                    .totalPrice(rs.getLong("total_price"))
                    .creationTime(creationDate)
                    .lastModificationTime(modificationDate)
                    .build();
        } catch (SQLException throwables) {
            throw new RowMappingException("Exception during mapping check", throwables);
        }
    }

    public CheckDTO mapRowToCheckDTO(ResultSet rs) {
        try {
            String engLangCode = "en_GB";
            String ukrLangCode = "uk_UA";
            Map<String, String> names = new HashMap<>();
            names.put(ukrLangCode, rs.getString("nameukr"));
            names.put(engLangCode, rs.getString("nameeng"));
            LocalDateTime modificationDate = rs.getTimestamp("modified_time") == null ?
                    null : rs.getTimestamp("modified_time").toLocalDateTime();
            LocalDateTime creationDate = rs.getTimestamp("creation_time") == null ?
                    null : rs.getTimestamp("creation_time").toLocalDateTime();
            return CheckDTO.builder()
                    .userName(rs.getString("username"))
                    .userEmail(rs.getString("email"))
                    .tourName(names)
                    .id(String.valueOf(rs.getLong("check_id")))
                    .status(CheckStatus.getInstanceByEnum(
                            CheckStatus.Status.valueOf(rs.getString("check_status")))
                    )
                    .totalPrice(String.valueOf(rs.getLong("total_price")))
                    .creationTime(creationDate)
                    .lastModificationTime(modificationDate)
                    .build();
        } catch (SQLException throwables) {
            throw new RowMappingException("Exception during mapping check to checkDTO", throwables);
        }
    }
}

