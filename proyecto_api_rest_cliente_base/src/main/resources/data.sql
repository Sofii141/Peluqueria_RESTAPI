INSERT INTO categorias (nombreCategoria) VALUES ('Cortes de Cabello');
INSERT INTO categorias (nombreCategoria) VALUES ('Tratamientos Capilares');
INSERT INTO categorias (nombreCategoria) VALUES ('Manicura y Pedicura');

INSERT INTO servicios (nombre, descripcion, precio, imagen, fechaCreacion, disponible, idCategoria)
VALUES ('Corte Estilo Bob', 'Un corte clásico y elegante por encima de los hombros.', 50000, 'url_a_la_imagen_corte_bob.jpg', '2025-10-23', true, 1);

INSERT INTO servicios (nombre, descripcion, precio, imagen, fechaCreacion, disponible, idCategoria)
VALUES ('Hidratación Profunda', 'Tratamiento a base de keratina para revitalizar tu cabello.', 80000, 'url_a_la_imagen_hidratacion.jpg', '2025-10-23', true, 2);