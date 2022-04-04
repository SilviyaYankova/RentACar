package service.impl;

import dao.UserRepository;
import exeption.*;
import model.Car;
import model.Order;
import model.Worker;
import model.enums.*;
import model.user.*;
import service.*;
import util.OrderValidator;
import util.UserValidator;
import util.WorkerValidator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static model.enums.CarStatus.FINISH_CLEANING;

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
        userRepository.save();
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
    public void editUser(User user) throws NoneExistingEntityException, NoPermissionException {
        userRepository.update(user);
        userRepository.save();
    }

    @Override
    public void deleteUser(Long id) throws NoneExistingEntityException {
        userRepository.deleteById(id);
        userRepository.save();
    }

    @Override
    public void approveOrder(List<Order> pendingOrders, User user) {
        for (Order pendingOrder : pendingOrders) {
            if (user.getRole().equals(Role.SELLER) || user.getRole().equals(Role.ADMINISTRATOR)) {
                pendingOrder.setOrderStatus(OrderStatus.START);
                try {
                    Driver driver = pendingOrder.getDriver();
                    List<User> sellers = null;
                    if (driver != null) {
                        sellers = driver.getSellers();
                    }
                    if (user.getRole().equals(Role.SELLER)) {
                        Seller seller = (Seller) user;

                        seller.getClientsHistory().add(pendingOrder.getUser());
                        List<Order> orders = seller.getOrders();

                        orders.add(pendingOrder);
                        seller.setOrders(orders);
                        pendingOrder.setSeller(seller);
                        userRepository.update(seller);

                        if (driver != null) {
                            sellers.add(seller);
                            pendingOrder.getDriver().setSellers(sellers);
                            userRepository.update(driver);
                        }

                    } else if (user.getRole().equals(Role.ADMINISTRATOR)) {
                        Administrator administrator = (Administrator) user;
                        administrator.getClientHistory().add(pendingOrder.getUser());
                        List<Order> orders = administrator.getOrders();

                        orders.add(pendingOrder);
                        administrator.setOrders(orders);
                        pendingOrder.setSeller(administrator);
                        userRepository.update(administrator);

                        if (driver != null) {
                            sellers.add(administrator);
                            pendingOrder.getDriver().setSellers(sellers);
                            userRepository.update(driver);
                        }
                    }
                    orderService.editOrder(pendingOrder);
                } catch (NoneExistingEntityException e) {
                    e.printStackTrace();
                }
            }
        }
        userRepository.save();
    }

    @Override
    public void returnCar(Car car) {
        Order order = car.getOrder();
        Driver driver = order.getDriver();
        try {
            order.setOrderStatus(OrderStatus.FINISH);
            if (driver != null) {
                driver.setDriverStatus(DriverStatus.AVAILABLE);
                userRepository.update(driver);
            }
            car.setCarStatus(CarStatus.WAITING_FOR_CLEANING);
            car.setOrder(null);
            car.setCarStatus(CarStatus.AVAILABLE);
            carService.editCar(car);
            order.setOrderStatus(OrderStatus.FINISH);
            orderService.editOrder(order);
        } catch (NoneExistingEntityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendCarsForCleaning(User user) {
        SiteManager siteManager = (SiteManager) userRepository.findById(3L);
        siteManager.getSellersHistory().add(user);
        List<Car> allWaitingCarsForCleaning = carService.getAllCarsWithStatus(CarStatus.WAITING_FOR_CLEANING);
        if (allWaitingCarsForCleaning.size() > 0) {
            allWaitingCarsForCleaning.stream().forEach(c -> {
                c.setCarStatus(CarStatus.CLEANING);
                siteManager.getCarsHistory().add(c);
                try {
                    carService.editCar(c);
                } catch (NoneExistingEntityException e) {
                    e.printStackTrace();
                }
            });

        }
    }

    @Override
    public void startCleaning(List<Car> allCarsWaitingForCleaning) throws NoneAvailableEntityException {
        workerService.cleanCar(allCarsWaitingForCleaning);
    }

    @Override
    public void returnCarToShop() {
        List<Car> allCarsWithStatus = carService.getAllCarsWithStatus(FINISH_CLEANING);
        for (Car car : allCarsWithStatus) {
            if (car.getCarStatus().equals(FINISH_CLEANING)) {
                car.setCarStatus(CarStatus.AVAILABLE);
                try {
                    carService.editCar(car);
                } catch (NoneExistingEntityException e) {
                    e.printStackTrace();
                }
                Worker worker = car.getWorker();
                worker.setWorkerStatus(WorkerStatus.AVAILABLE);
                worker.setCurrentCar(null);
                try {
                    workerService.editWorker(worker);
                } catch (NoneExistingEntityException e) {
                    e.printStackTrace();
                }
            }
        }

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
        Collection<Order> allOrders = orderService.getAllOrders();
        allOrders.forEach(System.out::println);
        double totalProfit = allOrders.stream().mapToDouble(Order::getCarPricePerDays).sum();
        return String.format("Total Profit %.2f%n", totalProfit);
    }

    @Override
    public String getProfitForPeriod(LocalDateTime from, LocalDateTime to) {
        double totalProfit = 0;
        Collection<Order> allOrders = orderService.getAllOrders();
        for (Order order : allOrders) {

            ChronoLocalDate orderDate = LocalDate.of(order.getPickUpDate().getYear(), order.getPickUpDate().getMonth(), order.getPickUpDate().getDayOfMonth());
            ChronoLocalDate fromData = LocalDate.of(from.getYear(), from.getMonth(), from.getDayOfMonth());
            ChronoLocalDate toDate = LocalDate.of(to.getYear(), to.getMonth(), to.getDayOfMonth());

            if (orderDate.equals(fromData) && !(orderDate.equals(toDate))) {
                totalProfit += order.getCarPricePerDays();
            }
        }
        return String.format("Total Profit from %s to %s: %.2f%n",
                from.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                to.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                totalProfit);
    }

    @Override
    public void loadData() {
        userRepository.load();
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
