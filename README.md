# ReceiptProcessor
Overview
This is a Spring Boot application that processes receipts, calculates points, and provides information about the points associated with a receipt.

Technologies Used
Java 17: The programming language used for development.
Spring Boot: The framework used for building the application.
Maven: Used for project management and build automation.
Docker: Used for containerization.
JUnit 5: Used for writing tests.
Project Structure
The project is structured as follows:

lua
Copy code
/receipt-processor
|-- src
|   |-- main
|       |-- java/com/example/receiptprocessor
|           |-- controller
|           |-- dto
|           |-- exception
|           |-- service
|       |-- resources
|       |-- ...
|-- target
|-- Dockerfile
|-- ...
src/main/java/com/example/receiptprocessor: Contains the main Java source code.
src/main/resources: Contains application properties and other resources.
target: Contains compiled bytecode and packaged JAR files.
Dockerfile: Specifies the instructions to build a Docker image.
How to Run the Application
Prerequisites
Java 17 installed
Maven installed
Docker installed
Steps
Build the JAR file:

bash
Copy code
mvn clean package
Build the Docker image:

bash
Copy code
docker build -t receipt-processor-app .
Run the Docker container:

bash
Copy code
docker run -p 8080:8080 receipt-processor-app
The application will be accessible at http://localhost:8080.

Usage
Processing a Receipt
To process a receipt, send a POST request to /receipts/process with a JSON payload containing receipt details. Example:

bash
Copy code
curl -X POST -H "Content-Type: application/json" -d @example-receipt.json http://localhost:8080/receipts/process
Getting Points for a Receipt
To get points for a receipt, send a GET request to /receipts/{id}/points where {id} is the receipt ID generated during processing. Example:

bash
Copy code
curl http://localhost:8080/receipts/{id}/points
Writing Tests
Tests are written using JUnit 5. The ReceiptControllerTest class demonstrates how to write unit tests for the ReceiptController class. The tests cover various scenarios, including successful receipt processing, handling exceptions, and checking response codes.

Why DTO?
DTOs (Data Transfer Objects) are used to represent data exchanged between different layers of the application. They help in decoupling the internal representation of data from the external representation, making the application more maintainable and scalable. DTOs are particularly useful in API development, allowing for clear communication between the client and server.

