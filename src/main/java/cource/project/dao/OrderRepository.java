package cource.project.dao;

import cource.project.model.Car;
import cource.project.model.Order;
import cource.project.model.user.User;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface OrderRepository extends Repository<Long, Order> {

    void insertPickUpDate(LocalDateTime pickUpDate, Long carId, User driver);

    void insertDropOffDate(LocalDateTime dropOffDate, Long carId, User driver);

    Collection<Order> findAllByUser(Long id);
}
