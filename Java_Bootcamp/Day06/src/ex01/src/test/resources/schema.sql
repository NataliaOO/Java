
create table products (
    identifier bigint identity primary key,
    name varchar(100) not null,
    price decimal(10,2) not null
);