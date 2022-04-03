package dao.impl;

import dao.IdGenerator;
import dao.UserRepository;
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
    public void load() {
    }

    @Override
    public void save() {
    }
}
