package cource.project.service.impl;

import cource.project.dao.UserRepository;
import cource.project.exeption.*;
import cource.project.model.Order;
import cource.project.model.enums.*;
import cource.project.model.user.*;
import cource.project.service.*;
import cource.project.util.validator.OrderValidator;
import cource.project.util.validator.UserValidator;
import cource.project.util.validator.WorkerValidator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final WorkerService workerService;
    private final OrderService orderService;
    private final CarService carService;
    private final CommentService commentService;
    private final UserValidator userValidator;
    private final WorkerValidator workerValidator;
    private final OrderValidator orderValidator;

    public UserServiceImpl(UserRepository userRepository, WorkerService workerService, OrderService orderService,
                           CarService carService, CommentService commentService) {
        this.userRepository = userRepository;
        this.workerService = workerService;
        this.orderService = orderService;
        this.carService = carService;
        this.commentService = commentService;
        this.userValidator = new UserValidator();
        this.workerValidator = new WorkerValidator();
        this.orderValidator = new OrderValidator();
    }

    @Override
    public User registerUser(User user) throws InvalidEntityDataException {
        try {
            userValidator.validate(user);
        } catch (ConstraintViolationException ex) {
            throw new InvalidEntityDataException(
                    String.format("Error creating user '%s'", user.getUsername()),
                    ex
            );
        }

        User newUser = userRepository.create(user);
        return newUser;

    }

    @Override
    public Collection<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) throws NoneExistingEntityException {
        User user = userRepository.findById(id);
        if (user == null) {
            throw new NoneExistingEntityException("User with ID " + id + " does not exist.");
        }

        return userRepository.findById(id);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public void editUser(User user) throws NoneExistingEntityException {
        System.out.println();
        userRepository.update(user);
    }

    @Override
    public void deleteUser(Long id) throws NoneExistingEntityException {
        userRepository.deleteById(id);
    }

    @Override
    public Collection<User> getUserByRole(Role role) {
        return userRepository
                .findAll()
                .stream()
                .filter(u -> u.getRole().equals(role))
                .collect(Collectors.toList());
    }

    @Override
    public String getProfit() {
        Collection<Order> allOrders = orderService.getAllOrdersWithStatus(OrderStatus.FINISH);

        double totalProfit = 0;
        for (Order order : allOrders) {
            totalProfit += order.getFinalPrice() - order.getDeposit();
        }
        return String.format("Total Profit: %.2f%n", totalProfit);
    }

    @Override
    public String getProfitForPeriod(LocalDateTime from, LocalDateTime to) {
        double totalProfit = 0;
        Collection<Order> allOrders = orderService.getAllOrdersWithStatus(OrderStatus.FINISH);
        for (Order order : allOrders) {
            ChronoLocalDate orderDate = LocalDate.of(order.getPickUpDate().getYear(), order.getPickUpDate().getMonth(), order.getPickUpDate().getDayOfMonth());
            ChronoLocalDate fromData = LocalDate.of(from.getYear(), from.getMonth(), from.getDayOfMonth());
            ChronoLocalDate toDate = LocalDate.of(to.getYear(), to.getMonth(), to.getDayOfMonth());

            if (orderDate.equals(fromData)) {
                totalProfit += order.getCarPricePerDays();
            }
        }
        return String.format("Total Profit from %s to %s: %.2f%n",
                from.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                to.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                totalProfit);
    }


    @Override
    public User getUserByUsernameAndPassword(String username, String password) {
        Collection<User> all = userRepository.findAll();
        User user = new User();
        for (User u : all) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                user = u;
                break;
            }
        }
        return user;
    }

}
