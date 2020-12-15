package ua.skidchenko.touristic_agency.dao;

import ua.skidchenko.touristic_agency.dao.impl.JDBCDaoFactory;

public abstract class DaoFactory {
    private static DaoFactory daoFactory;

//    public abstract CarDao createCarDao();
//    public abstract DriverDao createDriverDao();

    public static DaoFactory getInstance(){
        if( daoFactory == null ){
            synchronized (DaoFactory.class){
                if(daoFactory==null){
                    DaoFactory temp = new JDBCDaoFactory();
                    daoFactory = temp;
                }
            }
        }
        return daoFactory;
    }

    public abstract UserDao createUserDao();
}