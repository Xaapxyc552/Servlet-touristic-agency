package ua.skidchenko.touristic_agency.dao;

import ua.skidchenko.touristic_agency.dao.impl.JDBCDaoFactory;

public abstract class DaoFactory {
    private static DaoFactory daoFactory;

    public static DaoFactory getInstance(){
        if( daoFactory == null ){
            synchronized (DaoFactory.class){
                if(daoFactory==null){
                    daoFactory = new JDBCDaoFactory();
                }
            }
        }
        return daoFactory;
    }
    public abstract CheckDao createCheckDao();

    public abstract UserDao createUserDao();

    public abstract TourDao createTourDao();
}