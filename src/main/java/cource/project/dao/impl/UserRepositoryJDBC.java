package cource.project.dao.impl;

import cource.project.dao.UserRepository;
import cource.project.exeption.EntityPersistenceException;
import cource.project.exeption.NoneAvailableEntityException;
import cource.project.exeption.NoneExistingEntityException;
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
public class UserRepositoryJdbc implements UserRepository {

    @SuppressWarnings("SqlResolve")
    public static final String SELECT_ALL_USERS = "select * from `users`;";
    @SuppressWarnings("SqlResolve")
    public static final String SELECT_USER_BY_ID = "select * from `users` where user_id=?;";
    @SuppressWarnings("SqlResolve")
    public static final String INSERT_NEW_USER = "insert into `users` (`first_name`, `last_name`, `email`, `phone_number`, `username`, `password`, `repeat_password`, `registered_on`, `role_id`) values (?, ?,?, ?, ?, ?, ?, ?, ?);";
    @SuppressWarnings("SqlResolve")
    public static final String UPDATE_USER = "update `users` " +
            "set `first_name`=?, `last_name`=?, `phone_number`=?, `password`=?, `repeat_password`=?, `role_id`=? " +
            "where user_id=?;";
    @SuppressWarnings("SqlResolve")
    public static final String DELETE_USER_BY_ID = "delete from `users` where user_id=?;";


    private Connection connection;

    protected UserRepositoryJdbc(Connection connection) {
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
                throw new EntityPersistenceException("Error rolling back SQL query: " + SELECT_ALL_USERS, ex);
            }
            log.error("Error creating connection to DB", ex);
            throw new EntityPersistenceException("Error executing SQL query: " + SELECT_ALL_USERS, ex);
        }
    }

    @Override
    public User findById(Long id) {
        User user = new User();
        try (var stmt = connection.prepareStatement(SELECT_USER_BY_ID)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {

                user.setId(rs.getLong("user_id"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setEmail(rs.getString("email"));
                user.setPhoneNumber(rs.getString("phone_number"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setRepeatPassword(rs.getString("repeat_password"));

                String registered_on = rs.getString("registered_on");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
                LocalDateTime localDateTime = LocalDateTime.parse(registered_on, formatter);
                user.setRegisteredOn(localDateTime);

                Long roleId = rs.getLong("role_id");
                if (roleId == 1) {
                    user.setRole(Role.ADMINISTRATOR);
                } else if (roleId == 2) {
                    user.setRole(Role.SELLER);
                } else if (roleId == 3) {
                    user.setRole(Role.SITE_MANAGER);
                } else if (roleId == 4) {
                    user.setRole(Role.DRIVER);
                } else {
                    user.setRole(Role.USER);
                }
            }
        } catch (SQLException ex) {
            log.error("Error creating connection to DB", ex);
            throw new EntityPersistenceException("Error executing SQL query: " + SELECT_USER_BY_ID, ex);
        }

        return user;
    }

    @Override
    public Collection<User> findAll() {
        try (var stmt = connection.prepareStatement(SELECT_ALL_USERS)) {
            // 4. Set params and execute SQL query
            var rs = stmt.executeQuery();
            // 5. Transform ResultSet to User
            return toUsers(rs);
        } catch (SQLException ex) {
            log.error("Error creating connection to DB", ex);
            throw new EntityPersistenceException("Error executing SQL query: " + SELECT_ALL_USERS, ex);
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
    public long count() {
        return 0;
    }

    @Override
    public Driver getAvailableDriver(LocalDateTime pickUpDate, LocalDateTime dropOffDate) throws NoneAvailableEntityException {
        return null;
    }

    @Override
    public User findUserByUsername(String username) {
        return null;
    }

    @Override
    public User findUserByEmail(String email) {
        return null;
    }


    public List<User> toUsers(ResultSet rs) throws SQLException {
        List<User> results = new ArrayList<>();

        while (rs.next()) {
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

            results.add(user);
        }
        return results;
    }
}