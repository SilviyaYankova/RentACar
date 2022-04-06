package cource.project.service;

import cource.project.exeption.InvalidEntityDataException;
import cource.project.exeption.NoneExistingEntityException;
import cource.project.model.enums.Role;
import cource.project.model.user.User;

import java.time.LocalDateTime;
import java.util.Collection;

public interface UserService {

    User registerUser(User user) throws InvalidEntityDataException;

    Collection<User> getAllUsers();

    User getUserById(Long id) throws NoneExistingEntityException;

    User getUserByUsername(String username);

    User getUserByEmail(String email);

    void editUser(User user) throws NoneExistingEntityException;

    void deleteUser(Long id) throws NoneExistingEntityException;

    Collection<User> getUserByRole(Role role);

    String getProfit();

    String getProfitForPeriod(LocalDateTime from, LocalDateTime to);

    User getUserByUsernameAndPassword(String username, String password);

}
