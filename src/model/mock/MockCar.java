package model.mock;

import model.Car;
import model.Comment;
import model.Order;
import model.enums.*;
import model.user.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class MockCar {
    public static final Car[] MOCK_CARS;

    static {
           MOCK_CARS = new Car[]{
                new Car("BMW", "330 i", "2021",
                        "https://cdn.bmwblog.com/wp-content/uploads/2021/02/The-New-BMW-330i-Iconic-Edition-in-Mineral-White-metallic-1.jpg",
                        "Mineral White Metallic", CarType.HATCHBACK, 4, 4,
                        List.of("Heated Seats", "Keyless Start", "Navigation System"),
                        List.of("Bluetooth", "HomeLink"), Drivetrain.REAR_WHEEL_DRIVE, Transmission.AUTOMATIC,
                        255, Fuel.GASOLINE, 59, 7.9,
                        150, 28.22, CarStatus.AVAILABLE),
                new Car("Citroen", "C3", "2020", "https://www.netcarshow.com/Citroen-C3-2020-1024-01.jpg",
                        "Red", CarType.SEDAN, 5, 5,
                        List.of("Heated Seats", "Keyless Start", "Navigation System"),
                        List.of("Bluetooth", "HomeLink"), Drivetrain.FRONT_WHEEL_DRIVE, Transmission.MANUAL,
                        83, Fuel.GASOLINE, 45, 4.2,
                        100, 20, CarStatus.AVAILABLE),
                new Car("Peugeot", "308", "2019", "https://www.auto-data.net/images/f99/Peugeot-3008-II-facelift-2020_3.jpg",
                        "Blue", CarType.SEDAN, 5, 5,
                        List.of("Heated Seats", "Keyless Start", "Navigation System"),
                        List.of("Bluetooth", "HomeLink"), Drivetrain.FRONT_WHEEL_DRIVE, Transmission.MANUAL,
                        83, Fuel.GASOLINE, 45, 4.2,
                        100, 20, CarStatus.AVAILABLE),
        };
    }
}
