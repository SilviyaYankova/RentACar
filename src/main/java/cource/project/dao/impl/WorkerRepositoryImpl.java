package cource.project.dao.impl;

import cource.project.dao.WorkerRepository;
import cource.project.exeption.EntityPersistenceException;
import cource.project.exeption.NoneExistingEntityException;
import cource.project.model.Worker;
import cource.project.model.enums.WorkerStatus;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
public class WorkerRepositoryImpl implements WorkerRepository {
    @SuppressWarnings("SqlResolve")
    public static final String FIND_ALL_WORKERS = "select * from `workers`;";
    @SuppressWarnings("SqlResolve")
    public static final String FIND_WORKER_BY_ID = "select * from `workers` where worker_id=?;";

    protected final Connection connection;

    protected WorkerRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Worker create(Worker entity) {
        return null;
    }

    @Override
    public Worker findById(Long id) {
        Worker worker = new Worker();
        try (var stmt = connection.prepareStatement(FIND_WORKER_BY_ID)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {

                long worker_status_id = rs.getLong("worker_status_id");
                WorkerStatus workerStatus = null;
                if (worker_status_id == 1) {
                    workerStatus = WorkerStatus.AVAILABLE;
                } else if (worker_status_id == 2) {
                    workerStatus = WorkerStatus.BUSY;
                }
                worker.setId(rs.getLong("worker_id"));
                worker.setFirstName(rs.getString("first_name"));
                worker.setLastName(rs.getString("last_name"));
                worker.setCode(rs.getString("code"));
                worker.setWorkerStatus(workerStatus);
                worker.setCarHistory(new ArrayList<>());
            }
        } catch (SQLException ex) {
            log.error("Error creating connection to DB", ex);
            throw new EntityPersistenceException("Error executing SQL query: " + FIND_WORKER_BY_ID, ex);
        }
        return worker;
    }

    @Override
    public Collection<Worker> findAll() {
        try (var stmt = connection.prepareStatement(FIND_ALL_WORKERS)) {
            var rs = stmt.executeQuery();
            return toWorkers(rs);
        } catch (SQLException ex) {
            log.error("Error creating connection to DB", ex);
            throw new EntityPersistenceException("Error executing SQL query: " + FIND_ALL_WORKERS, ex);
        }
    }

    @Override
    public void update(Worker entity) throws NoneExistingEntityException {

    }

    @Override
    public void deleteById(Long id) throws NoneExistingEntityException {

    }

    public List<Worker> toWorkers(ResultSet rs) throws SQLException {
        List<Worker> results = new ArrayList<>();
        while (rs.next()) {
            Worker worker = new Worker();
            long worker_status_id = rs.getLong("worker_status_id");
            WorkerStatus workerStatus = null;
            if (worker_status_id == 1) {
                workerStatus = WorkerStatus.AVAILABLE;
            } else if (worker_status_id == 2) {
                workerStatus = WorkerStatus.BUSY;
            }
            worker.setId(rs.getLong("worker_id"));
            worker.setFirstName(rs.getString("first_name"));
            worker.setLastName(rs.getString("last_name"));
            worker.setCode(rs.getString("code"));
            worker.setWorkerStatus(workerStatus);
            worker.setCarHistory(new ArrayList<>());
            results.add(worker);
        }
        return results;
    }
}
