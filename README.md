# Final Project Spring 
Final project of the internet computing course. Rest API with Spring

## Project setup

### 1. Create and Run Postgres DATABASE (scrumicesi)

Before of create the database you have to update your postgres user's password to the password used in the application properties 
[Application Properties]https://github.com/DouglasPapu/ComputacionTaller4/blob/master/src/main/resources/application.properties).
```
ALTER USER your_user_name WITH PASSWORD 'password_in_application_properties';
```
Or you can change the username and password given in the application with your postgress username


Create a database called scrumicesi at pgAdmin or, at the sqlSHELL of postgres run the following commands
```
CREATE DATABASE scrumicesi;
```
```
c\ scrumicesi
```

### 2. Run the application at localhost:8084
Use user="superadmin" password="123"
