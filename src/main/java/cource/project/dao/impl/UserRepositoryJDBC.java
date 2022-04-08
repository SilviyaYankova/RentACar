package cource.project.dao.impl;

import cource.project.dao.UserRepository;
import cource.project.exeption.EntityPersistenceException;
import cource.project.exeption.NoneAvailableEntityException;
import cource.project.exeption.NoneExistingEntityException;
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

    public static final String SELECT_ALL_USERS = "select * from `users`;";
    public static final String SELECT_USER_BY_ID = "select * from `users` where user_id=?;";

    private Connection connection;

    protected UserRepositoryJdbc(Connection connection) {
        this.connection = connection;
    }

    @Override
    public User create(User entity) {
        return null;
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
            // 5. Transform ResultSet to Book
            return toUsers(rs);
        } catch (SQLException ex) {
            log.error("Error creating connection to DB", ex);
            throw new EntityPersistenceException("Error executing SQL query: " + SELECT_ALL_USERS, ex);
        }
    }

    @Override
    public void update(User entity) throws NoneExistingEntityException {

    }

    @Override
    public void deleteById(Long id) throws NoneExistingEntityException {

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
            results.add(new User(
                    rs.getLong(1),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("email"),
                    rs.getString("phone_number"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("repeat_password"),
                    localDateTime
            ));
        }
        return results;
    }
}