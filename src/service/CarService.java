package service;

import exeption.InvalidEntityDataException;
import exeption.NoneExistingEntityException;
import model.Car;
import model.Order;
import model.enums.CarStatus;

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

    void returnCar(Order order) throws NoneExistingEntityException;

    void loadData();

}
