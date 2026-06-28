-- =========================
-- BASE DE DATOS
-- =========================
CREATE DATABASE clipo_inventario;
USE clipo_inventario;

-- =========================
-- USUARIO
-- =========================
CREATE TABLE usuario (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    usuario VARCHAR(50) NOT NULL UNIQUE,
    contraseña VARCHAR(100) NOT NULL,
    tipo_usuario ENUM('EMPLEADO', 'ENCARGADO') NOT NULL
);

-- =========================
-- PRODUCTO
-- =========================
CREATE TABLE producto (
    id_producto INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    categoria VARCHAR(50),
    precio DOUBLE NOT NULL,
    stock_minimo INT NOT NULL
);

-- =========================
-- LOCAL
-- =========================
CREATE TABLE local (
    id_local INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    direccion VARCHAR(150)
);

-- =========================
-- STOCK 
-- =========================
CREATE TABLE stock (
    id_stock INT AUTO_INCREMENT PRIMARY KEY,
    id_producto INT NOT NULL,
    id_local INT NOT NULL,
    cantidad INT NOT NULL DEFAULT 0,

    CONSTRAINT fk_stock_producto
        FOREIGN KEY (id_producto) REFERENCES producto(id_producto),

    CONSTRAINT fk_stock_local
        FOREIGN KEY (id_local) REFERENCES local(id_local)
);

-- =========================
-- OPERACION (Tabla padre)
-- =========================
CREATE TABLE operacion (
    id_operacion INT AUTO_INCREMENT PRIMARY KEY,
    fecha DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    cantidad INT NOT NULL,

    tipo_operacion ENUM('COMPRA', 'VENTA', 'TRANSFERENCIA') NOT NULL,

    id_usuario_responsable INT NOT NULL,

    CONSTRAINT fk_operacion_usuario
        FOREIGN KEY (id_usuario_responsable)
        REFERENCES usuario(id_usuario)
);

-- =========================
-- COMPRA (HEREDA OPERACION)
-- =========================
CREATE TABLE compra (
    id_operacion INT PRIMARY KEY,

    id_producto INT NOT NULL,
    id_local INT NOT NULL,

    CONSTRAINT fk_compra_operacion
        FOREIGN KEY (id_operacion) REFERENCES operacion(id_operacion)
        ON DELETE CASCADE,

    CONSTRAINT fk_compra_producto
        FOREIGN KEY (id_producto) REFERENCES producto(id_producto),

    CONSTRAINT fk_compra_local
        FOREIGN KEY (id_local) REFERENCES local(id_local)
);

-- =========================
-- VENTA (HEREDA OPERACION)
-- =========================
CREATE TABLE venta (
    id_operacion INT PRIMARY KEY,

    id_producto INT NOT NULL,
    id_local INT NOT NULL,

    CONSTRAINT fk_venta_operacion
        FOREIGN KEY (id_operacion) REFERENCES operacion(id_operacion)
        ON DELETE CASCADE,

    CONSTRAINT fk_venta_producto
        FOREIGN KEY (id_producto) REFERENCES producto(id_producto),

    CONSTRAINT fk_venta_local
        FOREIGN KEY (id_local) REFERENCES local(id_local)
);

-- =========================
-- TRANSFERENCIA (HEREDA OPERACION)
-- =========================
CREATE TABLE transferencia (
    id_operacion INT PRIMARY KEY,

    id_producto INT NOT NULL,
    id_local_origen INT NOT NULL,
    id_local_destino INT NOT NULL,

    CONSTRAINT fk_transferencia_operacion
        FOREIGN KEY (id_operacion) REFERENCES operacion(id_operacion)
        ON DELETE CASCADE,

    CONSTRAINT fk_transferencia_producto
        FOREIGN KEY (id_producto) REFERENCES producto(id_producto),

    CONSTRAINT fk_transferencia_origen
        FOREIGN KEY (id_local_origen) REFERENCES local(id_local),

    CONSTRAINT fk_transferencia_destino
        FOREIGN KEY (id_local_destino) REFERENCES local(id_local)
);

INSERT INTO usuario (nombre, usuario, contraseña, tipo_usuario)
VALUES 
('Juan Perez', 'jperez', '1234', 'ENCARGADO'),
('Maria Gomez', 'mgomez', '1234', 'EMPLEADO');

INSERT INTO producto (nombre, categoria, precio, stock_minimo)
VALUES 
('Lápiz', 'Papelería', 100, 10),
('Cuaderno', 'Papelería', 500, 5);

INSERT INTO local (nombre, direccion)
VALUES 
('Centro', 'San Martín 123'),
('Norte', 'Belgrano 456');

INSERT INTO stock (id_producto, id_local, cantidad)
VALUES 
(1, 1, 50),  -- Lápiz en Centro
(1, 2, 20),  -- Lápiz en Norte
(2, 1, 15),  -- Cuaderno en Centro
(2, 2, 10);  -- Cuaderno en Norte

SELECT * FROM producto;
SELECT * FROM local;
SELECT * FROM stock;
SELECT * FROM usuario;
