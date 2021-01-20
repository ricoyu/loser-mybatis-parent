create database mybatis_example default character set utf8mb4 default collate utf8mb4_general_ci;
use mybatis_example;
grant all on mybatis_example.* to 'mybatis'@'%' identified by '123456';
flush privileges;

create table t_user(
  id int primary key auto_increment,
  user_name varchar(50),
  create_time datetime
);

insert into t_user(id, user_name, create_time) values (1, "ricoyu", '2021-01-09 14:41:12');
insert into t_user(id, user_name, create_time) values (2, "JimGreen", '2021-01-11 14:41:12');
