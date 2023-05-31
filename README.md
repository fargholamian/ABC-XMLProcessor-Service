# ABC-XMLProcessor-Service

This Spring Boot service is designed to process XML files, store the data in a MySQL database, and provide REST APIs for fetching the data. It listens to a Kafka topic to receive events sent by the [ABC-XMLUploader-Service](https://github.com/fargholamian/ABC-XMLUploader-Service). After receiving an event, it retrieves the corresponding file from the file system, processes it, and stores the product objects into the database.

## Features

- XML file processing: The service processes XML files uploaded by users. Each XML file contains multiple nested Product objects, which are stored in a normalized table in MySQL.
- Authentication mechanism: User authentication is implemented using an Interception mechanism. The `AuthenticationInterceptor` class intercepts incoming requests, extracts the JWT token from the header, calls the [ABC-Authentication-Service](https://github.com/fargholamian/ABC-Authentication-Service) (`/api/user`) to authenticate and retrieve user information, and adds the user info to the request as a request attribute.
- REST API for fetching products: At the moment, the service provides a REST API for fetching products by userId. It supports pagination with the ability to specify the offset and page size. The API also allows clients to request the result in three formats: JSON (default), XML, or CSV.
- Output format customization: The service returns the fetched products in the requested output format (JSON, XML, or CSV).


The project is implemented using Spring Boot 3.1, Spring Web, Spring Data Jps, and Spring Kafka. XML file processing is performed using the StAX API.

After startup, the service listens on port 8083. This applies to both running the service in an IDE or within a Docker container using docker-compose in the abc-orchestrator repository.

## API Endpoints

The service exposes the following REST API endpoint:

- **GET /api/product**: Retrieves a list of products for a given userId.
    - Parameters:
        - `userId` (mandatory): The ID of the user to fetch products for.
        - `offset` (optional): The page offset (default: 0).
        - `size` (optional): The page size (default: 100, maximum: 1000).
        - `format` (optional): The output format (default: JSON). Supported values: JSON, XML, CSV.
    - Response: Returns a list of products in the requested format.
    ```
    curl --location --request GET 'http://127.0.0.1:8083/api/product?userId={user_id}&offset={offset}&size={sizr}&format={format}' \
    --header 'Authorization: Bearer AUTH_TOKEN'
    ```

## Project Structure

The project follows a standard Spring Boot project structure:

- `configuration`: Contains the application configurations.
    - `AppConfig`: Reads the application configurations from `application.yaml` and provides them to other classes.
    - `AuthenticationInterceptor`: Implements the `HandlerInterceptor` interface to intercept incoming requests. It uses the `AuthenticationService` to fetch user information details and validate the token. It also checks the user role (currently only `ROLE_USER` is allowed).
    - `WebMvcConfig`: Implements the `WebMvcConfigurer` interface and adds the interceptor to the `InterceptorRegistry`.
- `controller`: Contains the controller classes.
    - `ProductController`: Handles all requests related to products (currently only provides the ability to get products by userId). It fetches the data from the `Product` repository and converts it to the requested output format (CSV, XML, or JSON).
    - `GenericExceptionHandler`: A generic exception handler that overrides the `ResponseEntity` for `RuntimeExceptions`.
- `enum`: Contains enumerations used in the project.
- `model`: Contains all the POJO (Plain Old Java Object) classes.
    - `ApiError`: Represents a generic exception response.
    - `User`: Contains the user model.
    - `Event`: Represents the contract for receiving events from `Kafka` the `xml-uploader` service.
    - Other models: Related to the `Product` model and its sub-models.
- `repo`: Contains the repository models for `Product` and `Event`, which extend the `JpaRepository` interface.

- `services`: Contains the service classes.
    - `AuthenticationService`: A service that uses `RestTemplate` to call the [ABC-Authentication-Service](https://github.com/fargholamian/ABC-Authentication-Service) (`/api/user`) and retrieve user information.
    - `EventProducer`: A service that listens to the Kafka topic `file-received-event`, retrieves the events, and calls the appropriate event handler. It also stores the event in the database.
    - `XmlUploadEventHandler`: Responsible for processing XML files. It uses the StAX API libraries to process the file in a memory-efficient manner.
- `utils`: Contains utility classes.
    - `CSVWriter`: A generic utility for converting an object to a CSV file using the object's properties.


## Prerequisites

To run this project, ensure that you have the following software installed:

- Java Development Kit (JDK) 17 or higher
- Gradle
- Kafka
- [ABC-Authentication-Service](https://github.com/fargholamian/ABC-Authentication-Service)

## Getting Started

Please see the instruction in [abc-orchestrator](https://github.com/fargholamian/abc-orchestrator) which will help you to run all the services together.

Anyway, If you want to run this project independently, follow the steps below to set up and run the project:


1. Ensure that you have Java and Gradle installed.
2. Clone the repository and navigate to the project's root directory.
3. Customize the application configurations in `application.yaml` to match your environment (e.g., database connection details, Kafka configuration, uploaded files directory).
4. Build and Run the application:
   ```
   ./gradlew bootRun
   ```

The service will start up and listen on port 8083. You can access the API endpoints described above to interact with the service.
