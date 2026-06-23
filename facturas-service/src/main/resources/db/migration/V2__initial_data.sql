INSERT INTO facturas (folio,id_reserva,id_pago,run_huesped,nombre_huesped,id_check_in,id_check_out,fecha_check_in,
                      fecha_check_out,descripcion_habitacion,cantidad_dias,subtotal,impuestos,total,metodo_pago,
                      estado_pago,fecha_factura) VALUES
('F101',1,1,'20111222','Carlos Andres Muñoz Vega',1,
 1,NOW(),NOW(),'Habitación simple con vista al jardín',3,
 135000,25650,160650,'TARJETA','PAGADO',NOW()),
('F102',2,2,'20999888','Fernanda Isabel Rojas Soto',2,
 2,NOW(),NOW(),'Habitación doble con vista a la piscina',2,
 150000,28500,178500,'EFECTIVO','PENDIENTE',NOW()),
('F103',3,3,'18777666','Miguel Angel Torres Silva',3,
 3,NOW(),NOW(),'Suite presidencial con terraza',5,
 750000,142500,892500,'TRANSFERENCIA','RECHAZADO',NOW());