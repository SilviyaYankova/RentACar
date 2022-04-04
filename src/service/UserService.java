package service;

import exeption.InvalidEntityDataException;
import exeption.NoPermissionException;
import exeption.NoneAvailableEntityException;
import exeption.NoneExistingEntityException;
import model.Car;
import model.Order;
import model.Worker;
import model.enums.Role;
import model.user.User;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface UserService {

    User registerUser(User user) throws InvalidEntityDataException;

    Collection<User> getAllUsers();

    User getUserById(Long id) throws NoneExistingEntityException;

    User getUserByUsername(String username);

    User getUserByEmail(String email);

    void editUser(User user) throws NoneExistingEntityException, NoPermissionException;

    void deleteUser(Long id) throws NoneExistingEntityException;

    void sendCarsForCleaning(User admin);

    void startCleaning(List<Car> allCarsWaitingForCleaning) throws NoneAvailableEntityException;

    void returnCarToShop();

    Collection<User> getUserByRole(Role role);

    String getProfit();

    String getProfitForPeriod(LocalDateTime from, LocalDateTime to);

    void loadData();

    User getUserByUsernameAndPassword(String username, String password);

}
