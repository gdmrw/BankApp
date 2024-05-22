# BankApp

## Project Overview

BankApp is an online banking system that allows customers to register and open accounts, log in to check their balance, and make deposits and withdrawals. The project consists of a React-based frontend and a Spring Boot-based backend.

## Features

### User Registration

- Users can register and open an account with a valid username, password, and initial balance.
- Usernames and passwords must conform to specified character sets and length requirements.
- Initial balance must be a valid positive number, formatted to two decimal places.

### User Login

- Users can log in using their registered username and password.

### Deposit

- Logged-in users can make deposits.
- Deposit amounts must be valid positive numbers, formatted to two decimal places.

### Withdrawal

- Logged-in users can make withdrawals.
- Withdrawal amounts must be valid positive numbers, formatted to two decimal places.
- Users cannot withdraw more than their current account balance; if attempted, the withdrawal will fail.

### Balance Inquiry

- Logged-in users can check their current account balance.


## Error Handling

- All invalid inputs will return the string "invalid_input" and display it on the screen.

## Project Structure & How to run

### Frontend (React)

- A user interface created using React that provides registration, login, deposit, withdrawal, and balance inquiry features.
- Ensures communication with the backend API.
```bash
cd frontend
npm install
npm start
```

### Backend (Spring Boot)

- A backend service implemented using Spring Boot that handles user requests, input validation, deposit and withdrawal operations, and maintains user data and account balances.
```bash
cd BankAppBackend
./mvnw spring-boot:run
```
or run BankAppBackendApplication

### Database (MySQL)
- Uses MySQL for storing user information and account data.
- Update the database configuration(username and password) in `application.properties`.
