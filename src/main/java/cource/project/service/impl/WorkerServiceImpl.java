package cource.project.service.impl;

import cource.project.dao.CarRepository;
import cource.project.dao.WorkerRepository;
import cource.project.exeption.NoneAvailableEntityException;
import cource.project.exeption.NoneExistingEntityException;
import cource.project.model.Car;
import cource.project.model.Worker;
import cource.project.model.enums.CarStatus;
import cource.project.model.enums.Role;
import cource.project.model.enums.WorkerStatus;
import cource.project.model.user.User;
import cource.project.service.WorkerService;

import java.util.Collection;
import java.util.List;

public class WorkerServiceImpl implements WorkerService {

    private final WorkerRepository workerRepository;
    private final CarRepository carRepository;

    public WorkerServiceImpl(WorkerRepository workerRepository, CarRepository carRepository) {
        this.workerRepository = workerRepository;
        this.carRepository = carRepository;
    }

    @Override
    public void addWorker(Worker worker) {
            workerRepository.create(worker);
    }

    @Override
    public Collection<Worker> getAllWorkers() {
        return workerRepository.findAll();
    }

    @Override
    public Worker getWorkerById(Long id) throws NoneExistingEntityException {
        Worker worker = workerRepository.findById(id);
        if (id == null) {
            throw new NoneExistingEntityException("Worker with ID " + id + " does not exist.");
        }

        return workerRepository.findById(id);
    }

    @Override
    public void editWorker(Worker worker) {
        try {
            workerRepository.update(worker);
        } catch (NoneExistingEntityException e) {
            System.out.println("Worker with ID='" + worker.getId() + "' does not exist.");
        }
    }

    @Override
    public void deleteWorker(Long id) {
        try {
            workerRepository.deleteById(id);
        } catch (NoneExistingEntityException e) {
            System.out.println("Worker with ID='" + id + "' does not exist.");
        }
    }

    @Override
    public Worker getAllAvailableWorker() throws NoneAvailableEntityException {
        Collection<Worker> all = workerRepository.findAll();
        Worker availableWorker = null;

        for (Worker worker : all) {
            if (worker.getWorkerStatus().equals(WorkerStatus.AVAILABLE)) {
                availableWorker = worker;
                break;
            }
        }

        if (availableWorker == null) {
            throw new NoneAvailableEntityException("Sorry there is no available Workers. Check again again later.");
        }

        return availableWorker;
    }

    @Override
    public void cleanCar(List<Car> allCarsWaitingForCleaning) {
        for (Car car : allCarsWaitingForCleaning) {
            Worker allAvailableWorker = null;
            try {
                allAvailableWorker = getAllAvailableWorker();
                allAvailableWorker.setWorkerStatus(WorkerStatus.BUSY);
                allAvailableWorker.setCurrentCar(car);
                allAvailableWorker.getCarHistory().add(car);
                car.setWorker(allAvailableWorker);
                car.setCarStatus(CarStatus.START_CLEANING);
                try {
                    workerRepository.update(allAvailableWorker);
                } catch (NoneExistingEntityException e) {
                    e.printStackTrace();
                }

                try {
                    carRepository.update(car);
                } catch (NoneExistingEntityException e) {
                    e.printStackTrace();
                }
            } catch (NoneAvailableEntityException e) {
                System.out.println("There is no available Worker.");
            }
        }
    }

    @Override
    public void finishCarCleaning(Car car) throws NoneExistingEntityException {
        Worker worker = car.getWorker();
        worker.setWorkerStatus(WorkerStatus.AVAILABLE);
        worker.setCurrentCar(null);
        car.setCarStatus(CarStatus.FINISH_CLEANING);

        workerRepository.update(worker);
        carRepository.update(car);
    }

}
