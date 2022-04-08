package cource.project.model.user;

import cource.project.model.Comment;
import cource.project.model.Order;
import cource.project.model.enums.Role;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class User extends BaseEntity {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String username;
    private String password;
    private String repeatPassword;
    private LocalDateTime registeredOn;
    private Role role;
    private List<Order> orders;
    private List<Comment> comments;

    public User() {
        this.role = Role.USER;
        this.orders = new ArrayList<>();
        this.comments = new ArrayList<>();
    }

    public User(Long id, String firstName, String lastName, String email, String phoneNumber, String username,
                String password, String repeatPassword, LocalDateTime registeredOn) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.username = username;
        this.password = password;
        this.repeatPassword = password;
        this.registeredOn = registeredOn;
        this.role = Role.USER;
        this.orders = new ArrayList<>();
        this.comments = new ArrayList<>();
    }


    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getRegisteredOn() {
        return registeredOn;
    }

    public void setRegisteredOn(LocalDateTime registeredOn) {
        this.registeredOn = registeredOn;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;

    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder()
                .append(User.class.getSimpleName() + ": ")
                .append("id = '" + super.getId() + "' ")
                .append("firstName = '" + firstName + "' ")
                .append("lastName = '" + lastName + "' ")
                .append("email = '" + email + "' ")
                .append("phoneNumber = '" + phoneNumber + "' ")
                .append("username = '" + username + "' ")
                .append(System.lineSeparator());
        if (registeredOn == null) {
            registeredOn = LocalDateTime.now();
        }
        sb.append("\t\t" + "registeredOn = '" + registeredOn.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")) + "' ");
        sb.append("role = '" + role.name() + "' ")
                .append("ordersCount = '" + orders.size() + "' ")
                .append("commentsCount = '" + comments.size() + "' ");


        return sb.toString();
    }
}
