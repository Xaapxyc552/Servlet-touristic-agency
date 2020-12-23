package ua.skidchenko.touristic_agency.dao.impl;

import ua.skidchenko.touristic_agency.controller.util.Page;
import ua.skidchenko.touristic_agency.dao.CheckDao;
import ua.skidchenko.touristic_agency.dao.ConnectionPool;
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

    private static final String GET_CHECK_WITH_TOUR_AND_USER =
            "select main_tour.id," +
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
            "where tour_status IN ('REGISTERED','WAITING','SOLD')" +
            "  AND (username = ?)" +
            " order by (case status " +
            "              when 'WAITING_FOR_CONFIRM' then 1" +
            "              when 'CONFIRMED' then 2" +
            "              when 'DECLINED' then 3" +
            "              when 'CANCELED' then 4" +
            "    end) limit ? offset ?;";

    private static final String GET_CHECK_BY_ID_AND_STATUS =
                    "select main_tour.id," +
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
                    "where check_status.status = ?" +
                    "  AND checks.id = ?;";

    private static final String COUNT_ALL_USERS_CHECKS =
            "with user_id as (select id from touristic_agency.\"user\" where username = ?)" +
                    "select count(*) from touristic_agency.\"check\" where user_id=(select max(user_id.id) from user_id);";

    private static final String CREATE_CHECK_AND_BOOK_TOUR_BY_USERNAME_AND_TOUR_ID =
            "begin;" +
                    "update touristic_agency.tour " +
                    "set tour_status='REGISTERED'" +
                    "where id = ?" +
                    "  AND tour_status = 'WAITING';" +
                    "with price as (select touristic_agency.tour.price as prices" +
                    "               from touristic_agency.tour" +
                    "               where touristic_agency.tour.id = ?" +
                    "               limit 1)," +
                    "     user_retrieved_money as" +
                    "         (select touristic_agency.user.money as moneys" +
                    "          from touristic_agency.user" +
                    "          where (touristic_agency.user.username = ?))" +
                    "update touristic_agency.user " +
                    "set money = (Select max(user_retrieved_money.moneys) from user_retrieved_money) - (Select max(price.prices) from price)" +
                    "where username = ?;" +
                    "" +
                    "with user_retrieved_id as" +
                    "         (select touristic_agency.user.id as id" +
                    "          from touristic_agency.user" +
                    "          where (touristic_agency.user.username = ?)" +
                    "          limit 1)" +
                    "        ," +
                    "     temp_tour_price AS (" +
                    "         SELECT price" +
                    "         FROM touristic_agency.tour" +
                    "         WHERE id = ?" +
                    "         limit 1) " +
                    "insert into touristic_agency.check (total_price, status_id, tour_id, user_id)" +
                    "VALUES ((Select max(temp_tour_price.price) from temp_tour_price), 1, ?," +
                    "        (Select max(user_retrieved_id.id) from user_retrieved_id));" +
                    "commit;";

    private static final String CANCEL_BOOKING_BY_ID =
            "begin;" +
                    "update touristic_agency.\"check\"" +
                    "set status_id=4 " +
                    "where id = ?" +
                    "  AND status_id = 1;" +
                    "" +
                    "with tour_cancel_booking_id as (" +
                    "    select tour_id as id" +
                    "    from touristic_agency.\"check\"" +
                    "    where (id = ?)" +
                    ")" +
                    "update touristic_agency.tour  " +
                    "set tour_status = 'WAITING'" +
                    "where id = (select max(tour_cancel_booking_id.id) from tour_cancel_booking_id)" +
                    "  AND tour_status = 'REGISTERED';" +
                    "" +
                    "with current_user_money as (" +
                    "    select money, \"user\".id" +
                    "    from touristic_agency.check" +
                    "             left join touristic_agency.user on \"check\".user_id = \"user\".id" +
                    "    where (\"check\".id = ?)" +
                    ")," +
                    "     check_total_price as" +
                    "         (select total_price" +
                    "          from touristic_agency.\"check\"" +
                    "          where id = ?)" +
                    "update touristic_agency.\"user\"" +
                    "set money=(select max(current_user_money.money) from current_user_money) +" +
                    "          (select max(check_total_price.total_price) from check_total_price)" +
                    "where id = (select max(current_user_money.id) from current_user_money);" +
                    "commit;";

    public static final String GET_ALL_WAITING_FOR_CONFORMATION =
                    "select main_tour.id," +
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
                    "from touristic_agency.tour main_tour " +
                    "         join touristic_agency.description_translation_mapping descukr" +
                    "              on descukr.tour_id = main_tour.id" +
                    "                  and descukr.lang_code = 'uk_UA'" +
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
                    "where check_status.status = 'WAITING_FOR_CONFIRM';";

    private static final String DECLINE_BOOKING_BY_ID =
            "begin;" +
                    "update touristic_agency.\"check\"" +
                    "set status_id=3 " +
                    "where id = ?" +
                    "  AND status_id = 1;" +
                    "" +
                    "with tour_cancel_booking_id as (" +
                    "    select tour_id as id" +
                    "    from touristic_agency.\"check\"" +
                    "    where (id = ?)" +
                    ")" +
                    "update touristic_agency.tour " +
                    "set tour_status = 'WAITING'" +
                    "where id = (select max(tour_cancel_booking_id.id) from tour_cancel_booking_id)" +
                    "  AND tour_status = 'REGISTERED';" +
                    "" +
                    "with current_user_money as (" +
                    "    select money, \"user\".id" +
                    "    from touristic_agency.check" +
                    "             left join touristic_agency.user on \"check\".user_id = \"user\".id" +
                    "    where (\"check\".id = ?)" +
                    ")," +
                    "     check_total_price as" +
                    "         (select total_price" +
                    "          from touristic_agency.\"check\"" +
                    "          where id = ?)" +
                    "update touristic_agency.\"user\"" +
                    "set money=((select max(current_user_money.money) from current_user_money) +" +
                    "           (select max(check_total_price.total_price) from check_total_price))" +
                    "where id = (select max(current_user_money.id) from current_user_money);" +
                    "commit;";

    public static final String CONFIRM_BOOKING_BY_ID =
            "begin;" +
                    "update touristic_agency.\"check\"" +
                    "set status_id=2 " +
                    "where id = ?" +
                    "  AND status_id = 1;" +
                    "" +
                    "with tour_cancel_booking_id as (" +
                    "    select tour_id as id" +
                    "    from touristic_agency.\"check\"" +
                    "    where (id = ?)" +
                    ")" +
                    "update touristic_agency.tour " +
                    "set tour_status='SOLD'" +
                    "where id = (select max(tour_cancel_booking_id.id) from tour_cancel_booking_id)" +
                    "  AND tour_status = 'REGISTERED';" +
                    "commit;";

    @Override
    public Page<Check> findAllByUserOrderByStatus(String username, int pageSize, int pageNum) throws SQLException {
        List<Check> checks;
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(GET_CHECK_WITH_TOUR_AND_USER);
             PreparedStatement st = connection.prepareStatement(COUNT_ALL_USERS_CHECKS)) {
            ps.setString(1, username);
            ps.setInt(2, pageSize);
            ps.setInt(3, pageNum * pageSize);
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
            st.setString(1,username);
            ResultSet countOfRows = st.executeQuery();
            countOfRows.next();

            return new Page<>(checks,(int) Math.ceil((double)countOfRows.getInt(1)/pageSize),pageNum);
        } catch (SQLException e) {
            throw new SQLException("Exception while retrieving checks from DB.", e);
        }
    }

    @Override
    public List<Check> findAllByStatus(CheckStatus status, int pageSize, int pageNum) {
        List<Check> checks = new ArrayList<>();
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(GET_ALL_WAITING_FOR_CONFORMATION)) {
            ResultSet resultSet = ps.executeQuery();
            TourRowMapper tourRowMapper = new TourRowMapper();
            UserRowMapper userRowMapper = new UserRowMapper();
            CheckRowMapper checkRowMapper = new CheckRowMapper();
            Check check;
            Tour tour;
            User user;
            while (resultSet.next()) {
                 check = checkRowMapper.mapRow(resultSet);
                 tour = tourRowMapper.mapRow(resultSet);
                 user = userRowMapper.mapRow(resultSet);
                 check.setUser(user);
                 check.setTour(tour);
                 checks.add(check);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return checks;
    }

    @Override
    public Optional<Check> findByIdAndStatus(Long id, CheckStatus status) {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(GET_CHECK_BY_ID_AND_STATUS)) {
            ps.setLong(2, id);
            ps.setString(1, status.getStatus().name());
            ResultSet resultSet = ps.executeQuery();
            TourRowMapper tourRowMapper = new TourRowMapper();
            UserRowMapper userRowMapper = new UserRowMapper();
            CheckRowMapper checkRowMapper = new CheckRowMapper();
            if (resultSet.next()) {
                Check check = checkRowMapper.mapRow(resultSet);
                Tour tour = tourRowMapper.mapRow(resultSet);
                User user = userRowMapper.mapRow(resultSet);
                check.setUser(user);
                check.setTour(tour);
                return Optional.of(check);
            } else {
                throw new NotPresentInDatabaseException("Check with id: " + id + " not present in database.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void createNewCheck(String username, Long tourId) {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(CREATE_CHECK_AND_BOOK_TOUR_BY_USERNAME_AND_TOUR_ID)) {
            ps.setLong(1, tourId);
            ps.setLong(2, tourId);
            ps.setString(3, username);
            ps.setString(4, username);
            ps.setString(5, username);
            ps.setLong(6, tourId);
            ps.setLong(7, tourId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void cancelBookingByCheckId(Long checkId) {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(CANCEL_BOOKING_BY_ID)) {
            ps.setLong(1, checkId);
            ps.setLong(2, checkId);
            ps.setLong(3, checkId);
            ps.setLong(4, checkId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void declineBookingById(Long checkId) {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(DECLINE_BOOKING_BY_ID)) {
            ps.setLong(1, checkId);
            ps.setLong(2, checkId);
            ps.setLong(3, checkId);
            ps.setLong(4, checkId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void confirmBookingByCheckId(Long checkId) {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(CONFIRM_BOOKING_BY_ID)) {
            ps.setLong(1, checkId);
            ps.setLong(2, checkId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
    public void close() throws Exception {

    }
}
