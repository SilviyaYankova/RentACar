package dao.file;

import dao.IdGenerator;
import dao.WorkerRepository;
import dao.impl.PersistableRepositoryFileImpl;
import model.Worker;

public class WorkerRepositoryFileImpl extends PersistableRepositoryFileImpl<Long, Worker> implements WorkerRepository {

    public WorkerRepositoryFileImpl(IdGenerator<Long> idGenerator, String dbFileName) {
        super(idGenerator, dbFileName);
    }
}
