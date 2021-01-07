package ua.skidchenko.touristic_agency.dao.impl;

import ua.skidchenko.touristic_agency.dao.connection_source.ConnectionPool;
import ua.skidchenko.touristic_agency.dao.UserDao;
import ua.skidchenko.touristic_agency.dao.rowmapper.impl.UserRowMapper;
import ua.skidchenko.touristic_agency.entity.User;
import ua.skidchenko.touristic_agency.entity.enums.Role;
import ua.skidchenko.touristic_agency.exceptions.UsernameExistsException;

import java.sql.*;
import java.util.Optional;

public class JDBCUserDao implements UserDao {

    private static final String USER_CREATE =
            "INSERT INTO touristic_agency.user (username,password,email,firstname,role,enabled,money) VALUES (?,?,?,?,?,?,?);";

    private static final String USER_BY_USERNAME_AND_ROLE =
            "SELECT * FROM touristic_agency.user WHERE (username=?) AND (role=?);";

    private static final String USER_BY_USERNAME =
            "SELECT * FROM touristic_agency.user WHERE (username=?);";

    private static final String UPDATE_USER =
            "UPDATE touristic_agency.user SET username=?, password=?, email=?,firstname=?, role=?, enabled=?, money=?" +
                    " where touristic_agency.user.id=?";

    private static final String RECHARGE_USER_WALLET =
            "update touristic_agency.\"user\" " +
                    "set money = (select max(money) from touristic_agency.\"user\" where username=?)+? where username = ?;";

    @Override
    public User create(User entity) {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(USER_CREATE, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, entity.getUsername());
            ps.setString(2, entity.getPassword());
            ps.setString(3, entity.getEmail());
            ps.setString(4, entity.getFirstname());
            ps.setString(5, entity.getRole().name());
            ps.setBoolean(6, entity.isEnabled());
            ps.setLong(7, entity.getMoney());
            ps.executeUpdate();
            ResultSet generatedKeys = ps.getGeneratedKeys();
            generatedKeys.next();
            entity.setId(generatedKeys.getLong(1));
            return entity;
        } catch (SQLException e) {
            e.printStackTrace();
            switch (e.getSQLState()) {
                case ("23505"):
                    throw new UsernameExistsException();
                default:throw new IllegalStateException("Unhandled exception while creating a user");
            }
        }
    }

    @Override
    public User update(User entity) {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(UPDATE_USER)) {
            ps.setString(1, entity.getUsername());
            ps.setString(2, entity.getPassword());
            ps.setString(3, entity.getEmail());
            ps.setString(4, entity.getFirstname());
            ps.setString(5, entity.getRole().name());
            ps.setBoolean(6, entity.isEnabled());
            ps.setLong(7, entity.getMoney());
            ps.setLong(8, entity.getId());
            ps.executeUpdate();
            return entity;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entity;
    }

    @Override
    public void delete(int id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<User> findByUsernameAndRole(String username, Role role) {
        User user = null;
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(USER_BY_USERNAME_AND_ROLE)) {
            ps.setString(1, username);
            ps.setString(2, role.name());
            ResultSet resultSet = ps.executeQuery();
            UserRowMapper rowMapper = new UserRowMapper();
            if (!resultSet.next()) {
                return Optional.empty();
            }
            user = rowMapper.mapRow(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        User user;
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(USER_BY_USERNAME)) {
            ps.setString(1, username);
            ResultSet resultSet = ps.executeQuery();
            UserRowMapper rowMapper = new UserRowMapper();
            if (resultSet.next()) {
                user = rowMapper.mapRow(resultSet);
                return Optional.of(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void rechargeUserWallet(Long amountOfCharge, String username) {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(RECHARGE_USER_WALLET)) {
            ps.setString(1, username);
            ps.setLong(2, amountOfCharge);
            ps.setString(3, username);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
