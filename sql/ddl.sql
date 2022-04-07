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