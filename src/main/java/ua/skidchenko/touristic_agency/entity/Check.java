package ua.skidchenko.touristic_agency.entity;

import ua.skidchenko.touristic_agency.entity.enums.CheckStatus;

import java.util.Objects;

public class Check {

    private Long id;
    private Tour tour;
    private User user;
    private Long totalPrice;
    private CheckStatus status;

    public Check(Long id, Tour tour, User user, Long totalPrice, CheckStatus status) {
        this.id = id;
        this.tour = tour;
        this.user = user;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    public Check() {
    }

    public static CheckBuilder builder() {
        return new CheckBuilder();
    }

    public void setTotalPrice(Long totalPrice) {
        if (totalPrice < 0) {
            throw new IllegalArgumentException("Total price cannot be negative!");
        }
        this.totalPrice = totalPrice;
    }

    public Long getId() {
        return this.id;
    }

    public Tour getTour() {
        return this.tour;
    }

    public User getUser() {
        return this.user;
    }

    public Long getTotalPrice() {
        return this.totalPrice;
    }

    public CheckStatus getStatus() {
        return this.status;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setStatus(CheckStatus status) {
        this.status = status;
    }

    public String toString() {
        return "Check(id=" + this.getId() + ", tour=" + this.getTour() +
                ", user=" + this.getUser() + ", totalPrice=" + this.getTotalPrice() +
                ", status=" + this.getStatus() + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Check check = (Check) o;
        return Objects.equals(id, check.id) &&
                Objects.equals(tour, check.tour) &&
                Objects.equals(user, check.user) &&
                Objects.equals(totalPrice, check.totalPrice) &&
                Objects.equals(status, check.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tour, user, totalPrice, status);
    }

    public static class CheckBuilder {
        private Long id;
        private Tour tour;
        private User user;
        private Long totalPrice;
        private CheckStatus status;

        CheckBuilder() {
        }

        public CheckBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public CheckBuilder tour(Tour tour) {
            this.tour = tour;
            return this;
        }

        public CheckBuilder user(User user) {
            this.user = user;
            return this;
        }

        public CheckBuilder totalPrice(Long totalPrice) {
            if (totalPrice < 0) {
                throw new IllegalArgumentException("Total price cannot be negative!");
            }
            this.totalPrice = totalPrice;
            return this;
        }

        public CheckBuilder status(CheckStatus status) {
            this.status = status;
            return this;
        }

        public Check build() {
            return new Check(id, tour, user, totalPrice, status);
        }

        public String toString() {
            return "Check.CheckBuilder(id=" + this.id + ", tour=" + this.tour + ", user=" + this.user + ", totalPrice=" + this.totalPrice + ", status=" + this.status + ")";
        }
    }
}
