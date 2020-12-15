package ua.skidchenko.touristic_agency.service;

import ua.skidchenko.touristic_agency.dao.DaoFactory;
import ua.skidchenko.touristic_agency.dao.UserDao;
import ua.skidchenko.touristic_agency.dto.UserDTO;
import ua.skidchenko.touristic_agency.entity.User;

import java.util.Optional;


public abstract class UserService {
    protected final UserDao userDao = DaoFactory.getInstance().createUserDao();

    public abstract User saveUser(UserDTO userDTO);

    public abstract Optional<User> getUserByUsername(String username);

    public abstract void chargeUserWallet(Long amountOfCharge, String username);

}