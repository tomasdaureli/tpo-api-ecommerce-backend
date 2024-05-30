-- Inserción de datos en la tabla `products`
INSERT INTO `products` (`product_name`, `price`, `url_image`, `stock`, `description`, `category`, `subcategory`)
VALUES 
('Running Shoes', 120.00, 'https://example.com/runningshoes.jpg', 100, 'Zapatos diseñados para correr, con soporte y amortiguación.', 'FOOTWEAR', 'RUNNING'),
('Football Jersey', 85.00, 'https://example.com/footballjersey.jpg', 50, 'Jersey oficial del equipo nacional, material transpirable.', 'CLOTHES', 'FOOTBALL'),
('Sport Cap', 25.00, 'https://example.com/sportcap.jpg', 75, 'Gorra deportiva ajustable con protección UV.', 'ACCESORIES', 'CAPS'),
('Hiking Sandals', 60.00, 'https://example.com/hikingsandals.jpg', 80, 'Sandalias resistentes para senderismo y actividades al aire libre.', 'FOOTWEAR', 'SANDALS'),
('Graphic T-Shirt', 29.99, 'https://example.com/graphicshirt.jpg', 150, 'Camiseta con gráfico moderno, 100% algodón.', 'CLOTHES', 'SHIRTS'),
('Sport Socks', 12.00, 'https://example.com/sportsocks.jpg', 200, 'Calcetines deportivos con soporte de arco y tejido transpirable.', 'ACCESORIES', 'SOCKS'),
('Leather Bag', 150.00, 'https://example.com/leatherbag.jpg', 40, 'Bolso de cuero elegante y funcional con varios compartimentos.', 'ACCESORIES', 'BAGS'),
('Casual Hoodie', 55.00, 'https://example.com/casualhoodie.jpg', 60, 'Hoodie casual suave y cómodo, ideal para el día a día.', 'CLOTHES', 'HOODIES'),
('Athletic Shorts', 35.00, 'https://example.com/athleticshorts.jpg', 90, 'Shorts atléticos ligeros para entrenamiento y deportes.', 'CLOTHES', 'SHORTS'),
('Outdoor Football', 40.00, 'https://example.com/outdoorfootball.jpg', 110, 'Balón de fútbol robusto para juegos en exteriores.', 'ACCESORIES', 'BALLS');

-- Inserción de datos en la tabla `users`
INSERT INTO `users` (`name`, `last_name`, `email`, `password`)
VALUES 
('Juan', 'Pérez', 'juan.perez@example.com', 'password123'),
('Ana', 'Lopez', 'ana.lopez@example.com', 'securepassword'),
('Carlos', 'Martinez', 'carlos.martinez@example.com', 'mypassword');

-- Inserción de datos en la tabla `buys`
INSERT INTO `buys` (`total`, `status`, `buyer_id`)
VALUES 
(1299.99, 'CONFIRMED', 1),
(500.00, 'PENDING', 2),
(1500.00, 'CANCELLED', 3);

-- Inserción de datos en la tabla `item_products`
INSERT INTO `item_products` (`buy_number`, `product_id`, `quantity`)
VALUES 
(1, 1, 1),
(1, 2, 2),
(2, 3, 1),
(2, 4, 2),
(3, 5, 2),
(3, 6, 3);
