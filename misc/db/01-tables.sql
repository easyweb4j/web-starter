create table  if not exists person (
  id int primary key auto_increment,
  name varchar(16) not null,
  age int not null,
  address varchar(32) default "none"
);

create table  if not exists house (
  id int primary key auto_increment,
  name varchar(16) not null,
  floor_number tinyint not null default 1,
  state bit(1) not null default b'00000001',
  state_ts datetime not null,
  create_date datetime not null
);
