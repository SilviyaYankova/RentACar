package cource.project.model.user;

import cource.project.model.enums.DriverStatus;
import cource.project.model.enums.Role;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Driver extends User {

    private DriverStatus driverStatus;
    private double pricePerDay;
    private List<User> users;
    private List<User> sellers;
    private List<LocalDateTime> pickUpDates;
    private List<LocalDateTime> dropOffDates;

    public Driver() {
        this.users = new ArrayList<>();
        this.sellers = new ArrayList<>();
        this.pickUpDates = new ArrayList<>();
        this.dropOffDates = new ArrayList<>();
    }

    public Driver(Long id, String firstName, String lastName, String email, String phoneNumber, String username,
                  String password, String repeatPassword, LocalDateTime registeredOn, double price) {
        super(id, firstName, lastName, email, phoneNumber, username, password, repeatPassword, registeredOn);
        this.setRole(Role.DRIVER);
        this.driverStatus = DriverStatus.AVAILABLE;
        this.pricePerDay = price;
        this.users = new ArrayList<>();
        this.sellers = new ArrayList<>();
        this.pickUpDates = new ArrayList<>();
        this.dropOffDates = new ArrayList<>();
    }


    public List<LocalDateTime> getPickUpDates() {
        return pickUpDates;
    }

    public void setPickUpDates(List<LocalDateTime> pickUpDates) {
        this.pickUpDates = pickUpDates;
    }

    public List<LocalDateTime> getDropOffDates() {
        return dropOffDates;
    }

    public void setDropOffDates(List<LocalDateTime> dropOffDates) {
        this.dropOffDates = dropOffDates;
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
                .append(System.lineSeparator())
                .append("\t\t" + "registeredOn = '" + super.getRegisteredOn().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")) + "' ")
                .append("role = " + super.getRole() + "' ")
                .append("ordersCount = " + super.getOrdersIds().size() + "' ")
                .append("driverStatus = " + driverStatus + "' ")
                .append("price = " + pricePerDay + "' ")
                .append("users = " + users.size() + "' ")
                .append("sellers = " + sellers.size() + "' ");


        if (pickUpDates.size() != 0) {
            sb.append("pickUpDate = '" + pickUpDates.size() + "' ");
        }

        if (dropOffDates.size() != 0) {
            sb.append("dropOffDate = '" + dropOffDates.size() + "' ");
        }
        return sb.toString();
    }
}