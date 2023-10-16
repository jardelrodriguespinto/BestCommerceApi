# Best Commerce API - Test Project for WSWork

Welcome to the Best Commerce API test project for WSWork. This is a Spring Boot application that provides a simple API for managing commerce-related data.

## Documentation

You can access the API documentation via Swagger at the following URL:
- [Swagger Documentation](http://localhost:8083/swagger-ui/index.html#)

## Getting Started

To get started with the application, follow these steps:

1. Clone the repository to your local machine:
   ```shell
   git clone https://github.com/jardelrodriguespinto/BestCommerceApi.git

2 - Navigate to the project directory:

cd BestCommerceApi

3- Use Docker Compose to start the application:

docker-compose up

4 This will create a containerized environment for the application, including a PostgreSQL database.
The application will run on port 8083, and you can access the API documentation via Swagger as mentioned above.

5 - Database Migrations
The application's database schema is created using database migrations, which are located in the resources/db/migrations/v1 directory. These migrations define the database schema and tables used by the API.

6 - Local Development
If you need to test the API endpoints locally, you can modify the application configuration in the src/main/resources/application.properties file. Adjust the database connection properties to point to your local database.

Example Configuration
properties

spring.datasource.url=jdbc:postgresql://localhost:5432/commerce-db
spring.datasource.username=admin
spring.datasource.password=admin
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.show-sql=true
logging.level.org.hibernate.SQL=DEBUG
spring.flyway.baselineOnMigrate=true
spring.flyway.url=jdbc:postgresql://localhost:5432/commerce-db
spring.flyway.user=admin
spring.flyway.password=admin
spring.flyway.locations=classpath:db/migration/v1
server.port=8083
api.security.token.secret=${JWT_SECRET:my-secret-key}
Feel free to customize this configuration to meet your local development needs.

Enjoy using the Best Commerce API! If you have any questions or encounter any issues, please don't hesitate to reach out.