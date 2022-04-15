drop database if exists AgendaFX_JDBC_1;
create database AgendaFX_JDBC_1 with encoding "UTF8";

create table producto (
	id SERIAL primary key,
	nombre VARCHAR(15) not null,
	precio real not null,
	stock integer not null,
	fecha_inicio date not null,
	fecha_fin date not null,
	constraint chk_precio CHECK(precio>0),
	constraint chk_stock CHECK(stock>0),
	constraint chk_catalogo CHECK(fecha_fin>=fecha_inicio)
);

create table pack(
	id SERIAL primary key,
	nombre VARCHAR(15) not null,
	precio real not null,
	stock integer not null,
	fecha_inicio date not null,
	fecha_fin date not null,
	dto real not null,
	constraint chk_precio CHECK(precio>0),
	constraint chk_stock CHECK(stock>0),
	constraint chk_catalogo CHECK(fecha_fin>=fecha_inicio),
	constraint chk_dto CHECK(dto>=0 and dto<=100)	
);

create table productos_pack(
	id_pack INTEGER,
	id_producto INTEGER,
	primary key(id_pack, id_producto),
	CONSTRAINT fk_id_pack FOREIGN key(id_pack) references pack(id),
	CONSTRAINT fk_id_producto FOREIGN key(id_producto) references producto(id)
);

CREATE TABLE persones (
	id SERIAL primary key,
	dni TEXT NOT NULL,
	nombre TEXT NOT NULL,
	apellidos TEXT NOT NULL,
	fecha_nacimiento date,
	email TEXT NOT NULL,
	telefon TEXT NOT NULL
);


create table cliente(
	id SERIAL primary key,
	dni TEXT NOT NULL,
	nombre TEXT NOT NULL,
	apellidos TEXT NOT NULL,
	fecha_nacimiento date,
	email TEXT NOT NULL,
	telefon TEXT NOT NULL
);

create table proveedor(
	id SERIAL primary key,
	dni TEXT NOT NULL,
	nombre TEXT NOT NULL,
	apellidos TEXT NOT NULL,
	fecha_nacimiento date,
	email TEXT NOT NULL,
	telefon TEXT NOT NULL
);

create table asistencia_trabajador(
	id_trabajador SERIAL primary key,
	fecha_entrada date,
	hora_entrada TEXT,
	hora_salida TEXT
);