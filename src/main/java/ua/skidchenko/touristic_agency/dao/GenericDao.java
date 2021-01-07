package ua.skidchenko.touristic_agency.dao;



public interface GenericDao<T> {

    T create (T entity);
    T update(T entity);
    void delete(int id);
}
