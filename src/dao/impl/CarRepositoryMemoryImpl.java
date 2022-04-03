package dao.impl;

import dao.CarRepository;
import dao.IdGenerator;
import model.Car;
import model.Order;

import java.util.List;

public class CarRepositoryMemoryImpl extends AbstractPersistableRepository<Long, Car> implements CarRepository {

    public CarRepositoryMemoryImpl(IdGenerator<Long> idGenerator) {
        super(idGenerator);
    }

    @Override
    public void load() {

    }

    @Override
    public void save() {

    }

}
