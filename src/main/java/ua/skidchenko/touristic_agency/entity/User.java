package ua.skidchenko.touristic_agency.entity;


import ua.skidchenko.touristic_agency.entity.enums.Role;

import java.util.Objects;

public class User {
    private Long id;
    private String username;
    private String password;
    private Role role;
    private String email;
    private String firstname;
    private boolean enabled;
    private Long money;

    public User(Long id, String username, String password, Role role, String email, String firstname, boolean enabled, Long money) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.email = email;
        this.firstname = firstname;
        this.enabled = enabled;
        this.money = money;
    }

    public User() {
    }

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    public Role getAuthorities() {
        return role;
    }

    public void setMoney(Long money) {
        if(money < 0) throw new IllegalArgumentException("Money value cannot be negative!");
        this.money = money;
    }

    public Long getId() {
        return this.id;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public Role getRole() {
        return this.role;
    }

    public String getEmail() {
        return this.email;
    }

    public String getFirstname() {
        return this.firstname;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public Long getMoney() {
        return this.money;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return  enabled == user.enabled &&
                Objects.equals(id, user.id) &&
                Objects.equals(username, user.username) &&
                Objects.equals(password, user.password) &&
                role == user.role &&
                Objects.equals(email, user.email) &&
                Objects.equals(firstname, user.firstname) &&
                Objects.equals(money, user.money);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, role, email, firstname, enabled, money);
    }

    public String toString() {
        return "User(id=" + this.getId() + ", username=" + this.getUsername() + ", password=" + this.getPassword() + ", role=" + this.getRole() + ", email=" + this.getEmail() + ", firstname=" + this.getFirstname() + ", enabled=" + this.isEnabled() + ", money=" + this.getMoney() + ")";
    }

    public static class UserBuilder {
        private Long id;
        private String username;
        private String password;
        private Role role;
        private String email;
        private String firstname;
        private boolean enabled;
        private Long money;

        UserBuilder() {
        }

        public UserBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public UserBuilder username(String username) {
            this.username = username;
            return this;
        }

        public UserBuilder password(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder role(Role role) {
            this.role = role;
            return this;
        }

        public UserBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder firstname(String firstname) {
            this.firstname = firstname;
            return this;
        }

        public UserBuilder enabled(boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        public UserBuilder money(Long money) {
            this.money = money;
            return this;
        }

        public User build() {
            return new User(id, username, password, role, email, firstname, enabled, money);
        }

        public String toString() {
            return "User.UserBuilder(id=" + this.id + ", username=" + this.username + ", password=" + this.password + ", role=" + this.role + ", email=" + this.email + ", firstname=" + this.firstname + ", enabled=" + this.enabled + ", money=" + this.money + ")";
        }
    }
}

