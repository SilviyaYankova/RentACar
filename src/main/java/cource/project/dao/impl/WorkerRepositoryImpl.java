package cource.project.dao.impl;

import cource.project.dao.WorkerRepository;
import cource.project.model.Worker;

import java.sql.Connection;

public class WorkerRepositoryImpl extends AbstractRepository<Long, Worker> implements WorkerRepository {


    protected WorkerRepositoryImpl(Connection connection) {
        super(connection);
    }
}
