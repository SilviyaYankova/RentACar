//package cource.project.model.mock;
//
//import cource.project.model.Car;
//import cource.project.model.Order;
//import cource.project.model.enums.*;
//import cource.project.model.user.User;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//public class MockOrders {
//    public static final Order[] MOCK_ORDERS;
//
//    static {
//
//        User martin = new User("Martin", "Vasilev", "martin@gmail.com", "0898227245",
//                "martin", "8101308797Ja*", "8101308797Ja*", LocalDateTime.now());
//        martin.setId(6L);
//        Car car1 = new Car("BMW", "330 i", "2021",
//                "https://cdn.bmwblog.com/wp-content/uploads/2021/02/The-New-BMW-330i-Iconic-Edition-in-Mineral-White-metallic-1.jpg",
//                "Mineral White Metallic", CarType.HATCHBACK, 4, 4,
//                List.of("Heated Seats", "Keyless Start", "Navigation System"),
//                List.of("Bluetooth", "HomeLink"), Drivetrain.REAR_WHEEL_DRIVE, Transmission.AUTOMATIC,
//                255, FuelType.GASOLINE, 59, 7.9,
//                150, 28.22, CarStatus.AVAILABLE);
//        car1.setId(1L);
//        Car car2 = new Car("Citroen", "C3", "2020", "https://www.netcarshow.com/Citroen-C3-2020-1024-01.jpg",
//                "Red", CarType.SEDAN, 5, 5,
//                List.of("Heated Seats", "Keyless Start", "Navigation System"),
//                List.of("Bluetooth", "HomeLink"), Drivetrain.FRONT_WHEEL_DRIVE, Transmission.MANUAL,
//                83, FuelType.GASOLINE, 45, 4.2,
//                100, 20, CarStatus.AVAILABLE);
//        car2.setId(2L);
//        Car car3 = new Car("Peugeot", "308", "2019", "https://www.auto-data.net/images/f99/Peugeot-3008-II-facelift-2020_3.jpg",
//                "Blue", CarType.SEDAN, 5, 5,
//                List.of("Heated Seats", "Keyless Start", "Navigation System"),
//                List.of("Bluetooth", "HomeLink"), Drivetrain.FRONT_WHEEL_DRIVE, Transmission.MANUAL,
//                83, FuelType.GASOLINE, 45, 4.2,
//                100, 20, CarStatus.AVAILABLE);
//        car3.setId(3L);
//        LocalDateTime pickUpDate = LocalDateTime.of(2022, 4, 3, 10, 0);
//        LocalDateTime dropOffDate = LocalDateTime.of(2022, 4, 4, 10, 0);
//
//        MOCK_ORDERS = new Order[]{
//                new Order(martin, true, car1, Location.VARNA, Location.BURGAS, pickUpDate, dropOffDate),
//                new Order(martin, true, car2, Location.VARNA, Location.BURGAS, pickUpDate, dropOffDate),
//                new Order(martin, true, car3, Location.VARNA, Location.BURGAS, pickUpDate, dropOffDate)
//        };
//    }
//}
