package ua.skidchenko.touristic_agency.dao;


import java.sql.Connection;
import java.sql.SQLException;

public interface GenericDao<T> extends AutoCloseable{

    T create (T entity);
    T update(T entity) throws SQLException;
    void delete(int id);
}
