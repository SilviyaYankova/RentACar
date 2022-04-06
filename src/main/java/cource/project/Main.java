package cource.project;

import cource.project.Controller.HomeController;
import cource.project.exeption.*;

public class Main {
    public static void main(String[] args) throws InvalidEntityDataException, NoneAvailableEntityException, NoneExistingEntityException {

//        UserService userService = new UserServiceImpl();

// create initial users
//        createInitialUsers(userService);
//        createInitialCars(carService, orderService);
//        createInitialOrders(orderService);
//        createInitialWorkers(workerService);

//        HomeController homeController = new HomeController(userService, carService, orderService, userRepository, workerService, commentService);
//        homeController.init();


    }

//    private static void createInitialOrders(OrderService orderService) throws NoneExistingEntityException, NoneAvailableEntityException, InvalidEntityDataException {
//        for (Order mockOrder : MOCK_ORDERS) {
//            orderService.addOrder(mockOrder, mockOrder.getCar());
//        }
//    }
//
//    private static void createInitialWorkers(WorkerService workerService) throws NoneExistingEntityException {
//
//        for (Worker MOCK_WORKER : MOCK_WORKERS) {
//            workerService.addWorker(MOCK_WORKER);
//        }
//    }
//
//    private static void createInitialCars(CarService carService, OrderService orderService) {
//        for (Car MOCK_CAR : MOCK_CARS) {
//            try {
//                carService.addCar(MOCK_CAR);
//            } catch (InvalidEntityDataException e) {
//                printErrors(e);
//            }
//        }
//    }
//
//    private static void printErrors(InvalidEntityDataException ex) {
//        StringBuilder sb = new StringBuilder(ex.getMessage());
//
//        if (ex.getCause() instanceof ConstraintViolationException) {
//            sb.append(", invalid fields:\n");
//            var violations = ((ConstraintViolationException) ex.getCause()).getFieldViolations();
//            sb.append(violations.stream()
//                    .map(v -> String.format(" - %s.%s [%s] - %s",
//                            v.getType().substring(v.getType().lastIndexOf(".") + 1),
//                            v.getField(),
//                            v.getInvalidValue(),
//                            v.getErrorMessage())
//                    ).collect(Collectors.joining("\n"))
//            );
//        }
//        System.out.println(sb);
//    }
//
//    private static void createInitialUsers(UserService userService) {
//        for (User MOCK_USER : MOCK_USERS) {
//            try {
//                userService.registerUser(MOCK_USER);
//            } catch (InvalidEntityDataException e) {
//                printErrors(e);
//            }
//        }
//    }
}
