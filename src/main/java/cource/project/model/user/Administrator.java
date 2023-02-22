package cource.project.model.user;

import cource.project.model.enums.Role;

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

    public Administrator(Long id, String firstName, String lastName, String email, String phoneNumber, String username,
                         String password, String repeatPassword) {
        super(id, firstName, lastName, email, phoneNumber, username, password, repeatPassword);
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
                .append(System.lineSeparator())
                .append("role = " + super.getRole() + "' ")
                .append("sellsHistory = " + super.getOrders().size() + "' ")
                .append("clientHistory = " + clientHistory.size() + "' ")
                .toString();
    }
}
