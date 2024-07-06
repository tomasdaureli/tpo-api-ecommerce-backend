-- Active: 1715090636063@@localhost@33060@ecommerce-app
-- Crear schema para la aplicacion
CREATE DATABASE `ecommerce-app`;

USE `ecommerce-app`;

-- Creacion de la tabla users
CREATE TABLE `users` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(255) NOT NULL,
    `last_name` VARCHAR(255) NOT NULL,
    `email` VARCHAR(255) NOT NULL,
    `password` VARCHAR(255) NOT NULL,
    `role` VARCHAR(255) DEFAULT 'COMPRADOR' CHECK (role IN ('VISITANTE', 'VENDEDOR', 'COMPRADOR'))
);

-- Creacion de la tabla discounts
CREATE TABLE `discounts` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `code` VARCHAR(255) NOT NULL,
    `percentage` DECIMAL(10, 2) NOT NULL,
    `expiry_date` DATE NOT NULL
);
-- Creacion de la tabla products
CREATE TABLE `products` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `product_name` VARCHAR(255) NOT NULL,
    `price` DECIMAL(10, 2) NOT NULL,
    `url_image` VARCHAR(255),
    `stock` INT,
    `description` TEXT,
    `active` BOOLEAN DEFAULT TRUE,
    `seller_id` BIGINT,
    `category` VARCHAR(255) CHECK (
        `category` IN (
            'FOOTWEAR',
            'CLOTHES',
            'ACCESORIES'
        )
    ),
    `subcategory` VARCHAR(255) CHECK (
        `subcategory` IN (
            'FASHION',
            'SPORTS',
            'RUNNING',
            'FOOTBALL',
            'SANDALS',
            'SHIRTS',
            'SOCKS',
            'CAPS',
            'BAGS',
            'HOODIES',
            'SHORTS',
            'TROUSERS',
            'BALLS'
        )
    ),
    CONSTRAINT `fk_seller_id` FOREIGN KEY (`seller_id`) REFERENCES `users` (`id`)
);

-- Creacion de la tabla buys
CREATE TABLE `buys` (
    `number` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `total` DECIMAL(19, 2) NOT NULL,
    `status` VARCHAR(255),
    `buyer_id` BIGINT NOT NULL,
    `discount_id` BIGINT,
    CONSTRAINT `fk_buys_buyer_id` FOREIGN KEY (`buyer_id`) REFERENCES `users` (`id`) ON DELETE CASCADE,
    CONSTRAINT `fk_discount_id` FOREIGN KEY (`discount_id`) REFERENCES `discounts` (`id`)
);

-- Creacion de la tabla item_products
CREATE TABLE `item_products` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `buy_number` BIGINT NOT NULL,
    `product_id` BIGINT ,
    `quantity` INT NOT NULL,
    CONSTRAINT `fk_item_products_buy_number` FOREIGN KEY (`buy_number`) REFERENCES `buys` (`number`) ON DELETE CASCADE,
    CONSTRAINT `fk_item_products_product_id` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`) ON DELETE SET NULL
);

