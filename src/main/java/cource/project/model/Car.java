package cource.project.model;

import cource.project.model.enums.*;
import cource.project.model.enums.Transmission;
import cource.project.model.user.BaseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Car extends BaseEntity{
    private String brand;
    private String model;
    private String year;
    private String pictureURL;
    private String color;
    private CarType carType;
    private int doors;
    private int seats;
    List<String> conveniences;
    List<String> entertainments;
    private Drivetrain drivetrain;
    private Transmission transmission;
    private int horsePowers;
    private FuelType fuelType;
    private int tankVolume;
    private double fuelConsumption;
    private double rating;
    private List<Comment> comments;
    private double deposit;
    private double pricePerDay;
    private CarStatus carStatus;
    private Worker worker;
    private List<Long> orders;
    private List<LocalDateTime> pickUpDates;
    private List<LocalDateTime> dropOffDates;

    public Car() {
    }

    public Car(Long id,String brand, String model, String year, String pictureURL, String color, CarType carType, int doors, int seats,
               List<String> convenience, List<String> entertainment, Drivetrain drivetrain, Transmission transmission,
               int horsePowers, FuelType type, int tankVolume, double fuelConsumption,
               double deposit, double pricePerDay, CarStatus status) {
        super(id);
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.pictureURL = pictureURL;
        this.color = color;
        this.carType = carType;
        this.doors = doors;
        this.seats = seats;
        this.conveniences = convenience;
        this.entertainments = entertainment;
        this.drivetrain = drivetrain;
        this.transmission = transmission;
        this.horsePowers = horsePowers;
        this.fuelType = type;
        this.tankVolume = tankVolume;
        this.fuelConsumption = fuelConsumption;
        this.rating = 0;
        this.deposit = deposit;
        this.pricePerDay = pricePerDay;
        this.carStatus = status;
        this.orders = new ArrayList<>();
        this.comments = new ArrayList<>();
        this.pickUpDates = new ArrayList<>();
        this.dropOffDates = new ArrayList<>();
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

    public List<String> getConveniences() {
        return conveniences;
    }

    public void setConveniences(List<String> conveniences) {
        this.conveniences = conveniences;
    }

    public List<String> getEntertainments() {
        return entertainments;
    }

    public void setEntertainments(List<String> entertainments) {
        this.entertainments = entertainments;
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

    public FuelType getFuelType() {
        return fuelType;
    }

    public void setFuelType(FuelType fuelType) {
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

    public CarStatus getCarStatus() {
        return carStatus;
    }

    public void setCarStatus(CarStatus carStatus) {
        this.carStatus = carStatus;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public List<Long> getOrders() {
        return orders;
    }

    public void setOrders(List<Long> orders) {
        this.orders = orders;
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
                .append(System.lineSeparator());

        if (conveniences.size() > 0 && entertainments.size() > 0) {
            sb.append("\t convenience = '" + conveniences + "' ")
                    .append(System.lineSeparator())
                    .append("\t entertainment = '" + entertainments + "' ")
                    .append(System.lineSeparator());
        }
        sb.append(String.format("\t rating = '%.2f' ", rating));
        if (comments != null) {
            sb.append("comments = '" + comments.size() + "' ");

        }
        sb.append(System.lineSeparator());
        sb.append("\t deposit = '" + deposit + "' ");
        sb.append(String.format("price per day = '%.2f' ", pricePerDay))
                .append(System.lineSeparator());

        if (orders != null) {
            sb.append("\t orders = '" + orders.size() + "' ")
                    .append(System.lineSeparator());
        }

        if (pickUpDates.size() != 0) {
            sb.append("\t pickUpDates = '" + pickUpDates.size() + "' ");
        }

        if (dropOffDates.size() != 0) {
            sb.append("dropOffDates = '" + dropOffDates.size() + "' ")
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
