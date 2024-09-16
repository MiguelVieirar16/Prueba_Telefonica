USE usuarios_saldos_telefonica;

INSERT INTO Usuarios (nombre, mail, numero_movil, documento_identidad) VALUES
('Juan Perez', 'miguelvieirar16@gmail.com', '04241234567', 'V12123123'),
('Juan Perez', 'juan.perez@gmail.com', '04143213213', 'V12123123'),
('Maria Gomez', 'maria.gomez@gmail.com', '04141234567', 'V32321321');

INSERT INTO Cupos (numero_movil, saldo, datos, plataforma, documento_identidad, plan) VALUES
('04241234567', "BS. 100.00", "5.00 MB", 'prepago', 'V12123123', '2000 MB'),
('04143213213', "BS. 20.00", "500.00 MB", 'postpago', 'V12123123', '5600 MB'),
('04141234567', "BS. 200.00", "10.00 MB", 'postpago', 'V32321321', '10000 MB');
