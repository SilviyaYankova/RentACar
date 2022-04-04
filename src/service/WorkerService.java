package service;

import exeption.NoPermissionException;
import exeption.NoneAvailableEntityException;
import exeption.NoneExistingEntityException;
import model.Car;
import model.Worker;
import model.user.User;

import java.util.Collection;
import java.util.List;

public interface WorkerService {

    void addWorker(Worker worker);

    Collection<Worker> getAllWorkers();

    Worker getWorkerById(Long id) throws NoneExistingEntityException;

    void editWorker(Worker worker ) throws NoneExistingEntityException ;

    void deleteWorker(Long id) throws NoneExistingEntityException;

    Worker getAllAvailableWorker() throws NoneAvailableEntityException;

    void cleanCar(List<Car> allCarsWaitingForCleaning) throws NoneAvailableEntityException;

    void finishCarCleaning(Worker worker);

    void loadData();
}
