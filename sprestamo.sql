drop database if exists sprestamo;
create database sprestamo;
\c sprestamo;

create table document_types
(
    code        varchar(1)  not null primary key,
    description varchar(48) not null
);

insert into document_types(code, description)
values ('0', 'DOC.TRIB.NO.DOM.SIN.RUC'),
       ('1', 'Documento Nacional de Identidad'),
       ('4', 'Carnet de extranjería'),
       ('6', 'Registro Unico de Contributentes'),
       ('7', 'Pasaporte'),
       ('A', 'Cédula Diplomática de identidad'),
       ('B', 'DOC.IDENT.PAIS.RESIDENCIA-NO.D'),
       ('C', 'Tax Identification Number - TIN – Doc Trib PP.NN'),
       ('D', 'Identification Number - IN – Doc Trib PP. JJ'),
       ('E', 'TAM- Tarjeta Andina de Migración');


create table customers
(
    id               serial primary key,
    document_type_id varchar(1)   not null,
    document_number  varchar(20)  not null,
    name             varchar(100) not null,
    last_name        varchar(100) not null,
    mother_last_name varchar(100) not null,
    email            varchar(100) not null,
    phone            varchar(10)  not null,
    date_birth       date         not null,
    constraint fk_customer_document_type
        foreign key (document_type_id) references document_types (code)
);

create table addresses
(
    id          serial primary key,
    department  varchar(100) not null,
    province    varchar(100) not null,
    district    varchar(100) not null,
    street      varchar(100) not null,
    number      varchar(100) null,
    reference   varchar(100) not null,
    postal_code varchar(20) null,
    latitude    varchar(50) null,
    longitude   varchar(50) null,
    customer_id integer      not null,
    constraint fk_address_customer
        foreign key (customer_id) references customers (id)
);
