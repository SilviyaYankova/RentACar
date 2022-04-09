package cource.project.dao.impl;


import cource.project.dao.CarRepository;
import cource.project.dao.OrderRepository;
import cource.project.dao.UserRepository;
import cource.project.exeption.EntityPersistenceException;
import cource.project.exeption.NoneExistingEntityException;
import cource.project.model.Car;
import cource.project.model.Order;
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
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
public class OrderRepositoryJBDC implements OrderRepository {
    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
    @SuppressWarnings("SqlResolve")
    public static final String FIND_ALL_ORDERS = "select * from `orders`;";
    @SuppressWarnings("SqlResolve")
    public static final String FIND_ORDER_BY_ID = "select * from `orders` where order_id=?;";
    @SuppressWarnings("SqlResolve")
    public static final String INSERT_NEW_ORDER = "insert into `orders` (`user_id`, `driver_id`, `hire_driver`, " +
            "`car_id`, `order_status_id`, `created_on`, `pick_up_location_id`, `drop_off_location_id`, " +
            "`pick_up_date`, `drop_off_date`, `days`, `car_price_per_day`, `deposit`, `driver_price`, `final_price`) " +
            "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";


    private final Connection connection;
    private final UserRepository userRepository;
    private final CarRepository carRepository;

    protected OrderRepositoryJBDC(Connection connection, UserRepository userRepository, CarRepository carRepository) {
        this.connection = connection;
        this.userRepository = userRepository;
        this.carRepository = carRepository;
    }

    @Override
    public Order create(Order order) {
        try (var stmt = connection.prepareStatement(INSERT_NEW_ORDER, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setLong(1, order.getUser().getId());
            stmt.setLong(2, order.getDriver().getId());

            stmt.setBoolean(3, order.isHireDriver());


            stmt.setLong(4, order.getCar().getId());

            Long orderStatusId = getOrderStatusId(order.getOrderStatus());
            stmt.setLong(5, orderStatusId);

            LocalDateTime createdOn = order.getCreatedOn();
            String createOn = createdOn.format(formatter);
            stmt.setString(6, createOn);

            long pick_up_location_id = getLocationId(order.getPickUpLocation());
            stmt.setLong(7, pick_up_location_id);

            long drop_off__location_id = getLocationId(order.getDropOffLocation());
            stmt.setLong(8, drop_off__location_id);

            stmt.setString(9, order.getPickUpDate().format(formatter));
            stmt.setString(10, order.getDropOffDate().format(formatter));

            stmt.setLong(11, order.getDays());
            stmt.setDouble(12, order.getCarPricePerDays());
            stmt.setDouble(13, order.getDeposit());
            stmt.setDouble(14, order.getDriver().getPricePerDay());
            stmt.setDouble(15, order.getFinalPrice());

            connection.setAutoCommit(false);
            var affectedRows = stmt.executeUpdate();
            // more updates here ...
            connection.commit();
            connection.setAutoCommit(true);
            if (affectedRows == 0) {
                throw new EntityPersistenceException("Creating order failed, no rows affected.");
            }
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    order.setId(generatedKeys.getLong(1));
                    return order;
                } else {
                    throw new EntityPersistenceException("Creating order failed, no ID obtained.");
                }
            }
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                throw new EntityPersistenceException("Error rolling back SQL query: " + INSERT_NEW_ORDER, ex);
            }
            log.error("Error creating connection to DB", ex);
            throw new EntityPersistenceException("Error executing SQL query: " + INSERT_NEW_ORDER, ex);
        }
    }

    private long getLocationId(Location pickUpLocation) {
        long id = 0;
        if (pickUpLocation.equals(Location.SOFIA)) {
            id = 1;
        } else if (pickUpLocation.equals(Location.PLOVDIV)) {
            id = 2;
        } else if (pickUpLocation.equals(Location.VARNA)) {
            id = 3;
        } else if (pickUpLocation.equals(Location.BURGAS)) {
            id = 4;
        } else if (pickUpLocation.equals(Location.RUSSE)) {
            id = 5;
        } else if (pickUpLocation.equals(Location.STARA_ZAGORA)) {
            id = 6;
        } else if (pickUpLocation.equals(Location.PLEVEN)) {
            id = 7;
        } else if (pickUpLocation.equals(Location.SLIVEN)) {
            id = 8;
        } else if (pickUpLocation.equals(Location.DOBRICH)) {
            id = 9;
        } else if (pickUpLocation.equals(Location.SHUMEN)) {
            id = 10;
        }


        return id;

    }

    private Long getOrderStatusId(OrderStatus orderStatus) {
        long order_status_id = 0;
        if (orderStatus.equals(OrderStatus.START)) {
            order_status_id = 1;
        }
        if (orderStatus.equals(OrderStatus.PENDING)) {
            order_status_id = 2;
        }
        if (orderStatus.equals(OrderStatus.FINISH)) {
            order_status_id = 3;
        }
        if (orderStatus.equals(OrderStatus.DELETED)) {
            order_status_id = 4;
        }

        return order_status_id;
    }

    @Override
    public Order findById(Long id) {
        Order order = new Order();
        try (var stmt = connection.prepareStatement(FIND_ORDER_BY_ID)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                setOrder(rs, order);
            }
        } catch (SQLException ex) {
            log.error("Error creating connection to DB", ex);
            throw new EntityPersistenceException("Error executing SQL query: " + FIND_ORDER_BY_ID, ex);
        }
        return order;
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
//        try (var stmt = connection.prepareStatement(INSERT_NEW_ORDER, Statement.RETURN_GENERATED_KEYS)) {
//            stmt.setLong(1, order.getId());
//            stmt.setLong(2, order.getUser().getId());
//            stmt.setLong(3, order.getDriver().getId());
//
//            stmt.setBoolean(4, order.isHireDriver());
//
//            stmt.setLong(5, order.getSeller().getId());
//
//            stmt.setLong(6, order.getCar().getId());
//
//            Long orderStatusId = getOrderStatusId(order.getOrderStatus());
//            stmt.setLong(7, orderStatusId);
//
//            LocalDateTime createdOn = order.getCreatedOn();
//            String createOn = createdOn.format(formatter);
//            stmt.setString(8, createOn);
//
//            LocalDateTime modifiedOn = order.getModifiedOn();
//            String mo = modifiedOn.format(formatter);
//            stmt.setString(9, mo);
//
//            long pick_up_location_id = getLocationId(order.getPickUpLocation());
//            stmt.setLong(10, pick_up_location_id);
//
//            long drop_off__location_id = getLocationId(order.getDropOffLocation());
//            stmt.setLong(11, drop_off__location_id);
//
//            stmt.setString(12, order.getPickUpDate().format(formatter));
//            stmt.setString(13, order.getDropOffDate().format(formatter));
//
//            stmt.setLong(14, order.getDays());
//            stmt.setDouble(15, order.getCarPricePerDays());
//            stmt.setDouble(16, order.getDeposit());
//            stmt.setDouble(17, order.getDriver().getPricePerDay());
//            stmt.setDouble(18, order.getFinalPrice());
//
//
//            connection.setAutoCommit(false);
//            var affectedRows = stmt.executeUpdate();
//            // more updates here ...
//            connection.commit();
//            connection.setAutoCommit(true);
//            if (affectedRows == 0) {
//                throw new EntityPersistenceException("Creating order failed, no rows affected.");
//            }
//            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
//                if (generatedKeys.next()) {
//                    order.setId(generatedKeys.getLong(1));
//                    return order;
//                } else {
//                    throw new EntityPersistenceException("Creating order failed, no ID obtained.");
//                }
//            }
//        } catch (SQLException ex) {
//            try {
//                connection.rollback();
//            } catch (SQLException e) {
//                throw new EntityPersistenceException("Error rolling back SQL query: " + INSERT_NEW_ORDER, ex);
//            }
//            log.error("Error creating connection to DB", ex);
//            throw new EntityPersistenceException("Error executing SQL query: " + INSERT_NEW_ORDER, ex);
//        }
    }

    @Override
    public void deleteById(Long id) throws NoneExistingEntityException {

    }

    private Collection<Order> toOrders(ResultSet rs) throws SQLException, NoneExistingEntityException {
        List<Order> orders = new ArrayList<>();
        while (rs.next()) {
            Order order = new Order();

            setOrder(rs, order);

            orders.add(order);
        }
        return orders;
    }

    private void setOrder(ResultSet rs, Order order) throws SQLException {
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
