package ua.skidchenko.touristic_agency.dto;

import ua.skidchenko.touristic_agency.entity.Check;
import ua.skidchenko.touristic_agency.entity.enums.CheckStatus;

import java.time.LocalDateTime;
import java.util.Map;


public class CheckDTO {

    private String id;
    private Map<String, String> tourName;
    private String userName;
    private String userEmail;
    private String totalPrice;
    private CheckStatus status;
    private LocalDateTime creationTime;
    private LocalDateTime lastModificationTime;

    CheckDTO(String id,
             Map<String, String> tourName,
             String userName,
             String userEmail,
             String totalPrice,
             CheckStatus status,
             LocalDateTime creationTime,
             LocalDateTime lastModificationTime) {
        this.id = id;
        this.tourName = tourName;
        this.userName = userName;
        this.userEmail = userEmail;
        this.totalPrice = totalPrice;
        this.status = status;
        this.creationTime = creationTime;
        this.lastModificationTime = lastModificationTime;
    }

    public static CheckDTOBuilder builder() {
        return new CheckDTOBuilder();
    }

    public String getId() {
        return this.id;
    }

    public Map<String, String> getTourName() {
        return this.tourName;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getUserEmail() {
        return this.userEmail;
    }

    public String getTotalPrice() {
        return this.totalPrice;
    }

    public CheckStatus getStatus() {
        return this.status;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTourName(Map<String, String> tourName) {
        this.tourName = tourName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setStatus(CheckStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public LocalDateTime getLastModificationTime() {
        return lastModificationTime;
    }

    public void setLastModificationTime(LocalDateTime lastModificationTime) {
        this.lastModificationTime = lastModificationTime;
    }

    public static class CheckDTOBuilder {
        private String id;
        private Map<String, String> tourName;
        private String userName;
        private String userEmail;
        private String totalPrice;
        private CheckStatus status;
        private LocalDateTime creationTime;
        private LocalDateTime lastModificationTime;

        CheckDTOBuilder() {
        }

        public CheckDTOBuilder id(String id) {
            this.id = id;
            return this;
        }

        public CheckDTOBuilder tourName(Map<String, String> tourName) {
            this.tourName = tourName;
            return this;
        }

        public CheckDTOBuilder userName(String userName) {
            this.userName = userName;
            return this;
        }

        public CheckDTOBuilder userEmail(String userEmail) {
            this.userEmail = userEmail;
            return this;
        }

        public CheckDTOBuilder totalPrice(String totalPrice) {
            this.totalPrice = totalPrice;
            return this;
        }

        public CheckDTOBuilder status(CheckStatus status) {
            this.status = status;
            return this;
        }

        public CheckDTOBuilder creationTime(LocalDateTime creationTime) {
            this.creationTime = creationTime;
            return this;
        }

        public CheckDTOBuilder lastModificationTime(LocalDateTime lastModificationTime) {
            this.lastModificationTime = lastModificationTime;
            return this;
        }


        public CheckDTO build() {
            return new CheckDTO(id, tourName, userName, userEmail, totalPrice, status, creationTime, lastModificationTime);
        }

        public String toString() {
            return "CheckDTO.CheckDTOBuilder(id=" + this.id + ", tourName=" + this.tourName + ", userName=" +
                    this.userName + ", userEmail=" + this.userEmail + ", totalPrice=" + this.totalPrice + ", status=" +
                    this.status + ")";
        }

    }
}
