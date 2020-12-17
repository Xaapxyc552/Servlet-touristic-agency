package ua.skidchenko.touristic_agency.dao.impl;

import junit.framework.TestCase;
import ua.skidchenko.touristic_agency.dao.DaoFactory;
import ua.skidchenko.touristic_agency.dao.OrderOfTours;
import ua.skidchenko.touristic_agency.dao.TourDao;
import ua.skidchenko.touristic_agency.entity.Tour;
import ua.skidchenko.touristic_agency.entity.enums.HotelType;
import ua.skidchenko.touristic_agency.entity.enums.TourStatus;
import ua.skidchenko.touristic_agency.entity.enums.TourType;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class JDBCTourDaoTest extends TestCase {

    public void testFindAllSortedPageableByTourStatus() {

        TourDao tourDao = DaoFactory.getInstance().createTourDao();
        List<Tour> asc = tourDao.findAllSortedPageableByTourStatus(OrderOfTours.AMOUNT_OF_PERSONS, TourStatus.WAITING, 8, 0, "asc");
        System.out.println("123");
    }

    public void testFindByIdAndTourStatus() {
        TourDao tourDao = DaoFactory.getInstance().createTourDao();
        Optional<Tour> byIdAndTourStatus = tourDao.findByIdAndTourStatus((long) 1, TourStatus.WAITING);
        System.out.println("123");
    }

    public void testCreate() throws SQLException {

        TourDao tourDao = DaoFactory.getInstance().createTourDao();
        String engLangCode = "en_GB";
        String ukrLangCode = "uk_UA";
        Map<String, String> names = new HashMap<>();
        Map<String, String> descriptions = new HashMap<>();
        names.put(ukrLangCode, "Українське ім'я тестового туру");
        names.put(engLangCode, "Engl test name");
        descriptions.put(ukrLangCode, "Український опист тестового туру");
        descriptions.put(engLangCode, "English test description");
        Tour build = Tour.builder()
                .name(names)
                .description(descriptions)
                .burning(false)
                .amountOfPersons(4)
                .price(82000L)
                .hotelType(HotelType.FOUR_STAR)
                .tourStatus(TourStatus.WAITING)
                .tourTypes(Collections.singletonList(TourType.getInstanceByType(TourType.Type.RECREATION)))
                .build();

        tourDao.create(build);
    }

    public void testUpdate() {
        TourDao tourDao = DaoFactory.getInstance().createTourDao();
        Tour tour = tourDao.findByIdAndTourStatus((long) 1, TourStatus.WAITING).get();
        tour.getName().put("en_GB", "Updated recently tour 1 english name");
        tour.getName().put("uk_UA", "Нещодавно оновлене ім'я 1-го туру");
        tour.getDescription().put("en_GB", "Updated recently tour 1 english description");
        tour.getDescription().put("uk_UA", "Нещодавно оновлений опис 1-го туру");
        tour.setTourTypes(Arrays.asList(
                TourType.getInstanceByType(TourType.Type.RECREATION),
                TourType.getInstanceByType(TourType.Type.SHOPPING)
        ));
        tour.setPrice(tour.getPrice() + 10000);
        try {
            tourDao.update(tour);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}