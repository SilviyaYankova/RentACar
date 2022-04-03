package dao.file;

import dao.IdGenerator;
import dao.UserRepository;
import dao.impl.PersistableRepositoryFileImpl;
import exeption.NoneAvailableEntityException;
import model.enums.DriverStatus;
import model.enums.Role;
import model.user.Driver;
import model.user.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserRepositoryFileImpl extends PersistableRepositoryFileImpl<Long, User> implements UserRepository {


    public UserRepositoryFileImpl(IdGenerator<Long> idGenerator, String dbFileName) {
        super(idGenerator, dbFileName);
    }


    @Override
    public Driver getAvailableDriver(LocalDateTime pickUpDate, LocalDateTime dropOffDate) throws NoneAvailableEntityException {
        Collection<User> all = findAll();
        Driver availableDriver = null;

        List<Driver> drivers = new ArrayList<>();
        for (User user : all) {
            if (user.getRole().equals(Role.DRIVER)) {
                Driver driver = (Driver) user;
                drivers.add(driver);
            }
        }


        for (Driver driver : drivers) {
        List<LocalDateTime> pickUpDates = driver.getPickUpDates();
            if (!pickUpDates.contains(pickUpDate)) {
                availableDriver = driver;
                break;
            }
        }




        if (availableDriver == null) {
            throw new NoneAvailableEntityException("Sorry there is no available Drivers. Please change your order.");
        }

        return availableDriver;
    }

}
