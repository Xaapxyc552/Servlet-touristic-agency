package ua.skidchenko.touristic_agency.dao.impl;

import ua.skidchenko.touristic_agency.dao.OrderOfTours;
import ua.skidchenko.touristic_agency.dao.TourDao;
import ua.skidchenko.touristic_agency.dao.rowmapper.impl.TourRowMapper;
import ua.skidchenko.touristic_agency.entity.Tour;
import ua.skidchenko.touristic_agency.entity.enums.TourStatus;
import ua.skidchenko.touristic_agency.entity.enums.TourType;

import java.sql.*;
import java.util.*;

public class JDBCTourDao implements TourDao {
    private final Connection connection;

    public JDBCTourDao(Connection connection) {
        this.connection = connection;
    }

    private static final String FIND_ALL_BY_TOUR_TYPE_PAGEABLE_ASC_SORTING =
            "select main_tour.id," +
                    "       main_tour.tour_status," +
                    "       main_tour.hotel_type," +
                    "       main_tour.amount_of_persons," +
                    "       main_tour.price," +
                    "       main_tour.burning," +
                    "       tour_types.tour_types," +
                    "       nameukr.name as nameukr," +
                    "       descukr.description as descukr," +
                    "       nameeng.name as nameeng," +
                    "       desceng.description as desceng " +
                    "from touristic_agency.tour main_tour " +
                    "         join touristic_agency.description_translation_mapping descukr on descukr.tour_id = main_tour.id" +
                    "    and descukr.lang_code = 'uk_UA'" +
                    "         join touristic_agency.description_translation_mapping desceng on desceng.tour_id = main_tour.id" +
                    "    and desceng.lang_code = 'en_GB'" +
                    "         join touristic_agency.name_translation_mapping nameukr on nameukr.name_id = main_tour.id\n" +
                    "    and nameukr.lang_code = 'uk_UA'" +
                    "         join touristic_agency.name_translation_mapping nameeng on nameeng.name_id = main_tour.id\n" +
                    "    and nameeng.lang_code = 'en_GB'" +
                    "         join (select tour_id, string_agg(tour_type.type, ',') as tour_types" +
                    "               from touristic_agency.tour" +
                    "                        left join touristic_agency.tour__tour_type on tour.id = tour__tour_type.tour_id" +
                    "                        left join touristic_agency.tour_type on tour__tour_type.tour_type_id = tour_type.id" +
                    "               group by tour_id) as tour_types on tour_types.tour_id=main_tour.id \n" +
                    "where (tour_status = ?)" +
                    "order by burning desc , ";
    private static final String ENDING = " limit ? offset ?;";

    private static final String FIND_BY_ID_AND_TOUR_STATUS =
            "select main_tour.id," +
                    "       main_tour.tour_status," +
                    "       main_tour.hotel_type," +
                    "       main_tour.amount_of_persons," +
                    "       main_tour.price," +
                    "       main_tour.burning," +
                    "       tour_types.tour_types," +
                    "       nameukr.name as nameukr," +
                    "       descukr.description as descukr," +
                    "       nameeng.name as nameeng," +
                    "       desceng.description as desceng " +
                    "from touristic_agency.tour main_tour " +
                    "         join touristic_agency.description_translation_mapping descukr on descukr.tour_id = main_tour.id" +
                    "    and descukr.lang_code = 'uk_UA'" +
                    "         join touristic_agency.description_translation_mapping desceng on desceng.tour_id = main_tour.id" +
                    "    and desceng.lang_code = 'en_GB'" +
                    "         join touristic_agency.name_translation_mapping nameukr on nameukr.name_id = main_tour.id\n" +
                    "    and nameukr.lang_code = 'uk_UA'" +
                    "         join touristic_agency.name_translation_mapping nameeng on nameeng.name_id = main_tour.id\n" +
                    "    and nameeng.lang_code = 'en_GB'" +
                    "         join (select tour_id, string_agg(tour_type.type, ',') as tour_types" +
                    "               from touristic_agency.tour" +
                    "                        left join touristic_agency.tour__tour_type on tour.id = tour__tour_type.tour_id" +
                    "                        left join touristic_agency.tour_type on tour__tour_type.tour_type_id = tour_type.id" +
                    "               group by tour_id) as tour_types on tour_types.tour_id=main_tour.id \n" +
                    "where (tour_status = ?) and (main_tour.id=?);";

    private static final String INSERT_TOUR =
            "INSERT INTO touristic_agency.tour (amount_of_persons, burning, hotel_type, price, tour_status)" +
                    "VALUES (?, ?, ?, ?, ?);";

    private static final String INSERT_TOUR_TOUR_ID_RELATION =
            "INSERT INTO touristic_agency.tour__tour_type (tour_type_id, tour_id) VALUES (?,?);";

    private static final String INSERT_NAMES_INTO_TRANSLATION_TABLE =
            "INSERT INTO touristic_agency.name_translation_mapping(name_id, name, lang_code) " +
                    "values (?,?,?);";

    private static final String INSERT_DESCRIPTION_INTO_TRANSLATION_TABLE =
            "INSERT INTO touristic_agency.description_translation_mapping(tour_id, description, lang_code)" +
                    " values (?,?,?);";


    public List<Tour> findAllSortedPageableByTourStatus(OrderOfTours orderOfTours,
                                                        TourStatus tourStatus,
                                                        int pageSize,
                                                        int pageNum,
                                                        String sortingDirection) {
        String sql = FIND_ALL_BY_TOUR_TYPE_PAGEABLE_ASC_SORTING + " " +
                orderOfTours.getPropertyToSort() + " " + sortingDirection + ENDING;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, tourStatus.name());
            ps.setInt(2, pageSize);
            ps.setInt(3, pageNum);
            ResultSet resultSet = ps.executeQuery();
            TourRowMapper tourRowMapper = new TourRowMapper();
            List<Tour> toursFromDb = new ArrayList<>();
            while (resultSet.next()) {
                toursFromDb.add(tourRowMapper.mapRow(resultSet));
            }
            return toursFromDb;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Override
    public Optional<Tour> findByIdAndTourStatus(Long id, TourStatus status) {
        try (PreparedStatement ps = connection.prepareStatement(FIND_BY_ID_AND_TOUR_STATUS)) {
            ps.setString(1, status.name());
            ps.setLong(2, id);
            ResultSet resultSet = ps.executeQuery();
            TourRowMapper tourRowMapper = new TourRowMapper();
            if (!resultSet.next()) {
                return Optional.empty();
            }
            return Optional.of(tourRowMapper.mapRow(resultSet));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Tour create(Tour tour) {
        try {
            connection.setAutoCommit(false);
            long newTourId;
            try (PreparedStatement ps = connection.prepareStatement(INSERT_TOUR, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, tour.getAmountOfPersons());
                ps.setBoolean(2, tour.isBurning());
                ps.setInt(3, tour.getHotelType().ordinal());
                ps.setLong(4, tour.getPrice());
                ps.setString(5, tour.getTourStatus().name());
                ps.executeUpdate();
                ResultSet generatedKeys = ps.getGeneratedKeys();
                if (!generatedKeys.next()) {
                    throw new SQLException("Tour was not persisted!");
                }
                 newTourId = generatedKeys.getLong(1);
            }
            try (PreparedStatement ps = connection.prepareStatement(INSERT_TOUR_TOUR_ID_RELATION)) {
                for (TourType tourType : tour.getTourTypes()) {
                    ps.setLong(1, tourType.getId());
                    ps.setLong(2, newTourId);
                    ps.executeUpdate();
                }
            }
            try (PreparedStatement ps = connection.prepareStatement(INSERT_NAMES_INTO_TRANSLATION_TABLE)) {
                for (Map.Entry<String,String> entry : tour.getName().entrySet()) {
                    ps.setLong(1, newTourId);
                    ps.setString(2, entry.getValue());
                    ps.setString(3, entry.getKey());
                    ps.executeUpdate();
                }
            }
            try(PreparedStatement ps = connection.prepareStatement(INSERT_DESCRIPTION_INTO_TRANSLATION_TABLE)) {
                for (Map.Entry<String,String> entry : tour.getDescription().entrySet()) {
                    ps.setLong(1, newTourId);
                    ps.setString(2, entry.getValue());
                    ps.setString(3, entry.getKey());
                    ps.executeUpdate();
                }
            }
            connection.commit();
            connection.setAutoCommit(true);
            tour.setId(newTourId);
            return tour;
        } catch (SQLException e) {
            e.printStackTrace();
            //TODO разобраться с непонятным экспешном
            try {
                connection.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return tour;
    }

    @Override
    public void update(Tour entity) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void close() throws Exception {

    }
}
