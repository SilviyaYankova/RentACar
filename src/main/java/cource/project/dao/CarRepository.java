package cource.project.dao;

import cource.project.model.Car;
import cource.project.model.Order;

public interface CarRepository extends Repository<Long, Car> {
    void insertCarsOrders(Car car, Order order);
}
