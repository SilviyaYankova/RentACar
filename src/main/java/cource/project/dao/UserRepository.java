package cource.project.dao;

import cource.project.exeption.NoneAvailableEntityException;
import cource.project.model.Order;
import cource.project.model.user.Driver;
import cource.project.model.user.User;

import java.time.LocalDateTime;
import java.util.List;

public interface UserRepository extends Repository<Long, User> {

    Driver getAvailableDriver(LocalDateTime pickUpDate, LocalDateTime dropOffDate) throws NoneAvailableEntityException;

    User findUserByUsername(String username);

    User findUserByEmail(String email);

    Driver findDriver(Long id);

    void insertUsersOrders(User user, Order order);

    List<User> findAllSellers();

}
