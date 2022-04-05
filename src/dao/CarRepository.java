package dao;

import dao.file.PersistableRepository;
import model.Car;
import model.Order;
import model.enums.CarStatus;

import java.util.List;
import java.util.stream.Collectors;

public interface CarRepository extends PersistableRepository<Long, Car> {
}
