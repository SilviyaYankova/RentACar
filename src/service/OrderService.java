package service;

import exeption.InvalidEntityDataException;
import exeption.NoneAvailableEntityException;
import exeption.NoneExistingEntityException;
import model.Car;
import model.Order;
import model.enums.OrderStatus;
import model.user.User;

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

    void loadData();

    void checkFinishedOrders() throws NoneExistingEntityException;
}
