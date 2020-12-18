package ua.skidchenko.touristic_agency.entity.enums;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

public class CheckStatus {
//    @Id
    private Long id;
    private Status status;

    public void setId(Long id) {
        throw new UnsupportedOperationException("Id cannot be changed!");
    }


    public CheckStatus(Long id, Status status) {
        this.id = id;
        this.status = status;
    }

    public CheckStatus() {
    }

    public Long getId() {
        return this.id;
    }

    public Status getStatus() {
        return this.status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CheckStatus that = (CheckStatus) o;
        return Objects.equals(id, that.id) &&
                status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status);
    }

    public String toString() {
        return "CheckStatus(id=" + this.getId() + ", status=" + this.getStatus() + ")";
    }
    public enum Status {
        WAITING_FOR_CONFIRM, CONFIRMED, DECLINED, CANCELED

    }

    public CheckStatus(Status status) {
        this.id = (long) status.ordinal() + 1;
        this.status = status;
    }
    private static final Map<Status, CheckStatus> map = new EnumMap<>(Status.class);


    public static CheckStatus getInstanceByEnum(Status status) {
        return map.computeIfAbsent(status, CheckStatus::new);
    }
}