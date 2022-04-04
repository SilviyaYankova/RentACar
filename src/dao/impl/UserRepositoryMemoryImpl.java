package dao.impl;

import dao.IdGenerator;
import dao.UserRepository;
import exeption.InvalidEntityDataException;
import exeption.NoneAvailableEntityException;
import model.enums.Role;
import model.user.Driver;
import model.user.User;

import java.time.LocalDateTime;
import java.util.*;

public class UserRepositoryMemoryImpl extends AbstractPersistableRepository<Long, User> implements UserRepository {

    public UserRepositoryMemoryImpl(IdGenerator<Long> idGenerator) {
        super(idGenerator);
    }

    @Override
    public Driver getAvailableDriver(LocalDateTime pickUpDate, LocalDateTime dropOffDate) throws NoneAvailableEntityException {
        Collection<User> all = findAll();
        Driver availableDriver = null;

        for (User user : all) {
            if (user.getRole().equals(Role.DRIVER)) {
                Driver driver = (Driver) user;

                List<LocalDateTime> pickUpDates = driver.getPickUpDates();
                if (!pickUpDates.contains(pickUpDate)) {
                    availableDriver = (Driver) user;
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
    public User findUserByUsername(String username){
        Collection<User> allUsers = findAll();
        User user = null;
        for (User u : allUsers) {
            if (u.getUsername().equals(username)) {
                user = u;
                break;
            }
        }
        return user;
    }

    @Override
    public void load() {
    }

    @Override
    public void save() {
    }

    @Override
    public User findUserByEmail(String email) {
        Collection<User> allUsers = findAll();
        User user = null;
        for (User u : allUsers) {
            if (u.getEmail().equals(email)) {
                user = u;
                break;
            }
        }
        return user;
    }
}
