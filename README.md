# ReceiptProcessor
# Overview
This application processes receipts and calculates points based on specific criteria. It provides RESTful endpoints for receipt processing and points retrieval.

Note: We can use Lombok for the model class to reduce boilerplate code, which is represented by comments at the end of the Item and Receipt class.

# Technologies Used:

Java 17: The programming language used for development.

Spring Boot: The framework used for building the application.

Maven: Used for project management and build automation.

Docker: Used for containerization.

JUnit 5: Used for writing tests.


# Project Structure

The project is structured as follows:


src/main/java/com/example/receiptprocessor: Contains the main Java source code.

src/main/resources: Contains application properties and other resources.

target: Contains compiled bytecode and packaged JAR files.

Dockerfile: Specifies the instructions to build a Docker image.


# How to Run the Application

Prerequisites:

Java 17 installed

Maven installed

Docker installed

# Steps:

1. Clone the GitHub Repository:

```
git clone https://github.com/deekshaprabhakar199/ReceiptProcessor.git

cd FetchRewardsApplication
```

2. Build the JAR file:

```
mvn clean package
```

2. Build the Docker image:

```
docker build -t receipt-processor-app .
```

3. Run the Docker container:

```
docker run -p 8080:8080 receipt-processor-app
```

The application will be accessible at http://localhost:8080.

# Usage

1. Processing a Receipt

To process a receipt, send a POST request to /receipts/process with a JSON payload containing receipt details. 

Example:

```
curl -X POST -H "Content-Type: application/json" -d '{
  "retailer": "Target",
  "purchaseDate": "2022-01-01",
  "purchaseTime": "13:01",
  "items": [
    {
      "shortDescription": "Mountain Dew 12PK",
      "price": "6.49"
    },{
      "shortDescription": "Emils Cheese Pizza",
      "price": "12.25"
    },{
      "shortDescription": "Knorr Creamy Chicken",
      "price": "1.26"
    },{
      "shortDescription": "Doritos Nacho Cheese",
      "price": "3.35"
    },{
      "shortDescription": "   Klarbrunn 12-PK 12 FL OZ  ",
      "price": "12.00"
    }
  ],
  "total": "35.35"
}' http://localhost:8080/receipts/process
```

2. Getting Points for a Receipt

To get points for a receipt, send a GET request to /receipts/{id}/points where {id} is the receipt ID generated during processing. 

Example:

```
curl http://localhost:8080/receipts/{id}/points
```


# Writing Tests

Tests are written using JUnit 5. The ReceiptControllerTest class demonstrates how to write unit tests for the ReceiptController class. The tests cover various scenarios, including successful receipt processing, handling exceptions, and checking response codes.

# Why DTO?

DTOs (Data Transfer Objects) are used to represent data exchanged between different layers of the application. They help in decoupling the internal representation of data from the external representation, making the application more maintainable and scalable. DTOs are particularly useful in API development, allowing for clear communication between the client and server.

