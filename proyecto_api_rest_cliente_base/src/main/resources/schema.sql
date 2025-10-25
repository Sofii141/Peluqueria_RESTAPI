CREATE TABLE categorias (
                            id INT AUTO_INCREMENT PRIMARY KEY,
                            nombreCategoria VARCHAR(255) NOT NULL
);

CREATE TABLE servicios (
                           id INT AUTO_INCREMENT PRIMARY KEY,
                           nombre VARCHAR(255) NOT NULL,
                           descripcion TEXT,
                           precio DOUBLE PRECISION NOT NULL,
                           imagen VARCHAR(255),
                           fechaCreacion DATE,
                           disponible BOOLEAN NOT NULL,
                           idCategoria INT,
                           FOREIGN KEY (idCategoria) REFERENCES categorias(id)
);