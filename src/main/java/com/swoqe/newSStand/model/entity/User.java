package com.swoqe.newSStand.model.entity;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable{

    private static final long serialVersionUID = 6297385302078200511L;

    private Long id;
    private String firstName;
    private String lastName;
    private String password;
    private UserRole userRole;
    private boolean locked = false;
    private boolean enable = true;
    private String email;

    public User(String firstName, String lastName, String password, UserRole userRole, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.userRole = userRole;
        this.email = email;
    }

    public User() {
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return locked == user.locked && enable == user.enable && id.equals(user.id) && firstName.equals(user.firstName) && lastName.equals(user.lastName) && password.equals(user.password) && userRole == user.userRole && email.equals(user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, password, userRole, locked, enable, email);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", userRole=" + userRole +
                ", locked=" + locked +
                ", enable=" + enable +
                ", email='" + email + '\'' +
                '}';
    }
}
