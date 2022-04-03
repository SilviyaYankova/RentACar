package dao.impl;

import dao.IdGenerator;
import dao.WorkerRepository;
import model.Worker;

public class WorkerRepositoryMemoryImpl extends AbstractPersistableRepository<Long, Worker> implements WorkerRepository {

    public WorkerRepositoryMemoryImpl(IdGenerator<Long> idGenerator) {
        super(idGenerator);
    }

    @Override
    public void load() {

    }

    @Override
    public void save() {

    }
}
