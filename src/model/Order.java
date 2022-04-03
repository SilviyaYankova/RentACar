package model;

import model.enums.OrderStatus;
import model.enums.Location;
import model.user.BaseEntity;
import model.user.Driver;
import model.user.User;
import org.w3c.dom.ls.LSOutput;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Order extends BaseEntity {

    private User user;
    private Driver driver;
    private boolean hireDriver;
    private User seller;
    private Car car;
    private OrderStatus orderStatus;
    private LocalDateTime createdOn;
    private LocalDateTime modifiedOn;
    private Location pickUpLocation;
    private Location dropOfLocation;
    private LocalDateTime pickUpDate;
    private LocalDateTime dropOffDate;
    private Long days;
    private double carPricePerDays;
    private double deposit;
    private double finalPrice;

    public Order() {
        this.orderStatus = OrderStatus.PENDING;
    }

    public Order(User user, boolean hireDriver, Car car, Location pickUpLocation, Location dropOfLocation, LocalDateTime pickUpDate, LocalDateTime dropOffDate) {
        this.user = user;
        this.hireDriver = hireDriver;
        this.car = car;
        this.pickUpLocation = pickUpLocation;
        this.dropOfLocation = dropOfLocation;
        this.pickUpDate = pickUpDate;
        this.dropOffDate = dropOffDate;
    }

    public Order(User user, Driver driver, boolean hireDriver, User seller, Car car, OrderStatus orderStatus, LocalDateTime createdOn,
                 Location pickUpLocation, Location dropOfLocation, LocalDateTime pickUpDate, LocalDateTime dropOffDate, Long days,
                 double pricePerDays, double deposit, double finalPrice) {
        this.user = user;
        this.driver = driver;
        this.hireDriver = hireDriver;
        this.seller = seller;
        this.car = car;
        this.orderStatus = orderStatus;
        this.createdOn = createdOn;
        this.pickUpLocation = pickUpLocation;
        this.dropOfLocation = dropOfLocation;
        this.pickUpDate = pickUpDate;
        this.dropOffDate = dropOffDate;
        this.days = days;
        this.carPricePerDays = pricePerDays;
        this.deposit = deposit;
        this.finalPrice = finalPrice;
    }

    public boolean isHireDriver() {
        return hireDriver;
    }

    public LocalDateTime getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(LocalDateTime modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public Location getDropOfLocation() {
        return dropOfLocation;
    }

    public Location getDropOffLocation() {
        return dropOfLocation;
    }

    public void setDropOfLocation(Location dropOfLocation) {
        this.dropOfLocation = dropOfLocation;
    }

    public boolean driverIsHired() {
        return hireDriver;
    }

    public void setHireDriver(boolean hireDriver) {
        this.hireDriver = hireDriver;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public Location getPickUpLocation() {
        return pickUpLocation;
    }

    public void setPickUpLocation(Location pickUpLocation) {
        this.pickUpLocation = pickUpLocation;
    }

    public LocalDateTime getPickUpDate() {
        return pickUpDate;
    }

    public void setPickUpDate(LocalDateTime pickUpDate) {
        this.pickUpDate = pickUpDate;
    }

    public LocalDateTime getDropOffDate() {
        return dropOffDate;
    }

    public void setDropOffDate(LocalDateTime dropOffDate) {
        this.dropOffDate = dropOffDate;
    }

    public Long getDays() {
        return days;
    }

    public void setDays(Long days) {
        this.days = days;
    }

    public double getCarPricePerDays() {
        return carPricePerDays;
    }

    public void setCarPricePerDays(double carPricePerDays) {
        this.carPricePerDays = carPricePerDays;
    }

    public double getDeposit() {
        return deposit;
    }

    public void setDeposit(double deposit) {
        this.deposit = deposit;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder()
                .append(Order.class.getSimpleName() + ": ")
                .append(" id = '" + super.getId() + "' ");
        if (user != null) {
            sb.append("userId = '" + user.getId() + "' ");
        }
        if (driver != null) {
            sb.append("driver = '" + driver.getUsername() + "' ");
        } else {
            sb.append("driver = 'NO' ");
        }
        if (seller != null) {
            sb.append("seller = '" + seller.getUsername() + "' ");
        }
        sb
                .append(System.lineSeparator())
                .append("\t\torderStatus = '" + orderStatus + "' ")
                .append(System.lineSeparator());

        sb.append("\t\tcreatedOn = '" + createdOn.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")) + "' ");
        if (modifiedOn != null) {
            sb.append("modifiedOn = '" + modifiedOn.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")) + "'");
        }
        sb
                .append(System.lineSeparator())
                .append("\t\tpick Up Location = '" + pickUpLocation.name() + "' ")
                .append("drop Off Location = '" + dropOfLocation.name() + "' ")
                .append(System.lineSeparator())
                .append("\t\tpickUpDate = '" + pickUpDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")) + "' ")
                .append("dropOffDate = '" + dropOffDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")) + "' ")
                .append(System.lineSeparator())
                .append("\t\tcarId = '" + car.getId() + "' ")
                .append(System.lineSeparator())
                .append("\t\tdays = '" + days + "' ")
                .append(String.format("pricePerDays = '%.2f' ", carPricePerDays))
                .append("deposit = '" + deposit + "' ");

        if (driver != null) {
            sb.append("driverPrice = '" + driver.getPricePerDay() + "' ");
        }
        sb.append(String.format("finalPrice = '%.2f' ", finalPrice));

        return sb.toString();
    }
}
