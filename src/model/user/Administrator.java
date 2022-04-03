package model.user;

import model.enums.Role;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Administrator extends User {
    private List<User> clientHistory;

    public Administrator() {
        this.clientHistory = new ArrayList<>();
    }

    public Administrator(String firstName, String lastName, String email, String phoneNumber, String username,
                         String password, String repeatPassword, LocalDateTime registeredOn) {
        super(firstName, lastName, email, phoneNumber, username, password, repeatPassword, registeredOn);
        this.setRole(Role.ADMINISTRATOR);
        this.clientHistory = new ArrayList<>();
    }

    public List<User> getClientHistory() {
        return clientHistory;
    }

    public void setClientHistory(List<User> clientHistory) {
        this.clientHistory = clientHistory;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append(Administrator.class.getSimpleName() + ": ")
                .append("id = '" + super.getId() + "' ")
                .append("firstName = '" + super.getFirstName() + "' ")
                .append("lastName = '" + super.getLastName() + "' ")
                .append("email = '" + super.getEmail() + "' ")
                .append("phoneNumber = '" + super.getPhoneNumber() + "' ")
                .append("username = '" + super.getUsername() + "'")
                .append("password = '" + super.getPassword() + String.format("%n"))
                .append("\t\t" + "registeredOn = '" + super.getRegisteredOn().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")) + "' ")
                .append("role = " + super.getRole() + "' ")
                .append("sellsHistory = " + super.getOrders().size() + "' ")
                .append("clientHistory = " + clientHistory.size() + "' ")
                .toString();
    }
}
