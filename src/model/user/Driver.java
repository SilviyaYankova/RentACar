package model.user;

import model.enums.DriverStatus;
import model.enums.Role;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Driver extends User {

    private DriverStatus driverStatus;
    private double pricePerDay;
    private List<User> users;
    private List<User> sellers;
    private List<LocalDateTime> pickUpDate;
    private List<LocalDateTime> dropOffDate;

    public Driver() {
        this.users = new ArrayList<>();
        this.sellers = new ArrayList<>();
        this.pickUpDate = new ArrayList<>();
        this.dropOffDate = new ArrayList<>();
    }

    public Driver(String firstName, String lastName, String email, String phoneNumber, String username,
                  String password, String repeatPassword, LocalDateTime registeredOn, double price) {
        super(firstName, lastName, email, phoneNumber, username, password, repeatPassword, registeredOn);
        this.setRole(Role.DRIVER);
        this.driverStatus = DriverStatus.AVAILABLE;
        this.pricePerDay = price;
        this.users = new ArrayList<>();
        this.sellers = new ArrayList<>();
        this.pickUpDate = new ArrayList<>();
        this.dropOffDate = new ArrayList<>();
    }

    public List<LocalDateTime> getPickUpDates() {
        return pickUpDate;
    }

    public void setPickUpDate(List<LocalDateTime> pickUpDate) {
        this.pickUpDate = pickUpDate;
    }

    public List<LocalDateTime> getDropOffDate() {
        return dropOffDate;
    }

    public void setDropOffDate(List<LocalDateTime> dropOffDate) {
        this.dropOffDate = dropOffDate;
    }

    public DriverStatus getDriverStatus() {
        return driverStatus;
    }

    public void setDriverStatus(DriverStatus driverStatus) {
        this.driverStatus = driverStatus;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<User> getSellers() {
        return sellers;
    }

    public void setSellers(List<User> sellers) {
        this.sellers = sellers;
    }

    public double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder()
                .append(Driver.class.getSimpleName() + ": ")
                .append("id = '" + super.getId() + "' ")
                .append("firstName = '" + super.getFirstName() + "' ")
                .append("lastName = '" + super.getLastName() + "' ")
                .append("email = '" + super.getEmail() + "' ")
                .append("phoneNumber = '" + super.getPhoneNumber() + "' ")
                .append("username = '" + super.getUsername() + "' ")
                .append("password = '" + super.getPassword() + String.format(" %n"))
                .append("\t\t" + "registeredOn = '" + super.getRegisteredOn().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")) + "' ")
                .append("role = " + super.getRole() + "' ")
                .append("orders = " + super.getOrders().size() + "' ")
                .append("driverStatus = " + driverStatus + "' ")
                .append("price = " + pricePerDay + "' ")
                .append("users = " + users.size() + "' ")
                .append("sellers = " + sellers.size() + "' ");


        if (pickUpDate != null) {
            sb.append("pickUpDate = '" + pickUpDate.size() + "' ");
        }

        if (pickUpDate != null) {
            sb.append("dropOffDate = '" + dropOffDate.size() + "' ");
        }
        return sb.toString();
    }
}