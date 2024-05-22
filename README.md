# BankApp

A simple bank web application based on SpringBoot and React with deliberately insecure vulnerabilities.

Authors: Nelson Wang, Kaichun chen, Changhao Li

## Build Instructions
### Prerequisites:
1. Java 17 or higher
2. node version v20.10.0 or higher

### Installations:
- [MySQL Workbench](https://dev.mysql.com/downloads/workbench/)
- [IntelliJ IDEA](https://www.jetbrains.com/idea/download/#section=mac)
- [Node.js and npm](https://nodejs.org/en/download)

### Build Database
1. Set up MySQL Workbench:
   - Open MySQL Workbench and connect to local instance 3306

2. Create schema:
   - Use the SQL script provided in [`databaseSetup.sql`](https://github.com/june-rains/BankApp/blob/main/BankAppBackend/databaseSetup.sql) to create `bank` database and `user` table

### Build Project
   - Download and Open code of project in IntelliJ IDEA

#### Backend - Spring Framework：
1. Set up database account:
   -  Open `src > main > resources`
    - Open the `application.properties` file, input your own MySQL username and password

2. Start the backend:
    - Run BankApplication:  src > main > java > org.swe266.bankappbackend

#### Frontend - React Framework:
1. Change directory to frontend using the following command:
    - cd frontend

2. Install app required packages:
    - npm install

3. Start the application:
    - npm start

4. A new page will be rendered:
    - http://localhost:3000/
