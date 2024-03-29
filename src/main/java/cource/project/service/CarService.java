package cource.project.service;

import cource.project.exeption.InvalidEntityDataException;
import cource.project.exeption.NoneExistingEntityException;
import cource.project.model.Car;
import cource.project.model.Order;
import cource.project.model.enums.CarStatus;

import java.util.Collection;
import java.util.List;

public interface CarService {

    void addCar(Car car) throws InvalidEntityDataException;

    Collection<Car> getAllCars();

    Car getCarById(Long id) throws NoneExistingEntityException;

    void editCar(Car car) throws NoneExistingEntityException;

    void deleteCar(Long id) throws NoneExistingEntityException;

    List<Car> getAllCarsWithStatus(CarStatus carStatus);

    List<Car> getAvailableCars(Order order);

    void returnCar(Car car) throws NoneExistingEntityException;

    void returnCarToShop(Car car) throws NoneExistingEntityException;

    void insertCarsOrders(Car car, Order order);

    void updateCarStatus(Car car);
}
