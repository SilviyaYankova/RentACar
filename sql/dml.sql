USE `rent-a-car`;

INSERT INTO `rent-a-car`.`car_status` (`id`, `car_status`)
VALUES ('1', 'AVAILABLE');
INSERT INTO `rent-a-car`.`car_status` (`id`, `car_status`)
VALUES ('2', 'BUSY');
INSERT INTO `rent-a-car`.`car_status` (`id`, `car_status`)
VALUES ('3', 'WAITING');
INSERT INTO `rent-a-car`.`car_status` (`id`, `car_status`)
VALUES ('4', 'WAITING_FOR_CLEANING');
INSERT INTO `rent-a-car`.`car_status` (`id`, `car_status`)
VALUES ('5', 'CLEANING');
INSERT INTO `rent-a-car`.`car_status` (`id`, `car_status`)
VALUES ('6', 'START_CLEANING');
INSERT INTO `rent-a-car`.`car_status` (`id`, `car_status`)
VALUES ('7', 'FINISH_CLEANING');

INSERT INTO `rent-a-car`.`car_type` (`id`, `car_type`)
VALUES ('1', 'HATCHBACK');
INSERT INTO `rent-a-car`.`car_type` (`id`, `car_type`)
VALUES ('2', 'SEDAN');
INSERT INTO `rent-a-car`.`car_type` (`id`, `car_type`)
VALUES ('3', 'ESTATE');
INSERT INTO `rent-a-car`.`car_type` (`id`, `car_type`)
VALUES ('4', 'MPV');
INSERT INTO `rent-a-car`.`car_type` (`id`, `car_type`)
VALUES ('5', 'SUV');
INSERT INTO `rent-a-car`.`car_type` (`id`, `car_type`)
VALUES ('6', 'COUPE');
INSERT INTO `rent-a-car`.`car_type` (`id`, `car_type`)
VALUES ('7', 'SPORTS_CAR');
INSERT INTO `rent-a-car`.`car_type` (`id`, `car_type`)
VALUES ('8', 'CONVERTIBLE');

INSERT INTO `rent-a-car`.`driver_status` (`id`, `driver_status`)
VALUES ('1', 'AVAILABLE');
INSERT INTO `rent-a-car`.`driver_status` (`id`, `driver_status`)
VALUES ('2', 'BUSY');

INSERT INTO `rent-a-car`.`drivetrain` (`id`, `drivetrain`)
VALUES ('1', 'FRONT_WHEEL_DRIVE');
INSERT INTO `rent-a-car`.`drivetrain` (`id`, `drivetrain`)
VALUES ('2', 'REAR_WHEEL_DRIVE');
INSERT INTO `rent-a-car`.`drivetrain` (`id`, `drivetrain`)
VALUES ('3', 'FOUR_WHEEL_DRIVE');
INSERT INTO `rent-a-car`.`drivetrain` (`id`, `drivetrain`)
VALUES ('4', 'ALL_WHEEL_DRIVE');

INSERT INTO `rent-a-car`.`fuel_type` (`id`, `fuel_type`)
VALUES ('1', 'GASOLINE');
INSERT INTO `rent-a-car`.`fuel_type` (`id`, `fuel_type`)
VALUES ('2', 'DIESEL');
INSERT INTO `rent-a-car`.`fuel_type` (`id`, `fuel_type`)
VALUES ('3', 'BIODIESEL');
INSERT INTO `rent-a-car`.`fuel_type` (`id`, `fuel_type`)
VALUES ('4', 'ETHANOL');
INSERT INTO `rent-a-car`.`fuel_type` (`id`, `fuel_type`)
VALUES ('5', 'COMPRESSED_NATURAL_GAS');
INSERT INTO `rent-a-car`.`fuel_type` (`id`, `fuel_type`)
VALUES ('6', 'LIQUEFIED_PETROLEUM_GAS');
INSERT INTO `rent-a-car`.`fuel_type` (`id`, `fuel_type`)
VALUES ('7', 'HYDROGEN');

INSERT INTO `rent-a-car`.`location` (`id`, `location`)
VALUES ('1', 'SOFIA');
INSERT INTO `rent-a-car`.`location` (`id`, `location`)
VALUES ('2', 'PLOVDIV');
INSERT INTO `rent-a-car`.`location` (`id`, `location`)
VALUES ('3', 'VARNA');
INSERT INTO `rent-a-car`.`location` (`id`, `location`)
VALUES ('4', 'BURGAS');
INSERT INTO `rent-a-car`.`location` (`id`, `location`)
VALUES ('5', 'RUSSE');
INSERT INTO `rent-a-car`.`location` (`id`, `location`)
VALUES ('6', 'STARA_ZAGORA');
INSERT INTO `rent-a-car`.`location` (`id`, `location`)
VALUES ('7', 'PLEVEN');
INSERT INTO `rent-a-car`.`location` (`id`, `location`)
VALUES ('8', 'SLIVEN');
INSERT INTO `rent-a-car`.`location` (`id`, `location`)
VALUES ('9', 'DOBRICH');
INSERT INTO `rent-a-car`.`location` (`id`, `location`)
VALUES ('10', 'SHUMEN');

INSERT INTO `rent-a-car`.`role` (`id`, `role`)
VALUES ('1', 'ADMINISTRATOR');
INSERT INTO `rent-a-car`.`role` (`id`, `role`)
VALUES ('2', 'SELLER');
INSERT INTO `rent-a-car`.`role` (`id`, `role`)
VALUES ('3', 'SITE_MANAGER');
INSERT INTO `rent-a-car`.`role` (`id`, `role`)
VALUES ('4', 'DRIVER');
INSERT INTO `rent-a-car`.`role` (`id`, `role`)
VALUES ('5', 'USER');

INSERT INTO `rent-a-car`.`worker_status` (`id`, `worker_status`)
VALUES ('1', 'AVAILABLE');
INSERT INTO `rent-a-car`.`worker_status` (`id`, `worker_status`)
VALUES ('2', 'BUSY');

INSERT INTO `rent-a-car`.`transmission` (`id`, `transmission`)
VALUES ('1', 'MANUAL');
INSERT INTO `rent-a-car`.`transmission` (`id`, `transmission`)
VALUES ('2', 'AUTOMATIC');
INSERT INTO `rent-a-car`.`transmission` (`id`, `transmission`)
VALUES ('3', 'CVT');
INSERT INTO `rent-a-car`.`transmission` (`id`, `transmission`)
VALUES ('4', 'SEMI_AUTOMATIC');
INSERT INTO `rent-a-car`.`transmission` (`id`, `transmission`)
VALUES ('5', 'DUAL_CLUTCH');


INSERT INTO `rent-a-car`.`users` (`user_id`, `first_name`, `last_name`, `email`, `phone_number`,
                                  `username`, `password`, `repeat_password`, `role_id`)
VALUES ('1', 'Silviya', 'Yankova', 'silviya@rentacar.com', '0898405658', 'silviya', '0123456789Ja*', '0123456789Ja*',
        '1');
INSERT INTO `rent-a-car`.`users` (`user_id`, `first_name`, `last_name`, `email`, `phone_number`,
                                  `username`, `password`, `repeat_password`, `role_id`)
VALUES ('2', 'Kalina', 'Dimitrova', 'kalina@rentacar.com', '0898405658', 'kalina', '0123456789Ja*', '0123456789Ja*',
        '2');
INSERT INTO `rent-a-car`.`users` (`user_id`, `first_name`, `last_name`, `email`, `phone_number`,
                                  `username`, `password`, `repeat_password`, `role_id`)
VALUES ('3', 'Stefan', 'Hristov', 'stefan@rentacar.com', '0898405658', 'stefan', '0123456789Ja*', '0123456789Ja*',
        '3');
INSERT INTO `rent-a-car`.`users` (`user_id`, `first_name`, `last_name`, `email`, `phone_number`,
                                  `username`, `password`, `repeat_password`, `role_id`)
VALUES ('4', 'Kristina', 'Dimitrova', 'kristina@rentacar.com', '0898405658', 'kristina', '0123456789Ja*',
        '0123456789Ja*', '4');
INSERT INTO `rent-a-car`.`users` (`user_id`, `first_name`, `last_name`, `email`, `phone_number`,
                                  `username`, `password`, `repeat_password`, `role_id`)
VALUES ('5', 'Petar', 'Arsov', 'petar@rentacar.com', '0898405658', 'petar', '0123456789Ja*', '0123456789Ja*', '4');
INSERT INTO `rent-a-car`.`users` (`user_id`, `first_name`, `last_name`, `email`, `phone_number`,
                                  `username`, `password`, `repeat_password`, `role_id`)
VALUES ('6', 'Martin', 'Yankov', 'martin@user.com', '0898405658', 'martin', '0123456789Ja*', '0123456789Ja*', '5');
INSERT INTO `rent-a-car`.`users` (`user_id`, `first_name`, `last_name`, `email`, `phone_number`,
                                  `username`, `password`, `repeat_password`, `role_id`)
VALUES ('7', 'Daniel', 'Petkov', 'daniel@user.com', '0898405658', 'daniel', '0123456789Ja*', '0123456789Ja*', '5');


INSERT INTO `rent-a-car`.`workers` (`worker_id`, `first_name`, `last_name`, `code`, `worker_status_id`)
VALUES ('1', 'Stanimir', 'Petrov', 'C1', '1');
INSERT INTO `rent-a-car`.`workers` (`worker_id`, `first_name`, `last_name`, `code`, `worker_status_id`)
VALUES ('2', 'Dimitar', 'Ivanov', 'C2', '1');
INSERT INTO `rent-a-car`.`workers` (`worker_id`, `first_name`, `last_name`, `code`, `worker_status_id`)
VALUES ('3', 'Petq', 'Ivanova', 'C3', '1');
INSERT INTO `rent-a-car`.`workers` (`worker_id`, `first_name`, `last_name`, `code`, `worker_status_id`)
VALUES ('4', 'Diana', 'Mihova', 'C4', '1');



INSERT INTO `rent-a-car`.`cars` (`car_id`, `brand`, `model`, `year`, `picture_url`, `color`, `car_type_id`, `doors`,
                                 `seats`,
                                 `conveniences`, `entertainments`, `drivetrain_id`, `transmission_id`, `horse_powers`,
                                 `fuel_type_id`, `tank_volume`,
                                 `fuel_consumption`, `rating`, `deposit`, `price_per_day`, `car_status_id`)
VALUES ('1', 'BMW', '330 i', '2021',
        'https://cdn.bmwblog.com/wp-content/uploads/2021/02/The-New-BMW-330i-Iconic-Edition-in-Mineral-White-metallic-1.jpg',
        'Mineral White Metallic', '1', '4', '4', 'Heated Seats, Keyless Start, Navigation System',
        'Bluetooth, HomeLink', '2', '2', '255', '1', '58', '7.9', '0.0', '150', '28.22',
        '1');

INSERT INTO `rent-a-car`.`cars` (`car_id`, `brand`, `model`, `year`, `picture_url`, `color`, `car_type_id`, `doors`,
                                 `seats`,
                                 `conveniences`, `entertainments`, `drivetrain_id`, `transmission_id`, `horse_powers`,
                                 `fuel_type_id`, `tank_volume`, `fuel_consumption`, `rating`, `deposit`, `price_per_day`,
                                 `car_status_id`)
VALUES ('2', 'Citroen', 'C3', '2020', 'https://www.netcarshow.com/Citroen-C3-2020-1024-01.jpg', 'Red', '2', '5',
        '5', 'Heated Seats, Keyless Start, Navigation System', 'Bluetooth, HomeLink', '1', '1',
        '83', '1', '45', '4.2', '0.0', '100', '20', '1');

INSERT INTO `rent-a-car`.`cars` (`car_id`, `brand`, `model`, `year`, `picture_url`, `color`, `car_type_id`, `doors`,
                                 `seats`,
                                 `conveniences`, `entertainments`, `drivetrain_id`, `transmission_id`, `horse_powers`,
                                 `fuel_type_id`, `tank_volume`, `fuel_consumption`, `rating`, `deposit`, `price_per_day`,
                                 `car_status_id`)
VALUES ('3', 'Peugeot', '308', '2019', 'https://www.auto-data.net/images/f99/Peugeot-3008-II-facelift-2020_3.jpg',
        'Blue', '2', '5', '5', 'Heated Seats, Keyless Start, Navigation System', 'Bluetooth, HomeLink',
        '1', '1', '90', '1', '45', '5', '0.0', '100', '20', '1');

INSERT INTO `rent-a-car`.`cars` (`car_id`, `brand`, `model`, `year`, `picture_url`, `color`, `car_type_id`, `doors`,
                                 `seats`,
                                 `conveniences`, `entertainments`, `drivetrain_id`, `transmission_id`, `horse_powers`,
                                 `fuel_type_id`, `tank_volume`, `fuel_consumption`, `rating`, `deposit`, `price_per_day`,
                                 `car_status_id`)
VALUES ('4', 'Dodge ', 'Durango SXT', '2020',
        'https://pictures.dealer.com/h/hughwhitecdjampcllc/0133/33fd4cd4038c9b5ee446d3741806dfc0x.jpg?impolicy=downsize&w=568',
        'Octane Red Pearlcoat', '5', '4', '5', 'Heated Seats, Keyless Start, Navigation System',
        'Bluetooth, HomeLink', '4', '2', '293', '1', '93', '18', '0.0', '200', '35', '1');

INSERT INTO `rent-a-car`.`cars` (`car_id`, `brand`, `model`, `year`, `picture_url`, `color`, `car_type_id`, `doors`,
                                 `seats`,
                                 `conveniences`, `entertainments`, `drivetrain_id`, `transmission_id`, `horse_powers`,
                                 `fuel_type_id`, `tank_volume`, `fuel_consumption`, `rating`, `deposit`, `price_per_day`,
                                 `car_status_id`)
VALUES ('5', 'Chevrolet ', 'Spark LS', '2022', 'https://www.ccarprice.com/products/Chevrolet_Spark_LS_2022_1.jpg',
        ' Caribben Blue', '1', '4', '4', 'Heated Seats, Keyless Start, Navigation System',
        'Bluetooth, HomeLink', '1', '1', '98', '2', '34', '7.6', '0.0', '160', '30', '1');

INSERT INTO `rent-a-car`.`user_drivers` (`driver_id`, `price_per_day`, `driver_status`)
VALUES ('4', '20', '1');
INSERT INTO `rent-a-car`.`user_drivers` (`driver_id`, `price_per_day`, `driver_status`)
VALUES ('5', '30', '1');
