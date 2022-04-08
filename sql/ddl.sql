CREATE SCHEMA `rent-a-car` DEFAULT CHARACTER SET utf8;

USE `rent-a-car`;

CREATE TABLE `rent-a-car`.`car_status`
(
    `id`         INT         NOT NULL AUTO_INCREMENT,
    `car_status` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `car_type`
(
    `id`       bigint      NOT NULL AUTO_INCREMENT,
    `car_type` varchar(45) NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `driver_status`
(
    `id`            bigint      NOT NULL AUTO_INCREMENT,
    `driver_status` varchar(45) NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `drivetrain`
(
    `id`         bigint      NOT NULL AUTO_INCREMENT,
    `drivetrain` varchar(45) NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `fuel_type`
(
    `id`        bigint      NOT NULL AUTO_INCREMENT,
    `fuel_type` varchar(45) NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `location`
(
    `id`       bigint      NOT NULL AUTO_INCREMENT,
    `location` varchar(45) NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `order_status`
(
    `id`           bigint      NOT NULL AUTO_INCREMENT,
    `order_status` varchar(45) NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `role`
(
    `id`   bigint      NOT NULL AUTO_INCREMENT,
    `role` varchar(45) NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `transmission`
(
    `id`           bigint      NOT NULL AUTO_INCREMENT,
    `transmission` varchar(45) NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `worker_status`
(
    `id`            bigint      NOT NULL AUTO_INCREMENT,
    `worker_status` varchar(45) NOT NULL,
    PRIMARY KEY (`id`)
);


CREATE TABLE `users`
(
    `user_id`         bigint      NOT NULL AUTO_INCREMENT,
    `first_name`      varchar(50) NOT NULL,
    `last_name`       varchar(50) NOT NULL,
    `email`           varchar(45) NOT NULL,
    `phone_number`    varchar(45) NOT NULL,
    `username`        varchar(45) NOT NULL,
    `password`        varchar(45) NOT NULL,
    `repeat_password` varchar(45) NOT NULL,
    `role_id`         bigint      NOT NULL,
    PRIMARY KEY (`user_id`),
    UNIQUE KEY `username_UNIQUE` (`username`),
    KEY `users_roles` (`role_id`)
);


CREATE TABLE `drop_off_dates`
(
    `id`            bigint   NOT NULL AUTO_INCREMENT,
    `drop_off_date` datetime NOT NULL,
    `car_id`        bigint   NOT NULL,
    `driver_id`     bigint DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `fk_drop_off_dates_cars` (`car_id`),
    KEY `fk_drop_off_dates_driver` (`driver_id`),
    CONSTRAINT `fk_drop_off_dates_cars` FOREIGN KEY (`car_id`) REFERENCES `cars` (`car_id`),
    CONSTRAINT `fk_drop_off_dates_driver` FOREIGN KEY (`driver_id`) REFERENCES `user_drivers` (`driver_id`)
);


CREATE TABLE `pick_up_dates`
(
    `id`           bigint   NOT NULL AUTO_INCREMENT,
    `pick_up_date` datetime NOT NULL,
    `car_id`       bigint   NOT NULL,
    `driver_id`    bigint DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `fk_pick_up_dates_cars` (`car_id`),
    KEY `fk_pick_up_dates_driver` (`driver_id`),
    CONSTRAINT `fk_pick_up_dates_cars` FOREIGN KEY (`car_id`) REFERENCES `cars` (`car_id`),
    CONSTRAINT `fk_pick_up_dates_driver` FOREIGN KEY (`driver_id`) REFERENCES `user_drivers` (`driver_id`)
);

CREATE TABLE `cars`
(
    `car_id`           bigint        NOT NULL AUTO_INCREMENT,
    `brand`            varchar(45)   NOT NULL,
    `model`            varchar(45)   NOT NULL,
    `year`             varchar(45)   NOT NULL,
    `picture_url`      varchar(245)  NOT NULL,
    `color`            varchar(45)   NOT NULL,
    `car_type_id`      bigint        NOT NULL,
    `doors`            int           NOT NULL,
    `seats`            int           NOT NULL,
    `conveniences`     varchar(245)  NOT NULL,
    `entertainments`   varchar(245)  NOT NULL,
    `drivetrain_id`    bigint        NOT NULL,
    `transmission_id`  bigint        NOT NULL,
    `horse_powers`     int           NOT NULL,
    `fuel_type_id`     bigint        NOT NULL,
    `tank_volume`      int           NOT NULL,
    `fuel_consumption` int           NOT NULL,
    `rating`           decimal(8, 2) NOT NULL,
    `deposit`          decimal(8, 2) NOT NULL,
    `price_per_day`    decimal(8, 2) NOT NULL,
    `car_status_id`    bigint        NOT NULL,
    `worker_id`        bigint DEFAULT NULL,
    PRIMARY KEY (`car_id`, `rating`),
    KEY `fk_cars_car_types` (`car_type_id`),
    KEY `fk_cars_drivetrains` (`drivetrain_id`),
    KEY `fk_cars_transmissions` (`transmission_id`),
    KEY `fk_cars_fuel_types` (`fuel_type_id`),
    KEY `fk_cars_car_status` (`car_status_id`),
    CONSTRAINT `fk_cars_car_status` FOREIGN KEY (`car_status_id`) REFERENCES `car_status` (`id`),
    CONSTRAINT `fk_cars_car_types` FOREIGN KEY (`car_type_id`) REFERENCES `car_type` (`id`),
    CONSTRAINT `fk_cars_drivetrains` FOREIGN KEY (`drivetrain_id`) REFERENCES `drivetrain` (`id`),
    CONSTRAINT `fk_cars_fuel_types` FOREIGN KEY (`fuel_type_id`) REFERENCES `fuel_type` (`id`),
    CONSTRAINT `fk_cars_transmissions` FOREIGN KEY (`transmission_id`) REFERENCES `transmission` (`id`)
);


CREATE TABLE `workers`
(
    `worker_id`        bigint      NOT NULL AUTO_INCREMENT,
    `first_name`       varchar(45) NOT NULL,
    `last_name`        varchar(45) NOT NULL,
    `code`             varchar(45) NOT NULL,
    `worker_status_id` bigint      NOT NULL,
    `current_car_id`   bigint DEFAULT NULL,
    PRIMARY KEY (`worker_id`),
    KEY `workers_current_car` (`current_car_id`),
    CONSTRAINT `workers_current_car` FOREIGN KEY (`current_car_id`) REFERENCES `cars` (`car_id`)
);

CREATE TABLE `comments`
(
    `comment_id`   bigint       NOT NULL AUTO_INCREMENT,
    `content`      varchar(255) NOT NULL,
    `rating`       int          NOT NULL,
    `user_id`      bigint       NOT NULL,
    `car_id`       bigint       NOT NULL,
    `posted_on`    datetime     NOT NULL,
    `modiefied_on` datetime DEFAULT NULL,
    PRIMARY KEY (`comment_id`),
    KEY `fk_comments_users` (`user_id`),
    KEY `fk_comments_cars` (`car_id`),
    CONSTRAINT `fk_comments_cars` FOREIGN KEY (`car_id`) REFERENCES `cars` (`car_id`),
    CONSTRAINT `fk_comments_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
);


CREATE TABLE `orders`
(
    `order_id`             bigint        NOT NULL AUTO_INCREMENT,
    `user_id`              bigint        NOT NULL,
    `driver_id`            bigint   DEFAULT NULL,
    `hire_driver`          tinyint       NOT NULL,
    `seller_id`            bigint        NOT NULL,
    `car_id`               bigint        NOT NULL,
    `order_status_id`      bigint        NOT NULL,
    `created_on`           datetime      NOT NULL,
    `modiefied_on`         datetime DEFAULT NULL,
    `pick_up_location_id`  bigint        NOT NULL,
    `drop_off_location_id` bigint        NOT NULL,
    `pick_up_date`         datetime      NOT NULL,
    `drop_off_date`        datetime      NOT NULL,
    `days`                 int           NOT NULL,
    `car_price_per_day`    decimal(8, 2) NOT NULL,
    `deposit`              decimal(8, 2) NOT NULL,
    `final_price`          decimal(8, 2) NOT NULL,
    PRIMARY KEY (`order_id`),
    KEY `orders_users` (`user_id`),
    KEY `orders_drivers` (`driver_id`),
    KEY `orders_sellers` (`seller_id`),
    KEY `orders_cars` (`car_id`),
    KEY `orders_order_status` (`order_status_id`),
    KEY `orders_pick_up_locations` (`pick_up_location_id`),
    KEY `orders_drop_off_locations` (`drop_off_location_id`),
    CONSTRAINT `orders_cars` FOREIGN KEY (`car_id`) REFERENCES `cars` (`car_id`),
    CONSTRAINT `orders_drivers` FOREIGN KEY (`driver_id`) REFERENCES `users` (`user_id`),
    CONSTRAINT `orders_drop_off_locations` FOREIGN KEY (`drop_off_location_id`) REFERENCES `location` (`id`),
    CONSTRAINT `orders_order_status` FOREIGN KEY (`order_status_id`) REFERENCES `order_status` (`id`),
    CONSTRAINT `orders_pick_up_locations` FOREIGN KEY (`pick_up_location_id`) REFERENCES `location` (`id`),
    CONSTRAINT `orders_sellers` FOREIGN KEY (`seller_id`) REFERENCES `users` (`user_id`),
    CONSTRAINT `orders_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
);


CREATE TABLE `cars_orders`
(
    `car_id`   bigint NOT NULL,
    `order_id` bigint NOT NULL,
    PRIMARY KEY (`car_id`, `order_id`),
    KEY `fk_cars_orders_orders` (`order_id`),
    CONSTRAINT `fk_cars_orders_cars` FOREIGN KEY (`car_id`) REFERENCES `cars` (`car_id`),
    CONSTRAINT `fk_cars_orders_orders` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`)
);

CREATE TABLE `comments_cars`
(
    `comment_id` bigint NOT NULL,
    `car_id`     bigint NOT NULL,
    PRIMARY KEY (`comment_id`, `car_id`),
    KEY `fk_comments_cars_cars` (`car_id`),
    CONSTRAINT `fk_comments_cars_cars` FOREIGN KEY (`car_id`) REFERENCES `cars` (`car_id`),
    CONSTRAINT `fk_comments_cars_comments` FOREIGN KEY (`comment_id`) REFERENCES `comments` (`comment_id`)
);


CREATE TABLE `comments_users`
(
    `comment_id` bigint NOT NULL,
    `user_id`    bigint NOT NULL,
    PRIMARY KEY (`comment_id`, `user_id`),
    KEY `fk_comments_users_users` (`user_id`),
    CONSTRAINT `fk_comments_users_comments` FOREIGN KEY (`comment_id`) REFERENCES `comments` (`comment_id`),
    CONSTRAINT `fk_comments_users_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
);


CREATE TABLE `users_orders`
(
    `user_id`  bigint NOT NULL,
    `order_id` bigint NOT NULL,
    PRIMARY KEY (`user_id`, `order_id`),
    KEY `fk_users_orders_orders` (`order_id`),
    CONSTRAINT `fk_users_orders_orders` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`),
    CONSTRAINT `fk_users_orders_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
);

CREATE TABLE `workers_cars`
(
    `worker_id` bigint NOT NULL,
    `car_id`    bigint NOT NULL,
    PRIMARY KEY (`worker_id`, `car_id`),
    KEY `fk_workers_cars_cars` (`car_id`),
    CONSTRAINT `fk_workers_cars_cars` FOREIGN KEY (`car_id`) REFERENCES `cars` (`car_id`),
    CONSTRAINT `fk_workers_cars_workers` FOREIGN KEY (`worker_id`) REFERENCES `workers` (`worker_id`)
);

CREATE TABLE `user_drivers`
(
    `driver_id`     bigint        NOT NULL,
    `price_per_day` decimal(8, 2) NOT NULL,
    `client_id`     bigint DEFAULT NULL,
    `seller_id`     bigint DEFAULT NULL,
    `driver_status` bigint        NOT NULL,
    PRIMARY KEY (`driver_id`),
    KEY `fk_users_users_drivers` (`driver_id`),
    KEY `fk_users_users_users_s` (`client_id`),
    KEY `fk_user_drivers` (`driver_status`),
    CONSTRAINT `fk_user_drivers` FOREIGN KEY (`driver_status`) REFERENCES `driver_status` (`id`),
    CONSTRAINT `fk_users_users_users_c` FOREIGN KEY (`client_id`) REFERENCES `users` (`user_id`),
    CONSTRAINT `fk_users_users_users_d` FOREIGN KEY (`driver_id`) REFERENCES `users` (`user_id`),
    CONSTRAINT `fk_users_users_users_s` FOREIGN KEY (`client_id`) REFERENCES `users` (`user_id`)
);


CREATE TABLE `user_site_managers_cars`
(
    `site_manager_id` bigint NOT NULL,
    `car_id`          bigint NOT NULL,
    PRIMARY KEY (`site_manager_id`, `car_id`),

    KEY `fk_site_manager_cars` (`car_id`),
    CONSTRAINT `fk_user_site_managers_user`
        FOREIGN KEY (`site_manager_id`)
            REFERENCES `users` (`user_id`),

    CONSTRAINT `fk_user_site_managers_cars`
        FOREIGN KEY (`car_id`)
            REFERENCES `cars` (`car_id`)
);