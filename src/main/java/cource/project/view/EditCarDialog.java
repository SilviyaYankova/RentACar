package cource.project.view;

import cource.project.exeption.NoneExistingEntityException;
import cource.project.model.Car;
import cource.project.model.enums.*;
import cource.project.model.user.User;
import cource.project.service.CarService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class EditCarDialog {
    Scanner scanner = new Scanner(System.in);
    private final CarService carService;

    public EditCarDialog(CarService carService) {
        this.carService = carService;
    }


    public void input(User LOGGED_IN_USER) throws NoneExistingEntityException {


        Collection<Car> all = carService.getAllCars();
        List<Car> allCars = new ArrayList<>(all);
        boolean continueEditing = true;
        while (continueEditing) {
            if (allCars.size() > 0) {
                System.out.println("Cars:");

                int count = 0;
                for (Car car : allCars) {
                    count++;
                    System.out.println(count + ". \t" + car);
                }

                System.out.println("Choose a car from the list above to edit .");
                String input = scanner.nextLine();
                int choice = 0;
                choice = checkValidInput(allCars, choice, input);
                Car car = allCars.get(choice - 1);

                boolean canBeEdited = true;
                for (LocalDateTime dropOffDate : car.getDropOffDates()) {
                    if (dropOffDate.isAfter(LocalDateTime.now())) {
                        canBeEdited = false;
                        break;
                    }
                }

                if (!canBeEdited) {
                    System.out.println("Sorry car can not be edited because it has orders.");
                } else {
                    System.out.println("Car you are going to edt:");
                    System.out.println(car);
                    System.out.println("Choose fields to edit: ");
                    System.out.println("1. Brand:");
                    System.out.println("2. Model:");
                    System.out.println("3. Year:");
                    System.out.println("4. Picture URL:");
                    System.out.println("5. Color:");
                    System.out.println("6. Car type:");
                    System.out.println("7. Doors: (from 1 to 5)");
                    System.out.println("8. Seats: (from 1 to 5)");
                    System.out.println("9. Conveniences:");
                    System.out.println("10. Entertainments:");
                    System.out.println("11. Drivetrain:");
                    System.out.println("12 Transmission:");
                    System.out.println("13. Horse powers:");
                    System.out.println("14. Fuel type:");
                    System.out.println("15. Tank volume:");
                    System.out.println("16. Fuel consumption:");
                    System.out.println("17. Deposit:");
                    System.out.println("18. Price per day:");
                    System.out.println("19. Car status::");

                    input = scanner.nextLine();
                    choice = 0;
                    choice = checkValidInput(choice, input);
                    boolean incorrectInput = true;

                    while (choice == 1 || choice == 2 || choice == 3 || choice == 4 || choice == 5
                            || choice == 6 || choice == 7 || choice == 8 || choice == 9
                            || choice == 10 || choice == 11 || choice == 12 || choice == 13 || choice == 14
                            || choice == 15 || choice == 16 || choice == 17 || choice == 18 || choice == 19) {

                        if (choice == 1) {
                            car.setBrand(null);
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
                            choice = confirmEditing(choice, car);
                        }

                        if (choice == 2) {
                            car.setModel(null);
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

                            choice = confirmEditing(choice, car);
                        }


                        if (choice == 3) {
                            car.setYear(null);
                            while (car.getYear() == null) {
                                System.out.println("Year:");
                                input = scanner.nextLine();

                                choice = 0;
                                choice = checkValidInput(choice, input);

                                int length = input.length();
                                int year = input.trim().length();
                                if (length != 4 || choice > LocalDate.now().getYear()) {
                                    System.out.println("Year should be 4 characters long and cannot be in the future.");
                                } else {
                                    car.setYear(input);
                                }
                            }
                            choice = confirmEditing(choice, car);
                        }

                        if (choice == 4) {
                            car.setPictureURL(null);
                            while (car.getPictureURL() == null) {
                                System.out.println("Picture URL:");
                                String p = scanner.nextLine();
                                car.setPictureURL(p);
                            }
                            choice = confirmEditing(choice, car);
                        }

                        if (choice == 5) {
                            car.setColor(null);
                            while (car.getColor() == null) {
                                System.out.println("Color:");
                                String c = scanner.nextLine();
                                if (c.equals("")) {
                                    System.out.println("Car must have a color.");
                                } else {
                                    car.setColor(c);
                                }
                            }
                            choice = confirmEditing(choice, car);
                        }

                        if (choice == 6) {
                            car.setCarType(null);
                            while (car.getCarType() == null) {
                                System.out.println("Car type:");

                                List<CarType> values = Arrays.stream(CarType.values()).collect(Collectors.toList());
                                count = 0;
                                for (CarType value : values) {
                                    count++;
                                    System.out.println(count + ".\t" + value);
                                }

                                System.out.println("Choose Car type from the list above.");
                                input = scanner.nextLine();
                                choice = 0;
                                choice = checkValidInput(values, choice, input);
                                CarType carType1 = values.get(choice - 1);
                                car.setCarType(carType1);
                            }
                            choice = confirmEditing(choice, car);
                        }

                        if (choice == 7) {
                            car.setDoors(0);
                            while (car.getDoors() == 0) {
                                System.out.println("Doors: (from 1 to 5)");
                                input = scanner.nextLine();
                                choice = 0;
                                choice = checkValidInput(choice, input);
                                if (choice < 1 || choice > 5) {
                                    System.out.println("Car doors should be between 2 and 5.");
                                } else {
                                    car.setDoors(choice);
                                }
                            }
                            choice = confirmEditing(choice, car);
                        }

                        if (choice == 8) {
                            car.setSeats(0);
                            while (car.getSeats() == 0) {
                                System.out.println("Seats: (from 1 to 5)");
                                input = scanner.nextLine();
                                choice = 0;
                                choice = checkValidInput(choice, input);
                                if (choice < 1 || choice > 5) {
                                    System.out.println("Car seats should be between 2 and 5.");
                                } else {
                                    car.setSeats(choice);
                                }
                            }
                            choice = confirmEditing(choice, car);
                        }

                        if (choice == 9) {
                            car.setConveniences(null);
                            while (car.getConveniences() == null) {
                                System.out.println("Conveniences:");
                                input = scanner.nextLine();
                                car.setConveniences(List.of(input));
                            }
                            choice = confirmEditing(choice, car);
                        }

                        if (choice == 10) {
                            car.setEntertainments(null);
                            while (car.getEntertainments() == null) {
                                System.out.println("Entertainments:");
                                input = scanner.nextLine();
                                car.setEntertainments(List.of(input));
                            }
                            choice = confirmEditing(choice, car);
                        }

                        if (choice == 11) {
                            car.setDrivetrain(null);
                            while (car.getDrivetrain() == null) {
                                System.out.println("Drivetrain:");

                                List<Drivetrain> values = Arrays.stream(Drivetrain.values()).collect(Collectors.toList());
                                count = 0;
                                for (Drivetrain value : values) {
                                    count++;
                                    System.out.println(count + ".\t" + value);
                                }

                                System.out.println("Choose Drivetrain from the list above.");
                                input = scanner.nextLine();
                                choice = 0;
                                choice = checkValidInput(values, choice, input);
                                Drivetrain drivetrain1 = values.get(choice - 1);
                                car.setDrivetrain(drivetrain1);
                            }
                            choice = confirmEditing(choice, car);
                        }

                        if (choice == 12) {
                            car.setTransmission(null);
                            while (car.getTransmission() == null) {
                                System.out.println("Transmission:");

                                List<Transmission> values = Arrays.stream(Transmission.values()).collect(Collectors.toList());
                                count = 0;
                                for (Transmission value : values) {
                                    count++;
                                    System.out.println(count + ".\t" + value);
                                }

                                System.out.println("Choose Transmission from the list above.");
                                input = scanner.nextLine();
                                choice = 0;
                                choice = checkValidInput(values, choice, input);
                                Transmission transmission = values.get(choice - 1);
                                car.setTransmission(transmission);
                            }
                            choice = confirmEditing(choice, car);
                        }

                        if (choice == 13) {
                            car.setHorsePowers(0);
                            while (car.getHorsePowers() == 0) {
                                System.out.println("Horse powers:");
                                input = scanner.nextLine();
                                choice = 0;
                                choice = checkValidInput(choice, input);
                                if (choice <= 0) {
                                    System.out.println("Car horse powers must be a positive number.");
                                } else {
                                    car.setHorsePowers(choice);
                                }
                            }
                            choice = confirmEditing(choice, car);
                        }

                        if (choice == 14) {
                            car.setFuelType(null);
                            while (car.getFuelType() == null) {
                                System.out.println("Fuel type:");

                                List<Fuel> values = Arrays.stream(Fuel.values()).collect(Collectors.toList());
                                count = 0;
                                for (Fuel value : values) {
                                    count++;
                                    System.out.println(count + ".\t" + value);
                                }

                                System.out.println("Choose Fuel type from the list above.");
                                input = scanner.nextLine();
                                choice = 0;
                                choice = checkValidInput(values, choice, input);
                                Fuel fuel = values.get(choice - 1);
                                car.setFuelType(fuel);
                            }
                            choice = confirmEditing(choice, car);
                        }

                        if (choice == 15) {
                            car.setTankVolume(0);
                            while (car.getTankVolume() == 0) {
                                System.out.println("Tank volume:");
                                input = scanner.nextLine();
                                choice = 0;
                                choice = checkValidInput(choice, input);
                                if (choice <= 0) {
                                    System.out.println("Car Tank Volume must be a positive number.");
                                } else {
                                    car.setTankVolume(choice);
                                }
                            }
                            choice = confirmEditing(choice, car);
                        }

                        if (choice == 16) {
                            car.setFuelConsumption(0);
                            while (car.getFuelConsumption() == 0) {
                                System.out.println("Fuel consumption:");
                                input = scanner.nextLine();
                                choice = 0;
                                choice = checkValidInput(choice, input);
                                if (choice <= 0) {
                                    System.out.println("Car Fuel Consumption must be a positive number.");
                                } else {
                                    car.setFuelConsumption(choice);
                                }
                            }
                            choice = confirmEditing(choice, car);
                        }

                        if (choice == 17) {
                            car.setDeposit(0);
                            while (car.getDeposit() == 0) {
                                System.out.println("Deposit:");
                                input = scanner.nextLine();
                                choice = 0;
                                choice = checkValidInput(choice, input);
                                if (choice <= 0) {
                                    System.out.println("Car Deposit must be a positive number.");
                                } else {
                                    car.setDeposit(choice);
                                }
                            }
                            choice = confirmEditing(choice, car);
                        }

                        if (choice == 18) {
                            car.setPricePerDay(0);
                            while (car.getPricePerDay() == 0) {
                                System.out.println("Price per day:");
                                input = scanner.nextLine();
                                choice = 0;
                                choice = checkValidInput(choice, input);
                                if (choice <= 0) {
                                    System.out.println("Price Per Day must be a positive number.");
                                } else {
                                    car.setPricePerDay(choice);
                                }
                            }
                            choice = confirmEditing(choice, car);
                        }

                        if (choice == 19) {
                            car.setCarStatus(null);
                            while (car.getCarStatus() == null) {
                                System.out.println("Car status:");

                                List<CarStatus> values = Arrays.stream(CarStatus.values()).collect(Collectors.toList());
                                count = 0;
                                for (CarStatus value : values) {
                                    count++;
                                    System.out.println(count + ".\t" + value);
                                }

                                System.out.println("Choose Car status from the list above.");
                                input = scanner.nextLine();
                                choice = 0;
                                choice = checkValidInput(values, choice, input);
                                CarStatus carStatus = values.get(choice - 1);
                                car.setCarStatus(carStatus);
                            }
                            choice = confirmEditing(choice, car);
                        }
                    }

                    if (choice != 0) {
                        choice = confirmEditing(choice, car);
                    }
                }
                continueEditing = confirmContinue(true);
            } else {
                System.out.println("There is no cars in the system.");
                continueEditing = false;
            }


        }

    }


    private boolean confirmContinue(boolean continueEditing) {
        System.out.println();
        System.out.println("For continue editing cars press 'C'?");
        System.out.println("For cancel press 'E'.");

        String input = scanner.nextLine();
        boolean incorrectInput = true;
        while (incorrectInput) {
            if (input.equals("C")) {
                System.out.println("You choose to continue editing cars.");
                break;
            } else if (input.equals("E")) {
                System.out.println("You canceled continue editing cars.");
                continueEditing = false;
                break;
            } else {
                System.out.println("Error: Please make a choice between 'YES' or 'E'");
                input = scanner.nextLine();
            }
        }
        return continueEditing;
    }

    private int confirmEditing(int choice, Car car) throws NoneExistingEntityException {
        System.out.println();
        System.out.println("To save edited car press 'YES'.");
        System.out.println("To continue editing car press 'C'.");
        System.out.println("For exit press 'E'.");

        String input = scanner.nextLine();
        boolean incorrectInput = true;
        while (incorrectInput) {
            if (input.equals("YES")) {
                incorrectInput = false;
                System.out.println("Successfully edited car:");
                carService.editCar(car);
                System.out.println(car);
            } else if (input.equals("C")) {
                System.out.println("You choose to continue editing car.");
                System.out.println("Choose fields to edit: ");
                System.out.println("1. Brand:");
                System.out.println("2. Model:");
                System.out.println("3. Year:");
                System.out.println("4. Picture URL:");
                System.out.println("5. Color:");
                System.out.println("6. Car type:");
                System.out.println("7. Doors: (from 1 to 5)");
                System.out.println("8. Seats: (from 1 to 5)");
                System.out.println("9. Conveniences:");
                System.out.println("10. Entertainments:");
                System.out.println("11. Drivetrain:");
                System.out.println("12 Transmission:");
                System.out.println("13. Horse powers:");
                System.out.println("14. Fuel type:");
                System.out.println("15. Tank volume:");
                System.out.println("16. Fuel consumption:");
                System.out.println("17. Deposit:");
                System.out.println("18. Price per day:");
                System.out.println("19. Car status:");
                input = scanner.nextLine();
                choice = 0;
                choice = checkValidInput(choice, input);
                break;
            } else if (input.equals("E")) {
                System.out.println("You canceled editing car.");
                choice = 0;
                break;
            } else {
                System.out.println("Error: Please make a choice between 'YES' or 'C' or 'E'");
                input = scanner.nextLine();
            }
        }
        if (input.equals("YES")) {
            choice = 0;
        }
        return choice;
    }

    public <T> int checkValidInput(List<T> T, int choice, String input) {
        while (choice == 0) {
            try {
                choice = Integer.parseInt(input);
                if (choice < 1 || choice > T.size()) {
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
