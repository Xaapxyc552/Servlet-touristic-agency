package ua.skidchenko.touristic_agency.entity.enums;

public enum Role  {
    ROLE_USER, ROLE_MANAGER, ROLE_ADMIN;

    public String getAuthority() {
        return name();
    }
}