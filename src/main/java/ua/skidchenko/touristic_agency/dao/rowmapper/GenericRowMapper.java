package ua.skidchenko.touristic_agency.dao.rowmapper;

import java.sql.ResultSet;

public interface GenericRowMapper <T>{
    public T mapRow(ResultSet rs);
}
