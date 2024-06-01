drop
    database if exists sprestamo;
create
    database sprestamo;
\c sprestamo;

-- MS Customers
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
    created_by       integer      not null,
    created_at       timestamp    not null,
    updated_by       integer      null,
    updated_at       timestamp    null,
    deleted_by       integer      null,
    deleted_at       timestamp    null,
    constraint fk_customer_document_type
        foreign key (document_type_id) references document_types (code)
);

create table addresses
(
    id          serial primary key,
    department  varchar(100) not null,
    province    varchar(100) not null,
    district    varchar(100) not null,
    address     varchar(255) not null,
    street      varchar(100) null,
    number      varchar(100) null,
    reference   varchar(100) not null,
    postal_code varchar(20)  null,
    latitude    varchar(50)  null,
    longitude   varchar(50)  null,
    customer_id integer      not null,
    created_by  integer      not null,
    created_at  timestamp    not null,
    updated_by  integer      null,
    updated_at  timestamp    null,
    deleted_by  integer      null,
    deleted_at  timestamp    null,
    constraint fk_address_customer
        foreign key (customer_id) references customers (id)
);

-- MS loans
drop table if exists payment_installments;
drop table if exists guaranties;
drop table if exists loans;

create table loans
(
    id             serial primary key,
    customer_id    integer        not null,
    amount         numeric(24, 6) not null,
    payment_method varchar(50)    not null,
    payment_type   varchar(50)    not null,
    contract_date  date           not null,
    start_date     date           not null,
    end_date       date           not null,
    interest_rate  numeric(24, 6) not null,
    status         varchar(50)    not null,
    term           integer        not null,
    fee            numeric(24, 6) not null,
    created_by     integer        not null,
    created_at     timestamp      not null,
    updated_by     integer        null,
    updated_at     timestamp      null,
    deleted_by     integer        null,
    deleted_at     timestamp      null,
    constraint fk_loan_customer
        foreign key (customer_id) references customers (id)
);

create table guaranties
(
    id              serial primary key,
    loan_id         integer        not null,
    name            varchar(100)   not null,
    description     text           not null,
    estimated_value numeric(24, 6) not null,
    status          varchar(1)     not null,
    image_url       varchar(255)   not null,
    created_by      integer        not null,
    created_at      timestamp      not null,
    updated_by      integer        null,
    updated_at      timestamp      null,
    deleted_by      integer        null,
    deleted_at      timestamp      null,
    constraint fk_guaranty_loan
        foreign key (loan_id) references loans (id)
);

create table payment_installments
(
    id         serial primary key,
    loan_id    integer        not null,
    amount     numeric(24, 6) not null,
    start_date date           not null,
    end_date   date           not null,
    status     varchar(50)    not null,
    created_by integer        not null,
    created_at timestamp      not null,
    updated_by integer        null,
    updated_at timestamp      null,
    deleted_by integer        null,
    deleted_at timestamp      null,
    constraint fk_payment_installment_loan
        foreign key (loan_id) references loans (id)
);

-- MS loans
drop table if exists users;

create table users
(
    id               serial primary key,
    document_type_id varchar(1)   not null,
    document_number  varchar(20)  not null,
    name             varchar(100) not null,
    last_name        varchar(100) not null,
    mother_last_name varchar(100) not null,
    email            varchar(100) not null,
    password         varchar(100) not null,
    phone            varchar(10)  not null,
    date_birth       date         not null,
    created_by       integer      not null,
    created_at       timestamp    not null,
    updated_by       integer      null,
    updated_at       timestamp    null,
    deleted_by       integer      null,
    deleted_at       timestamp    null,
    constraint fk_customer_document_type
        foreign key (document_type_id) references document_types (code)
);

-- MS payment
create table pay
(
    id              serial primary key,
    installments_id integer        not null,
    day             timestamp      not null,
    modality        varchar(20)    not null,
    method          varchar(20)    not null,
    amount          numeric(24, 6) not null,
    created_by      integer        not null,
    created_at      timestamp      not null,
    updated_by      integer        null,
    updated_at      timestamp      null,
    deleted_by      integer        null,
    deleted_at      timestamp      null,
    constraint fk_pay_payment_installment
        foreign key (installments_id) references payment_installments (id)
);

create table rol
(
    id        serial primary key,
    name_role varchar(100) not null
);

-- INSERT ROL
insert into rol (name_role)
values ('ADMIN');
insert into rol (name_role)
values ('USER');

create table user_role
(
    id_user serial,
    id_rol  serial,
    constraint fk_users
        foreign key (id_user) references users (id),
    constraint fk_rol
        foreign key (id_rol) references rol (id)

);

INSERT INTO users (id, document_type_id, document_number, name, last_name, mother_last_name, email, password,
                   phone, date_birth, created_by, created_at, updated_by, updated_at, deleted_by, deleted_at)
VALUES (1, '1', '71991757', 'Yuler', 'Figueroa', 'Jimenez', 'yuleralex@gmail.com', 'root', '935836953', '2024-06-01', 1,
        '2024-06-01 02:52:25.000000', null, null, null, null);

INSERT INTO users (id, document_type_id, document_number, name, last_name, mother_last_name, email, password,
                   phone, date_birth, created_by, created_at, updated_by, updated_at, deleted_by, deleted_at)
VALUES (2, '1', '71991754', 'Alex', 'Figueroa', 'Jimenez', 'yuleralex@gmail.com', 'root', '935836953', '2024-06-01', 1,
        '2024-06-01 02:52:25.000000', null, null, null, null);



INSERT INTO user_role (id_user, id_rol)
VALUES (1, 1);
INSERT INTO user_role (id_user, id_rol)
VALUES (2, 2);