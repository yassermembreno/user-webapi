# User-WebApi

## Instalacion

Clone el repositorio.

```bash
git clone https://github.com/yassermembreno/user-webapi.git
git status
```

Requisitos:
Java SE 8+ (https://www.oracle.com/java/technologies/downloads/#java8)

Ejecutar la aplicación:

On Mac or Linux
```bash
./mvnw spring-boot:run
```
Consumir endpoints:

login: 
curl --header "Content-Type: application/json" \
  --request POST \
  --data '{
    "email": "pepito.perez@outlook.cl",
    "password": "firulais"}' \
  http://localhost:8080/api/user/login

List users:
curl -X GET -H 'Accept: application/json' -H "Authorization: Bearer $token  http://localhost:8080/api/user/users

Create user:

  curl -X POST -H 'Accept: application/json' \
   -H "Authorization: Bearer $token" --data '{"name": "Chepito Perez","email": "chepito.perez@outlook.cl", "password": "piquinyuqui","phones": [{ "number": "89765544", "cityCode": "AZ", "countryCode": "USA" }]}' \
   http://localhost:8080/api/user/register