create table if not exists "USER"( 
    id uuid primary key not null, 
    name varchar(100) not null, 
    email varchar(100) not null, 
    password varchar(255) not null, 
    last_login timestamp not null, 
    token varchar(255), 
    is_active boolean, 
    created_on timestamp not null, 
    modified_on timestamp not null
);

create table if not exists PHONE (
    id uuid primary key not null,
    user_id uuid not null,
    number varchar(10) not null,
    city_code varchar(10) not null,
    country_code varchar(10) not null    
);

alter table  PHONE
ADD CONSTRAINT fk_phone_user FOREIGN KEY (user_id) references "USER";
   
create table if not exists ROLE(
    id bigint primary key auto_increment,
    role_name varchar(100) not null,
    description varchar(255) 
);

create table if not exists USER_ROLE(
    user_id uuid not null,
    role_id bigint not null,
    primary key (user_id, role_id)
);

alter table USER_ROlE
add constraint fk_user_user_role foreign key (user_id) references "USER";

alter table USER_ROlE
add constraint fk_role_user_role foreign key (role_id) references ROLE;