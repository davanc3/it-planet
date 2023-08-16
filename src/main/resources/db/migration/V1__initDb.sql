create table account
(
    id         bigserial not null,
    email      varchar(255),
    first_name varchar(255),
    last_name  varchar(255),
    password   varchar(255),
    role_id    bigint,
    primary key (id)
);

create table animal (
    id bigserial not null,
    chipping_date_time TIMESTAMP (6),
    death_date_time TIMESTAMP (6),
    gender varchar(20),
    height float(53),
    length float(53),
    life_status varchar(20),
    weight float(53),
    chipper_id bigint,
    chipping_location_id bigint,
    primary key (id)
);

create table animal_to_animal_type (
    animal_id bigint not null,
    animal_type_id bigint not null,
    primary key (animal_id, animal_type_id)
);

create table animal_to_location (
    id bigserial not null,
    date_time_of_visit_location_point TIMESTAMP (6),
    animal_id bigint,
    location_id bigint,
    primary key (id)
);

create table animal_type (
    id bigserial not null,
    type varchar(255),
    primary key (id)
);

create table location (
    id bigserial not null,
    latitude float(53),
    longitude float(53),
    primary key (id)
);

create table role (
    id bigserial not null,
    name varchar(10),
    primary key (id)
);

alter table if exists account
    add constraint account_role_fk
    foreign key (role_id) references role;

alter table if exists animal
    add constraint animal_chipper_fk
    foreign key (chipper_id) references account;

alter table if exists animal
    add constraint animal_location_fk
    foreign key (chipping_location_id) references location;

alter table if exists animal_to_animal_type
    add constraint animal_to_animal_type_animal_type_id_fk
    foreign key (animal_type_id) references animal_type;

alter table if exists animal_to_animal_type
    add constraint animal_to_animal_type_animal_fk
    foreign key (animal_id) references animal;

alter table if exists animal_to_location
    add constraint animal_to_location_animal_fk
    foreign key (animal_id) references animal;

alter table if exists animal_to_location
    add constraint animal_to_location_location_fk
    foreign key (location_id) references location;
