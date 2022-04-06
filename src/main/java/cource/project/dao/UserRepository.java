package cource.project.dao;

import cource.project.exeption.NoneAvailableEntityException;
import cource.project.model.user.Driver;
import cource.project.model.user.User;

import java.time.LocalDateTime;

public interface UserRepository extends Repository<Long, User> {

    Driver getAvailableDriver(LocalDateTime pickUpDate, LocalDateTime dropOffDate) throws NoneAvailableEntityException;

    User findUserByUsername(String username);

    User findUserByEmail(String email);
}
