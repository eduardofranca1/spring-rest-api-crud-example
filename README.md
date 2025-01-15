# Spring REST API CRUD Example

- [Features](#features)
- [How to execute](#how-to-execute)
- [Run docker](#run-docker)

### Features

- User CRUD
- Docker
- Tests
  - Controller
  - Service
  - Repository

### How to execute

- **mvn spring-boot:run** (to start the server)
- **mvn test** (to run the tests)

### Run Docker

- To run docker, execute the commands below:

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
