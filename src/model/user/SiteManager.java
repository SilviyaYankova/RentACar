package model.user;

import model.Car;
import model.Worker;
import model.enums.Role;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SiteManager extends User {
    List<Worker> workers;
    private List<Car> carsHistory;

    public SiteManager() {
        this.workers = new ArrayList<>();
        this.carsHistory = new ArrayList<>();
    }

    public SiteManager(String firstName, String lastName, String email, String phoneNumber, String username,
                       String password, String repeatPassword, LocalDateTime registeredOn) {
        super(firstName, lastName, email, phoneNumber, username, password, repeatPassword, registeredOn);
        this.setRole(Role.SITE_MANAGER);
        this.workers = new ArrayList<>();
        this.carsHistory = new ArrayList<>();
    }

    public List<Worker> getWorkers() {
        return workers;
    }

    public void setWorkers(List<Worker> workers) {
        this.workers = workers;
    }

    public List<Car> getCarsHistory() {
        return carsHistory;
    }

    public void setCarsHistory(List<Car> carsHistory) {
        this.carsHistory = carsHistory;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append(SiteManager.class.getSimpleName() + ": ")
                .append("id = '" + super.getId() + "' ")
                .append("firstName = '" + super.getFirstName() + "' ")
                .append("lastName = '" + super.getLastName() + "' ")
                .append("email = '" + super.getEmail() + "'")
                .append("phoneNumber = '" + super.getPhoneNumber() + "' ")
                .append("username = '" + super.getUsername() + "' ")
                .append(System.lineSeparator())
                .append("\t\t" + "registeredOn = '" + super.getRegisteredOn().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")) + "' ")
                .append("role = '" + super.getRole() + "' ")
                .append("workers = '" + workers.size() + "' ")
                .append("carsHistory = '" + carsHistory.size() + "' ")
                .toString();
    }
}
