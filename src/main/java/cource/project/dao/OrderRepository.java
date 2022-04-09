package cource.project.dao;

import cource.project.model.Car;
import cource.project.model.Order;
import cource.project.model.user.User;

import java.time.LocalDateTime;

public interface OrderRepository extends Repository<Long, Order> {

    void insertPickUpDate(LocalDateTime pickUpDate, Long carId, User driver);

    void insertDropOffDate(LocalDateTime dropOffDate, Long carId, User driver);
}
