
# Fault System

Fault System is a Spring Boot application designed to manage customer faults. It provides functionalities to create, update, cancel, and retrieve faults associated with customers.

## Table of Contents

- [Installation](#installation)
- [Usage](#usage)
- [API Endpoints](#api-endpoints)
- [Running Tests](#running-tests)
- [Technologies Used](#technologies-used)
- [Contributing](#contributing)
- [License](#license)

## Installation

1. Clone the repository:
    ```sh
    git clone https://github.com/adem2817/faultsystem.git
    cd faultsystem
    ```

2. Build the project using Maven:
    ```sh
    mvn clean install
    ```

3. Run the application:
    ```sh
    mvn spring-boot:run
    ```

## Usage

Once the application is running, you can interact with it using the provided API endpoints.

## API Endpoints

### Create a Fault

- **URL:** `/faults`
- **Method:** `POST`
- **Request Body:**
    ```json
    {
        "customerUUID": "e61b6fbc-9d1e-46bb-88de-16af5766f6e6",
        "faultType": "HARDWARE",
        "description": "Description1"
    }
    ```
- **Response:**
    ```json
    {
        "status": "SUCCESS"
    }
    ```

### Update a Fault

- **URL:** `/faults/{fault-uuid}`
- **Method:** `PUT`
- **Request Body:**
    ```json
    {
        "customerUUID": "e61b6fbc-9d1e-46bb-88de-16af5766f6e6",
        "faultType": "SOFTWARE",
        "description": "Description2"
    }
    ```
- **Response:**
    ```json
    {
        "status": "SUCCESS"
    }
    ```

### Cancel a Fault

- **URL:** `/faults/{fault-uuid}/cancel`
- **Method:** `POST`
- **Request Body:**
    ```json
    {
        "customerUUID": "e61b6fbc-9d1e-46bb-88de-16af5766f6e6"
    }
    ```
- **Response:**
    ```json
    {
        "status": "SUCCESS"
    }
    ```

### Mark a Fault as Successful

- **URL:** `/faults/{fault-uuid}/success`
- **Method:** `POST`
- **Request Body:**
    ```json
    {
        "customerUUID": "e61b6fbc-9d1e-46bb-88de-16af5766f6e6",
        "satisfactionScore": 5
    }
    ```
- **Response:**
    ```json
    {
        "status": "SUCCESS"
    }
    ```

### Get All Faults by Customer

- **URL:** `/faults/customer/{customer-uuid}`
- **Method:** `GET`
- **Response:**
    ```json
    [
        {
            "uuid": "31f5f92e-4d53-4874-80d9-49aaed6f3b70",
            "faultType": "HARDWARE",
            "description": "Description1",
            "cancelReason": "Reason1",
            "satisfactionScore": 3
        }
    ]
    ```

## Running Tests

To run the tests, use the following command:
```sh
mvn test
```

## Technologies Used

- Java
- Spring Boot
- Maven
- JUnit

## Contributing

Contributions are welcome! Please open an issue or submit a pull request for any improvements or bug fixes.

## License

This project is licensed under the MIT License.
