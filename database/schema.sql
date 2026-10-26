-- Taller: Gestion de proyectos empresariales
-- Ejecutar como root (el docker-compose ya crea el servidor MySQL 8.0)

CREATE DATABASE IF NOT EXISTS gestion_proyectos
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE gestion_proyectos;

DROP TABLE IF EXISTS empleado_proyecto;
DROP TABLE IF EXISTS proyectos;
DROP TABLE IF EXISTS empleados;

-- EMPLEADOS
CREATE TABLE empleados (
    id      INT AUTO_INCREMENT PRIMARY KEY,
    nombre  VARCHAR(100) NOT NULL,
    correo  VARCHAR(150) NOT NULL,
    cargo   VARCHAR(100) NOT NULL,
    CONSTRAINT uq_empleados_correo UNIQUE (correo),            -- regla 2
    CONSTRAINT ck_empleados_nombre CHECK (TRIM(nombre) <> ''), -- regla 1
    CONSTRAINT ck_empleados_correo CHECK (TRIM(correo) <> ''),
    CONSTRAINT ck_empleados_cargo  CHECK (TRIM(cargo)  <> '')
) ENGINE=InnoDB;

-- PROYECTOS
CREATE TABLE proyectos (
    id           INT AUTO_INCREMENT PRIMARY KEY,
    nombre       VARCHAR(150) NOT NULL,                        -- regla 3
    descripcion  VARCHAR(500) NULL,
    fecha_inicio DATE NULL,
    fecha_fin    DATE NULL,
    estado       VARCHAR(20) NOT NULL DEFAULT 'PLANIFICADO',
    CONSTRAINT ck_proyectos_nombre CHECK (TRIM(nombre) <> ''),
    CONSTRAINT ck_proyectos_estado CHECK (estado IN ('PLANIFICADO', 'EN_CURSO', 'FINALIZADO')), -- regla 5
    CONSTRAINT ck_proyectos_fechas CHECK (fecha_fin IS NULL OR fecha_inicio IS NULL OR fecha_fin >= fecha_inicio) -- regla 4
) ENGINE=InnoDB;

-- EMPLEADO_PROYECTO (tabla intermedia con datos propios)
CREATE TABLE empleado_proyecto (
    empleado_id       INT NOT NULL,
    proyecto_id       INT NOT NULL,
    rol               VARCHAR(100) NOT NULL,
    fecha_asignacion  DATE NOT NULL,
    horas_semanales   DECIMAL(4,1) NOT NULL,
    PRIMARY KEY (empleado_id, proyecto_id),                    -- regla 8: sin duplicados
    CONSTRAINT fk_ep_empleado FOREIGN KEY (empleado_id) REFERENCES empleados(id)
        ON DELETE RESTRICT ON UPDATE CASCADE,                  -- regla 7 / no borrar con asignaciones
    CONSTRAINT fk_ep_proyecto FOREIGN KEY (proyecto_id) REFERENCES proyectos(id)
        ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT ck_ep_rol   CHECK (TRIM(rol) <> ''),
    CONSTRAINT ck_ep_horas CHECK (horas_semanales > 0 AND horas_semanales <= 40) -- regla 6
) ENGINE=InnoDB;

-- Datos de prueba
INSERT INTO empleados (nombre, correo, cargo) VALUES
    ('Ana Torres',  'ana.torres@empresa.com',  'Desarrolladora Backend'),
    ('Luis Perez',  'luis.perez@empresa.com',  'Analista QA'),
    ('Marta Gomez', 'marta.gomez@empresa.com', 'Project Manager');

INSERT INTO proyectos (nombre, descripcion, fecha_inicio, fecha_fin, estado) VALUES
    ('Portal Web',  'Portal de clientes',      '2026-01-10', '2026-06-30', 'EN_CURSO'),
    ('App Movil',   'App para ventas',         '2026-03-01', NULL,         'PLANIFICADO');

INSERT INTO empleado_proyecto (empleado_id, proyecto_id, rol, fecha_asignacion, horas_semanales) VALUES
    (1, 1, 'Desarrolladora', '2026-01-15', 30),
    (2, 1, 'Tester',         '2026-02-01', 20),
    (3, 1, 'Lider',          '2026-01-10', 10),
    (1, 2, 'Arquitecta',     '2026-03-05', 8);
