package cource.project.model;

import cource.project.model.enums.WorkerStatus;
import cource.project.model.user.BaseEntity;

import java.util.ArrayList;
import java.util.List;

public class Worker extends BaseEntity {
    private WorkerStatus workerStatus;
    private String firstName;
    private String lastName;
    private String code;
    private Car currentCar;
    private List<Car> carHistory;

    public Worker() {
    }

    public Worker(Long id, String firstName, String lastName, String code) {
      super(id);
        this.workerStatus = WorkerStatus.AVAILABLE;
        this.firstName = firstName;
        this.lastName = lastName;
        this.code = code;
        this.carHistory = new ArrayList<>();
    }

    public Car getCurrentCar() {
        return currentCar;
    }

    public void setCurrentCar(Car currentCar) {
        this.currentCar = currentCar;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<Car> getCarHistory() {
        return carHistory;
    }

    public void setCarHistory(List<Car> carHistory) {
        this.carHistory = carHistory;
    }

    public WorkerStatus getWorkerStatus() {
        return workerStatus;
    }

    public void setWorkerStatus(WorkerStatus workerStatus) {
        this.workerStatus = workerStatus;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder()
                .append(Worker.class.getSimpleName() + ": ")
                .append("id = '" + super.getId() + "' ")
                .append("firstName = '" + firstName + "' ")
                .append("lastName = '" + lastName + "' ")
                .append("code = '" + code + "' ")
                .append("workerStatus = '" + workerStatus + "' ");

        if (currentCar != null) {
            sb.append("currentCarId = '" + currentCar.getId() + "' ");
        }

        if (carHistory.size() > 0) {
            sb.append("carHistory = " + carHistory.size() + "' ");

        }
        return sb.toString();
    }
}
