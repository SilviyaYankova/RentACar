package cource.project.dao.impl;

import cource.project.dao.CarRepository;
import cource.project.dao.WorkerRepository;
import cource.project.exeption.EntityPersistenceException;
import cource.project.exeption.NoneExistingEntityException;
import cource.project.model.Car;
import cource.project.model.Worker;
import cource.project.model.enums.*;
import cource.project.service.WorkerService;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
public class CarRepositoryJDBC implements CarRepository {
    @SuppressWarnings("SqlResolve")
    public static final String FIND_ALL_CARS = "select * from `cars`;";
    @SuppressWarnings("SqlResolve")
    public static final String FIND_CAR_BY_ID = "select * from `cars` where car_id=?;";


    private Connection connection;
    private WorkerRepository workerRepository;

    public CarRepositoryJDBC(Connection connection, WorkerRepository workerRepository) {
        this.connection = connection;
        this.workerRepository = workerRepository;
    }

    @Override
    public Car create(Car entity) {
        return null;
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
    public void update(Car entity) throws NoneExistingEntityException {

    }

    @Override
    public void deleteById(Long id) throws NoneExistingEntityException {

    }

    private List<Car> toCars(ResultSet rs) throws SQLException, NoneExistingEntityException {
        List<Car> results = new ArrayList<>();

        while (rs.next()) {
            long car_type_id = rs.getLong("car_type_id");
            CarType carType = getCarType(car_type_id);

            long drivetrain_id = rs.getLong("drivetrain_id");
            Drivetrain drivetrain = getDrivetrain(drivetrain_id);

            long transmission_id = rs.getLong("transmission_id");
            Transmission transmission = getTransmission(transmission_id);

            long fuel_type_id = rs.getLong("fuel_type_id");
            FuelType fuelType = getFuelType(fuel_type_id);

            long car_status_id = rs.getLong("car_status_id");
            CarStatus carStatus = getCarStatus(car_status_id);

            Car car = new Car();

            setCar(car, rs);

            results.add(car);

        }
        return results;
    }

    private Car setCar(Car car, ResultSet rs) throws SQLException, NoneExistingEntityException {
        long car_type_id = rs.getLong("car_type_id");
        CarType carType = getCarType(car_type_id);

        long drivetrain_id = rs.getLong("drivetrain_id");
        Drivetrain drivetrain = getDrivetrain(drivetrain_id);

        long transmission_id = rs.getLong("transmission_id");
        Transmission transmission = getTransmission(transmission_id);

        long fuel_type_id = rs.getLong("fuel_type_id");
        FuelType fuelType = getFuelType(fuel_type_id);

        long car_status_id = rs.getLong("car_status_id");
        CarStatus carStatus = getCarStatus(car_status_id);

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

        return car;
    }

    private CarStatus getCarStatus(long car_status_id) {
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

    private FuelType getFuelType(long fuel_type_id) {
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

    private Transmission getTransmission(long transmission_id) {
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

    private Drivetrain getDrivetrain(long drivetrain_id) {
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

    private CarType getCarType(long car_type_id) {
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
}
