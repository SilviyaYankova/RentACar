package cource.project.service.impl;

import cource.project.dao.CarRepository;
import cource.project.dao.OrderRepository;
import cource.project.dao.UserRepository;
import cource.project.exeption.ConstraintViolationException;
import cource.project.exeption.InvalidEntityDataException;
import cource.project.exeption.NoneExistingEntityException;
import cource.project.model.Car;
import cource.project.model.Order;
import cource.project.model.enums.CarStatus;
import cource.project.model.user.User;
import cource.project.service.CarService;
import cource.project.service.WorkerService;
import cource.project.util.validator.CarValidator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final WorkerService workerService;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final CarValidator carValidator;

    public CarServiceImpl(CarRepository carRepository, WorkerService workerService, UserRepository userRepository, OrderRepository orderRepository) {
        this.carRepository = carRepository;
        this.workerService = workerService;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.carValidator = new CarValidator();
    }

    @Override
    public void addCar(Car car) throws InvalidEntityDataException {
        try {
            carValidator.validate(car);
        } catch (ConstraintViolationException ex) {
            throw new InvalidEntityDataException(
                    String.format("Error creating car '%s %s'", car.getBrand(), car.getModel()),
                    ex
            );
        }
        carRepository.create(car);
    }

    @Override
    public Collection<Car> getAllCars() {
        return carRepository.findAll();
    }

    @Override
    public Car getCarById(Long id) throws NoneExistingEntityException {
        Car car = carRepository.findById(id);
        if (car == null) {
            throw new NoneExistingEntityException("Car with ID " + id + " does not exist.");
        }

        return carRepository.findById(id);
    }

    @Override
    public void editCar(Car car) throws NoneExistingEntityException {
        carRepository.update(car);
    }

    @Override
    public void deleteCar(Long id) throws NoneExistingEntityException {
        carRepository.deleteById(id);
    }

    @Override
    public List<Car> getAllCarsWithStatus(CarStatus carStatus) {
        return carRepository.findAll().stream()
                .filter(o -> o.getCarStatus().equals(carStatus))
                .collect(Collectors.toList());
    }

    @Override
    public List<Car> getAvailableCars(Order order) {
        Collection<Car> allCars = getAllCarsWithStatus(CarStatus.AVAILABLE);
        List<Car> availableCarsForDates = new ArrayList<>();
        for (Car car : allCars) {
            if (car.getPickUpDates() == null) {
                car.setPickUpDates(new ArrayList<>());
                car.setDropOffDates(new ArrayList<>());
            }
                if (!car.getPickUpDates().contains(order.getPickUpDate())) {
                    availableCarsForDates.add(car);
                }


        }
        return availableCarsForDates;
    }

    @Override
    public void returnCar(Order order) throws NoneExistingEntityException {
        Car car = order.getCar();
        LocalDateTime pickUpDate = order.getPickUpDate();
        LocalDateTime dropOffDate = order.getDropOffDate();

        if (order.getDriver() != null) {
            order.getDriver().getPickUpDates().remove(pickUpDate);
            order.getDriver().getDropOffDates().remove(dropOffDate);
            userRepository.update(order.getDriver());
        }
        car.setCarStatus(CarStatus.WAITING_FOR_CLEANING);
        car.setOrders(null);
        car.getPickUpDates().remove(pickUpDate);
        car.getDropOffDates().remove(dropOffDate);
        carRepository.update(car);
    }

    @Override
    public void returnCarToShop(Car car) throws NoneExistingEntityException {
        car.setCarStatus(CarStatus.AVAILABLE);
        carRepository.update(car);
    }
}
