package cource.project.dao.impl;

import cource.project.dao.UserRepository;
import cource.project.exeption.EntityPersistenceException;
import cource.project.exeption.NoneAvailableEntityException;
import cource.project.exeption.NoneExistingEntityException;
import cource.project.model.Order;
import cource.project.model.Worker;
import cource.project.model.enums.DriverStatus;
import cource.project.model.enums.Role;
import cource.project.model.user.Driver;
import cource.project.model.user.User;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
public class UserRepositoryJDBC implements UserRepository {

    @SuppressWarnings("SqlResolve")
    public static final String FIND_ALL_USERS = "select * from `users`;";
    @SuppressWarnings("SqlResolve")
    public static final String FIND_USER_BY_ID = "select * from `users` where user_id=?;";
    @SuppressWarnings("SqlResolve")
    public static final String INSERT_NEW_USER = "insert into `users` (`first_name`, `last_name`, `email`, `phone_number`, `username`, `password`, `repeat_password`, `registered_on`, `role_id`) values (?, ?,?, ?, ?, ?, ?, ?, ?);";
    @SuppressWarnings("SqlResolve")
    public static final String UPDATE_USER = "update `users` " +
            "set `first_name`=?, `last_name`=?, `phone_number`=?, `password`=?, `repeat_password`=?, `role_id`=? " +
            "where user_id=?;";
    @SuppressWarnings("SqlResolve")
    public static final String DELETE_USER_BY_ID = "delete from `users` where user_id=?;";
    @SuppressWarnings("SqlResolve")
    public static final String FIND_USER_BY_USERNAME = "select * from `users` where username=?;";
    @SuppressWarnings("SqlResolve")
    public static final String FIND_USER_BY_EMAIL = "select * from `users` where email=?;";
    @SuppressWarnings("SqlResolve")
    public static final String SELECT_DRIVER = "select * from `drivers` where driver_id=?;";
    @SuppressWarnings("SqlResolve")
    public static final String SELECT_USERS_ORDERS = "select order_id from users_orders where user_id=?";
    @SuppressWarnings("SqlResolve")
    public static final String SELECT_DRIVER_PICK_UP_DATES = "select pick_up_date from pick_up_dates where driver_id=?";
    @SuppressWarnings("SqlResolve")
    public static final String SELECT_DRIVER_DROP_OFF_DATES = "select drop_off_date from drop_off_dates where driver_id=?";
    @SuppressWarnings("SqlResolve")
    public static final String INSERT_USERS_ORDERS = "insert into `users_orders` (`user_id`, `order_id`) values (?, ?);";

    private Connection connection;

    protected UserRepositoryJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public User create(User entity) {
        try (var stmt = connection.prepareStatement(INSERT_NEW_USER, Statement.RETURN_GENERATED_KEYS)) {
            // 4. Set params and execute SQL query
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
            LocalDateTime localDateTime = entity.getRegisteredOn();
            String registeredOn = localDateTime.format(formatter);

            stmt.setString(1, entity.getFirstName());
            stmt.setString(2, entity.getLastName());
            stmt.setString(3, entity.getEmail());
            stmt.setString(4, entity.getPhoneNumber());
            stmt.setString(5, entity.getUsername());
            stmt.setString(6, entity.getPassword());
            stmt.setString(7, entity.getRepeatPassword());
            stmt.setString(8, registeredOn);

            Long roleId = 0L;
            if (entity.getRole().equals(Role.ADMINISTRATOR)) {
                roleId = 1L;
            } else if (entity.getRole().equals(Role.SELLER)) {
                roleId = 2L;
            } else if (entity.getRole().equals(Role.SITE_MANAGER)) {
                roleId = 3L;
            } else if (entity.getRole().equals(Role.DRIVER)) {
                roleId = 4L;
            } else {
                roleId = 5L;
            }
            stmt.setLong(9, roleId);

            // 5. Execute insert statement
            connection.setAutoCommit(false);
            var affectedRows = stmt.executeUpdate();
            // more updates here ...
            connection.commit();
            connection.setAutoCommit(true);

            // 6. Check results and Get generated primary key
            if (affectedRows == 0) {
                throw new EntityPersistenceException("Creating user failed, no rows affected.");
            }
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setId(generatedKeys.getLong(1));
                    return entity;
                } else {
                    throw new EntityPersistenceException("Creating user failed, no ID obtained.");
                }
            }
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                throw new EntityPersistenceException("Error rolling back SQL query: " + FIND_ALL_USERS, ex);
            }
            log.error("Error creating connection to DB", ex);
            throw new EntityPersistenceException("Error executing SQL query: " + FIND_ALL_USERS, ex);
        }
    }

    @Override
    public User findById(Long id) {
        User user = new User();
        try (var stmt = connection.prepareStatement(FIND_USER_BY_ID)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                user = getUser(rs);
            }
        } catch (SQLException ex) {
            log.error("Error creating connection to DB", ex);
            throw new EntityPersistenceException("Error executing SQL query: " + FIND_USER_BY_ID, ex);
        }

        return user;
    }

    @Override
    public Collection<User> findAll() {
        try (var stmt = connection.prepareStatement(FIND_ALL_USERS)) {
            var rs = stmt.executeQuery();
            return toUsers(rs);
        } catch (SQLException ex) {
            log.error("Error creating connection to DB", ex);
            throw new EntityPersistenceException("Error executing SQL query: " + FIND_ALL_USERS, ex);
        }
    }

    @Override
    public void update(User entity) throws NoneExistingEntityException {
        try (var stmt = connection.prepareStatement(UPDATE_USER)) {
            stmt.setString(1, entity.getFirstName());
            stmt.setString(2, entity.getLastName());
            stmt.setString(3, entity.getPhoneNumber());
            stmt.setString(4, entity.getPassword());
            stmt.setString(5, entity.getRepeatPassword());

            Long roleId = 0L;
            if (entity.getRole().equals(Role.ADMINISTRATOR)) {
                roleId = 1L;
            } else if (entity.getRole().equals(Role.SELLER)) {
                roleId = 2L;
            } else if (entity.getRole().equals(Role.SITE_MANAGER)) {
                roleId = 3L;
            } else if (entity.getRole().equals(Role.DRIVER)) {
                roleId = 4L;
            } else {
                roleId = 5L;
            }
            stmt.setLong(6, roleId);
            stmt.setLong(7, entity.getId());

            connection.setAutoCommit(false);
            var affectedRows = stmt.executeUpdate();
            // more updates here ...
            connection.commit();
            connection.setAutoCommit(true);

            // 6. Check results and Get generated primary key
            if (affectedRows == 0) {
                throw new EntityPersistenceException("Updating user failed, no rows affected.");
            }


        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                throw new EntityPersistenceException("Error rolling back SQL query: " + UPDATE_USER, ex);
            }
            log.error("Error creating connection to DB", ex);
            throw new EntityPersistenceException("Error executing SQL query: " + UPDATE_USER, ex);
        }
    }

    @Override
    public void deleteById(Long id) throws NoneExistingEntityException {
        try (var stmt = connection.prepareStatement(DELETE_USER_BY_ID)) {
            stmt.setLong(1, id);
            connection.setAutoCommit(false);
            var affectedRows = stmt.executeUpdate();
            // more updates here ...
            connection.commit();
            connection.setAutoCommit(true);

            // 6. Check results and Get generated primary key
            if (affectedRows == 0) {
                throw new EntityPersistenceException("Deleting user failed, no rows affected.");
            }

        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                throw new EntityPersistenceException("Error rolling back SQL query: " + DELETE_USER_BY_ID, ex);
            }
            log.error("Error creating connection to DB", ex);
            throw new EntityPersistenceException("Error executing SQL query: " + DELETE_USER_BY_ID, ex);
        }
    }

    @Override
    public Driver getAvailableDriver(LocalDateTime pickUpDate, LocalDateTime dropOffDate) throws NoneAvailableEntityException {
        Collection<User> all = findAll();
        Driver availableDriver = null;

        for (User user : all) {
            if (user.getRole().equals(Role.DRIVER)) {
                Driver driver = findDriver(user.getId());
                driver.setId(user.getId());
                List<LocalDateTime> pickUpDates = driver.getPickUpDates();
                if (!pickUpDates.contains(pickUpDate)) {
                    availableDriver = driver;
                    break;
                }
            }
        }

        if (availableDriver == null) {
            throw new NoneAvailableEntityException("Sorry there is no available Drivers. Please change your order.");
        }

        return availableDriver;
    }

    @Override
    public User findUserByUsername(String username) {
        User user = new User();
        try (var stmt = connection.prepareStatement(FIND_USER_BY_USERNAME)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                user = getUser(rs);
            }
        } catch (SQLException ex) {
            log.error("Error creating connection to DB", ex);
            throw new EntityPersistenceException("Error executing SQL query: " + FIND_USER_BY_USERNAME, ex);
        }

        return user;
    }

    @Override
    public User findUserByEmail(String email) {
        User user = new User();
        try (var stmt = connection.prepareStatement(FIND_USER_BY_EMAIL)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                user = getUser(rs);
            }
        } catch (SQLException ex) {
            log.error("Error creating connection to DB", ex);
            throw new EntityPersistenceException("Error executing SQL query: " + FIND_USER_BY_EMAIL, ex);
        }

        return user;
    }

    public List<User> toUsers(ResultSet rs) throws SQLException {
        List<User> results = new ArrayList<>();

        while (rs.next()) {
            User user = getUser(rs);

            results.add(user);
        }
        return results;
    }

    private User getUser(ResultSet rs) throws SQLException {
        String registered_on = rs.getString("registered_on");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(registered_on, formatter);
        long roleId = rs.getLong("role_id");
        Role role;
        if (roleId == 1) {
            role = Role.ADMINISTRATOR;
        } else if (roleId == 2) {
            role = Role.SELLER;
        } else if (roleId == 3) {
            role = Role.SITE_MANAGER;
        } else if (roleId == 4) {
            role = Role.DRIVER;
        } else {
            role = Role.USER;
        }

        User user = new User(
                rs.getLong(1),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("email"),
                rs.getString("phone_number"),
                rs.getString("username"),
                rs.getString("password"),
                rs.getString("repeat_password"),
                localDateTime);
        user.setRole(role);

        List<Long> orderIds = new ArrayList<>();
        try (var stmt = connection.prepareStatement(SELECT_USERS_ORDERS)) {
            stmt.setLong(1, rs.getLong("user_id"));
            var resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                long order_id = resultSet.getLong("order_id");
                orderIds.add(order_id);
            }
        } catch (SQLException ex) {
            log.error("Error creating connection to DB", ex);
            throw new EntityPersistenceException("Error executing SQL query: " + SELECT_USERS_ORDERS, ex);
        }

        user.setOrdersIds(orderIds);
        return user;
    }

    public Driver findDriver(Long id) {
        Driver driver = new Driver();

        try (var stmt = connection.prepareStatement(SELECT_DRIVER)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                DriverStatus driverStatus = null;
                long driver_status = rs.getLong("driver_status");
                if (driver_status == 1) {
                    driverStatus = DriverStatus.AVAILABLE;
                } else {
                    driverStatus = DriverStatus.BUSY;
                }
                driver.setPricePerDay(rs.getDouble("price_per_day"));
                driver.setDriverStatus(driverStatus);


                List<LocalDateTime> pickUpDates = new ArrayList<>();
                try (var stmt2 = connection.prepareStatement(SELECT_DRIVER_PICK_UP_DATES)) {
                    stmt2.setLong(1, rs.getLong("driver_id"));
                    var resultSet = stmt2.executeQuery();
                    while (resultSet.next()) {
                        String pick_up_date = resultSet.getString("pick_up_date");
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
                        LocalDateTime pickUpDate = LocalDateTime.parse(pick_up_date, formatter);
                        pickUpDates.add(pickUpDate);
                    }
                } catch (SQLException ex) {
                    log.error("Error creating connection to DB", ex);
                    throw new EntityPersistenceException("Error executing SQL query: " + SELECT_DRIVER_PICK_UP_DATES, ex);
                }

                driver.setPickUpDates(pickUpDates);

                List<LocalDateTime> dropOffDates = new ArrayList<>();
                try (var stmt3 = connection.prepareStatement(SELECT_DRIVER_DROP_OFF_DATES)) {
                    stmt3.setLong(1, rs.getLong("driver_id"));
                    var resultSet = stmt3.executeQuery();
                    while (resultSet.next()) {
                        String drop_off_date = resultSet.getString("drop_off_date");
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
                        LocalDateTime dropOffDate = LocalDateTime.parse(drop_off_date, formatter);
                        dropOffDates.add(dropOffDate);
                    }
                } catch (SQLException ex) {
                    log.error("Error creating connection to DB", ex);
                    throw new EntityPersistenceException("Error executing SQL query: " + SELECT_DRIVER_DROP_OFF_DATES, ex);
                }

                driver.setDropOffDates(dropOffDates);


            }
        } catch (SQLException ex) {
            log.error("Error creating connection to DB", ex);
            throw new EntityPersistenceException("Error executing SQL query: " + SELECT_DRIVER, ex);
        }


        return driver;
    }

    @Override
    public void insertUsersOrders(User user, Order order) {
        try (var stmt = connection.prepareStatement(INSERT_USERS_ORDERS)) {
            stmt.setLong(1, user.getId());
            stmt.setLong(2, order.getId());

            connection.setAutoCommit(false);
            var affectedRows = stmt.executeUpdate();
            connection.commit();
            connection.setAutoCommit(true);

            if (affectedRows == 0) {
                throw new EntityPersistenceException("Updating users_orders failed, no rows affected.");
            }
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                throw new EntityPersistenceException("Error rolling back SQL query: " + INSERT_USERS_ORDERS, ex);
            }
            log.error("Error creating connection to DB", ex);
            throw new EntityPersistenceException("Error executing SQL query: " + INSERT_USERS_ORDERS, ex);
        }
    }
}