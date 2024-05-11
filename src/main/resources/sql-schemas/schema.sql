-- Crear schema para la aplicacion
CREATE DATABASE `ecommerce-app`;

USE `ecommerce-app`;

-- Creacion de la tabla products
CREATE TABLE `products` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `product_name` VARCHAR(255) NOT NULL,
    `price` DECIMAL(10, 2) NOT NULL,
    `url_image` VARCHAR(255),
    `quantity` INT,
    `description` TEXT
);