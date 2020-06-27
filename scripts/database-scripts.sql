
--drop table address;
--drop table before_order;
--drop table user_order;
--drop table product;
--drop table before_order_type;
--drop table before_order;
--drop table user;

create table user(
	user_id int primary key AUTO_INCREMENT,
	full_name varchar(50) not null,
	date_of_birth date not null,
	email_id varchar(50) not null,
	password varchar(100) not null,
	is_admin boolean not null default false
);
/*insert into `user`(full_name, date_of_birth, email_id, password, is_admin)
values ('Snehal Honade', '1992-01-25', 'snehalhonade25@gmail.com', '1234', true);*/

create table address(
    address_id int primary key AUTO_INCREMENT,
    user_id int not null,
    address1 varchar(50),
    city varchar(50),
    state varchar(50),
    zip varchar(6) not null,
    foreign key (user_id) references user(user_id)
);

create table product(
	product_id int primary key AUTO_INCREMENT,
	product_name varchar(200) not null,
	description varchar(500),
	owner varchar(50),
	price decimal(10,2) not null,
	discount_percentage int default 0,
	image blob,
	quantity int not null default 0
);
alter table product AUTO_INCREMENT=1000;
--insert into product(product_name) values ('Samsumng M40 mobile');

create table user_order(
	user_order_id int primary key AUTO_INCREMENT,
	user_id int not null,
	product_id int not null,
	order_date datetime not null,
	status varchar(20),
	foreign key (user_id) references user(user_id),
	foreign key (product_id) references product(product_id)
);
alter table user_order AUTO_INCREMENT=2000;

create table before_order_type(
    before_order_type_id int primary key,
    before_order_type_desc varchar(50) not null
);
/*insert into before_order_type values
(1, 'Added to cart'),
(2, 'Added to wishlist');*/

create table before_order(
	before_order_id int primary key AUTO_INCREMENT,
	user_id int not null,
	product_id int not null,
	before_order_type_id int,
	order_date datetime not null,
	foreign key (user_id) references user(user_id),
	foreign key (product_id) references product(product_id),
	foreign key (before_order_type_id) references before_order_type(before_order_type_id)
);

