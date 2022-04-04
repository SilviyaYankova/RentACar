package service.impl;

import dao.OrderRepository;
import dao.UserRepository;
import exeption.ConstraintViolationException;
import exeption.InvalidEntityDataException;
import exeption.NoneExistingEntityException;
import model.Car;
import model.Order;
import model.enums.*;
import model.user.Administrator;
import model.user.Driver;
import model.user.Seller;
import model.user.User;
import service.CarService;
import service.OrderService;
import util.OrderValidator;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;

public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CarService carService;
    private final OrderValidator orderValidator;

    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository, CarService carService) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.carService = carService;
        this.orderValidator = new OrderValidator();
    }


    @Override
    public void addOrder(Order pendingOrder, Car car) throws NoneExistingEntityException, InvalidEntityDataException {
        User user = pendingOrder.getUser();
        Order order = new Order();
        order.setUser(user);

        if (pendingOrder.isHireDriver()) {
            Driver availableDriver = pendingOrder.getDriver();
            if (availableDriver != null) {
                List<Order> orders = availableDriver.getOrders();
                orders.add(order);
                availableDriver.setOrders(orders);

                List<User> users = availableDriver.getUsers();
                users.add(user);
                availableDriver.setUsers(users);
                availableDriver.getPickUpDates().add(pendingOrder.getPickUpDate());
                availableDriver.getDropOffDate().add(pendingOrder.getDropOffDate());
                order.setDriver(availableDriver);
                userRepository.update(availableDriver);
                userRepository.save();
            }
        }

        order.setCreatedOn(LocalDateTime.now());
        order.setPickUpLocation(pendingOrder.getPickUpLocation());
        order.setDropOfLocation(pendingOrder.getDropOfLocation());
        order.setPickUpDate(pendingOrder.getPickUpDate());
        order.setDropOffDate(pendingOrder.getDropOffDate());

        long days = DAYS.between(pendingOrder.getPickUpDate(), pendingOrder.getDropOffDate());
        order.setDays(days);

        double driverPricePerDays = 0;
        if (pendingOrder.getDriver() != null) {
            driverPricePerDays = days * pendingOrder.getDriver().getPricePerDay();
        }
        double perDay = 0;
        perDay += calculateCarPrice(days, car.getPricePerDay());
        double totalPrice = perDay + car.getDeposit() + driverPricePerDays;

        order.setCarPricePerDays(perDay);
        order.setDeposit(car.getDeposit());
        order.setFinalPrice(totalPrice);


        try {
            orderValidator.validate(order);
        } catch (ConstraintViolationException ex) {
            throw new InvalidEntityDataException("Error creating order", ex);
        }
        order.setCar(car);
        user.getOrders().add(order);


        orderRepository.create(order);
        orderRepository.save();

        userRepository.update(order.getUser());
        userRepository.save();


        car.setOrder(order);

        car.getPickUpDates().add(order.getPickUpDate());
        car.getDropOffDates().add(order.getDropOffDate());
        carService.editCar(car);

    }

    public double calculateCarPrice(long days, double pricePerDay) {
        double total = 0;

        switch ((int) days) {
            case 1, 2 -> total = days * pricePerDay;
            case 3 -> total = days * pricePerDay * 0.7653;
            case 4 -> total = days * pricePerDay * 0.7527;
            case 5 -> total = days * pricePerDay * 0.6736;
            case 6 -> total = days * pricePerDay * 0.6004;
            case 7 -> total = days * pricePerDay * 0.6014;
            case 8, 9, 10, 11, 12, 13, 14 -> total = days * pricePerDay * 0.5358;
            case 15, 16 -> total = days * pricePerDay * 0.6543;
            case 17 -> total = days * pricePerDay * 0.6129;
            case 18 -> total = days * pricePerDay * 0.61;
            case 19 -> total = days * pricePerDay * 0.6074;
            case 20 -> total = days * pricePerDay * 0.6050;
        }

        if (days > 20 && days <= 50) {
            total = days * 28.22 * 0.6919;
        } else if (days > 50 && days <= 100) {
            total = days * 28.22 * 0.7722;
        } else if (days > 100) {
            total = days * 28.22 * 0.7778;
        }

        return total;
    }

    @Override
    public void loadData() {
        orderRepository.load();
    }

    @Override
    public Collection<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order getOrderById(Long id) throws NoneExistingEntityException {
        return orderRepository.findById(id);
    }

    @Override
    public void editOrder(Order order) throws NoneExistingEntityException {
        orderRepository.update(order);
        orderRepository.save();
    }

    @Override
    public void deleteOrder(Long id) throws NoneExistingEntityException {
        orderRepository.deleteById(id);
        orderRepository.save();
    }

    @Override
    public List<Order> getAllPendingOrders() {
        return orderRepository.findAll().stream()
                .filter(o -> o.getOrderStatus().equals(OrderStatus.PENDING))
                .collect(Collectors.toList());
    }

    @Override
    public void approveOrder(List<Order> pendingOrders, User user) throws NoneExistingEntityException {
        for (Order pendingOrder : pendingOrders) {
            if (user.getRole().equals(Role.SELLER) || user.getRole().equals(Role.ADMINISTRATOR)) {
                pendingOrder.setOrderStatus(OrderStatus.START);

                if (user.getRole().equals(Role.SELLER)) {
                    Seller seller = (Seller) user;

                    seller.getClientsHistory().add(pendingOrder.getUser());
                    seller.getOrders().add(pendingOrder);

                    pendingOrder.setSeller(seller);

                    if (pendingOrder.getDriver() != null) {
                        pendingOrder.getDriver().getSellers().add(seller);
                        userRepository.update(pendingOrder.getDriver());

                    }
                    userRepository.update(seller);
                    userRepository.save();
                } else if (user.getRole().equals(Role.ADMINISTRATOR)) {
                    Administrator administrator = (Administrator) user;

                    administrator.getClientHistory().add(pendingOrder.getUser());
                    administrator.getOrders().add(pendingOrder);

                    pendingOrder.setSeller(administrator);
                    if (pendingOrder.getDriver() != null) {
                        pendingOrder.getDriver().getSellers().add(administrator);
                        userRepository.update(pendingOrder.getDriver());

                    }
                    userRepository.update(administrator);
                    userRepository.save();

                }
            }
            orderRepository.update(pendingOrder);
            orderRepository.save();
        }
    }

    @Override
    public void approveOrder(Order pendingOrder, User user) throws NoneExistingEntityException {
        if (user.getRole().equals(Role.SELLER) || user.getRole().equals(Role.ADMINISTRATOR)) {
            pendingOrder.setOrderStatus(OrderStatus.START);

            if (user.getRole().equals(Role.SELLER)) {
                Seller seller = (Seller) user;

                seller.getClientsHistory().add(pendingOrder.getUser());
                seller.getOrders().add(pendingOrder);

                pendingOrder.setSeller(seller);

                if (pendingOrder.getDriver() != null) {
                    pendingOrder.getDriver().getSellers().add(seller);
                    userRepository.update(pendingOrder.getDriver());

                }
                userRepository.update(seller);
                userRepository.save();
            } else if (user.getRole().equals(Role.ADMINISTRATOR)) {
                Administrator administrator = (Administrator) user;

                administrator.getClientHistory().add(pendingOrder.getUser());
                administrator.getOrders().add(pendingOrder);

                pendingOrder.setSeller(administrator);
                if (pendingOrder.getDriver() != null) {
                    pendingOrder.getDriver().getSellers().add(administrator);
                    userRepository.update(pendingOrder.getDriver());

                }
                userRepository.update(administrator);
                userRepository.save();

            }
        }
        orderRepository.update(pendingOrder);
        orderRepository.save();
    }
}
