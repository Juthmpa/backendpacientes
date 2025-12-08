-- Crea la base de datos si no existe
CREATE
DATABASE IF NOT EXISTS sistemapacientes;

-- Usa la base de datos
USE
sistemapacientes;

-- Creación de la tabla paciente
CREATE TABLE pacientes
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre       VARCHAR(100)        NOT NULL,
    correo       VARCHAR(100) UNIQUE NOT NULL,
    edad         INT,
    direccion    VARCHAR(255),
    numero_cedula VARCHAR(15) UNIQUE  NOT NULL,
    activo       BOOLEAN DEFAULT TRUE
);

-- Datos de prueba (Opcional, pero útil)
INSERT INTO pacientes (nombre, correo, edad, direccion, numero_cedula)
VALUES ('Luis Andrade', 'luis@hospital.com', 45, 'Sector Sur Quito', '1725352924'),
       ('Raúl Gomez', 'raul@hospital.com', 22, 'Av. 6 Diciembre', '1754622767');