package ua.skidchenko.touristic_agency.dao.impl;

import ua.skidchenko.touristic_agency.dao.CheckDao;
import ua.skidchenko.touristic_agency.dao.OrderOfTours;
import ua.skidchenko.touristic_agency.dao.TourDao;
import ua.skidchenko.touristic_agency.dao.rowmapper.impl.CheckRowMapper;
import ua.skidchenko.touristic_agency.dao.rowmapper.impl.TourRowMapper;
import ua.skidchenko.touristic_agency.dao.rowmapper.impl.UserRowMapper;
import ua.skidchenko.touristic_agency.entity.Check;
import ua.skidchenko.touristic_agency.entity.Tour;
import ua.skidchenko.touristic_agency.entity.User;
import ua.skidchenko.touristic_agency.entity.enums.CheckStatus;
import ua.skidchenko.touristic_agency.entity.enums.TourStatus;
import ua.skidchenko.touristic_agency.entity.enums.TourType;
import ua.skidchenko.touristic_agency.exceptions.NotPresentInDatabaseException;

import java.sql.*;
import java.util.*;

public class JDBCCheckDao implements CheckDao {
    private final Connection connection;

    private static final String GET_CHECK_WITH_TOUR_AND_USER = "select main_tour.id," +
            "       main_tour.tour_status," +
            "       main_tour.hotel_type," +
            "       main_tour.amount_of_persons," +
            "       main_tour.price," +
            "       main_tour.burning," +
            "       tour_types.tour_types," +
            "       nameukr.name        as nameukr," +
            "       descukr.description as descukr," +
            "       nameeng.name        as nameeng," +
            "       desceng.description as desceng," +
            "       checks.total_price," +
            "       check_status.status as check_status," +
            "       checks.id           as check_id," +
            "       checks.user_id," +
            "       users.role," +
            "       users.enabled," +
            "       users.email," +
            "       users.firstname," +
            "       users.money," +
            "       users.password," +
            "       users.username " +
            " from touristic_agency.tour main_tour " +
            "         join touristic_agency.description_translation_mapping descukr on descukr.tour_id = main_tour.id" +
            "    and descukr.lang_code = 'uk_UA'" +
            "         join touristic_agency.description_translation_mapping desceng on desceng.tour_id = main_tour.id" +
            "    and desceng.lang_code = 'en_GB'" +
            "         join touristic_agency.name_translation_mapping nameukr on nameukr.name_id = main_tour.id" +
            "    and nameukr.lang_code = 'uk_UA'" +
            "         join touristic_agency.name_translation_mapping nameeng on nameeng.name_id = main_tour.id" +
            "    and nameeng.lang_code = 'en_GB'" +
            "         join (select tour_id, string_agg(tour_type.type, ',') as tour_types" +
            "               from touristic_agency.tour" +
            "                        left join touristic_agency.tour__tour_type on tour.id = tour__tour_type.tour_id" +
            "                        left join touristic_agency.tour_type on tour__tour_type.tour_type_id = tour_type.id" +
            "               group by tour_id) as tour_types on tour_types.tour_id = main_tour.id" +
            "         right join touristic_agency.check checks on main_tour.id = checks.tour_id" +
            "         join touristic_agency.check_status on touristic_agency.check_status.id = checks.status_id" +
            "         join touristic_agency.user users on checks.user_id = users.id " +
            "where tour_status IN ('REGISTERED', 'CANCELED')" +
            "  AND (username = ?)" +
            " order by (case check_status.status " +
            "              when 'WAITING_FOR_CONFIRM' then 1" +
            "              when 'CANCELED' then 2" +
            "    end) limit ? offset ?;";

    public JDBCCheckDao(Connection connection) {
        this.connection = connection;
    }


    @Override
    public List<Check> findAllByUserOrderByStatus(String username, int pageSize, int pageNum) throws SQLException {
        List<Check> checks;
        try (PreparedStatement ps = connection.prepareStatement(GET_CHECK_WITH_TOUR_AND_USER)){
            ps.setString(1,username);
            ps.setInt(2,pageSize);
            ps.setInt(3,pageNum*pageSize);
            ResultSet resultSet = ps.executeQuery();
            TourRowMapper tourRowMapper = new TourRowMapper();
            UserRowMapper userRowMapper = new UserRowMapper();
            CheckRowMapper checkRowMapper = new CheckRowMapper();
            checks = new ArrayList<>();
            while (resultSet.next()) {
                Check check = checkRowMapper.mapRow(resultSet);
                Tour tour = tourRowMapper.mapRow(resultSet);
                User user = userRowMapper.mapRow(resultSet);
                check.setUser(user);
                check.setTour(tour);
                checks.add(check);
            }
        } catch (SQLException e) {
            throw new SQLException("Exception while retrieving checks from DB.", e);
        }

        return checks;
    }

    @Override
    public List<Check> findAllByStatus(CheckStatus status, int pageSize, int pageNum) {
        return null;
    }

    @Override
    public Optional<Check> findByIdAndStatus(Long id, CheckStatus statuse) {
        return Optional.empty();
    }

    @Override
    public Check create(Check entity) {
        return null;
    }

    @Override
    public Check update(Check entity) throws SQLException {
        return null;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public Connection getConnection() {
        return this.connection;
    }

    @Override
    public void close() throws Exception {
        this.connection.close();
    }
}
