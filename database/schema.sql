CREATE DATABASE IF NOT EXISTS usuarios_saldos_telefonica;

USE usuarios_saldos_telefonica;

CREATE TABLE Usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    mail VARCHAR(100) NOT NULL,
    numero_movil VARCHAR(11) NOT NULL UNIQUE,
    CONSTRAINT chk_numero_movil CHECK (numero_movil REGEXP '^0(424|414)[0-9]{7}$'),
    documento_identidad VARCHAR(20) NOT NULL
);

CREATE TABLE Cupos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    numero_movil VARCHAR(11) NOT NULL,
    saldo VARCHAR(20) NOT NULL,
    datos VARCHAR(20) NOT NULL,
    plataforma ENUM('Prepago', 'Postpago') NOT NULL,
    documento_identidad VARCHAR(20) NOT NULL,
    plan VARCHAR(10) NOT NULL,
    FOREIGN KEY (numero_movil) REFERENCES Usuarios(numero_movil)
);

CREATE TABLE verification_codes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    code VARCHAR(6) NOT NULL,
    expiration DATETIME NOT NULL,
    FOREIGN KEY (user_id) REFERENCES Usuarios(id)
);