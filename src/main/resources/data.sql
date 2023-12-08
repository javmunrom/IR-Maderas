INSERT INTO authorities(id,authority) VALUES (1,'OWNER');
INSERT INTO authorities(id,authority) VALUES (2,'USER');
INSERT INTO appusers(id,username,password,email,phone,authority) VALUES (1,'owner1','$2a$10$KWCE.PBwj0kgCAmo5rtfCOhvSN6XFo0Fr9ftrQ0i94MwnqRSKnczS','owner1@gmail,com','111111111',1);


INSERT INTO appusers(id,username,password,email,phone,authority) VALUES (2,'user1','$2a$10$nMmTWAhPTqXqLDJTag3prumFrAJpsYtroxf0ojesFYq0k4PmcbWUS','user1@gmail,com','111111111',2), (3,'1','$2a$10$KWCE.PBwj0kgCAmo5rtfCOhvSN6XFo0Fr9ftrQ0i94MwnqRSKnczS','1@gmail,com','111111111', 2);

INSERT INTO tablero (id, espesor, stock, color, tipo_material, img) VALUES (1, 40, 20, 'AMARILLO', 'MELAMINA', '/tableros/melaminaAmarilla.jpg');
INSERT INTO tablero (id, espesor, stock, color, tipo_material, img) VALUES (2, 40, 20, 'NEGRO', 'MELAMINA', '/tableros/melaminaNegra.jpg');
INSERT INTO tablero (id, espesor, stock, color, tipo_material, img) VALUES (3, 40, 20, 'BLANCO', 'MELAMINA', '/tableros/melaminaBlanca.jpg');
INSERT INTO tablero (id, espesor, stock, color, tipo_material, img) VALUES (4, 40, 20, 'GRIS', 'MELAMINA', '/tableros/melaminaGris.jpg');
INSERT INTO tablero (id, espesor, stock, color, tipo_material, img) VALUES (5, 40, 20, 'AMARILLO', 'CONTRACHAPADO', '/tableros/contrachapadoAmarilla.jpg');
INSERT INTO tablero (id, espesor, stock, color, tipo_material, img) VALUES (6, 40, 20, 'NEGRO', 'CONTRACHAPADO', '/tableros/contrachapadoNegra.jpg');
INSERT INTO tablero (id, espesor, stock, color, tipo_material, img) VALUES (7, 40, 20, 'BLANCO', 'CONTRACHAPADO', '/tableros/contrachapadoBlanca.jpg');
INSERT INTO tablero (id, espesor, stock, color, tipo_material, img) VALUES (8, 40, 20, 'GRIS', 'CONTRACHAPADO', '/tableros/contrachapadoGris.jpg');
INSERT INTO tablero (id, espesor, stock, color, tipo_material, img) VALUES (9, 40, 20, 'AMARILLO', 'MACIZO', '/tableros/macizoAmarillo.jpg');
INSERT INTO tablero (id, espesor, stock, color, tipo_material, img) VALUES (10, 40, 20, 'NEGRO', 'MACIZO', '/tableros/macizoNegro.jpg');
INSERT INTO tablero (id, espesor, stock, color, tipo_material, img) VALUES (11, 40, 20, 'BLANCO', 'MACIZO', '/tableros/macizoBlanco.jpg');
INSERT INTO tablero (id, espesor, stock, color, tipo_material, img) VALUES (12, 40, 20, 'GRIS', 'MACIZO', '/tableros/macizoGris.jpg');

