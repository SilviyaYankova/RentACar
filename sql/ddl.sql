CREATE SCHEMA `rent-a-car` DEFAULT CHARACTER SET utf8;

USE `rent-a-car`;

CREATE TABLE `rent-a-car`.`users`
(
    `id`              INT         NOT NULL AUTO_INCREMENT,
    `first_name`      VARCHAR(50) NOT NULL,
    `last_name`       VARCHAR(50) NOT NULL,
    `email`           VARCHAR(45) NOT NULL,
    `phone_number`    VARCHAR(45) NOT NULL,
    `username`        VARCHAR(45) NOT NULL,
    `password`        VARCHAR(45) NOT NULL,
    `repeat_password` VARCHAR(45) NOT NULL,
    `role`            VARCHAR(45) NOT NULL,
    `orders`          VARCHAR(45) NULL,
    `comments`        VARCHAR(45) NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `username_UNIQUE` (`username` ASC) VISIBLE
);


CREATE TABLE `rent-a-car`.`workers`
(
    `id`            INT         NOT NULL AUTO_INCREMENT,
    `first_name`    VARCHAR(45) NOT NULL,
    `last_name`     VARCHAR(45) NOT NULL,
    `code`          VARCHAR(45) NOT NULL,
    `worker_status` VARCHAR(45) NOT NULL,
    `current_car`   VARCHAR(45) NULL,
    `car_history`   VARCHAR(45) NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `code_UNIQUE` (`code` ASC) VISIBLE
);


CREATE TABLE `rent-a-car`.`cars`
(
    `id`               INT           NOT NULL AUTO_INCREMENT,
    `brand`            VARCHAR(45)   NOT NULL,
    `model`            VARCHAR(45)   NOT NULL,
    `year`             VARCHAR(45)   NOT NULL,
    `picture_url`      VARCHAR(245)   NOT NULL,
    `color`            VARCHAR(45)   NOT NULL,
    `car_type`         VARCHAR(45)   NOT NULL,
    `doors`            INT           NOT NULL,
    `seats`            INT           NOT NULL,
    `conveniences`     VARCHAR(245)   NOT NULL,
    `entertainments`   VARCHAR(245)   NOT NULL,
    `drivetrain`       VARCHAR(45)   NOT NULL,
    `transmission`     VARCHAR(45)   NOT NULL,
    `horse_powers`     INT           NOT NULL,
    `fuel_type`        VARCHAR(45)   NOT NULL,
    `tank_volume`      INT           NOT NULL,
    `fuel_consumption` INT           NOT NULL,
    `rating`           DECIMAL(8, 2) NULL,
    `comments`         VARCHAR(45)   NULL,
    `deposit`          DECIMAL(8, 2) NOT NULL,
    `price_per_day`    DECIMAL(8, 2) NOT NULL,
    `car_status`       VARCHAR(45)   NOT NULL,
    `worker`           VARCHAR(45)   NULL,
    `orders`           VARCHAR(45)   NULL,
    `pick_up_dates`    VARCHAR(45)   NULL,
    `drop_off_dates`   VARCHAR(45)   NULL,
    PRIMARY KEY (`id`)
);
