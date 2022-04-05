package model;

import model.enums.*;
import model.user.BaseEntity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Car extends BaseEntity {
    private String brand;
    private String model;
    private String year;
    private String pictureURL;
    private String color;
    private CarType carType;
    private int doors;
    private int seats;
    List<String> convenience;
    List<String> entertainment;
    private Drivetrain drivetrain;
    private Transmission transmission;
    private int horsePowers;
    private Fuel fuelType;
    private int tankVolume;
    private double fuelConsumption;
    private double rating;
    private List<Comment> comments;
    private double deposit;
    private double pricePerDay;
    private CarStatus carStatus;
    private Worker worker;
    private Order order;
    private List<LocalDateTime> pickUpDate;
    private List<LocalDateTime> dropOffDate;

    public Car() {
    }

    public Car(String brand, String model, String year, String pictureURL, String color, CarType carType, int doors, int seats,
               List<String> convenience, List<String> entertainment, Drivetrain drivetrain, Transmission transmission,
               int horsePowers, Fuel type, int tankVolume, double fuelConsumption,
               double deposit, double pricePerDay, CarStatus status) {
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.pictureURL = pictureURL;
        this.color = color;
        this.carType = carType;
        this.doors = doors;
        this.seats = seats;
        this.convenience = convenience;
        this.entertainment = entertainment;
        this.drivetrain = drivetrain;
        this.transmission = transmission;
        this.horsePowers = horsePowers;
        this.fuelType = type;
        this.tankVolume = tankVolume;
        this.fuelConsumption = fuelConsumption;
        this.rating = rating;
        this.deposit = deposit;
        this.pricePerDay = pricePerDay;
        this.carStatus = status;
        this.comments = new ArrayList<>();
        this.pickUpDate = new ArrayList<>();
        this.dropOffDate = new ArrayList<>();
    }

    public List<LocalDateTime> getPickUpDates() {
        return pickUpDate;
    }

    public void setPickUpDate(List<LocalDateTime> pickUpDate) {
        this.pickUpDate = pickUpDate;
    }

    public List<LocalDateTime> getDropOffDates() {
        return dropOffDate;
    }

    public void setDropOffDate(List<LocalDateTime> dropOffDate) {
        this.dropOffDate = dropOffDate;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getPictureURL() {
        return pictureURL;
    }

    public void setPictureURL(String pictureURL) {
        this.pictureURL = pictureURL;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public CarType getCarType() {
        return carType;
    }

    public void setCarType(CarType carType) {
        this.carType = carType;
    }

    public int getDoors() {
        return doors;
    }

    public void setDoors(int doors) {
        this.doors = doors;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public List<String> getConvenience() {
        return convenience;
    }

    public void setConvenience(List<String> convenience) {
        this.convenience = convenience;
    }

    public List<String> getEntertainment() {
        return entertainment;
    }

    public void setEntertainment(List<String> entertainment) {
        this.entertainment = entertainment;
    }

    public Drivetrain getDrivetrain() {
        return drivetrain;
    }

    public void setDrivetrain(Drivetrain drivetrain) {
        this.drivetrain = drivetrain;
    }

    public Transmission getTransmission() {
        return transmission;
    }

    public void setTransmission(Transmission transmission) {
        this.transmission = transmission;
    }

    public int getHorsePowers() {
        return horsePowers;
    }

    public void setHorsePowers(int horsePowers) {
        this.horsePowers = horsePowers;
    }

    public Fuel getFuelType() {
        return fuelType;
    }

    public void setFuelType(Fuel fuelType) {
        this.fuelType = fuelType;
    }

    public int getTankVolume() {
        return tankVolume;
    }

    public void setTankVolume(int tankVolume) {
        this.tankVolume = tankVolume;
    }

    public double getFuelConsumption() {
        return fuelConsumption;
    }

    public void setFuelConsumption(double fuelConsumption) {
        this.fuelConsumption = fuelConsumption;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public CarStatus getCarStatus() {
        return carStatus;
    }

    public void setCarStatus(CarStatus carStatus) {
        this.carStatus = carStatus;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public double getDeposit() {
        return deposit;
    }

    public void setDeposit(double deposit) {
        this.deposit = deposit;
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
                .append(Car.class.getSimpleName() + ": ")
                .append("id = '" + super.getId() + "' ")
                .append("brand = '" + brand + "' ")
                .append("model = '" + model + "' ")
                .append("year = '" + year + "' ")
                .append(System.lineSeparator())
                .append("\t color = '" + color + "' ")
                .append("pictureURL = '" + pictureURL + "' ")
                .append(System.lineSeparator())
                .append("\t carType = '" + carType + "' ")
                .append("doors = '" + doors + "' ")
                .append("seats = '" + seats + "' ")
                .append(System.lineSeparator())
                .append("\t drivetrain = '" + drivetrain + "' ")
                .append("transmission = '" + transmission + "' ")
                .append("horsePowers = '" + horsePowers + "' ")
                .append(System.lineSeparator())
                .append("\t type = '" + fuelType + "' ")
                .append("tankVolume = '" + tankVolume + "' ")
                .append("fuelConsumption = '" + fuelConsumption + "' ")
                .append(System.lineSeparator())
                .append("\t convenience = '" + convenience + "' ")
                .append(System.lineSeparator())
                .append("\t entertainment = '" + entertainment + "' ")
                .append(System.lineSeparator())
                .append(String.format("\t rating = '%.2f' ", rating))
                .append("comments = '" + comments.size() + "' ")
                .append(System.lineSeparator());
        sb.append("\t deposit = '" + deposit + "' ");
        sb.append(String.format("price per day = '%.2f' ", pricePerDay))
                .append(System.lineSeparator());

        if (order != null) {
            sb.append("\t currentOrderId = '" + order.getId() + "' ")
                    .append(System.lineSeparator());
        }

        if (pickUpDate.size() != 0) {
            sb.append("\t pickUpDate = '" + pickUpDate.size() + "' ");
        }

        if (pickUpDate.size() != 0) {
            sb.append("dropOffDate = '" + dropOffDate.size() + "' ")
                    .append(System.lineSeparator());
        }
        sb.append("\t CarStatus = '" + carStatus + "' ")
                .append(System.lineSeparator());
        if (carStatus.equals(CarStatus.CLEANING) && worker != null) {
            sb.append("\t worker = '" + worker.getCode() + "' ");
        }
        return sb.toString();
    }
}
