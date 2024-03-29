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

import java.sql.*;
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
    public static final String FIND_ALL_ORDERS_BY_USER = "select * from `orders` where user_id=?;";
    @SuppressWarnings("SqlResolve")
    public static final String FIND_ORDER_BY_ID = "select * from `orders` where order_id=?;";
    @SuppressWarnings("SqlResolve")
    public static final String INSERT_NEW_ORDER_WITH_DRIVER = "insert into `orders` (`user_id`, `driver_id`, `hire_driver`, " +
            "`car_id`, `order_status_id`, `created_on`, `pick_up_location_id`, `drop_off_location_id`, " +
            "`pick_up_date`, `drop_off_date`, `days`, `car_price_per_day`, `deposit`, `driver_price`, `final_price`) " +
            "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    @SuppressWarnings("SqlResolve")
    public static final String INSERT_NEW_ORDER_WITHOUT_DRIVER = "insert into `orders` (`user_id`, `hire_driver`, " +
            "`car_id`, `order_status_id`, `created_on`, `pick_up_location_id`, `drop_off_location_id`, " +
            "`pick_up_date`, `drop_off_date`, `days`, `car_price_per_day`, `deposit`, `final_price`) " +
            "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    @SuppressWarnings("SqlResolve")
    public static final String INSERT_PICK_UP_DATES_WITH_DRIVER = "insert into `pick_up_dates` (`pick_up_date`, `car_id`, `driver_id`) values (?, ?, ?);";
    @SuppressWarnings("SqlResolve")
    public static final String INSERT_PICK_UP_DATES_WITHOUT_DRIVER = "insert into `pick_up_dates` (`pick_up_date`, `car_id`) values (?, ?);";
    @SuppressWarnings("SqlResolve")
    public static final String INSERT_DROP_OFF_DATES_WITH_DRIVER = "insert into `drop_off_dates` (`drop_off_date`, `car_id`, `driver_id`) values (?, ?, ?);";
    @SuppressWarnings("SqlResolve")
    public static final String INSERT_DROP_OFF_DATES_WITHOUT_DRIVER = "insert into `drop_off_dates` (`drop_off_date`, `car_id`) values (?, ?);";
    @SuppressWarnings("SqlResolve")
    public static final String DELETE_PICK_UP_DATE = "delete from `pick_up_dates` where pick_up_date=? and car_id=?;";
    @SuppressWarnings("SqlResolve")
    public static final String DELETE_DROP_OFF_DATE = "delete from `drop_off_dates` where drop_off_date=? and car_id=?;";
    @SuppressWarnings("SqlResolve")
    public static final String DELETE_CARS_ORDERS = "delete from `cars_orders` where car_id=?;";
    @SuppressWarnings("SqlResolve")
    public static final String DELETE_USERS_ORDERS = "delete from `users_orders` where order_id=?;";
    @SuppressWarnings("SqlResolve")
    public static final String DELETE_ORDER = "delete from `orders` where order_id=?;";
    @SuppressWarnings("SqlResolve")
    public static final String UPDATE_ORDER_STATUS = "update `orders` " +
            "set `order_status_id`=?, seller_id=? " +
            "where order_id=?;";
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
        Driver driver = order.getDriver();
        if (driver != null) {
            try (var stmt = connection.prepareStatement(INSERT_NEW_ORDER_WITH_DRIVER, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setLong(1, order.getUser().getId());

                stmt.setLong(2, order.getDriver().getId());

                if (order.isHireDriver()) {
                    stmt.setBoolean(3, true);
                } else {
                    stmt.setBoolean(3, false);
                }

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
                    throw new EntityPersistenceException("Error rolling back SQL query: " + INSERT_NEW_ORDER_WITH_DRIVER, ex);
                }
                log.error("Error creating connection to DB", ex);
                throw new EntityPersistenceException("Error executing SQL query: " + INSERT_NEW_ORDER_WITH_DRIVER, ex);
            }
        } else {
            try (var stmt = connection.prepareStatement(INSERT_NEW_ORDER_WITHOUT_DRIVER, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setLong(1, order.getUser().getId());
                if (order.isHireDriver()) {
                    stmt.setBoolean(2, true);
                } else {
                    stmt.setBoolean(2, false);
                }

                stmt.setLong(3, order.getCar().getId());

                Long orderStatusId = getOrderStatusId(order.getOrderStatus());
                stmt.setLong(4, orderStatusId);

                LocalDateTime createdOn = order.getCreatedOn();
                String createOn = createdOn.format(formatter);
                stmt.setString(5, createOn);

                long pick_up_location_id = getLocationId(order.getPickUpLocation());
                stmt.setLong(6, pick_up_location_id);

                long drop_off__location_id = getLocationId(order.getDropOffLocation());
                stmt.setLong(7, drop_off__location_id);

                stmt.setString(8, order.getPickUpDate().format(formatter));
                stmt.setString(9, order.getDropOffDate().format(formatter));

                stmt.setLong(10, order.getDays());
                stmt.setDouble(11, order.getCarPricePerDays());
                stmt.setDouble(12, order.getDeposit());

                stmt.setDouble(13, order.getFinalPrice());

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
                    throw new EntityPersistenceException("Error rolling back SQL query: " + INSERT_NEW_ORDER_WITHOUT_DRIVER, ex);
                }
                log.error("Error creating connection to DB", ex);
                throw new EntityPersistenceException("Error executing SQL query: " + INSERT_NEW_ORDER_WITHOUT_DRIVER, ex);
            }
        }
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
    public void update(Order order) throws NoneExistingEntityException {
        if (order.getOrderStatus().equals(OrderStatus.PENDING)){
            try (var stmt = connection.prepareStatement(UPDATE_ORDER_STATUS)) {
                // set order status 1 = START
                stmt.setLong(1, OrderStatus.START.ordinal());
                stmt.setLong(2, order.getSeller().getId());
                stmt.setLong(3, order.getId());

                connection.setAutoCommit(false);
                var affectedRows = stmt.executeUpdate();
                connection.commit();
                connection.setAutoCommit(true);

                if (affectedRows == 0) {
                    throw new EntityPersistenceException("Updating UPDATE_ORDER failed, no rows affected.");
                }
            } catch (SQLException ex) {
                try {
                    connection.rollback();
                } catch (SQLException e) {
                    throw new EntityPersistenceException("Error rolling back SQL query: " + UPDATE_ORDER_STATUS, ex);
                }
                log.error("Error creating connection to DB", ex);
                throw new EntityPersistenceException("Error executing SQL query: " + UPDATE_ORDER_STATUS, ex);
            }
        }

        if (order.getOrderStatus().equals(OrderStatus.FINISH)){
            try (var stmt = connection.prepareStatement(UPDATE_ORDER_STATUS)) {
                // set order status 1 = finish
                stmt.setLong(1, OrderStatus.FINISH.ordinal());
                stmt.setLong(2, order.getSeller().getId());
                stmt.setLong(3, order.getId());

                connection.setAutoCommit(false);
                var affectedRows = stmt.executeUpdate();
                connection.commit();
                connection.setAutoCommit(true);

                if (affectedRows == 0) {
                    throw new EntityPersistenceException("Updating UPDATE_ORDER failed, no rows affected.");
                }
            } catch (SQLException ex) {
                try {
                    connection.rollback();
                } catch (SQLException e) {
                    throw new EntityPersistenceException("Error rolling back SQL query: " + UPDATE_ORDER_STATUS, ex);
                }
                log.error("Error creating connection to DB", ex);
                throw new EntityPersistenceException("Error executing SQL query: " + UPDATE_ORDER_STATUS, ex);
            }
        }
    }

    @Override
    public void deleteById(Long id) throws NoneExistingEntityException {
        Order order = findById(id);
        Long carID = order.getCar().getId();
        LocalDateTime pick = order.getPickUpDate();
        String pickUpDate = pick.format(formatter);

        try (var stmt = connection.prepareStatement(DELETE_PICK_UP_DATE)) {
            stmt.setString(1, pickUpDate);
            stmt.setLong(2, carID);
            connection.setAutoCommit(false);
            var affectedRows = stmt.executeUpdate();

            connection.commit();
            connection.setAutoCommit(true);

            if (affectedRows == 0) {
                throw new EntityPersistenceException("Deleting pickupdate failed, no rows affected.");
            }
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                throw new EntityPersistenceException("Error rolling back SQL query: " + DELETE_PICK_UP_DATE, ex);
            }
            log.error("Error creating connection to DB", ex);
            throw new EntityPersistenceException("Error executing SQL query: " + DELETE_PICK_UP_DATE, ex);
        }

        LocalDateTime drop = order.getDropOffDate();
        String dropOffDate = drop.format(formatter);
        try (var stmt = connection.prepareStatement(DELETE_DROP_OFF_DATE)) {
            stmt.setString(1, dropOffDate);
            stmt.setLong(2, carID);
            connection.setAutoCommit(false);
            var affectedRows = stmt.executeUpdate();
            connection.commit();
            connection.setAutoCommit(true);

            if (affectedRows == 0) {
                throw new EntityPersistenceException("Deleting DELETE_DROP_OFF_DATE failed, no rows affected.");
            }
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                throw new EntityPersistenceException("Error rolling back SQL query: " + DELETE_DROP_OFF_DATE, ex);
            }
            log.error("Error creating connection to DB", ex);
            throw new EntityPersistenceException("Error executing SQL query: " + DELETE_DROP_OFF_DATE, ex);
        }

        try (var stmt = connection.prepareStatement(DELETE_CARS_ORDERS)) {
            stmt.setLong(1, carID);
            connection.setAutoCommit(false);
            var affectedRows = stmt.executeUpdate();
            connection.commit();
            connection.setAutoCommit(true);

            if (affectedRows == 0) {
                throw new EntityPersistenceException("Deleting DELETE_CARS_ORDERS failed, no rows affected.");
            }

        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                throw new EntityPersistenceException("Error rolling back SQL query: " + DELETE_CARS_ORDERS, ex);
            }
            log.error("Error creating connection to DB", ex);
            throw new EntityPersistenceException("Error executing SQL query: " + DELETE_CARS_ORDERS, ex);
        }

        try (var stmt = connection.prepareStatement(DELETE_USERS_ORDERS)) {
            stmt.setLong(1, order.getId());
            connection.setAutoCommit(false);
            var affectedRows = stmt.executeUpdate();
            connection.commit();
            connection.setAutoCommit(true);

            if (affectedRows == 0) {
                throw new EntityPersistenceException("Deleting DELETE_USERS_ORDERS failed, no rows affected.");
            }

        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                throw new EntityPersistenceException("Error rolling back SQL query: " + DELETE_USERS_ORDERS, ex);
            }
            log.error("Error creating connection to DB", ex);
            throw new EntityPersistenceException("Error executing SQL query: " + DELETE_USERS_ORDERS, ex);
        }

        try (var stmt = connection.prepareStatement(DELETE_ORDER)) {
            stmt.setLong(1, order.getId());
            connection.setAutoCommit(false);
            var affectedRows = stmt.executeUpdate();
            connection.commit();
            connection.setAutoCommit(true);

            if (affectedRows == 0) {
                throw new EntityPersistenceException("Deleting DELETE_ORDER failed, no rows affected.");
            }
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                throw new EntityPersistenceException("Error rolling back SQL query: " + DELETE_ORDER, ex);
            }
            log.error("Error creating connection to DB", ex);
            throw new EntityPersistenceException("Error executing SQL query: " + DELETE_ORDER, ex);
        }
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

    public Location getLocationName(long pick_up_location_id) {
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

    public OrderStatus getOrderStatusName(long order_status_id) {
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

    @Override
    public void insertPickUpDate(LocalDateTime pickUpDate, Long carId, User driver) {
        if (driver != null) {
            try (var stmt = connection.prepareStatement(INSERT_PICK_UP_DATES_WITH_DRIVER)) {
                stmt.setString(1, pickUpDate.format(formatter));
                stmt.setLong(2, carId);
                stmt.setLong(3, driver.getId());

                connection.setAutoCommit(false);
                var affectedRows = stmt.executeUpdate();
                connection.commit();
                connection.setAutoCommit(true);

                if (affectedRows == 0) {
                    throw new EntityPersistenceException("Updating pick_up_dates failed, no rows affected.");
                }
            } catch (SQLException ex) {
                try {
                    connection.rollback();
                } catch (SQLException e) {
                    throw new EntityPersistenceException("Error rolling back SQL query: " + INSERT_PICK_UP_DATES_WITH_DRIVER, ex);
                }
                log.error("Error creating connection to DB", ex);
                throw new EntityPersistenceException("Error executing SQL query: " + INSERT_PICK_UP_DATES_WITH_DRIVER, ex);
            }
        } else {
            try (var stmt = connection.prepareStatement(INSERT_PICK_UP_DATES_WITHOUT_DRIVER)) {
                stmt.setString(1, pickUpDate.format(formatter));
                stmt.setLong(2, carId);

                connection.setAutoCommit(false);
                var affectedRows = stmt.executeUpdate();
                connection.commit();
                connection.setAutoCommit(true);

                if (affectedRows == 0) {
                    throw new EntityPersistenceException("Updating pick_up_dates failed, no rows affected.");
                }
            } catch (SQLException ex) {
                try {
                    connection.rollback();
                } catch (SQLException e) {
                    throw new EntityPersistenceException("Error rolling back SQL query: " + INSERT_PICK_UP_DATES_WITHOUT_DRIVER, ex);
                }
                log.error("Error creating connection to DB", ex);
                throw new EntityPersistenceException("Error executing SQL query: " + INSERT_PICK_UP_DATES_WITHOUT_DRIVER, ex);
            }
        }
    }

    @Override
    public void insertDropOffDate(LocalDateTime dropOffDate, Long carId, User driver) {
        if (driver != null) {
            try (var stmt = connection.prepareStatement(INSERT_DROP_OFF_DATES_WITH_DRIVER)) {
                stmt.setString(1, dropOffDate.format(formatter));
                stmt.setLong(2, carId);
                stmt.setLong(3, driver.getId());

                connection.setAutoCommit(false);
                var affectedRows = stmt.executeUpdate();
                connection.commit();
                connection.setAutoCommit(true);

                if (affectedRows == 0) {
                    throw new EntityPersistenceException("Updating pick_up_dates failed, no rows affected.");
                }
            } catch (SQLException ex) {
                try {
                    connection.rollback();
                } catch (SQLException e) {
                    throw new EntityPersistenceException("Error rolling back SQL query: " + INSERT_DROP_OFF_DATES_WITH_DRIVER, ex);
                }
                log.error("Error creating connection to DB", ex);
                throw new EntityPersistenceException("Error executing SQL query: " + INSERT_DROP_OFF_DATES_WITH_DRIVER, ex);
            }
        } else {
            try (var stmt = connection.prepareStatement(INSERT_DROP_OFF_DATES_WITHOUT_DRIVER)) {
                stmt.setString(1, dropOffDate.format(formatter));
                stmt.setLong(2, carId);

                connection.setAutoCommit(false);
                var affectedRows = stmt.executeUpdate();
                connection.commit();
                connection.setAutoCommit(true);

                if (affectedRows == 0) {
                    throw new EntityPersistenceException("Updating pick_up_dates failed, no rows affected.");
                }
            } catch (SQLException ex) {
                try {
                    connection.rollback();
                } catch (SQLException e) {
                    throw new EntityPersistenceException("Error rolling back SQL query: " + INSERT_DROP_OFF_DATES_WITHOUT_DRIVER, ex);
                }
                log.error("Error creating connection to DB", ex);
                throw new EntityPersistenceException("Error executing SQL query: " + INSERT_DROP_OFF_DATES_WITHOUT_DRIVER, ex);
            }
        }
    }

    @Override
    public Collection<Order> findAllByUser(Long id) {
        try (var stmt = connection.prepareStatement(FIND_ALL_ORDERS_BY_USER)) {
            stmt.setLong(1, id);
            var rs = stmt.executeQuery();
            return toOrders(rs);
        } catch (SQLException | NoneExistingEntityException ex) {
            log.error("Error creating connection to DB", ex);
            throw new EntityPersistenceException("Error executing SQL query: " + FIND_ALL_ORDERS_BY_USER, ex);
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
}
