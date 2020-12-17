package ua.skidchenko.touristic_agency.service.impl;


import ua.skidchenko.touristic_agency.dto.UserDTO;
import ua.skidchenko.touristic_agency.entity.enums.Role;
import ua.skidchenko.touristic_agency.entity.User;
import ua.skidchenko.touristic_agency.exceptions.NotPresentInDatabaseException;
import ua.skidchenko.touristic_agency.exceptions.UsernameExistsException;
import ua.skidchenko.touristic_agency.service.UserService;

import java.sql.SQLException;
import java.util.Optional;

//@Log4j2
public class UserServiceImpl extends UserService {

    @Override
//    @Transactional
    public User saveUser(UserDTO userDTO) {
//        log.info("Saving user into DB. User data: " + userDTO.toString());
        if (userDao.findByUsername(userDTO.getUsername()).isPresent()) {
//            log.warn("Username " + userDTO.getUsername() + " already exists in system;");
            throw new UsernameExistsException("Username " + userDTO.getUsername() + " already exists in system;");
        }
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
        User user = userDao.findByUsernameAndRole(username, Role.ROLE_USER)
                .<NotPresentInDatabaseException>orElseThrow(() -> {
//            log.warn("User not present in DB. Recharging interrupted. Username: " + username);
            throw new NotPresentInDatabaseException("User not found in DB. Username: " + username);
        });
        user.setMoney(user.getMoney() + amountOfCharge);
        try {
            userDao.update(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
