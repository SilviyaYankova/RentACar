package cource.project.view;


import cource.project.exeption.InvalidEntityDataException;
import cource.project.model.Car;
import cource.project.model.enums.*;
import cource.project.model.user.User;
import cource.project.service.CarService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.*;


public class AddCarDialog {
    Scanner scanner = new Scanner(System.in);
    private final CarService carService;

    public AddCarDialog(CarService carService) {
        this.carService = carService;
    }

    public void input(User LOGGED_IN_USER) throws InvalidEntityDataException {


        Car car = new Car();
        while (car.getBrand() == null) {
            System.out.println("Brand:");
            String brand = scanner.nextLine();
            int length = brand.length();
            if (length < 2 || length > 10) {
                System.out.println("Car brand should be between 2 and 10 characters long.");
            } else {
                car.setBrand(brand);
            }
        }

        while (car.getModel() == null) {
            System.out.println("Model:");
            String model = scanner.nextLine();

            int length = model.length();
            if (length < 2 || length > 15) {
                System.out.println("Car model should be between 2 and 15 characters long.");
            } else {
                car.setModel(model);
            }
        }

        while (car.getYear() == null) {
            System.out.println("Year:");
            String input = scanner.nextLine();

            int choice = 0;
            choice = checkValidInput(choice, input);

            int length = input.length();
            int year = input.trim().length();
            if (length != 4 || choice > LocalDate.now().getYear()) {
                System.out.println("Year should be 4 characters long and cannot be in the future.");
            } else {
                car.setYear(input);
            }
        }

        while (car.getPictureURL() == null) {
            System.out.println("Picture URL:");
            String p = scanner.nextLine();
            car.setPictureURL(p);
        }

        while (car.getColor() == null) {
            System.out.println("Color:");
            String c = scanner.nextLine();
            if (c.equals("")){
                System.out.println("Car must have a color.");
            } else {
            car.setColor(c);
            }
        }

        while (car.getCarType() == null) {
            System.out.println("Car type:");

            List<CarType> values = Arrays.stream(CarType.values()).collect(Collectors.toList());
            int count = 0;
            for (CarType value : values) {
                count++;
                System.out.println(count + ".\t" + value);
            }

            System.out.println("Choose Car type from the list above.");
            String input = scanner.nextLine();
            int choice = 0;
            choice = checkValidInput(values, choice, input);
            CarType carType1 = values.get(choice - 1);
            car.setCarType(carType1);
        }

        while (car.getDoors() == 0) {
            System.out.println("Doors: (from 1 to 5)");
            String input = scanner.nextLine();
            int choice = 0;
            choice = checkValidInput(choice, input);
            if (choice < 1 || choice > 5) {
                System.out.println("Car doors should be between 2 and 5.");
            } else {
                car.setDoors(choice);
            }
        }

        while (car.getSeats() == 0) {
            System.out.println("Seats: (from 1 to 5)");
            String input = scanner.nextLine();
            int choice = 0;
            choice = checkValidInput(choice, input);
            if (choice < 1 || choice > 5) {
                System.out.println("Car seats should be between 2 and 5.");
            } else {
                car.setSeats(choice);
            }
        }

        while (car.getConveniences() == null) {
            System.out.println("Conveniences:");
            String input = scanner.nextLine();
            car.setConveniences(List.of(input));
        }

        while (car.getEntertainments() == null) {
            System.out.println("Entertainments:");
            String input = scanner.nextLine();
            car.setEntertainments(List.of(input));
        }

        while (car.getDrivetrain() == null) {
            System.out.println("Drivetrain:");
            List<Drivetrain> values = Arrays.stream(Drivetrain.values()).collect(Collectors.toList());
            int count = 0;
            for (Drivetrain value : values) {
                count++;
                System.out.println(count + ".\t" + value);
            }

            System.out.println("Choose Drivetrain from the list above.");
            String input = scanner.nextLine();
            int choice = 0;
            choice = checkValidInput(values, choice, input);
            Drivetrain drivetrain1 = values.get(choice - 1);
            car.setDrivetrain(drivetrain1);
        }

        while (car.getTransmission() == null) {
            System.out.println("Transmission:");

            List<Transmission> values = Arrays.stream(Transmission.values()).collect(Collectors.toList());;
            int count = 0;
            for (Transmission value : values) {
                count++;
                System.out.println(count + ".\t" + value);
            }

            System.out.println("Choose Transmission from the list above.");
            String input = scanner.nextLine();
            int choice = 0;
            choice = checkValidInput(values, choice, input);
            Transmission transmission = values.get(choice - 1);
            car.setTransmission(transmission);
        }

        while (car.getHorsePowers() == 0) {
            System.out.println("Horse powers:");
            String input = scanner.nextLine();
            int choice = 0;
            choice = checkValidInput(choice, input);
            if (choice <= 0) {
                System.out.println("Car horse powers must be a positive number.");
            } else {
                car.setHorsePowers(choice);
            }
        }

        while (car.getFuelType() == null) {
            System.out.println("Fuel type:");

            List<Fuel> values = Arrays.stream(Fuel.values()).collect(Collectors.toList());
            int count = 0;
            for (Fuel value : values) {
                count++;
                System.out.println(count + ".\t" + value);
            }

            System.out.println("Choose Fuel type from the list above.");
            String input = scanner.nextLine();
            int choice = 0;
            choice = checkValidInput(values, choice, input);
            Fuel fuel = values.get(choice - 1);
            car.setFuelType(fuel);
        }

        while (car.getTankVolume() == 0) {
            System.out.println("Tank volume:");
            String input = scanner.nextLine();
            int choice = 0;
            choice = checkValidInput(choice, input);
            if (choice <= 0) {
                System.out.println("Car Tank Volume must be a positive number.");
            } else {
                car.setTankVolume(choice);
            }
        }

        while (car.getFuelConsumption() == 0) {
            System.out.println("Fuel consumption:");
            String input = scanner.nextLine();
            int choice = 0;
            choice = checkValidInput(choice, input);
            if (choice <= 0) {
                System.out.println("Car Fuel Consumption must be a positive number.");
            } else {
                car.setFuelConsumption(choice);
            }
        }

        while (car.getDeposit() == 0) {
            System.out.println("Deposit:");
            String input = scanner.nextLine();
            int choice = 0;
            choice = checkValidInput(choice, input);
            if (choice <= 0) {
                System.out.println("Car Deposit must be a positive number.");
            } else {
                car.setDeposit(choice);
            }
        }

        while (car.getPricePerDay() == 0) {
            System.out.println("Price per day:");
            String input = scanner.nextLine();
            int choice = 0;
            choice = checkValidInput(choice, input);
            if (choice <= 0) {
                System.out.println("Price Per Day must be a positive number.");
            } else {
                car.setPricePerDay(choice);
            }
        }

        while (car.getCarStatus() == null) {
            System.out.println("Car status:");

            List<CarStatus> values = Arrays.stream(CarStatus.values()).collect(Collectors.toList());
            int count = 0;
            for (CarStatus value : values) {
                count++;
                System.out.println(count + ".\t" + value);
            }

            System.out.println("Choose Car status from the list above.");
            String input = scanner.nextLine();
            int choice = 0;
            choice = checkValidInput(values, choice, input);
            CarStatus carStatus = values.get(choice - 1);
            car.setCarStatus(carStatus);
        }

        car.setPickUpDates(new ArrayList<>());
        car.setDropOffDates(new ArrayList<>());
        car.setConveniences(new ArrayList<>());
        carService.addCar(car);
    }

    public <T> int checkValidInput(List<T> t, int choice, String input) {
        while (choice == 0) {
            try {
                choice = Integer.parseInt(input);
                if (choice < 1 || choice > t.size()) {
                    System.out.println("Error: Please choose a valid number.");
                    choice = 0;
                    input = scanner.nextLine();
                }
            } catch (NumberFormatException ex) {
                System.out.println("Error: Numbers only.");
                input = scanner.nextLine();
            }

        }
        return choice;
    }


    public int checkValidInput(int choice, String input) {

        while (choice == 0) {
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException ex) {
                System.out.println("Error: Numbers only.");
                input = scanner.nextLine();
            }

        }
        return choice;
    }
}
