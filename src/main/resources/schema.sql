create table admins
(
    id   int auto_increment
        primary key,
    name varchar(50) null
);

create table customers
(
    id      int auto_increment
        primary key,
    name    varchar(50)  not null,
    address varchar(150) not null
);

create table accounts
(
    id      int auto_increment
        primary key,
    cust_id int null,
    constraint accounts___fk___cust
        foreign key (cust_id) references customers (id)
);

create table chequebook_requests
(
    id         int auto_increment
        primary key,
    acc_id     int                  null,
    authorized tinyint(1) default 0 null,
    constraint chequebook_requests___fk___acc
        foreign key (acc_id) references accounts (id)
);

create table transactions
(
    id     int auto_increment
        primary key,
    acc_id int  null,
    type   char not null,
    constraint transactions___fk__acc
        foreign key (acc_id) references accounts (id)
);

create table users
(
    id       int auto_increment
        primary key,
    username varchar(20)  not null,
    cust_id  int          null,
    admin_id int          null,
    password varchar(500) null,
    constraint Users_userid_uindex
        unique (username),
    constraint users___fk___admin
        foreign key (admin_id) references admins (id),
    constraint users___fk___cust
        foreign key (cust_id) references customers (id)
);