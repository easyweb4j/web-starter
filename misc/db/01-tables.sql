create table  if not exists person (
  id int primary key auto_increment,
  name varchar(16) not null,
  age int not null,
  address varchar(32) default "none"
);
