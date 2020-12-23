package ua.skidchenko.touristic_agency.service.impl;


import ua.skidchenko.touristic_agency.dto.UserDTO;
import ua.skidchenko.touristic_agency.entity.enums.Role;
import ua.skidchenko.touristic_agency.entity.User;
import ua.skidchenko.touristic_agency.exceptions.UsernameExistsException;
import ua.skidchenko.touristic_agency.service.UserService;

import java.util.Optional;

//@Log4j2
public class UserServiceImpl extends UserService {

    @Override
//    @Transactional
    public User saveUser(UserDTO userDTO) {
        return userDao.create(buildUserFromDTO(userDTO));
    }
    @Override
    public Optional<User> getUserByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
//    @Transactional
    public void chargeUserWallet(Long amountOfCharge, String username) {
//        log.info("Starting recharging user`s account. Amount: " + amountOfCharge + ". Username: " + username);
        userDao.rechargeUserWallet(amountOfCharge,username);
    }

    private User buildUserFromDTO(UserDTO userDTO) {
//        log.info("Building new user from userDTO. UserDTO: " + userDTO.toString());
        return User.builder()
                .password(
                        //TODO подключить пассворд энкоер BCrypt
                        //passwordEncoder.encode(userDTO.getPassword())
                        userDTO.getPassword()
                )
                .firstname(userDTO.getFirstname())
                .role(Role.ROLE_USER)
                .email(userDTO.getEmail())
                .username(userDTO.getUsername())
                .enabled(true)
                .money(0L)
                .build();
    }

}
