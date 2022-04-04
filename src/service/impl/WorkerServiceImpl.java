package service.impl;

import dao.CarRepository;
import dao.WorkerRepository;
import exeption.NoneAvailableEntityException;
import exeption.NoneExistingEntityException;
import model.Car;
import model.Worker;
import model.enums.CarStatus;
import model.enums.Role;
import model.enums.WorkerStatus;
import model.user.User;
import service.WorkerService;

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
            workerRepository.save();
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
            workerRepository.save();
        } catch (NoneExistingEntityException e) {
            System.out.println("Worker with ID='" + worker.getId() + "' does not exist.");
        }
    }

    @Override
    public void deleteWorker(Long id) {
        try {
            workerRepository.deleteById(id);
            workerRepository.save();
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
                    workerRepository.save();
                } catch (NoneExistingEntityException e) {
                    e.printStackTrace();
                }

                try {
                    carRepository.update(car);
                    carRepository.save();
                } catch (NoneExistingEntityException e) {
                    e.printStackTrace();
                }
            } catch (NoneAvailableEntityException e) {
                System.out.println("There is no available Worker.");
            }
        }
    }

    @Override
    public void finishCarCleaning(Worker worker) {
        worker.getCurrentCar().setCarStatus(CarStatus.FINISH_CLEANING);
        worker.setWorkerStatus(WorkerStatus.AVAILABLE);
        try {
            workerRepository.update(worker);
            workerRepository.save();
        } catch (NoneExistingEntityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadData() {
        workerRepository.load();
    }
}
