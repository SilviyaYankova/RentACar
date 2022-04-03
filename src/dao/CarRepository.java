package dao;

import dao.file.PersistableRepository;
import model.Car;
import model.Order;

import java.util.List;

public interface CarRepository extends PersistableRepository<Long, Car> {

}
