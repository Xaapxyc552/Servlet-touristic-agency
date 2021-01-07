package ua.skidchenko.touristic_agency.dao.rowmapper.impl;

import ua.skidchenko.touristic_agency.dao.rowmapper.GenericRowMapper;
import ua.skidchenko.touristic_agency.entity.User;
import ua.skidchenko.touristic_agency.entity.enums.Role;
import ua.skidchenko.touristic_agency.exceptions.RowMappingException;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements GenericRowMapper<User> {
    @Override
    public User mapRow(ResultSet rs) {
        try {
            return User.builder()
                    .email(rs.getString("email"))
                    .firstname(rs.getString("firstname"))
                    .id(rs.getLong("id"))
                    .enabled(rs.getBoolean("enabled"))
                    .role(Role.valueOf(rs.getString("role")))
                    .money(rs.getLong("money"))
                    .username(rs.getString("username"))
                    .password(rs.getString("password"))
                    .build();
        } catch (SQLException e) {
            throw new RowMappingException("Exception during mapping user",e);
        }
    }
}
