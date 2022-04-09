package cource.project.dao.impl;


import cource.project.dao.CarRepository;
import cource.project.dao.OrderRepository;
import cource.project.dao.UserRepository;
import cource.project.exeption.EntityPersistenceException;
import cource.project.exeption.NoneExistingEntityException;
import cource.project.model.Car;
import cource.project.model.Order;
import cource.project.model.enums.DriverStatus;
import cource.project.model.enums.Location;
import cource.project.model.enums.OrderStatus;
import cource.project.model.enums.Role;
import cource.project.model.user.Driver;
import cource.project.model.user.Seller;
import cource.project.model.user.User;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
public class OrderRepositoryJBDC implements OrderRepository {
    @SuppressWarnings("SqlResolve")
    public static final String FIND_ALL_ORDERS = "select * from `orders`;";


    private final Connection connection;
    private final UserRepository userRepository;
    private final CarRepository carRepository;

    protected OrderRepositoryJBDC(Connection connection, UserRepository userRepository, CarRepository carRepository) {
        this.connection = connection;
        this.userRepository = userRepository;
        this.carRepository = carRepository;
    }

    @Override
    public Order create(Order entity) {
        return null;
    }

    @Override
    public Order findById(Long id) {
        return null;
    }

    @Override
    public Collection<Order> findAll() {
        try (var stmt = connection.prepareStatement(FIND_ALL_ORDERS)) {
            var rs = stmt.executeQuery();
            return toOrders(rs);
        } catch (SQLException | NoneExistingEntityException ex) {
            log.error("Error creating connection to DB", ex);
            throw new EntityPersistenceException("Error executing SQL query: " + FIND_ALL_ORDERS, ex);
        }
    }


    @Override
    public void update(Order entity) throws NoneExistingEntityException {

    }

    @Override
    public void deleteById(Long id) throws NoneExistingEntityException {

    }

    private Collection<Order> toOrders(ResultSet rs) throws SQLException, NoneExistingEntityException {
        List<Order> orders = new ArrayList<>();
        while (rs.next()) {
            Order order = new Order();

            order.setId(rs.getLong("order_id"));
            long user_id = rs.getLong("user_id");
            User user = userRepository.findById(user_id);
            order.setUser(user);


            boolean hire_driver = rs.getBoolean("hire_driver");
            order.setHireDriver(hire_driver);
            if (hire_driver) {
                long driver_id = rs.getLong("driver_id");
                User userDriver = userRepository.findById(driver_id);
                Driver driver = new Driver();
                driver.setId(userDriver.getId());
                driver.setFirstName(userDriver.getFirstName());
                driver.setLastName(userDriver.getLastName());
                driver.setEmail(userDriver.getEmail());
                driver.setPassword(userDriver.getPhoneNumber());
                driver.setUsername(userDriver.getUsername());
                driver.setPassword(userDriver.getPassword());
                driver.setRepeatPassword(userDriver.getRepeatPassword());
                driver.setRegisteredOn(userDriver.getRegisteredOn());
                driver.setRole(Role.DRIVER);

                Driver foundDriver = userRepository.findDriver(driver.getId());
                driver.setPricePerDay(foundDriver.getPricePerDay());
                driver.setDriverStatus(foundDriver.getDriverStatus());

                order.setDriver(driver);
            }

            long seller_id = rs.getLong("seller_id");
            User userSeller = userRepository.findById(seller_id);

            Seller seller = new Seller();
            seller.setId(userSeller.getId());
            seller.setFirstName(userSeller.getFirstName());
            seller.setLastName(userSeller.getLastName());
            seller.setEmail(userSeller.getEmail());
            seller.setPassword(userSeller.getPhoneNumber());
            seller.setUsername(userSeller.getUsername());
            seller.setPassword(userSeller.getPassword());
            seller.setRepeatPassword(userSeller.getRepeatPassword());
            seller.setRegisteredOn(userSeller.getRegisteredOn());
            seller.setRole(userSeller.getRole());

            order.setSeller(seller);

            long car_id = rs.getLong("car_id");
            Car car = carRepository.findById(car_id);
            order.setCar(car);

            long order_status_id = rs.getLong("order_status_id");
            OrderStatus orderStatus = getOrderStatusName(order_status_id);
            order.setOrderStatus(orderStatus);

            String created_on = rs.getString("created_on");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
            LocalDateTime co = LocalDateTime.parse(created_on, formatter);
            order.setCreatedOn(co);

            if (order.getModifiedOn() != null) {
                String modiefied_on = rs.getString("modiefied_on");
                LocalDateTime mo = LocalDateTime.parse(modiefied_on, formatter);
                order.setModifiedOn(mo);
            }

            long pick_up_location_id = rs.getLong("pick_up_location_id");
            Location pickUpLocation = getLocationName(pick_up_location_id);
            order.setPickUpLocation(pickUpLocation);

            long drop_off_location_id = rs.getLong("drop_off_location_id");
            Location dropOffLocation = getLocationName(drop_off_location_id);
            order.setDropOfLocation(dropOffLocation);

            String pick_up_date = rs.getString("pick_up_date");
            LocalDateTime pickUpDate = LocalDateTime.parse(pick_up_date, formatter);
            order.setPickUpDate(pickUpDate);
            String drop_off_date = rs.getString("drop_off_date");
            LocalDateTime dropOffDate = LocalDateTime.parse(drop_off_date, formatter);
            order.setDropOffDate(dropOffDate);

            order.setDays(rs.getLong("days"));
            order.setCarPricePerDays(rs.getDouble("car_price_per_day"));
            order.setDeposit(rs.getDouble("deposit"));
            order.setFinalPrice(rs.getDouble("final_price"));

            orders.add(order);
        }
        return orders;
    }

    private Location getLocationName(long pick_up_location_id) {
        Location location = null;

        if (pick_up_location_id == 1) {
            location = Location.SOFIA;
        } else if (pick_up_location_id == 2) {
            location = Location.PLOVDIV;
        } else if (pick_up_location_id == 3) {
            location = Location.VARNA;
        } else if (pick_up_location_id == 4) {
            location = Location.BURGAS;
        } else if (pick_up_location_id == 5) {
            location = Location.RUSSE;
        } else if (pick_up_location_id == 6) {
            location = Location.STARA_ZAGORA;
        } else if (pick_up_location_id == 7) {
            location = Location.PLEVEN;
        } else if (pick_up_location_id == 8) {
            location = Location.SLIVEN;
        } else if (pick_up_location_id == 9) {
            location = Location.DOBRICH;
        } else if (pick_up_location_id == 10) {
            location = Location.SHUMEN;
        }

        return location;
    }

    private OrderStatus getOrderStatusName(long order_status_id) {
        OrderStatus orderStatus = null;
        if (order_status_id == 1) {
            orderStatus = OrderStatus.START;
        } else if (order_status_id == 2) {
            orderStatus = OrderStatus.PENDING;
        } else if (order_status_id == 3) {
            orderStatus = OrderStatus.FINISH;
        } else if (order_status_id == 4) {
            orderStatus = OrderStatus.DELETED;
        }


        return orderStatus;
    }
}