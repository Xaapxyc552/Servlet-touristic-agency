package ua.skidchenko.touristic_agency.service.impl;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;
import ua.skidchenko.touristic_agency.dto.UserDTO;
import ua.skidchenko.touristic_agency.entity.enums.Role;
import ua.skidchenko.touristic_agency.entity.User;
import ua.skidchenko.touristic_agency.service.UserService;

import java.util.Optional;

public class UserServiceImpl extends UserService {

    private static final Logger log = LogManager.getLogger(UserServiceImpl.class.getName());

    @Override
    public User saveUser(UserDTO userDTO) {
        return userDao.create(buildUserFromDTO(userDTO));
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public void chargeUserWallet(Long amountOfCharge, String username) {
        log.info("Starting recharging user`s account. Amount: {}. Username: {}", amountOfCharge, username);
        userDao.rechargeUserWallet(amountOfCharge, username);
    }

    private User buildUserFromDTO(UserDTO userDTO) {
        log.info("Building new user from userDTO. UserDTO:{}", userDTO);
        return User.builder()
                .password(BCrypt.hashpw(userDTO.getPassword(),BCrypt.gensalt()))
                .firstname(userDTO.getFirstname())
                .role(Role.ROLE_USER)
                .email(userDTO.getEmail())
                .username(userDTO.getUsername())
                .enabled(true)
                .money(0L)
                .build();
    }

}
