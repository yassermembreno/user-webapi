insert into ROLE(role_name, description, created_on, modified_on) values('ADMIN', 'User with administration profile', current_timestamp, current_timestamp), ('EXTERNAL', 'User with common access profile' , current_timestamp, current_timestamp);

insert into User(id, name, email, password, last_login, token, is_active, created_on, modified_on) values ('c0a80104-7faf-1cef-817f-af51da8a0001','Pepito Perez', 'pepito.perez@outlook.cl','$2y$10$6rtGzNMjOjPkUS08TvRa0.sWqvTZvfvvguIbdgdRcZb3wGhxVpP0.',current_timestamp, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwZXBpdG8ucGVyZXpAb3V0bG9vay5jbCIsIk9IRXdRWGczYVZwM1JtdzJNVWxUU1ZGM1ZtWnBOakpOUVRKalNFaFpNWEJ4TTNRPSI6W3siYXV0aG9yaXR5IjoiQURNSU4ifV0sImlhdCI6MTY0NzkxNjI4NywiZXhwIjoxNjQ5NjQ0Mjg3fQ.cp4GTZ9ryyD1CY8ZJUrgqufxEVi26ZntGidEAz9zVSg',true,current_timestamp, current_timestamp);

insert into PHONE(user_id, number, city_code, country_code, created_on, modified_on) values ('c0a80104-7faf-1cef-817f-af51da8a0001', '77665544','LA','USA', current_timestamp, current_timestamp);
insert into USER_ROLE(user_id, role_id) values ('c0a80104-7faf-1cef-817f-af51da8a0001', 1)