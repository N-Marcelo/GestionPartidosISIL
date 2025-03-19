CREATE TABLE Alumno(
id int PRIMARY KEY IDENTITY(1,1),
nombres varchar(100),
apellidoPaterno varchar(100),
apellidoMaterno varchar(100)
);

CREATE TABLE PartidoPolitico(
id int PRIMARY KEY IDENTITY(1,1),
nombre varchar(100),
simbolo varchar(100),
fechaCreacion Date,
idAlumno int,
FOREIGN KEY (idAlumno) REFERENCES Alumno(id)
);