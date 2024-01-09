create table user
(
    id           bigint         auto_increment  PRIMARY KEY,
    email        varchar(255)   not null        UNIQUE,
    nickname     varchar(255)   not null        UNIQUE,
    job          varchar(255)   null,
    login_method enum ('PASSWORD', 'GOOGLE') not null
);


create table password
(
    user_id  bigint       not null PRIMARY KEY,
    password varchar(255) not null,
    foreign key (user_id) references user (id)
);
