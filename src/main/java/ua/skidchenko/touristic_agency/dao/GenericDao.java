package ua.skidchenko.touristic_agency.dao;


public interface GenericDao<T> extends AutoCloseable{
    T create (T entity);
    void update(T entity);
    void delete(int id);
}
