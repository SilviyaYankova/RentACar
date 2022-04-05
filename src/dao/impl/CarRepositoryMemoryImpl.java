package dao.impl;

import dao.CarRepository;
import dao.IdGenerator;
import model.Car;
import model.Order;
import model.enums.CarStatus;

import java.util.List;
import java.util.stream.Collectors;

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
