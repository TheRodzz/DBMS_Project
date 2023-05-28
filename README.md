# OTT Platform

The OTT Platform is a Java-based application that allows users to register and log in as end users or admin users. It provides functionality for user registration, login, and user-specific operations based on their role.

## Requirements

To run the OTT Platform application, you need the following:

- Java Development Kit (JDK) 8 or higher installed on your system.
- MySQL database server.
- MySQL Connector/J JDBC driver.

## Getting Started

1. Clone the repository to your local machine:

```
git clone https://github.com/TheRodzz/DBMS_Project
```

2. Navigate to the project directory:

```
cd OTT-Platform-master
```

3. Set up the database:

- Create a new database in MySQL using the following command:
```
CREATE DATABASE ottplatform;
```

- Execute the SQL script `tables.sql` located in the `src` directory to create the necessary tables:
```
SOURCE <path-to-project-folder>/DBMS_Project/src/ott.sql;
```
- Optionally, if you want to test the project with some sample data, execute the `sample_data.sql` script:
```
SOURCE <path-to-project-folder>/DBMS_Project/src/sample_data.sql;
```
4. Update database login credentials
- Open the `DBConfiguration.java` file in your preferred IDE or text editor.
- Update the `USERNAME` and `PASSWORD` strings according to your system.

5. Build the project:

```
javac -d bin -cp lib/* src/*.java
```

6. Run the application:

```
java -cp bin:lib/* Main
```

## Project Structure

The project structure is as follows:

```
ott-platform/
├─ src/
|  ├─ tables.sql
|  ├─ sample_data.sql
│  ├─ DBConnection.java
│  ├─ UserManager.java
│  ├─ Main.java
│  └─ ...
├─ lib/
│  └─ mysql-connector-java.jar
└─ README.md
```

- `src/`: Contains the source code files.
- `lib/`: Contains the MySQL Connector/J JDBC driver JAR file.
- `tables.sql`: SQL script to create the necessary database tables.
- `sample_data.sql`: SQL script to populate database with sample data.
- `README.md`: Project documentation.

## Usage

1. Registration:
   - Run the application and select option 1 to register a new account.
   - Choose whether to register as an end user or an admin user.
   - Enter the required information, such as full name, username, and password.
   - Upon successful registration, you can proceed to log in.

2. Login:
   - Run the application and select option 2 to log in.
   - Enter your username and password.
   - If the credentials are valid, you will be logged in as an end user or an admin user, depending on your role.

3. End User Operations:
   - End users can perform specific operations based on their role, such as viewing available content or managing their profile.

4. Admin User Operations:
   - Admin users have additional privileges to manage the platform, such as adding new content or managing user accounts.

5. Exiting:
   - To exit the application, select option 0 at the main menu.

## Dependencies

- MySQL Connector/J: The project requires the MySQL Connector/J JDBC driver to connect to the MySQL database. The driver JAR file should be placed in the `lib` directory.

## Contributing

Contributions to the OTT Platform project are welcome. If you encounter any issues or have suggestions for improvement, please create an issue or submit a pull request.

## License

The OTT Platform project is licensed under the [MIT License](LICENSE). You are free to use, modify, and distribute the code as permitted by the license.
