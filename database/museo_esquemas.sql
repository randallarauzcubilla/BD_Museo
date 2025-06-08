create database jdba_museo_MAHN;
USE jdba_museo_MAHN;

CREATE TABLE Museos (
    idMuseo INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    tipo ENUM('Arte', 'Historia', 'Musical', 'Militar') NOT NULL,
    ubicacion VARCHAR(255) NOT NULL,
    fechaFundacion DATE NOT NULL,
    director VARCHAR(100) NOT NULL,
    sitioWeb VARCHAR(255)
);

CREATE TABLE Salas (
    idSala INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT NOT NULL,
    tematica ENUM('Antropología', 'Ciencias Físicas', 'Biodiversidad', 'Genoma', 'Informática', 'Invertebrados', 'Paleontología', 'Zoología') NOT NULL,
    idMuseo INT,
    FOREIGN KEY (idMuseo) REFERENCES Museos(idMuseo)
);

CREATE TABLE Colecciones (
    idColeccion INT PRIMARY KEY AUTO_INCREMENT,
    nombreColeccion VARCHAR(100) NOT NULL,
    siglo VARCHAR(50) NOT NULL,
    descripcion TEXT NOT NULL,
    idSala INT,
    FOREIGN KEY (idSala) REFERENCES Salas(idSala)
);

CREATE TABLE Especies (
    idEspecie INT PRIMARY KEY AUTO_INCREMENT,
    nombreCientificoDeEspecie VARCHAR(100) NOT NULL,
    nombreComunDeEspecie VARCHAR(100) NOT NULL,
    fechaExtincion DATE,
    epoca VARCHAR(50),
    peso DECIMAL(10,2),
    tamanio DECIMAL(10,2),
    caracteristicas TEXT,
    idColeccion INT,
    FOREIGN KEY (idColeccion) REFERENCES Colecciones(idColeccion)
);

CREATE TABLE Tematicas (
    idTematica INT PRIMARY KEY AUTO_INCREMENT,
    nombreDeTematica VARCHAR(100) NOT NULL,
    caracteristicas TEXT NOT NULL,
    epocaDeTematica VARCHAR(50) NOT NULL,
    idSala INT,
    FOREIGN KEY (idSala) REFERENCES Salas(idSala)
);

CREATE TABLE Precios (
    idPrecio INT PRIMARY KEY AUTO_INCREMENT,
    idSala INT,
    precioLunesSabado DECIMAL(10,2) NOT NULL,
    precioDomingo DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (idSala) REFERENCES Salas(idSala)
);

CREATE TABLE ComisionesTarjetas (
    idComision INT PRIMARY KEY AUTO_INCREMENT,
    tipoTarjeta ENUM('VISA', 'Mastercard', 'American Express', 'Dinner Club', 'Union Pay') NOT NULL,
    porcentajeComision DECIMAL(5,2) NOT NULL
);


CREATE TABLE Entradas (
    idEntrada INT PRIMARY KEY AUTO_INCREMENT,
    fechaCompra DATE NOT NULL,
    fechaVisita DATE NOT NULL,
    precioTotal DECIMAL(10,2) NOT NULL,
    codigoQR VARCHAR(255) NOT NULL
);

CREATE TABLE Entrada_Salas (
    idEntradaSala INT PRIMARY KEY AUTO_INCREMENT,
    idEntrada INT,
    idSala INT,
    precioSala DECIMAL(10,2) NOT NULL, -- Se obtiene de Precios
    FOREIGN KEY (idEntrada) REFERENCES Entradas(idEntrada),
    FOREIGN KEY (idSala) REFERENCES Salas(idSala)
);

CREATE TABLE Registro_Comisiones (
    idRegistroComision INT PRIMARY KEY AUTO_INCREMENT,
    idEntrada INT,
    idComision INT,
    valorCobrado DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (idEntrada) REFERENCES Entradas(idEntrada),
    FOREIGN KEY (idComision) REFERENCES ComisionesTarjetas(idComision)
);

CREATE TABLE Imagenes_Salas (
    idImagen INT PRIMARY KEY AUTO_INCREMENT,
    idSala INT,
    urlImagen VARCHAR(255) NOT NULL, 
    descripcion TEXT,
    tipo ENUM('Tematica', 'Especie') NOT NULL, -- Define si la imagen es de una temática o especie
    FOREIGN KEY (idSala) REFERENCES Salas(idSala)
);

CREATE TABLE Valoraciones (
    idValoracion INT PRIMARY KEY AUTO_INCREMENT,
    idSala INT,
    estrellas INT CHECK (estrellas BETWEEN 1 AND 5),
    observacion TEXT,
    FOREIGN KEY (idSala) REFERENCES Salas(idSala)
);