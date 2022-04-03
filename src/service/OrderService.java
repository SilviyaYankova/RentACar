package service;

import exeption.InvalidEntityDataException;
import exeption.NoneAvailableEntityException;
import exeption.NoneExistingEntityException;
import model.Car;
import model.Order;

import java.util.Collection;
import java.util.List;

public interface OrderService {

    void addOrder(Order order, Car car) throws NoneExistingEntityException, NoneAvailableEntityException, InvalidEntityDataException;

    Collection<Order> getAllOrders();

    Order getOrderById(Long id) throws NoneExistingEntityException;

    void editOrder(Order car) throws NoneExistingEntityException;

    void deleteOrder(Long id) throws NoneExistingEntityException;

    List<Order> getAllPendingOrders();

    double calculateCarPrice(long days, double pricePerDay);

    void loadData();

}
