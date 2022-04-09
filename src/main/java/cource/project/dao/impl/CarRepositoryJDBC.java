package cource.project.dao.impl;

import cource.project.dao.CarRepository;
import cource.project.dao.OrderRepository;
import cource.project.dao.WorkerRepository;
import cource.project.exeption.EntityPersistenceException;
import cource.project.exeption.NoneExistingEntityException;
import cource.project.model.Car;
import cource.project.model.Order;
import cource.project.model.Worker;
import cource.project.model.enums.*;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
public class CarRepositoryJDBC implements CarRepository {
    @SuppressWarnings("SqlResolve")
    public static final String FIND_ALL_CARS = "select * from `cars`;";
    @SuppressWarnings("SqlResolve")
    public static final String FIND_CAR_BY_ID = "select * from `cars` where car_id=?;";
    @SuppressWarnings("SqlResolve")
    public static final String INSERT_NEW_CAR = "insert into `cars` (`brand`, `model`, `year`, `picture_url`, " +
            "`color`, `car_type_id`, `doors`, `seats`, `conveniences`, `entertainments`, `drivetrain_id`, " +
            "`transmission_id`, `horse_powers`, `fuel_type_id`, `tank_volume`, `fuel_consumption`, `rating`, " +
            "`deposit`, `price_per_day`, `car_status_id`, `worker_id`) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

    @SuppressWarnings("SqlResolve")
    public static final String UPDATE_CAR = "update `cars` " +
            "set `brand`=?, `model`=?, `year`=?, `picture_url`=?, " +
            "`color`=?, `car_type_id`=?, `doors`=?, `seats`=?, `conveniences`=?, `entertainments`=?, `drivetrain_id`=?, " +
            "`transmission_id`=?, `horse_powers`=?, `fuel_type_id`=?, `tank_volume`=?, `fuel_consumption`=?, `rating`=?, " +
            "`deposit`=?, `price_per_day`=?, `car_status_id`=?, `worker_id`=? " +
            "where car_id=?;";
    @SuppressWarnings("SqlResolve")
    public static final String DELETE_CAR_BY_ID = "delete from `cars` where car_id=?;";
    @SuppressWarnings("SqlResolve")
    public static final String SELECT_CARS_ORDERS = "select order_id from cars_orders where car_id=?";
//    public static final String SELECT_ORDER = "select  orders where order_id=?";

    private Connection connection;
    private WorkerRepository workerRepository;

    public CarRepositoryJDBC(Connection connection, WorkerRepository workerRepository) {
        this.connection = connection;
        this.workerRepository = workerRepository;
    }

    @Override
    public Car create(Car car) {
        try (var stmt = connection.prepareStatement(INSERT_NEW_CAR, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, car.getBrand());
            stmt.setString(2, car.getModel());
            stmt.setString(3, car.getYear());
            stmt.setString(4, car.getPictureURL());
            stmt.setString(5, car.getColor());
            Long car_type_id = getCarTypeId(car);
            stmt.setLong(6, car_type_id);
            stmt.setInt(7, car.getDoors());
            stmt.setInt(8, car.getSeats());
            stmt.setString(9, car.getConveniences().toString());
            stmt.setString(10, car.getEntertainments().toString());
            Long drivetrain_id = getDrivetrainId(car.getDrivetrain());
            stmt.setLong(11, drivetrain_id);
            Long transmission_id = getTransmissionId(car.getTransmission());
            stmt.setLong(12, transmission_id);
            stmt.setInt(13, car.getHorsePowers());
            Long fuel_type_id = getFuelTypeId(car.getFuelType());
            stmt.setLong(14, fuel_type_id);
            stmt.setInt(15, car.getTankVolume());
            stmt.setDouble(16, car.getFuelConsumption());
            stmt.setDouble(17, car.getRating());
            stmt.setDouble(18, car.getDeposit());
            stmt.setDouble(19, car.getPricePerDay());
            Long car_status_id = getCarStatusId(car.getCarStatus());
            stmt.setLong(20, car_status_id);
            Worker worker = car.getWorker();
            if (worker != null) {
                stmt.setLong(21, worker.getId());
            } else {
                stmt.setLong(21, 0);
            }

            connection.setAutoCommit(false);
            var affectedRows = stmt.executeUpdate();
            // more updates here ...
            connection.commit();
            connection.setAutoCommit(true);
            if (affectedRows == 0) {
                throw new EntityPersistenceException("Creating car failed, no rows affected.");
            }
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    car.setId(generatedKeys.getLong(1));
                    return car;
                } else {
                    throw new EntityPersistenceException("Creating car failed, no ID obtained.");
                }
            }
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                throw new EntityPersistenceException("Error rolling back SQL query: " + INSERT_NEW_CAR, ex);
            }
            log.error("Error creating connection to DB", ex);
            throw new EntityPersistenceException("Error executing SQL query: " + INSERT_NEW_CAR, ex);
        }
    }

    @Override
    public Car findById(Long id) {
        Car car = new Car();
        try (var stmt = connection.prepareStatement(FIND_CAR_BY_ID)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                setCar(car, rs);
            }
        } catch (SQLException | NoneExistingEntityException ex) {
            log.error("Error creating connection to DB", ex);
            throw new EntityPersistenceException("Error executing SQL query: " + FIND_CAR_BY_ID, ex);
        }


        return car;
    }

    @Override
    public Collection<Car> findAll() {
        try (var stmt = connection.prepareStatement(FIND_ALL_CARS)) {
            var rs = stmt.executeQuery();
            return toCars(rs);
        } catch (SQLException | NoneExistingEntityException ex) {
            log.error("Error creating connection to DB", ex);
            throw new EntityPersistenceException("Error executing SQL query: " + FIND_ALL_CARS, ex);
        }
    }

    @Override
    public void update(Car car) throws NoneExistingEntityException {
        try (var stmt = connection.prepareStatement(UPDATE_CAR)) {
            stmt.setString(1, car.getBrand());
            stmt.setString(2, car.getModel());
            stmt.setString(3, car.getYear());
            stmt.setString(4, car.getPictureURL());
            stmt.setString(5, car.getColor());
            Long car_type_id = getCarTypeId(car);
            stmt.setLong(6, car_type_id);
            stmt.setInt(7, car.getDoors());
            stmt.setInt(8, car.getSeats());
            stmt.setString(9, car.getConveniences().toString());
            stmt.setString(10, car.getEntertainments().toString());
            Long drivetrain_id = getDrivetrainId(car.getDrivetrain());
            stmt.setLong(11, drivetrain_id);
            Long transmission_id = getTransmissionId(car.getTransmission());
            stmt.setLong(12, transmission_id);
            stmt.setInt(13, car.getHorsePowers());
            Long fuel_type_id = getFuelTypeId(car.getFuelType());
            stmt.setLong(14, fuel_type_id);
            stmt.setInt(15, car.getTankVolume());
            stmt.setDouble(16, car.getFuelConsumption());
            stmt.setDouble(17, car.getRating());
            stmt.setDouble(18, car.getDeposit());
            stmt.setDouble(19, car.getPricePerDay());
            Long car_status_id = getCarStatusId(car.getCarStatus());
            stmt.setLong(20, car_status_id);
            Worker worker = car.getWorker();
            if (worker != null) {
                stmt.setLong(21, worker.getId());
            } else {
                stmt.setLong(21, 0);
            }

            stmt.setLong(22, car.getId());

            connection.setAutoCommit(false);
            var affectedRows = stmt.executeUpdate();
            connection.commit();
            connection.setAutoCommit(true);

            if (affectedRows == 0) {
                throw new EntityPersistenceException("Updating car failed, no rows affected.");
            }
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                throw new EntityPersistenceException("Error rolling back SQL query: " + UPDATE_CAR, ex);
            }
            log.error("Error creating connection to DB", ex);
            throw new EntityPersistenceException("Error executing SQL query: " + UPDATE_CAR, ex);
        }
    }

    @Override
    public void deleteById(Long id) throws NoneExistingEntityException {
        try (var stmt = connection.prepareStatement(DELETE_CAR_BY_ID)) {
            stmt.setLong(1, id);
            connection.setAutoCommit(false);
            var affectedRows = stmt.executeUpdate();
            connection.commit();
            connection.setAutoCommit(true);

            if (affectedRows == 0) {
                throw new EntityPersistenceException("Deleting car failed, no rows affected.");
            }

        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                throw new EntityPersistenceException("Error rolling back SQL query: " + DELETE_CAR_BY_ID, ex);
            }
            log.error("Error creating connection to DB", ex);
            throw new EntityPersistenceException("Error executing SQL query: " + DELETE_CAR_BY_ID, ex);
        }
    }

    private Long getCarStatusId(CarStatus carStatus) {
        long id = 0;
        if (carStatus.equals(CarStatus.AVAILABLE)){
            id = 1;
        } else if (carStatus.equals(CarStatus.BUSY)){
            id = 2;
        } else if (carStatus.equals(CarStatus.WAITING)){
            id = 3;
        } else if (carStatus.equals(CarStatus.WAITING_FOR_CLEANING)){
            id = 4;
        } else if (carStatus.equals(CarStatus.CLEANING)){
            id = 5;
        } else if (carStatus.equals(CarStatus.START_CLEANING)){
            id = 6;
        } else if (carStatus.equals(CarStatus.FINISH_CLEANING)){
            id = 7;
        }

        return id;
    }

    private Long getFuelTypeId(FuelType fuelType) {
        long id = 0;
        if (fuelType.equals(FuelType.GASOLINE)) {
            id = 1;
        } else if (fuelType.equals(FuelType.DIESEL)) {
            id = 2;
        } else if (fuelType.equals(FuelType.BIODIESEL)) {
            id = 3;
        } else if (fuelType.equals(FuelType.ETHANOL)) {
            id = 4;
        } else if (fuelType.equals(FuelType.COMPRESSED_NATURAL_GAS)) {
            id = 5;
        } else if (fuelType.equals(FuelType.LIQUEFIED_PETROLEUM_GAS)) {
            id =6 ;
        } else if (fuelType.equals(FuelType.HYDROGEN)) {
            id = 7;
        }

        return id;
    }

    private Long getTransmissionId(Transmission transmission) {
        long id = 0;
        if (transmission.equals(Transmission.MANUAL)) {
            id = 1;
        } else if (transmission.equals(Transmission.AUTOMATIC)) {
            id = 2;
        } else if (transmission.equals(Transmission.CVT)) {
            id = 3;
        } else if (transmission.equals(Transmission.SEMI_AUTOMATIC)) {
            id = 4;
        } else if (transmission.equals(Transmission.DUAL_CLUTCH)) {
            id = 5;
        }

        return id;
    }

    private Long getDrivetrainId(Drivetrain drivetrain) {
        long id = 0;

        if (drivetrain.equals(Drivetrain.FRONT_WHEEL_DRIVE)) {
            id = 1;
        } else if (drivetrain.equals(Drivetrain.REAR_WHEEL_DRIVE)) {
            id = 2;
        } else if (drivetrain.equals(Drivetrain.FOUR_WHEEL_DRIVE)) {
            id = 3;
        } else if (drivetrain.equals(Drivetrain.ALL_WHEEL_DRIVE)) {
            id = 4;
        }
        return id;
    }

    private Long getCarTypeId(Car car) {
        long car_type_id = 0L;
        if (car.getCarType().equals(CarType.HATCHBACK)) {
            car_type_id = 1L;
        } else if (car.getCarType().equals(CarType.SEDAN)) {
            car_type_id = 2L;
        } else if (car.getCarType().equals(CarType.ESTATE)) {
            car_type_id = 3L;
        } else if (car.getCarType().equals(CarType.MPV)) {
            car_type_id = 4L;
        } else if (car.getCarType().equals(CarType.SUV)) {
            car_type_id = 5L;
        } else if (car.getCarType().equals(CarType.COUPE)) {
            car_type_id = 6L;
        } else if (car.getCarType().equals(CarType.SPORTS_CAR)) {
            car_type_id = 7L;
        } else if (car.getCarType().equals(CarType.CONVERTIBLE)) {
            car_type_id = 8L;
        }

        return car_type_id;
    }

    private CarStatus getCarStatusName(long car_status_id) {
        CarStatus carStatus = null;
        if (car_status_id == 1) {
            carStatus = CarStatus.AVAILABLE;
        } else if (car_status_id == 2) {
            carStatus = CarStatus.BUSY;
        } else if (car_status_id == 3) {
            carStatus = CarStatus.WAITING;
        } else if (car_status_id == 4) {
            carStatus = CarStatus.WAITING_FOR_CLEANING;
        } else if (car_status_id == 5) {
            carStatus = CarStatus.CLEANING;
        } else if (car_status_id == 6) {
            carStatus = CarStatus.START_CLEANING;
        } else if (car_status_id == 7) {
            carStatus = CarStatus.FINISH_CLEANING;
        }

        return carStatus;
    }

    private FuelType getFuelTypeName(long fuel_type_id) {
        FuelType fuelType = null;
        if (fuel_type_id == 1) {
            fuelType = FuelType.GASOLINE;
        } else if (fuel_type_id == 2) {
            fuelType = FuelType.DIESEL;
        } else if (fuel_type_id == 3) {
            fuelType = FuelType.BIODIESEL;
        } else if (fuel_type_id == 4) {
            fuelType = FuelType.ETHANOL;
        } else if (fuel_type_id == 5) {
            fuelType = FuelType.COMPRESSED_NATURAL_GAS;
        } else if (fuel_type_id == 6) {
            fuelType = FuelType.LIQUEFIED_PETROLEUM_GAS;
        } else if (fuel_type_id == 7) {
            fuelType = FuelType.HYDROGEN;
        }

        return fuelType;
    }

    private Transmission getTransmissionName(long transmission_id) {
        Transmission transmission = null;
        if (transmission_id == 1) {
            transmission = Transmission.MANUAL;
        } else if (transmission_id == 2) {
            transmission = Transmission.AUTOMATIC;
        } else if (transmission_id == 3) {
            transmission = Transmission.CVT;
        } else if (transmission_id == 4) {
            transmission = Transmission.SEMI_AUTOMATIC;
        } else if (transmission_id == 5) {
            transmission = Transmission.DUAL_CLUTCH;
        }
        return transmission;
    }

    private Drivetrain getDrivetrainName(long drivetrain_id) {
        Drivetrain drivetrain = null;

        if (drivetrain_id == 1) {
            drivetrain = Drivetrain.FRONT_WHEEL_DRIVE;
        } else if (drivetrain_id == 2) {
            drivetrain = Drivetrain.REAR_WHEEL_DRIVE;
        } else if (drivetrain_id == 3) {
            drivetrain = Drivetrain.FOUR_WHEEL_DRIVE;
        } else if (drivetrain_id == 4) {
            drivetrain = Drivetrain.ALL_WHEEL_DRIVE;
        }

        return drivetrain;
    }

    private CarType getCarTypeName(long car_type_id) {
        CarType carType = null;
        if (car_type_id == 1) {
            carType = CarType.HATCHBACK;
        } else if (car_type_id == 2) {
            carType = CarType.SEDAN;
        } else if (car_type_id == 3) {
            carType = CarType.ESTATE;
        } else if (car_type_id == 4) {
            carType = CarType.MPV;
        } else if (car_type_id == 5) {
            carType = CarType.SUV;
        } else if (car_type_id == 6) {
            carType = CarType.COUPE;
        } else if (car_type_id == 7) {
            carType = CarType.SPORTS_CAR;
        } else if (car_type_id == 8) {
            carType = CarType.CONVERTIBLE;
        }
        return carType;
    }

    private void setCar(Car car, ResultSet rs) throws SQLException, NoneExistingEntityException {
        long car_type_id = rs.getLong("car_type_id");
        CarType carType = getCarTypeName(car_type_id);

        long drivetrain_id = rs.getLong("drivetrain_id");
        Drivetrain drivetrain = getDrivetrainName(drivetrain_id);

        long transmission_id = rs.getLong("transmission_id");
        Transmission transmission = getTransmissionName(transmission_id);

        long fuel_type_id = rs.getLong("fuel_type_id");
        FuelType fuelType = getFuelTypeName(fuel_type_id);

        long car_status_id = rs.getLong("car_status_id");
        CarStatus carStatus = getCarStatusName(car_status_id);

        car.setId(rs.getLong("car_id"));
        car.setBrand(rs.getString("brand"));
        car.setModel(rs.getString("model"));
        car.setYear(rs.getString("year"));
        car.setPictureURL(rs.getString("picture_url"));
        car.setColor(rs.getString("color"));
        car.setDoors(rs.getInt("doors"));
        car.setSeats(rs.getInt("seats"));
        car.setConveniences(List.of(rs.getString("conveniences")));
        car.setEntertainments(List.of(rs.getString("entertainments")));
        car.setHorsePowers(rs.getInt("horse_powers"));
        car.setTankVolume(rs.getInt("tank_volume"));
        car.setFuelConsumption(rs.getInt("fuel_consumption"));
        car.setRating(rs.getDouble("rating"));
        car.setDeposit(rs.getDouble("deposit"));
        car.setPricePerDay(rs.getDouble("price_per_day"));

        car.setCarType(carType);
        car.setDrivetrain(drivetrain);
        car.setTransmission(transmission);
        car.setFuelType(fuelType);
        car.setCarStatus(carStatus);

        long worker_id = rs.getLong("worker_id");
        if (worker_id != 0) {
            Worker worker = workerRepository.findById(worker_id);
            car.setWorker(worker);
        }

        List<Long> ordersIds = new ArrayList<>();
        try (var stmt = connection.prepareStatement(SELECT_CARS_ORDERS)) {
            stmt.setLong(1, rs.getLong("car_id"));
            var resultSet = stmt.executeQuery();
            while (resultSet.next()){
                long order_id = resultSet.getLong("order_id");
                ordersIds.add(order_id);
            }
        } catch (SQLException  ex) {
            log.error("Error creating connection to DB", ex);
            throw new EntityPersistenceException("Error executing SQL query: " + SELECT_CARS_ORDERS, ex);
        }

        car.setOrders(ordersIds);
    }

    private List<Car> toCars(ResultSet rs) throws SQLException, NoneExistingEntityException {
        List<Car> results = new ArrayList<>();

        while (rs.next()) {
            long car_type_id = rs.getLong("car_type_id");
            CarType carType = getCarTypeName(car_type_id);

            long drivetrain_id = rs.getLong("drivetrain_id");
            Drivetrain drivetrain = getDrivetrainName(drivetrain_id);

            long transmission_id = rs.getLong("transmission_id");
            Transmission transmission = getTransmissionName(transmission_id);

            long fuel_type_id = rs.getLong("fuel_type_id");
            FuelType fuelType = getFuelTypeName(fuel_type_id);

            long car_status_id = rs.getLong("car_status_id");
            CarStatus carStatus = getCarStatusName(car_status_id);

            Car car = new Car();

            setCar(car, rs);

            results.add(car);

        }
        return results;
    }
}
