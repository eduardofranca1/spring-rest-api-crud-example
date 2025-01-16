# Spring REST API CRUD Example

- [Technologies](#technologies)
- [Features](#features)
- [How to run the project](#how-to-run-the-project)
- [Run docker](#run-docker)

## Technologies

- [Spring Boot](https://spring.io/projects/spring-boot)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [Docker](https://www.docker.com/)
- [H2 Database](https://www.h2database.com/html/main.html)
- For Testing
  - [AssertJ](https://docs.spring.io/spring-framework/reference/testing/mockmvc/assertj.html)
  - [JUnit](https://junit.org/junit5/)
  - [Mockito](https://site.mockito.org/)
  - [Spring Framework Test](https://docs.spring.io/spring-framework/reference/testing.html)

## Features

- User CRUD
- DTO (Data Transfer Object)
- Docker
- Tests
  - Controller
  - Service
  - Repository

## How to run the project

Follow this steps to run the project

1. **mvn clean install**

   - to build the project and download all maven dependencies

2. **mvn spring-boot:run**

   - to start the server

3. **mvn test**

   - to run the tests

## Run Docker

To run docker, execute the commands below:

1. Execute the command to compile the project and generate .jar file

   - **mvn clean package**

2. Use the Docker command to create a Docker image

   - **docker build -t [name] .**

     - t: Name and tag for the docker image
     - . : The context for the build process

3. Run the container

   - **docker run -p 8080 'your-application-name'**

4. Test the application

   - Access the application using the browser
   - http://localhost:8080
