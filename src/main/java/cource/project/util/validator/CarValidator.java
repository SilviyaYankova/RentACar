package cource.project.util.validator;

import cource.project.exeption.ConstraintViolation;
import cource.project.exeption.ConstraintViolationException;
import cource.project.model.Car;
import cource.project.model.enums.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CarValidator implements Validator<Car> {

    @Override
    public void validate(Car car) throws ConstraintViolationException {
        List<ConstraintViolation> violations = new ArrayList<>();

        int brand = car.getBrand().length();
        if (brand < 2 || brand > 10) {
            violations.add(new ConstraintViolation(car.getClass().getName(), "brand", car.getBrand(),
                    "Car brand should be between 2 and 10 characters long."));
        }
        int model = car.getModel().length();
        if (model < 2 || model > 15) {
            violations.add(new ConstraintViolation(car.getClass().getName(), "model", car.getModel(),
                    "Car model should be between 2 and 15 characters long."));
        }
        int year = car.getYear().trim().length();

        if (year != 4 && year > LocalDate.now().getYear()) {
            violations.add(new ConstraintViolation(car.getClass().getName(), "year", car.getYear(),
                    "Year should be 4 characters long and cannot be in the future."));
        }

        String pictureURL = car.getPictureURL();
        if (pictureURL == null) {
            violations.add(new ConstraintViolation(car.getClass().getName(), "pictureURL", car.getPictureURL(),
                    "Car must have picture."));
        }
        String color = car.getColor();
        if (color == null) {
            violations.add(new ConstraintViolation(car.getClass().getName(), "color", car.getColor(),
                    "Car must have color."));
        }
        CarType carType = car.getCarType();
        if (carType == null) {
            violations.add(new ConstraintViolation(car.getClass().getName(), "carType", car.getCarType(),
                    "Car must have Type."));
        }
        int doors = car.getDoors();
        if (doors < 2 || doors > 5) {
            violations.add(new ConstraintViolation(car.getClass().getName(), "doors", car.getDoors(),
                    "Car doors should be between 2 and 5."));
        }

        int seats = car.getSeats();
        if (seats < 2 || seats > 5) {
            violations.add(new ConstraintViolation(car.getClass().getName(), "seats", car.getSeats(),
                    "Car seats should be between 2 and 5."));
        }
        List<String> convenience = car.getConvenience();
        if (convenience == null) {
            violations.add(new ConstraintViolation(car.getClass().getName(), "convenience", car.getConvenience(),
                    "Car must have at least one convenience."));
        }
        List<String> entertainment = car.getConvenience();
        if (entertainment == null) {
            violations.add(new ConstraintViolation(car.getClass().getName(), "entertainment", car.getEntertainment(),
                    "Car entertainment must have at least one convenience."));
        }
        Drivetrain drivetrain = car.getDrivetrain();
        if (drivetrain == null) {
            violations.add(new ConstraintViolation(car.getClass().getName(), "drivetrain", car.getDrivetrain(),
                    "Car must have Drivetrain."));
        }
        Transmission transmission = car.getTransmission();
        if (transmission == null) {
            violations.add(new ConstraintViolation(car.getClass().getName(), "transmission", car.getTransmission(),
                    "Car must have Transmission."));
        }
        int horsePowers = car.getHorsePowers();
        if (horsePowers <= 0) {
            violations.add(new ConstraintViolation(car.getClass().getName(), "horsePowers", car.getHorsePowers(),
                    "Car horse powers must be a positive number."));
        }
        Fuel fuelType  = car.getFuelType();
        if (fuelType == null) {
            violations.add(new ConstraintViolation(car.getClass().getName(), "fuelType", car.getFuelType(),
                    "Car must have Fuel Type."));
        }
        int tankVolume = car.getTankVolume();
        if (tankVolume <= 0) {
            violations.add(new ConstraintViolation(car.getClass().getName(), "tankVolume", car.getTankVolume(),
                    "Car Tank Volume must be a positive number."));
        }
        double fuelConsumption = car.getTankVolume();
        if (fuelConsumption <= 0) {
            violations.add(new ConstraintViolation(car.getClass().getName(), "fuelConsumption", car.getTankVolume(),
                    "Car Fuel Consumption must be a positive number."));
        }
        double deposit = car.getTankVolume();
        if (deposit <= 0) {
            violations.add(new ConstraintViolation(car.getClass().getName(), "deposit", car.getDeposit(),
                    "Car Deposit must be a positive number."));
        }
        double pricePerDay = car.getPricePerDay();
        if (pricePerDay <= 0) {
            violations.add(new ConstraintViolation(car.getClass().getName(), "pricePerDay", car.getPricePerDay(),
                    "Car Price Per Day must be a positive number."));
        }
        CarStatus carStatus = car.getCarStatus();
        if (carStatus == null) {
            violations.add(new ConstraintViolation(car.getClass().getName(), "carStatus", car.getCarStatus(),
                    "Car must have Status."));
        }

        if (violations.size() > 0) {
            throw new ConstraintViolationException("Invalid user field", violations);
        }
    }

}
