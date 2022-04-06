package cource.project.model.user;

import cource.project.model.enums.Role;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Seller extends User {
    private List<User> clientsHistory;

    public Seller() {
        this.clientsHistory = new ArrayList<>();
    }

    public Seller(String firstName, String lastName, String email, String phoneNumber, String username,
                  String password, String repeatPassword, LocalDateTime registeredOn) {
        super(firstName, lastName, email, phoneNumber, username, password, repeatPassword, registeredOn);
        this.setRole(Role.SELLER);
        this.clientsHistory = new ArrayList<>();
    }


    public List<User> getClientsHistory() {
        return clientsHistory;
    }

    public void setClientsHistory(List<User> clientsHistory) {
        this.clientsHistory = clientsHistory;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append(Seller.class.getSimpleName() + ": ")
                .append("id = '" + super.getId() + "' ")
                .append("firstName = '" + super.getFirstName() + "' ")
                .append("lastName = '" + super.getLastName() + "' ")
                .append("email = '" + super.getEmail() + "' ")
                .append("phoneNumber = '" + super.getPhoneNumber() + "' ")
                .append("username = '" + super.getUsername() + "'")
                .append(System.lineSeparator())
                .append("\t\t" + "registeredOn = '" + super.getRegisteredOn().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")) + "' ")
                .append("role = " + super.getRole() + "' ")
                .append("sellsHistory = " + super.getOrders().size() + "' ")
                .append("clientsHistory = " + clientsHistory.size() + "' ")
                .toString();
    }

}
