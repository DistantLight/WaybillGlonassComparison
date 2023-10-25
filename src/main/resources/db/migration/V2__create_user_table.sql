CREATE TABLE users (
  id                    bigserial,
  email              varchar(30) not null unique,
  password                 varchar(255) not null,
  role                 varchar(20) default 'USER',
  status                 varchar(20) default 'ACTIVE',
  primary key (id)
);