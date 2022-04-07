package cource.project.dao.impl;

import cource.project.dao.UserRepository;
import cource.project.exeption.NoneAvailableEntityException;
import cource.project.exeption.NoneExistingEntityException;
import cource.project.model.user.Driver;
import cource.project.model.user.User;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.Collection;

public class UserRepositoryImpl extends AbstractRepository<Long, User> implements UserRepository {

    protected UserRepositoryImpl(Connection connection) {
        super(connection);
    }

    @Override
    public User findById(Long id) {
        return null;
    }

    @Override
    public Collection<User> findAll() {
        return null;
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
}
