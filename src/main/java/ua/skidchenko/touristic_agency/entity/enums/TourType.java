package ua.skidchenko.touristic_agency.entity.enums;


import ua.skidchenko.touristic_agency.entity.Tour;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


//@Entity
//@Table(name = "tour_type")
public class TourType {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Enumerated(EnumType.STRING)
//    @Column(name = "type")
    private Type type;

//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(name = "tour__tour_type",
//            joinColumns = @JoinColumn(name = "tour_type_id"),
//            inverseJoinColumns = @JoinColumn(name = "tour_id"))
    private List<Tour> tour;

    public void setId(Long id) {
        throw new UnsupportedOperationException("Id cannot be changed!");
    }

    public static List<TourType> getTourTypesFromStringList(List<String> list) {
        return list.stream().
                map(el -> TourType.getInstanceByType(Enum.valueOf(Type.class, el)))
                .collect(Collectors.toList());
    }


    public TourType(Long id, Type type, List<Tour> tour) {
        this.id = id;
        this.type = type;
        this.tour = tour;
    }

    public TourType() {
    }

    public Long getId() {
        return this.id;
    }

    public Type getType() {
        return this.type;
    }

    public List<Tour> getTour() {
        return this.tour;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setTour(List<Tour> tour) {
        this.tour = tour;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TourType tourType = (TourType) o;
        return Objects.equals(id, tourType.id) &&
                type == tourType.type &&
                Objects.equals(tour, tourType.tour);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, tour);
    }

    public String toString() {
        return "TourType(id=" + this.getId() + ", type=" + this.getType() + ")";
    }
    public enum Type {
        RECREATION, EXCURSION, SHOPPING

    }

    public TourType(Type type) {
        this.id = (long) (type.ordinal() + 1);
        this.type = type;
    }
    private static final Map<Type, TourType> map = new EnumMap<>(Type.class);


    public static TourType getInstanceByType(Type type) {
        return map.computeIfAbsent(type, TourType::new);
    }
}
