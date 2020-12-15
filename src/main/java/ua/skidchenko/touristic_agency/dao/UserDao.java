package ua.skidchenko.touristic_agency.dao;

import ua.skidchenko.touristic_agency.entity.User;
import ua.skidchenko.touristic_agency.entity.enums.Role;

import java.util.Optional;

public interface UserDao extends GenericDao<User>{

    Optional<User> findByUsernameAndRole(String username, Role role);

    Optional<User> findByUsername(String username);

}
