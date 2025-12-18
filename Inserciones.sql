use db_apirest;

-- Datos iniciales para servicios_vip
INSERT INTO servicios_vip (nombre_serv, descr, precio, activo) VALUES 
('Limpieza Exterior', 'Lavado completo exterior del vehículo', 15.00, TRUE),
('Carga Eléctrica', 'Carga completa para vehículos eléctricos', 10.00, TRUE),
('Aparcacoches Personal', 'Servicio de aparcacoches con personal dedicado', 20.00, TRUE),
('Limpieza Interior', 'Aspirado y limpieza completa del interior', 25.00, TRUE),
('Revisión Básica', 'Revisión de presión de neumáticos y fluidos', 30.00, TRUE),
('Cambio de Aceite', 'Cambio de aceite y filtro', 45.00, TRUE),
('Limpieza Premium', 'Limpieza interior y exterior premium', 50.00, TRUE);

-- Datos iniciales para plazas (manteniendo tu estructura)
INSERT INTO plazas (numero_plaza, tipo, estado, planta) VALUES 
('B-01', 'NORMAL', 'DISPONIBLE', 'Baja'),
('B-02', 'NORMAL', 'DISPONIBLE', 'Baja'),
('B-03', 'NORMAL', 'DISPONIBLE', 'Baja'),
('V-01', 'VIP', 'DISPONIBLE', 'VIP'),
('V-02', 'VIP', 'DISPONIBLE', 'VIP'),
('V-03', 'VIP', 'DISPONIBLE', 'VIP'),
('D-01', 'DISCAPACITADOS', 'DISPONIBLE', 'Baja');

-- Datos iniciales para clientes
INSERT INTO clientes (nombre, apellidos, matricula, tipo_cliente, cuota_pagada) VALUES 
('Juan', 'Pérez López', 'ABC1234', 'NORMAL', TRUE),
('María', 'García Ruiz', 'DEF5678', 'VIP', TRUE),
('Carlos', 'Martínez Sánchez', 'GHI9012', 'VIP', FALSE),
('Ana', 'Rodríguez Fernández', 'JKL3456', 'NORMAL', TRUE);

-- Datos iniciales para reservas
INSERT INTO reservas (id_cliente, id_plaza, fecha_res, hora_ini, hora_fin, es_vip, estado, total) VALUES 
(1, 1, DATE_ADD(CURDATE(), INTERVAL 1 DAY), '09:00:00', '13:00:00', FALSE, 'ACTIVA', 20.00),
(2, 4, DATE_ADD(CURDATE(), INTERVAL 1 DAY), '10:00:00', '14:00:00', TRUE, 'ACTIVA', 32.00),
(3, 5, DATE_ADD(CURDATE(), INTERVAL 2 DAY), '14:00:00', '18:00:00', TRUE, 'ACTIVA', 32.00);

-- Datos iniciales para reserva_servicio (usando la nueva tabla)
INSERT INTO reserva_servicio (id_reserva, id_servicio, cantidad, subtotal) VALUES 
(2, 1, 1, 15.00),
(2, 2, 1, 10.00),
(3, 1, 2, 30.00),
(3, 3, 1, 20.00);