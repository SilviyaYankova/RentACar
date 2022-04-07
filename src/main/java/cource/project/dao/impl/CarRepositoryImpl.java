package cource.project.dao.impl;

import cource.project.dao.CarRepository;
import cource.project.model.Car;

import java.sql.Connection;

public class CarRepositoryImpl extends AbstractRepository<Long, Car> implements CarRepository {

    protected CarRepositoryImpl(Connection connection) {
        super(connection);
    }
}
