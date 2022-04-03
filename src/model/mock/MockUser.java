package model.mock;

import model.Order;
import model.Worker;
import model.user.*;

import java.time.LocalDateTime;
import java.util.List;

public class MockUser {
    public static final User[] MOCK_USERS;

    static {

        MOCK_USERS = new User[]{
                new Administrator("Silviya", "Yankova", "silviya@admin.com", "0898405658",
                        "silviya", "8101308797Ja*","8101308797Ja*", LocalDateTime.now()),
                new Seller("kalina", "Dimitrova", "kalina@rentacar.com", "0896203256",
                        "Kalina", "8101308797Ja*","8101308797Ja*", LocalDateTime.now()),
                new SiteManager("Stefan", "Hristov", "stefan@rentacar.com", "0897895425",
                        "stefan", "8101308797Ja*", "8101308797Ja*",LocalDateTime.now()),
                new Driver("Kristina", "Driver", "DriverKristina@rentacar.com", "0893658941",
                        "kristina", "8101308797Ja*","8101308797Ja*", LocalDateTime.now(), 150),
                new Driver("Petar", "Arsov", "DriverPetar@rentacar.com", "0893658941",
                        "petar", "8101308797Ja*", "8101308797Ja*",LocalDateTime.now(), 100),
                new User("Martin", "Vasilev", "martin@gmail.com", "0898227245",
                        "martin", "8101308797Ja*","8101308797Ja*", LocalDateTime.now()),
                new User("Daniel", "Arsov", "daniel@gmail.com", "0898227245",
                        "daniel", "8101308797Ja*","8101308797Ja*", LocalDateTime.now()),
        };
    }
}
