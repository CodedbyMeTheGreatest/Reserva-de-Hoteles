INSERT INTO pagos (id_habitacion, id_huesped, precio_por_noche, cant_dias, subtotal, impuestos, total, metodo_pago, estado_pago, fecha_pago) VALUES
(1, 1, 45000, 3, 135000, 25650, 160650, 'TARJETA', 'PAGADO', '2026-05-01 14:30:00'),
(2, 2, 75000, 2, 150000, 28500, 178500, 'EFECTIVO', 'PENDIENTE', NULL),
(3, 3, 150000, 5, 750000, 142500, 892500, 'TRANSFERENCIA', 'RECHAZADO', NULL);