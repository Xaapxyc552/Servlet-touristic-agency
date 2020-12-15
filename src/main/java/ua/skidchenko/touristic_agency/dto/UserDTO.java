package ua.skidchenko.touristic_agency.dto;

import javax.validation.constraints.*;
import java.util.Objects;

public class UserDTO {

    @NotBlank(message = "Firstname field blank!")
    @Size(min = 2, max = 20, message = "Firstname field not in size 2 - 20.")
    @Pattern(regexp = "[A-Za-zА-Яа-я]{2,20}",
            message = "Firstname field should match regexp [A-Za-zА-Яа-я]{1,20}")
    private String firstname;

    @NotNull(message = "Username field null!")
    @NotBlank(message = "Username field blank!")
    @Size(min = 2, max = 30, message = "Username field not in size 2 - 30.")
    @Pattern(regexp = "[A-Za-z]{2,30}",
            message = "Username field should match regexp [A-Za-z]{2,30}")
    private String username;

    @NotBlank(message = "Email field blank!")
    @Email
    private String email;

    @NotNull(message = "Password field null!")
    @NotBlank(message = "Password field blank!")
    @Size(min = 8, max = 30,
            message = "Password field not in size 8 - 30.")
    private String password;

    public UserDTO(String firstname, String username, String email,  String password) {
        this.firstname = firstname;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public UserDTO() {
    }

    public static UserDTOBuilder builder() {
        return new UserDTOBuilder();
    }

    public String getFirstname() {
        return this.firstname;
    }

    public  String getUsername() {
        return this.username;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO userDTO = (UserDTO) o;
        return Objects.equals(firstname, userDTO.firstname) &&
                Objects.equals(username, userDTO.username) &&
                Objects.equals(email, userDTO.email) &&
                Objects.equals(password, userDTO.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstname, username, email, password);
    }

    public String toString() {
        return "UserDTO(firstname=" + this.getFirstname() + ", username=" + this.getUsername() + ", email=" + this.getEmail() + ")";
    }

    public static class UserDTOBuilder {
        private String firstname;
        private String username;
        private String email;
        private String password;

        UserDTOBuilder() {
        }

        public UserDTOBuilder firstname(String firstname) {
            this.firstname = firstname;
            return this;
        }

        public UserDTOBuilder username( String username) {
            this.username = username;
            return this;
        }

        public UserDTOBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserDTOBuilder password(String password) {
            this.password = password;
            return this;
        }

        public UserDTO build() {
            return new UserDTO(firstname, username, email, password);
        }

        public String toString() {
            return "UserDTO.UserDTOBuilder(firstname=" + this.firstname + ", username=" + this.username + ", email=" + this.email + ", password=" + this.password + ")";
        }
    }
}
