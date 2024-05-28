-- Active: 1715090636063@@localhost@33060@ecommerce-app
-- Crear schema para la aplicacion
CREATE DATABASE `ecommerce-app`;

USE `ecommerce-app`;

-- Creacion de la tabla products
CREATE TABLE `products` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `product_name` VARCHAR(255) NOT NULL,
    `price` DECIMAL(10, 2) NOT NULL,
    `url_image` VARCHAR(255),
    `stock` INT,
    `description` TEXT
);

ALTER TABLE `products` ADD COLUMN `category` VARCHAR(255);

ALTER TABLE `products` ADD COLUMN `subcategory` VARCHAR(255);

-- Creacion de la tabla users
CREATE TABLE `users` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(255) NOT NULL,
    `last_name` VARCHAR(255) NOT NULL,
    `email` VARCHAR(255) NOT NULL,
    `password` VARCHAR(255) NOT NULL
);

-- Creacion de la tabla buys
CREATE TABLE `buys` (
    `number` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `total` DECIMAL(19, 2) NOT NULL,
    `status` VARCHAR(255),
    `buyer_id` BIGINT NOT NULL,
    FOREIGN KEY (`buyer`) REFERENCES `users` (`id`)
);

-- Creacion de la tabla item_products
CREATE TABLE `item_products` (
    `buy_number` BIGINT NOT NULL,
    `product_id` BIGINT NOT NULL,
    `quantity` INT NOT NULL,
    FOREIGN KEY (`buy_number`) REFERENCES `buys` (`number`),
    FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
);