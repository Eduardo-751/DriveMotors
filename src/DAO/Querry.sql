drop database if exists DriveMotors;
create database DriveMotors;
use DriveMotors;

create table client(
	client_id  int auto_increment,
	client_cpf  varchar(11) unique,
	client_name varchar(20),
    client_rg	varchar(11),
    client_mail	varchar(40),
    primary key (client_id)
);

insert into client (client_cpf, client_name, client_rg, client_mail) values ('12312312312', 'Eduardo dos Santos', '1212312310', '1@gmail.com');
insert into client (client_cpf, client_name, client_rg, client_mail) values ('11122233312', 'Isabela de Lurdes', '2132132130', '2@gmail.com');

create table profile (
    profile_id int auto_increment,
    profile_name varchar(20) unique,
    profile_description varchar(255),
    primary key (profile_id)
);

insert into profile (profile_name, profile_description) values ('Administrador', 'Acesso Total ao Sistema');

create table user(
	user_id  int auto_increment,
	user_login varchar(18) unique,
	user_password 	varchar(18),
    user_name  varchar(20),
    profile_id	int default 1,
    primary key (user_id),
    foreign key (profile_id) references profile(profile_id)
);

insert into user (user_login, user_password, user_name) values ('admin', 'admin', 'Administrador');
insert into user (user_login, user_password, user_name) values ('751463', '751463', 'Eduardo');

CREATE TABLE accessories (
    accessories_id int auto_increment,
    alarm boolean,
    abs_brake boolean,
    air_conditioning boolean,
    electric_windows boolean,
	power_steering boolean,
    alloy_wheels boolean,
    rear_view boolean,
    digital_radio boolean,
    keyless_start boolean,
    parking_assistance boolean,
    primary key (accessories_id)
);

Insert into accessories (alarm, abs_brake, air_conditioning, electric_windows, power_steering, alloy_wheels, rear_view, digital_radio, keyless_start, parking_assistance)
				  Values (false, false, false, false, false, false, false, false, false, false);
Insert into accessories (alarm, abs_brake, air_conditioning, electric_windows, power_steering, alloy_wheels, rear_view, digital_radio, keyless_start, parking_assistance)
				  Values (true, true, true, true, true, true, true, true, true, true);
Insert into accessories (alarm, abs_brake, air_conditioning, electric_windows, power_steering, alloy_wheels, rear_view, digital_radio, keyless_start, parking_assistance)
				  Values (true, true, true, true, true, true, false, true, false, true);
Insert into accessories (alarm, abs_brake, air_conditioning, electric_windows, power_steering, alloy_wheels, rear_view, digital_radio, keyless_start, parking_assistance)
				  Values (true, true, false, true, true, false, false, false, false, false);
				Insert into accessories (alarm, abs_brake, air_conditioning, electric_windows, power_steering, alloy_wheels, rear_view, digital_radio, keyless_start, parking_assistance)
				  Values (true, true, true, true, true, true, false, true, false, true);                  
				Insert into accessories (alarm, abs_brake, air_conditioning, electric_windows, power_steering, alloy_wheels, rear_view, digital_radio, keyless_start, parking_assistance)
				  Values (true, true, false, true, true, false, false, false, false, false);
				Insert into accessories (alarm, abs_brake, air_conditioning, electric_windows, power_steering, alloy_wheels, rear_view, digital_radio, keyless_start, parking_assistance)
				  Values (true, true, false, true, true, false, false, false, false, false);
				Insert into accessories (alarm, abs_brake, air_conditioning, electric_windows, power_steering, alloy_wheels, rear_view, digital_radio, keyless_start, parking_assistance)
				  Values (true, true, true, true, true, true, false, true, false, true);
				Insert into accessories (alarm, abs_brake, air_conditioning, electric_windows, power_steering, alloy_wheels, rear_view, digital_radio, keyless_start, parking_assistance)
				  Values (true, true, true, true, true, true, true, true, true, true);
				Insert into accessories (alarm, abs_brake, air_conditioning, electric_windows, power_steering, alloy_wheels, rear_view, digital_radio, keyless_start, parking_assistance)
				  Values (true, true, true, true, true, true, true, true, true, true);
                  
create table exteriorcolor(
	color_id int auto_increment,
    color_name varchar(20) unique,
    primary key (color_id)
);

insert into exteriorcolor (color_name) values ('Champanhe');
insert into exteriorcolor (color_name) values ('Cobre');
insert into exteriorcolor (color_name) values ('Pérola');
insert into exteriorcolor (color_name) values ('Metálico');
insert into exteriorcolor (color_name) values ('Matte');
insert into exteriorcolor (color_name) values ('Rosé');
insert into exteriorcolor (color_name) values ('Roxo');
insert into exteriorcolor (color_name) values ('Turquesa');
insert into exteriorcolor (color_name) values ('Branco Pérola');
insert into exteriorcolor (color_name) values ('Prata Metálico');
insert into exteriorcolor (color_name) values ('Azul Marinho');
insert into exteriorcolor (color_name) values ('Verde Musgo');
insert into exteriorcolor (color_name) values ('Vermelho Cereja');
insert into exteriorcolor (color_name) values ('Amarelo Limão');
insert into exteriorcolor (color_name) values ('Laranja Escuro');
insert into exteriorcolor (color_name) values ('Bronze Escuro');
insert into exteriorcolor (color_name) values ('Cinza Escuro');
insert into exteriorcolor (color_name) values ('Branco Gelo');
insert into exteriorcolor (color_name) values ('Prata Escuro');
insert into exteriorcolor (color_name) values ('Azul Escuro');
insert into exteriorcolor (color_name) values ('Verde Floresta');
insert into exteriorcolor (color_name) values ('Vermelho Escuro');
insert into exteriorcolor (color_name) values ('Azul Claro');
insert into exteriorcolor (color_name) values ('Verde Claro');
insert into exteriorcolor (color_name) values ('Dourado Claro');
insert into exteriorcolor (color_name) values ('Vermelho Metálico');
insert into exteriorcolor (color_name) values ('Cinza Claro');
insert into exteriorcolor (color_name) values ('Amarelo Metálico');
insert into exteriorcolor (color_name) values ('Marrom Escuro');
insert into exteriorcolor (color_name) values ('Bege Claro');
insert into exteriorcolor (color_name) values ('Azul Claro Metálico');

create table brand(
	brand_id int auto_increment,
    brand_name varchar (15) unique,
    primary key (brand_id)
);

Insert Into brand (brand_name) value ("AUDI");
Insert Into brand (brand_name) value ("PEUGEOT");
Insert Into brand (brand_name) value ("FIAT");
Insert Into brand (brand_name) value ("FORD");
Insert Into brand (brand_name) value ("HYUNDAI");
Insert Into brand (brand_name) value ("HONDA");
Insert Into brand (brand_name) value ("JEEP");
Insert Into brand (brand_name) value ("VOLKSWAGEN");
Insert Into brand (brand_name) value ("TOYOTA");
Insert Into brand (brand_name) value ("RENAULT");

create table model(
	model_id int auto_increment,
    model_name varchar (15) unique,
    brand_id int not null,
    accessories_id int default 1,
    primary key (model_id),
    foreign key (brand_id) references brand(brand_id),
    foreign key (accessories_id) references accessories(accessories_id)
);

Insert Into model (model_name, brand_id, accessories_id) value ("A3" , 1, 2);
Insert Into model (model_name, brand_id, accessories_id) value ("A4" , 1, 2);
Insert Into model (model_name, brand_id, accessories_id) value ("Q3" , 1, 2);
Insert Into model (model_name, brand_id, accessories_id) value ("Q5" , 1, 2);
Insert Into model (model_name, brand_id, accessories_id) value ("E-TRON" , 1, 2);
Insert Into model (model_name, brand_id, accessories_id) value ("208" , 2, 4);
Insert Into model (model_name, brand_id, accessories_id) value ("2008" , 2, 3);
Insert Into model (model_name, brand_id, accessories_id) value ("3008" , 2, 3);
Insert Into model (model_name, brand_id, accessories_id) value ("FASTBACK" , 3, 2);
Insert Into model (model_name, brand_id, accessories_id) value ("PULSE" , 3, 3);
Insert Into model (model_name, brand_id, accessories_id) value ("ARGO" , 3, 4);
Insert Into model (model_name, brand_id, accessories_id) value ("CRONOS" , 3, 4);
Insert Into model (model_name, brand_id, accessories_id) value ("RANGER" , 4, 3);
Insert Into model (model_name, brand_id, accessories_id) value ("MAVERICK" , 4, 2);
Insert Into model (model_name, brand_id, accessories_id) value ("F-150" , 4, 3);
Insert Into model (model_name, brand_id, accessories_id) value ("HB20" , 5, 1);
Insert Into model (model_name, brand_id, accessories_id) value ("HB20S" , 5, 1);
Insert Into model (model_name, brand_id, accessories_id) value ("CRETA" , 5, 3);
Insert Into model (model_name, brand_id, accessories_id) value ("TUCSON" , 5, 2);
Insert Into model (model_name, brand_id, accessories_id) value ("CIVIC" , 6, 2);
Insert Into model (model_name, brand_id, accessories_id) value ("CITY" , 6, 3);
Insert Into model (model_name, brand_id, accessories_id) value ("HRV" , 6, 2);
Insert Into model (model_name, brand_id, accessories_id) value ("RENEGADE" , 7, 3);
Insert Into model (model_name, brand_id, accessories_id) value ("COMPASS" , 7, 2);
Insert Into model (model_name, brand_id, accessories_id) value ("COMMANDER" , 7, 2);
Insert Into model (model_name, brand_id, accessories_id) value ("POLO" , 8, 3);
Insert Into model (model_name, brand_id, accessories_id) value ("VIRTUS" , 8, 3);
Insert Into model (model_name, brand_id, accessories_id) value ("JETTA" , 8, 2);
Insert Into model (model_name, brand_id, accessories_id) value ("NIVUS" , 8, 3);
Insert Into model (model_name, brand_id, accessories_id) value ("GOL" , 8, 1);
Insert Into model (model_name, brand_id, accessories_id) value ("YARIS" , 9, 4);
Insert Into model (model_name, brand_id, accessories_id) value ("COROLLA" , 9, 3);
Insert Into model (model_name, brand_id, accessories_id) value ("COROLLA-CROSS" , 9, 2);
Insert Into model (model_name, brand_id, accessories_id) value ("DUSTER" , 10, 3);
Insert Into model (model_name, brand_id, accessories_id) value ("LOGAN" , 10, 4);
Insert Into model (model_name, brand_id, accessories_id) value ("KIWID" , 10, 1);

create table car(
	car_id int auto_increment,
	car_renavam varchar(12)unique,
    plate_number varchar(8) unique,
    car_year int,
	car_mileage int,
    car_engine float,
    car_price float,
    car_transmission varchar(20),
    car_drivetrain varchar(20),
	car_fuel varchar(50),
    car_notes varchar(255),
	model_id int,
    color_id int,
    accessories_id int,
    primary key (car_id),
    foreign key (model_id) references model(model_id),
    foreign key (color_id) references exteriorcolor(color_id),
    foreign key (accessories_id) references accessories(accessories_id)
);

Insert Into car (car_renavam, plate_number, car_year, car_mileage, car_engine, car_price, car_transmission, car_drivetrain, car_fuel, car_notes, model_id, color_id, accessories_id) 
		  value ('12345678912', 'GTX-1020', 2021, 0, 1.8, 81490.00, 'AUTOMATICO', 'DIANTEIRA', 'Flex', 'COMPLETO', 26, 17, 5);
Insert Into car (car_renavam, plate_number, car_year, car_mileage, car_engine, car_price, car_transmission, car_drivetrain, car_fuel, car_notes, model_id, color_id, accessories_id) 
		  value ('12345612345', 'FWU-1020', 2011, 89500, 1.0, 81490.00, 'Manual', 'DIANTEIRA', 'Etanol', 'COMPLETO', 16, 13, 6);
Insert Into car (car_renavam, plate_number, car_year, car_mileage, car_engine, car_price, car_transmission, car_drivetrain, car_fuel, car_notes, model_id, color_id, accessories_id) 
		  value ('12312312312', 'ERK-0721', 2016, 40000, 2.0, 81490.00, 'Manual', 'DIANTEIRA', 'Gasolina', 'COMPLETO', 6, 10, 7);
Insert Into car (car_renavam, plate_number, car_year, car_mileage, car_engine, car_price, car_transmission, car_drivetrain, car_fuel, car_notes, model_id, color_id,accessories_id) 
		  value ('11122233344', 'ABC-1234', 2018, 21000, 1.4, 81490.00, 'AUTOMATICO', 'DIANTEIRA', 'Gasolina', 'COMPLETO', 10, 4, 8);
Insert Into car (car_renavam, plate_number, car_year, car_mileage, car_engine, car_price, car_transmission, car_drivetrain, car_fuel, car_notes, model_id, color_id, accessories_id) 
		  value ('98765432198', 'BMW-9999', 2022, 0, 2.0, 81490.00, 'AUTOMATICO', 'DIANTEIRA', 'Híbrido', 'COMPLETO', 22, 20, 9);
Insert Into car (car_renavam, plate_number, car_year, car_mileage, car_engine, car_price, car_transmission, car_drivetrain, car_fuel, car_notes, model_id, color_id, accessories_id) 
		  value ('10203040506', 'RDN-5050', 2019, 15000, 1.8, 81490.00, 'AUTOMATICO', 'DIANTEIRA', 'Flex', 'COMPLETO', 32, 27, 10);
          
Select car_id, brand_name, model_name, plate_number, car_year, car.accessories_id From car 
																				  Inner Join model On car.model_id = model.model_id
																				  Inner Join brand On model.brand_id = brand.brand_id;
                                                              
Select model_id, model_name, brand_name, accessories_id From model Inner Join brand On model.brand_id = brand.brand_id;

UPDATE accessories SET alarm = true, abs_brake = true, air_conditioning = true, electric_windows = true, power_steering = true, alloy_wheels = true,
					   rear_view = true, digital_radio = true, keyless_start = true, parking_assistance = true WHERE accessories_id = 2;
                                                      
Select * From accessories;