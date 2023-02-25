package cource.project.service;

import cource.project.exeption.InvalidEntityDataException;
import cource.project.exeption.NoneAvailableEntityException;
import cource.project.exeption.NoneExistingEntityException;
import cource.project.model.Car;
import cource.project.model.Order;
import cource.project.model.enums.OrderStatus;
import cource.project.model.user.User;

import java.util.Collection;
import java.util.List;

public interface OrderService {

    void addOrder(Order order, Car car) throws NoneExistingEntityException, NoneAvailableEntityException, InvalidEntityDataException;

    Collection<Order> getAllOrders();

    Order getOrderById(Long id) throws NoneExistingEntityException;

    void editOrder(Order order) throws NoneExistingEntityException;

    void deleteOrder(Long id) throws NoneExistingEntityException;

    List<Order> getAllOrdersWithStatus(OrderStatus orderStatus);

    double calculateCarPrice(long days, double pricePerDay);

    void approveOrder(List<Order> pendingOrders, User user) throws NoneExistingEntityException;

    void approveOrder(Order pendingOrders, User user) throws NoneExistingEntityException;

    void finishOrders() throws NoneExistingEntityException;

    Collection<Order> getUserOrders(Long id);
}
