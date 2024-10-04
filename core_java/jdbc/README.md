# Introduction
A Java Stock Quote command-line application that allows users to simulate a personal Stock Wallet. An external 
API is integrated into the application to fetch real-time stock data, ensuring up-to-date information.
Technologies utilized in this project include JDBC for database interactions, PostgreSQL for the database 
management system, Maven for application dependency management and project building, additionally Docker
is used for containerization and distribution. The application covers unit and integration testing through use of 
JUnit and Mockito.

# Implementaiton
## ER Diagram
![ER diagram](./src/main/resources/stock_quote.png)

## Design Patterns
This project utilizes Data Access Object (DAO) and Repository design patterns. The DAO pattern focuses on encapsulating 
the data access logic and separating it from the business logic. It provides a way to perform Create, Read, Update,
Delete (CRUD) operations on data sources with external storage, such as databases, REST APIs, file systems, etc.
Both Quote and Position entities implement their own DAO classes which leverage a common interface and contain 
corresponding methods to facilitate the CRUD operations. This approach ensures a clean, modular structure, making the 
application more maintainable and easier to extend with additional features in the future. Additionally, the repository 
design pattern complements the DAO approach by abstracting the data access layer even further. By implementing 
single-table access per class, each repository manages its specific entity, facilitating clear and organized data handling.

# Test
How you test your app against the database? (e.g. database setup, test data set up, query result)
The application was thoroughly tested against the database using JUnit and Mockito. The database was set up using 
PostgreSQL and ran in a docker container. A `stock_quote` database was created with two tables, namely `quote` and 
`position`, the quote table holds values corresponding to stock data received from the AlphaVantage RapidAPI and the position
table holds information about the users number of shares bought. Through unit testing, test data was mocked to allow for
isolated tests where dependencies could be negligible. Through integrated testing, test data pulled from the external API
and tested against expected values, this ensured end to end functionality across data and service layers. The application
additionally utilizes log4j to create an info log file that encapsulate the flow of the application as well as an error log
file to aid in any required debugging.