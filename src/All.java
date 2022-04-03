import exeption.*;

public class All {
    public static final String USERS_DB_FILENAME = "users.db";

    public static void main(String[] args) throws NoneExistingEntityException, InvalidEntityDataException {
//        DaoFactory daoFactory = new DaoFactoryMemoryImp();
//
//        CarRepository carRepository = daoFactory.createCarRepository();
//        UserRepository userRepository = daoFactory.createUserRepositoryFile(USERS_DB_FILENAME);
//        WorkerRepository workerRepository = daoFactory.createWorkerRepository();
//        OrderRepository orderRepository = daoFactory.createOrderRepository();
//        CommentRepository commentRepository = daoFactory.createCommentRepository();
//
//        WorkerService workerService = new WorkerServiceImpl(workerRepository, carRepository);
//        CarService carService = new CarServiceImpl(carRepository, workerService);
//
//        OrderService orderService = new OrderServiceImpl(orderRepository, userRepository, carService);
//        CommentService commentService = new CommentServiceImpl(commentRepository, carService, userRepository);
//        UserService userService = new UserServiceImpl(userRepository, workerService, orderService, carService, commentService);


// create initial users
//        userService.getAllUsers().forEach(System.out::println);
//
//        System.out.println();
//        System.out.println("after save");
//        userRepository.save();
//        userService.getAllUsers().forEach(System.out::println);
//        userService.loadData();
//        Collection<User> allUsers = userService.getAllUsers();
//        userService.getAllUsers().forEach(System.out::println);
//        System.out.println();
//        userRepository.save();
//
//        createInitialUsers(userService);
//        userRepository.save();
//        UserController userController = new UserController(userService);
//        userController.init();




// user validations
//        userValidations(userService);

// get user by id
//        getUserById(userService);

// user updates its profile and try to change its Role
//        updateUser(userService);

// delete user ->
//        deleteUser(userService);

// print all users after update and delete
//        userService.getAllUsers().forEach(System.out::println);









// create initial cars
//        createInitialCars(carService);

// car validations
//        carValidations(carService);

//        carService.getAllCars().forEach(System.out::println);
//        Car car = carService.getCarById(1L);
//        System.out.println(car);

// edit car
//        car.setBrand("Mercedes");
//        carService.editCar(car);
//        System.out.println(car);

// delete car
//        carService.deleteCar(car.getId());
//        carService.getAllCars().forEach(System.out::println);

// create initial workers
//        createInitialWorkers(userService);

// worker validations
//        workerValidations(userService);

//        workerService.getAllWorkers().forEach(System.out::println);

//        Worker worker = workerService.getWorkerById(1L);

// administrator or site manager edit worker
//        worker.setFirstName("worker");
//        workerService.editWorker(worker);
//        workerService.getAllWorkers().forEach(System.out::println);

// delete worker
//        workerService.deleteWorker(worker.getId());
//        workerService.deleteWorker(33L);

//        workerService.getAllWorkers().forEach(System.out::println);

// register newUser
//        User newUser = new User("Nikol", "Ilieva", "nikol@gmail.com", "0898233245",
//                "Nikol", "passworD86*", LocalDateTime.now());
//        try {
//            userService.registerUser(newUser);
//        } catch (InvalidEntityDataException e) {
//            printErrors(e);
//        }
//        userService.getAllUsers().forEach(System.out::println);

// rent  a car
//        User client = userService.getUserById(newUser.getId());
//        PickUpLocation pickUpLocation = VARNA;
//        LocalDateTime pickUpDate = LocalDateTime.of(2022, 3, 29, 22, 0);
//        LocalDateTime dropOffDate = LocalDateTime.of(2022, 3, 31, 22, 0);
//
//        Collection<Car> allCars = carService.getAllCars();
//        Car car1 = null;
//        Car car2 = null;
//        Car car3 = null;
//
//        if (!allCars.isEmpty()) {
//            List<Car> cars = new ArrayList<>(allCars);
//            car1 = cars.get(0);
//            car2 = cars.get(1);
//            car3 = cars.get(2);
//
//// rent validations
////            rentValidations(userService, client, car1);
//
//            try {
//                userService.bookCar(car1, client, true, pickUpLocation, pickUpDate, dropOffDate);
//                userService.bookCar(car2, client, false, pickUpLocation, pickUpDate, dropOffDate);
//// no drivers
////                userService.bookCar(car2, client, true, pickUpLocation, pickUpDate, dropOffDate);
//// car not available
////                userService.bookCar(car2, client, true, pickUpLocation, pickUpDate, dropOffDate);
//            } catch (InvalidEntityDataException e) {
//                printErrors(e);
//            } catch (NoneAvailableEntityException e) {
//                e.printStackTrace();
//            }
//
////            orderService.getAllOrders().forEach(System.out::println);
////
////            System.out.println(userService.getUserById(4L));
//        }

// seller approve booking
//        User seller = userService.getUserById(2L);
//        List<Order> pendingOrders = orderService.getAllPendingOrders();
//
//        userService.approveOrder(pendingOrders, seller);
//        orderService.getAllOrders().forEach(System.out::println);
//        System.out.println(seller);


// rent  a car
//        LocalDateTime pickUpDate2 = LocalDateTime.of(2022, 3, 29, 22, 0);
////        LocalDateTime dropOffDate2 = LocalDateTime.of(2022, 4, 1, 23, 46);
//        LocalDateTime dropOffDate2 = LocalDateTime.of(2022, 3, 31, 22, 0);
//        try {
//            try {
//                userService.bookCar(car3, client, true, pickUpLocation, pickUpDate2, dropOffDate2);
//            } catch (InvalidEntityDataException e) {
//                printErrors(e);
//            }
////                userService.bookCar(car3, client, false, pickUpLocation, pickUpDate, dropOffDate);
//        } catch (NoneAvailableEntityException e) {
//            e.printStackTrace();
//        }
//        orderService.getAllOrders().forEach(System.out::println);

// admin approve booking
//        User administrator = userService.getUserById(1L);
//        pendingOrders = orderService.getAllPendingOrders();
//        userService.approveOrder(pendingOrders, administrator);
//
//        orderService.getAllOrders().forEach(System.out::println);
//        System.out.println(administrator);
//        System.out.println(seller);
//        carService.getAllCars().forEach(System.out::println);


// return car -> change the date, hours and minutes of dropOfDate
//        System.out.println(car3);
//        orderService.getOrderById(3L);
//        System.out.println(orderService.getOrderById(3L));
//        System.out.println(userService.getUserById(5L));
//        System.out.println();
//        dropOffDate2 = LocalDateTime.of(2022, 3, 28, 17, 36);
//        LocalDateTime now = LocalDateTime.now();
//        if (dropOffDate2.getYear() == now.getYear()
//                && dropOffDate2.getMonth() == now.getMonth()
//                && dropOffDate2.getDayOfMonth() == now.getDayOfMonth()
//                && dropOffDate2.getHour() == now.getHour()
//                && dropOffDate2.getMinute() == now.getMinute()) {
//            userService.returnCar(car3);
//
//            System.out.println(car3);
//            System.out.println(orderService.getOrderById(3L));
//            System.out.println(userService.getUserById(5L));
//        }

// comment validations
//        commentValidations(commentService, client, car1);


// add comment - rate from 1 to 5
//        Comment comment = new Comment("BMW '330 i' is a really good car", 5, client, car1,
//                LocalDateTime.now(), LocalDateTime.now());
//
//        Comment comment2 = new Comment("BMW '330 i' is a really good car", 1, client, car1,
//                LocalDateTime.now(), LocalDateTime.now());
//
//        try {
//            commentService.addCarComment(comment);
//        } catch (InvalidEntityDataException e) {
//            printErrors(e);
//        }
//        try {
//            commentService.addCarComment(comment2);
//        } catch (InvalidEntityDataException e) {
//            printErrors(e);
//        }
//        System.out.println(car1);
//        System.out.println(client);
//        commentService.getAllComments().forEach(System.out::println);
//        System.out.println();

// edit comment
//        comment2.setRating(3);
//        commentService.editComment(comment2, car1);
//        System.out.println(car1);
//        System.out.println(client);
//        commentService.getAllComments().forEach(System.out::println);

// delete comment
//        commentService.deleteComment(comment2.getId());
//        System.out.println(client);
//        System.out.println(car1);
//        commentService.getAllComments().forEach(System.out::println);

// seller or admin sends the car to site manager
// just for test because dates needs to be changed first
//        Car returnedCar = carService.getCarById(1L);
//        returnedCar.setCarStatus(CarStatus.WAITING_FOR_CLEANING);
//        carService.editCar(returnedCar);
//
//        userService.sendCarsForCleaning(administrator);
//        System.out.println(returnedCar);

// site manager appoints a workers to clean the cars
//        List<Car> allCarsWaitingForCleaning = carService.getAllCarsWithStatus(CarStatus.CLEANING);
//        try {
//            userService.startCleaning(allCarsWaitingForCleaning);
//        } catch (NoneAvailableEntityException e) {
//            e.printStackTrace();
//        }
//        Worker C1 = returnedCar.getWorker();
//        System.out.println(returnedCar);
//        System.out.println(C1);
//        System.out.println(returnedCar);
//        System.out.println();
//        workerService.getAllWorkers().forEach(System.out::println);


// worker finish cleaning
//        workerService.finishCarCleaning(C1);
//        System.out.println(returnedCar);
//        System.out.println(C1);

// site manager returns cleaned cars to the shop
//        userService.returnCarToShop();
//        System.out.println(returnedCar);
//        System.out.println(C1);
//        carService.getAllCars().forEach(System.out::println);


//----------------------------------------------------- S T A T I S T I C S ---------------------------------------------------------------------

// USER statistics
//        newUser.getOrders().forEach(System.out::println);
//        newUser.getComments().forEach(System.out::println);

// Seller statistics
//        Seller seller1 = (Seller) userService.getUserById(2L);
//        System.out.println(seller1);
// seller's sells
//        seller.getOrders().forEach(System.out::println);
        // order client
//        seller1.getClientsHistory().forEach(System.out::println);


// Site manager statistics
//        SiteManager sm = (SiteManager) userService.getUserById(3L);
//        sm.getSellersHistory().forEach(System.out::println);
//        sm.getWorkers().forEach(System.out::println);
//        sm.getCarsHistory().forEach(System.out::println);

// Driver statistics
//        Driver driver = (Driver) userService.getUserById(4L);
//         status is busy because dropOfDate needs to be changed
//        System.out.println(driver);
//        driver.getOrders().forEach(System.out::println);
//        driver.getSellers().forEach(System.out::println);
//        driver.getUsers().forEach(System.out::println);

// admin statistics
//        Administrator admin = (Administrator) userService.getUserById(1L);
//        System.out.println(admin);
// get statistic by role
//        Collection<User> userByRole = userService.getUserByRole(Role.USER);
//        userByRole.forEach(System.out::println);

// all car statistic
//        carService.getAllCars().forEach(System.out::println);
// all comments statistic
//        commentService.getAllComments().forEach(System.out::println);
// all orders statistic
//        orderService.getAllOrders().forEach(System.out::println);
// administrator's sells
//        admin.getOrders().forEach(System.out::println);

// administrator's clients
//        admin.getClientHistory().forEach(System.out::println);

//// total profit
//        System.out.println(userService.getProfit());

// total profit for a period of time how
//        LocalDateTime from = LocalDateTime.of(2022, 3, 22, 22, 0);
//        LocalDateTime to = LocalDateTime.of(2022, 3, 23, 22, 0);
//        System.out.println(userService.getProfitForPeriod(from, LocalDateTime.now()));

    }

//    private static void commentValidations(CommentService commentService, User client, Car car1) {
//        Comment invalidComment = new Comment("short", 0, client, car1,
//                LocalDateTime.now(), LocalDateTime.now());
//        try {
//            commentService.addCarComment(invalidComment);
//        } catch (InvalidEntityDataException e) {
//            printErrors(e);
//        }
//    }

//    private static void rentValidations(UserService userService, User client, Car car1) throws NoneExistingEntityException {
//        LocalDateTime invalidPickUpDate = LocalDateTime.of(2022, 2, 22, 22, 0);
//        LocalDateTime invalidPropOffDate = LocalDateTime.of(2022, 2, 26, 23, 46);
//
//        try {
//            userService.bookCar(car1, client, false, VARNA, invalidPickUpDate, invalidPropOffDate);
//        } catch (InvalidEntityDataException e) {
//            printErrors(e);
//        } catch (NoneAvailableEntityException e) {
//            e.printStackTrace();
//        }
//    }

//    private static void workerValidations(UserService userService) throws NoneExistingEntityException {
//        Worker invalidWorker = new Worker("1", "1", "1");
//        try {
//            userService.addWorker(invalidWorker);
//        } catch (InvalidEntityDataException e) {
//            printErrors(e);
//        }
//    }

//    private static void createInitialWorkers(UserService userService) throws NoneExistingEntityException {
//        User byId = userService.getUserById(3L);
//        for (Worker MOCK_WORKER : MOCK_WORKERS) {
//            try {
//                userService.addWorker(MOCK_WORKER);
//            } catch (InvalidEntityDataException e) {
//                printErrors(e);
//            }
//        }
//    }
//
//    private static void carValidations(CarService carService) {
//        Car invalidCar = new Car("a", "i", "1", null, null, null,
//                64, 60, null, null, null, null,
//                0, null, 0, 0, 0, -8, null);
//        try {
//            carService.addCar(invalidCar);
//        } catch (InvalidEntityDataException e) {
//            printErrors(e);
//        }
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
//
//    private static void createInitialCars(CarService carService) {
//        for (Car MOCK_CAR : MOCK_CARS) {
//            try {
//                carService.addCar(MOCK_CAR);
//            } catch (InvalidEntityDataException e) {
//                printErrors(e);
//            }
//        }
//    }
//
//    private static void deleteUser(UserService userService) {
//        try {
//            User siteManager = userService.getUserById(7L);
//            userService.deleteUser(siteManager.getId());
//        } catch (NoneExistingEntityException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static void updateUser(UserService userService) throws NoneExistingEntityException {
//        User user = userService.getUserById(6L);
//        System.out.println(user);
//        user.setFirstName("user");
//        Role userOldRole = user.getRole();
//        user.setRole(Role.ADMINISTRATOR);
//        try {
//            userService.editUser(user, userOldRole);
//        } catch (NoPermissionException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println(user);
//    }
//
//    private static void getUserById(UserService userService) {
//        try {
//            User administrator = userService.getUserById(1L);
////            System.out.println(administrator);
//        } catch (NoneExistingEntityException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static void userValidations(UserService userService) {
//        User invalidUser = new User("z", "z", "nikolgmail.com", "z",
//                "IU", "password", "password", LocalDateTime.now());
//        try {
//            userService.registerUser(invalidUser);
//        } catch (InvalidEntityDataException ex) {
//            printErrors(ex);
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
}
