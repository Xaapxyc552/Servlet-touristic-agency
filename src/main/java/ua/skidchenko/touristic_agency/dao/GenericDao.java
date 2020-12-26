package ua.skidchenko.touristic_agency.dao;


import java.sql.Connection;
import java.sql.SQLException;

public interface GenericDao<T> {

    T create (T entity);
    T update(T entity);
    void delete(int id);
}
