package dao;

import dao.file.PersistableRepository;
import exeption.NoneAvailableEntityException;
import model.user.Driver;
import model.user.User;

import java.time.LocalDateTime;

public interface UserRepository extends PersistableRepository<Long, User> {

    Driver getAvailableDriver(LocalDateTime pickUpDate, LocalDateTime dropOffDate) throws NoneAvailableEntityException;

}
