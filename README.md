# Emerchant Pay Task

using Springboot, Hibernate, React, PostgresSQL


## Usage
make sure postgresSQL server is up and running, SpringBoot Application will connect and automatically create the setup data, use the frontend Application to display or postman to cover all the test cases.

# Backend
is divided into *util*, *controller*, *service*,*repository*, *model* and *DTO* packages. The starting point is BackendApplication. SecurityConfig is needed config. to enable security layer over the app. CorsConfig to enable testing backend and frontend applications from same localhost server.

# Frontend
superUser is logged in once the application is loaded, it is used to do the required interactions and hitting Merchant, Transaction, User APIs.

### Packages from Backend.

## SetupDataLoader

to create "ROLE_ADMIN", "ROLE_USER" and SuperUser once the application start first time.

## Controller
for defining API endpoints. all the controllers are using service layer for the logic behind each API endpoint. 
 - UserController for managing security layer and import admins as well.
 - TransactionController for managing the required endpoints related to transaction.
 - MerchantController for managing the required endpoints related to merchant.

## Service
for all the business logic. each service has interface and custom implementation class for the current requirements of the task, so later o we can "without breaking the contract with the initial interface" add more customized implementations depend on the required. 
 
 - User Service for managing the required endpoints related to user.
 - Transaction Service for managing the required endpoints related to transaction.
 - Merchant Service for managing the required endpoints related to merchant.



## ArchiveManager

to archive transactions older than one hour

## Factory classes
MerchantFactory to create Merchants, UserFactory to create Users.
Both UserFactory, MerchantFactory are using opencsv library to read and parse CSV files then to create the desired objects.

## Repository
use to communicate with database and save, update, delete or get the required data. all the Repositories are extending JpaRepository for ready easy-forward implementation and already tested.

## DTO
Data Transfer Objects for Login and Signup with Lombok annotations for easy and short validated implementation.

## Model
Transaction model use TransactionType, TransactionStatus enums and represent the data model for transactions.
also this package contains Merchant, Role, User data models.