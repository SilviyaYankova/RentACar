package dao.file;

import dao.CarRepository;
import dao.IdGenerator;
import dao.impl.PersistableRepositoryFileImpl;
import model.Car;

public class CarsRepositoryFileImpl extends PersistableRepositoryFileImpl<Long, Car> implements CarRepository {

    public CarsRepositoryFileImpl(IdGenerator<Long> idGenerator, String dbFileName) {
        super(idGenerator, dbFileName);
    }
}
